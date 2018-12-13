package app.credit.controller;

import app.credit.model.Credit;
import app.credit.model.CreditType;
import app.credit.model.User;
import app.credit.repository.CreditRepository;
import app.credit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.validation.Valid;
import java.util.List;

@Controller

public class CreditController {
    @Autowired
    private CreditRepository creditRepository;
    @Autowired
    private UserRepository userRepository;


    @RequestMapping(value = "/credit")
    public String add(ModelMap map, @RequestParam(value = "message", required = false) String message,
                      @RequestParam(name = "id", required = false) int id) {
        User one = userRepository.findOne(id);
        List<Credit> byUserId = creditRepository.findAllByUser(one);
        map.addAttribute("message", message != null ? message : "");
        map.addAttribute("creditor", new Credit());
        map.addAttribute("user",one);
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
            map.addAttribute("mess", date.substring(5, 10) + " amsatvin partq chi exel");
        } else {
            map.addAttribute("allByDate", allByDate);
        }
        return "result";
    }

    @RequestMapping(value = "/allByMax")
    public String allByMax(ModelMap modelMap) {
        modelMap.addAttribute("allByMax", creditRepository.allByMaxPrice());
        modelMap.addAttribute("allUsersByMax", creditRepository.allUsersByMaxPrice());
        return "result";
    }

    @RequestMapping(value = "/change")
    public String change(ModelMap map, @RequestParam("id") int id) {
        Credit one = creditRepository.findOne(id);
        map.addAttribute("credit",one);
        return "changePrice";
    }

    @RequestMapping(value = "/updatePrice")
    public String updatePrice(@ModelAttribute(name = "credit") Credit credit){
        Credit one = creditRepository.findOne(credit.getId());
        one.setValue(credit.getValue());
        if (!credit.getDate().equals("")){
            one.setDate(credit.getDate());
        }
        creditRepository.save(one);
        return "redirect:/credit?id="+one.getUser().getId();

    }
}
