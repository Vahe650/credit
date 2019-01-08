package app.credit.controller;

import app.credit.dto.CreditDto;
import app.credit.dto.UserSumDto;
import app.credit.jms.JmsConsumer;
import app.credit.jms.JmsProducer;
import app.credit.model.Credit;
import app.credit.model.CreditType;
import app.credit.model.User;
import app.credit.repository.CreditRepository;
import app.credit.repository.UserRepository;
import app.credit.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.jms.JMSException;
import javax.validation.Valid;
import java.util.List;

@Controller

public class CreditController {
    @Autowired
    private JmsConsumer jmsConsumer;
    @Autowired
    private CreditRepository creditRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CreditService creditService;
    @Autowired
    private JmsProducer producer;


    @RequestMapping(value = "/credit")
    public String add(ModelMap map, @RequestParam(value = "message", required = false) String message,
                      @RequestParam(name = "id", required = false) int id) throws JMSException {
        User one = userRepository.findOne(id);
        List<Credit> byUserId = creditRepository.findAllByUser(one);
        List<CreditDto> creditDtos = creditRepository.getCreditDtos(one);
        for (CreditDto creditDto : creditDtos) {
            producer.send(creditDto, creditDto.getUserName());
            jmsConsumer.sending("credit with '"+creditDto.getId()+"' is exist" ,creditDto.getUserName());
        }
        final Credit credit = creditRepository.findTop1ByUserOrderByIdDesc(one);
        if (credit != null) {
            if (StringUtils.isEmpty(credit.getArmDate())) {
                String presentDate = creditService.getDates(credit.getDate());
                credit.setArmDate(presentDate);
                creditRepository.save(credit);
            }
        }
        map.addAttribute("message", message != null ? message : "");
        map.addAttribute("creditor", new Credit());
        map.addAttribute("user", one);
        map.addAttribute("credit", byUserId);
        map.addAttribute("userSum", creditRepository.userSum(one));
        return "details";
    }

    @RequestMapping(value = "/addCredit")
    public String cred(@Valid @ModelAttribute(name = "creditor") Credit credit, BindingResult result) {
        StringBuilder sb = new StringBuilder();
        if (result.hasErrors()) {
            for (ObjectError objectError : result.getAllErrors()) {
                sb.append(objectError.getDefaultMessage()).append("<br>");
            }
            return "redirect:/credit?id=" + credit.getUser().getId() + "&message=" + sb.toString();
        }
        credit.setType(CreditType.NEW);
        creditRepository.save(credit);
        return "redirect:/credit" + "?id=" + credit.getUser().getId();
    }

    @RequestMapping(value = "/changeType")
    public String type(@RequestParam("id") int id) {
        Credit one = creditRepository.findOne(id);
        one.setType(CreditType.END);
        creditRepository.save(one);
        return "redirect:/credit?id=" + one.getUser().getId();
    }

    @RequestMapping(value = "/deletePrice")
    public String delete(@RequestParam("id") int id) {
        Credit one = creditRepository.findOne(id);
        creditRepository.delete(id);
        return "redirect:/credit?id=" + one.getUser().getId();

    }

    @RequestMapping(value = "/searchByDate")
    public String date(ModelMap map, @RequestParam("date") String date) {
        List<Credit> allByDate = creditRepository.findAllByDate(date);
        if (allByDate.isEmpty()) {
            map.addAttribute("mess", creditService.getDates(date) + " in partq chi exel");
        } else {
            map.addAttribute("allByDate", allByDate);
        }
        return "result";
    }

    @RequestMapping(value = "/allByMax")
    public String allByMax(ModelMap modelMap) {
//        modelMap.addAttribute("allByMax", creditRepository.allByMaxPrice());
//        modelMap.addAttribute("allUsersByMax", creditRepository.allUsersByMaxPrice());
        modelMap.addAttribute("userSumDto", creditRepository.createUserSum());
        return "result";
    }

    @RequestMapping(value = "/searchDtoByName")
    public String searchDto(ModelMap modelMap, @RequestParam("name") String name) {
        List<UserSumDto> userList = creditRepository.searchUserSDto(name.trim());
        if (userList.isEmpty()) {
            modelMap.addAttribute("message", name + "n  partq chuni");
        } else {
            modelMap.addAttribute("allDtos", userList);
        }
        return "result";

    }

    @RequestMapping(value = "/change")
    public String change(ModelMap map, @RequestParam("id") int id) {
        Credit one = creditRepository.findOne(id);
        map.addAttribute("creditor", one);
        return "changePrice";
    }

    @RequestMapping(value = "/updatePrice")
    public String updatePrice(@Valid @ModelAttribute(name = "creditor") Credit credit, BindingResult result) {
        Credit one = creditRepository.findOne(credit.getId());
        StringBuilder sb = new StringBuilder();
        if (result.hasErrors()) {
            for (ObjectError objectError : result.getAllErrors()) {
                sb.append(objectError.getDefaultMessage()).append("<br>");
            }
            return "redirect:/credit?id=" + one.getUser().getId() + "&message=" + sb.toString();
        }
        one.setValue(credit.getValue());
        if (!credit.getDate().equals("")) {
            one.setArmDate(creditService.getDates(credit.getDate()));
        }
        creditRepository.save(one);
        return "redirect:/credit?id=" + one.getUser().getId();

    }
}
