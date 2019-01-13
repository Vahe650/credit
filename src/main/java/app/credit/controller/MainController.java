package app.credit.controller;


import app.credit.dto.UserSumDto;
import app.credit.repository.CreditRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {
    @Autowired
    private CreditRepository creditRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/")
    public String allByMax(ModelMap modelMap) {
//        modelMap.addAttribute("allByMax", creditRepository.allByMaxPrice());
//        modelMap.addAttribute("allUsersByMax", creditRepository.allUsersByMaxPrice());
        modelMap.addAttribute("userSumDto", creditRepository.createUserSum());
        modelMap.addAttribute("s", creditRepository.sum());
        return "result";
    }
    @RequestMapping(value = "/searchDtoByName")
    public String searchDto(ModelMap modelMap, @RequestParam("name") String name) {
        List<UserSumDto> userList = creditRepository.searchUserSDto(name.trim());
        if (userList.isEmpty()) {
            modelMap.addAttribute("message", name);
        } else {
            modelMap.addAttribute("allDtos", userList);
            modelMap.addAttribute("s", creditRepository.sum());
        }
        return "result";

    }
}
