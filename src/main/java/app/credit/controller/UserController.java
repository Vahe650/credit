package app.credit.controller;

import app.credit.model.User;
import app.credit.repository.CreditRepository;
import app.credit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class UserController {
    private UserRepository userRepository;
    private CreditRepository creditRepository;
    private Sort sortByNameAsc() {
        return new Sort(Sort.Direction.ASC, "name");
    }

    @RequestMapping(value = "/allByMax")
    public String home(ModelMap map, @RequestParam(value = "page", required = false) Integer page,
                       @PageableDefault(size = 20) Pageable pageable) {
        PageRequest pageRequest;
        if (page != null && page > 0) {
            pageRequest = PageRequest.of(page, pageable.getPageSize(),sortByNameAsc());
        } else {
            pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),sortByNameAsc());
        }
        map.addAttribute("currentUrl","allByMax");
        map.addAttribute("all", userRepository.findAll(pageRequest));
        map.addAttribute("s", creditRepository.sum());
        return "index";

    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public String delete(@RequestParam("id") int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            String message = "notEmpty";
            if (!creditRepository.findHaveingPrice(user.get()).isEmpty()) {
                return "redirect:/app?id=" + user.get().getId() + "&message=" + message;
            }
            userRepository.delete(user.get());
            return "redirect:/allByMax";
        }
        return "redirect:/error";
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
                sb.append(objectError.getDefaultMessage());
            }
            return "redirect:/admin?message=" + sb.toString();
        }
        if (userRepository.findByNameAndCountry(user.getName(), user.getCountry()) != null) {
            String message = "userExist";
            return "redirect:/admin?message=" + message;
        }
        user.setName(user.getName().toLowerCase());
        userRepository.save(user);
        return "redirect:/app?id=" + user.getId();
    }

    @RequestMapping(value = "/update")
    public String update(ModelMap map,
                         @RequestParam(name = "id") int id,
                         @RequestParam(name = "message",required = false) String message) {
        final Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            map.addAttribute("message", message != null ? message : "");
            map.addAttribute("user", byId.get());
            return "update";
        }
        return "redirect:/error";
    }

    @RequestMapping(value = "/up", method = RequestMethod.POST)
    public String up(@Valid @ModelAttribute(name = "user") User user,
                     BindingResult result,
                     @RequestParam(name = "id",required = false) int id) {
        StringBuilder sb = new StringBuilder();
        if (result.hasErrors()) {
            for (ObjectError objectError : result.getAllErrors()) {
                sb.append(objectError.getDefaultMessage());
            }
            return "redirect:/update?id=" + user.getId() + "&message=" + sb.toString();
        }
        user.setId(id);
        userRepository.save(user);
        return "redirect:/app?id=" + user.getId();
    }

    @RequestMapping(value = "/search")
    public String search(ModelMap modelMap, @RequestParam(name = "search", required = false) String search,
                         @RequestParam(value = "page", required = false) Integer page,
                         @PageableDefault(size = 20) Pageable pageable) {
        PageRequest pageRequest;
        if (page != null && page > 0) {
            pageRequest = PageRequest.of(page, pageable.getPageSize(),sortByNameAsc());
        } else {
            pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),sortByNameAsc());
        }
        final Page<User> userByNameLike = userRepository.findUserByNameLike(pageRequest, search.trim());
        if ( userByNameLike.getTotalElements()==0) {
            modelMap.addAttribute("mess",  search);
        } else {
            modelMap.addAttribute("currentUrl","/search{search}");
            modelMap.addAttribute("search",search);
            modelMap.addAttribute("allUsers", userByNameLike);
            modelMap.addAttribute("s", creditRepository.sum());
        }
        return "index";
    }
}




