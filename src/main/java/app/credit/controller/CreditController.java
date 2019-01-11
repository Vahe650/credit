package app.credit.controller;


import app.credit.dto.UserSumDto;
import app.credit.model.Credit;
import app.credit.model.CreditType;
import app.credit.model.User;
import app.credit.repository.CreditRepository;
import app.credit.repository.UserRepository;
import app.credit.service.CreditService;
import lombok.AllArgsConstructor;
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
import java.util.Optional;

@Controller
@AllArgsConstructor
public class CreditController {

//    private JmsConsumer jmsConsumer;
//    private JmsProducer producer;

    private CreditRepository creditRepository;
    private UserRepository userRepository;
    private CreditService creditService;


    @RequestMapping(value = "/credit")
    public String add(ModelMap map, @RequestParam(value = "message", required = false) String message,
                      @RequestParam(name = "id", required = false) int userId) throws JMSException {
        Optional<User> one = userRepository.findById(userId);
        if (one.isPresent()) {


            List<Credit> byUserId = creditRepository.findAllByUser(one.get());
//        List<CreditDto> creditDtos = creditRepository.getCreditDtos(one.get());
//        for (CreditDto creditDto : creditDtos) {
//            producer.send(creditDto, creditDto.getUserName());
//            jmsConsumer.sending("credit with '" + creditDto.getId() + "' is exist", creditDto.getUserName());
//        }
            final Credit credit = creditRepository.findTop1ByUserOrderByIdDesc(one.get());
            if (credit != null) {
                if (StringUtils.isEmpty(credit.getArmDate())) {
                    String presentDate = creditService.getDates(credit.getDate());
                    credit.setArmDate(presentDate);
                    creditRepository.save(credit);
                }
            }
            final List<Credit> news = creditRepository.newCredits(one.get());
            final List<Credit> ends = creditRepository.endCredits(one.get());
            map.addAttribute("message", message != null ? message : "");
            map.addAttribute("creditor", new Credit());
            map.addAttribute("user", one.get());
            map.addAttribute("byUserId", byUserId);
            map.addAttribute("news", news);
            map.addAttribute("ends", ends);
            map.addAttribute("userSum", creditRepository.userSum(one.get()));
            return "details";
        }
        return "redirect:/error";

    }

    @RequestMapping(value = "/addCredit")
    public String cred(@Valid @ModelAttribute(name = "creditor") Credit credit, BindingResult result,
                       @RequestParam(name = "userId", required = false) int userId) {
        StringBuilder sb = new StringBuilder();
        if (result.hasErrors()) {
            for (ObjectError objectError : result.getAllErrors()) {
                sb.append(objectError.getDefaultMessage());
                if (objectError.toString().contains(NumberFormatException.class.getName())) {
                    sb = new StringBuilder("shat mec tiv es gre");
                }
            }
            return "redirect:/credit?id=" + userId + "&message=" + sb.toString();
        }
        credit.setType(CreditType.NEW);
        final Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {

            credit.setUser(user.get());
            creditRepository.save(credit);
            return "redirect:/credit" + "?id=" + credit.getUser().getId();
        }
        return "redirect:/error";
    }

    @RequestMapping(value = "/changeType")
    public String type(@RequestParam("id") int id) {
        Optional<Credit> one = creditRepository.findById(id);
        if (one.isPresent()) {
            one.get().setType(CreditType.END);
            creditRepository.save(one.get());
            return "redirect:/credit?id=" + one.get().getUser().getId();
        }
        return "redirect:/error";
    }

    @RequestMapping(value = "/deletePrice")
    public String delete(@RequestParam("id") int id) {
        Optional<Credit> one = creditRepository.findById(id);
        if (one.isPresent()) {
            creditRepository.delete(one.get());
            return "redirect:/credit?id=" + one.get().getUser().getId();
        }
        return "redirect:/error";
    }


    @RequestMapping(value = "/searchByDate")
    public String date(ModelMap map, @RequestParam("date") String date) {
        List<Credit> allByDate = creditRepository.findAllByDate(date);
        if (allByDate.isEmpty()) {
            map.addAttribute("mess", creditService.getDates(date) + " in partq chi exel");
        } else {
            map.addAttribute("allByDate", allByDate);
            map.addAttribute("s", creditRepository.sum());
        }
        return "result";
    }





    @RequestMapping(value = "/change")
    public String change(ModelMap map, @RequestParam("id") int id) {
        Optional<Credit> one = creditRepository.findById(id);
        if (one.isPresent()) {
            map.addAttribute("creditor", one.get());
            return "changePrice";
        }
        return "redirect:/error";
    }

    @RequestMapping(value = "/updatePrice")
    public String updatePrice(@Valid @ModelAttribute(name = "creditor") Credit credit, BindingResult result, @RequestParam(name = "id", required = false) int id) {
        Optional<Credit> one = creditRepository.findById(credit.getId());
        if (one.isPresent()) {
            StringBuilder sb = new StringBuilder();
            if (result.hasErrors()) {
                for (ObjectError objectError : result.getAllErrors()) {
                    sb.append(objectError.getDefaultMessage());
                    if (objectError.toString().contains(NumberFormatException.class.getName())) {
                        sb = new StringBuilder("shat mec tiv es gre");
                    }
                }
                return "redirect:/credit?id=" + one.get().getUser().getId() + "&message=" + sb.toString();
            }
            one.get().setValue(credit.getValue());
            if (!credit.getDate().equals("")) {
                one.get().setArmDate(creditService.getDates(credit.getDate()));
            }
            one.get().setId(id);
            creditRepository.save(one.get());
            return "redirect:/credit?id=" + one.get().getUser().getId();
        }
        return "redirect:/error";

    }

}
