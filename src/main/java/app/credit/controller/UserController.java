package app.credit.controller;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller


public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CreditRepository creditRepository;

    @RequestMapping(value = "/")
    public String home(ModelMap map) {
        map.addAttribute("all", userRepository.findAllByOrderByNameAsc());
        map.addAttribute("s", creditRepository.sum());
        return "index";
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public String delete(@RequestParam("id") int id) {
        User user = userRepository.findOne(id);
        String message = user.getName() + "@   partq uni, Mi jnje!!!";
        if (!creditRepository.findHaveingPrice(user).isEmpty()) {
            return "redirect:/credit?id=" + user.getId() + "&message=" + message;
        }
        userRepository.delete(id);
        return "redirect:/";
    }


    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(ModelMap map, @RequestParam(name = "message",required = false) String message) {
        map.addAttribute("message", message != null ? message : "");
        map.addAttribute("user", new User());
        return "AddUser";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute(name = "user") User user, BindingResult result) {
        StringBuilder sb = new StringBuilder();
        if (result.hasErrors()) {
            for (ObjectError objectError : result.getAllErrors()) {
                sb.append(objectError.getDefaultMessage()).append("<br>");
            }
            return "redirect:/admin?message=" + sb.toString();
        }
        if (userRepository.findByNameAndCountry(user.getName(), user.getCountry()) != null) {
            String message = "ed anunov mard ka grancvac<br> LAV MAN ARI!!!";
            return "redirect:/admin?message=" + message;
        }
        user.setName(user.getName().toLowerCase());
        userRepository.save(user);
        return "redirect:/credit?id=" + user.getId();
    }

    @RequestMapping(value = "/update")
    public String update(ModelMap map,
                         @RequestParam("id") int id,
                         @RequestParam(name = "message",required = false) String message) {
        map.addAttribute("message", message != null ? message : "");
        map.addAttribute("user", userRepository.findOne(id));
        return "update";
    }

    @RequestMapping(value = "/up", method = RequestMethod.POST)
    public String up(@Valid @ModelAttribute(name = "user") User user, BindingResult result) {
        StringBuilder sb = new StringBuilder();
        if (result.hasErrors()) {
            for (ObjectError objectError : result.getAllErrors()) {
                sb.append(objectError.getDefaultMessage()).append("<br>");
            }
            return "redirect:/update?id=" + user.getId() + "&message=" + sb.toString();
        }
        userRepository.save(user);
        return "redirect:/credit?id=" + user.getId();
    }

    @RequestMapping(value = "/search")
    public String search(ModelMap modelMap, @RequestParam(name = "search", required = false) String search) {
        List<User> userList = userRepository.findUserByNameLike(search.trim());
        if (userList.isEmpty()) {
            modelMap.addAttribute("mess", "<strong>' " + search + " '</strong>" + "  anunov mard chka");
        } else {
            modelMap.addAttribute("allUsers", userList);
        }
        return "result";
    }
}




