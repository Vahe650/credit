package app.credit.controller;

import app.credit.model.CreditType;
import app.credit.model.Creditor;
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

public class CreditorController {
    @Autowired
    private CreditRepository creditRepository;
    @Autowired
    private UserRepository userRepository;


    @RequestMapping(value = "/credit")
    public String add(ModelMap map, @RequestParam(value = "message", required = false) String message,
                      @RequestParam(name = "id", required = false) int id) {
        User one = userRepository.findOne(id);
        List<Creditor> byUserId = creditRepository.findAllByUser(one);
        map.addAttribute("message", message != null ? message : "");
        map.addAttribute("creditor", new Creditor());
        map.addAttribute("user",one);
        map.addAttribute("credit", byUserId);
        map.addAttribute("userSum", creditRepository.userSum(one));
        return "details";
    }

    @RequestMapping(value = "/addCredit")
    public String cred(@Valid @ModelAttribute(name = "creditor") Creditor creditor, BindingResult result) {
        StringBuilder sb = new StringBuilder();
        if (result.hasErrors()) {
            for (ObjectError objectError : result.getAllErrors()) {
                sb.append(objectError.getDefaultMessage()).append("<br>");
            }
            return "redirect:/credit?id=" + creditor.getUser().getId() + "&message=" + sb.toString();
        }
        creditor.setType(CreditType.NEW);
        creditRepository.save(creditor);
        return "redirect:/credit" + "?id=" + creditor.getUser().getId();
    }

    @RequestMapping(value = "/changeType")
    public String type(@RequestParam("id") int id) {
        Creditor one = creditRepository.findOne(id);
        one.setType(CreditType.END);
        creditRepository.save(one);
        return "redirect:/credit" + "?id=" + one.getUser().getId();
    }

    @RequestMapping(value = "/deletePrice")
    public String delete(@RequestParam("id") int id) {
        Creditor one = creditRepository.findOne(id);
        creditRepository.delete(id);
        return "redirect:/credit" + "?id=" + one.getUser().getId();

    }

    @RequestMapping(value = "/searchByDate")
    public String date(ModelMap map, @RequestParam("date") String date) {
        List<Creditor> allByDate = creditRepository.findAllByDate(date);
        if (allByDate.isEmpty()) {
            map.addAttribute("mess", date.substring(5, 10) + " amsatvin partq chi exel");
        } else {
            map.addAttribute("allByDate", allByDate);
        }
        return "result";
    }

    @RequestMapping(value = "/allByMax")
    public String allByMax(ModelMap modelMap) {
        modelMap.addAttribute("allByMax", userRepository.allByMaxPrice());
        modelMap.addAttribute("allUsersByMax", creditRepository.allUsersByMaxPrice());
        return "result";
    }

    @RequestMapping(value = "/change")
    public String change(ModelMap map, @RequestParam("id") int id) {
        map.addAttribute("credit",creditRepository.findOne(id));
        return "changePrice";
    }

    @RequestMapping(value = "/updatePrice")
    public String updatePrice(@ModelAttribute(name = "credit") Creditor creditor){
        Creditor one = creditRepository.findOne(creditor.getId());
        one.setValue(creditor.getValue());
        creditRepository.save(one);
        return "redirect:/credit?id="+one.getUser().getId();

    }
}
