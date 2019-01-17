package app.credit.controller;


import app.credit.dto.UserSumDto;
import app.credit.repository.CreditRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.web.PageableDefault;
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
    private Sort sortBySumAsc() {
        return new Sort(Sort.Direction.ASC, "SUM(c.value)");
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/")
    public String allByMax(ModelMap modelMap, @RequestParam(value = "page", required = false) Integer page,
                           @PageableDefault(size = 20) Pageable pageable) {
        PageRequest pageRequest;
        if (page != null && page > 0) {
            pageRequest = PageRequest.of(page, pageable.getPageSize(), JpaSort.unsafe("SUM(value)"));
        } else {
            pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), JpaSort.unsafe("SUM(c.value)"));
        }
//        modelMap.addAttribute("allByMax", creditRepository.allByMaxPrice());
//        modelMap.addAttribute("allUsersByMax", creditRepository.allUsersByMaxPrice());
        modelMap.addAttribute("currentUrl","");
        modelMap.addAttribute("userSumDto", creditRepository.createUserSum(pageRequest));
        modelMap.addAttribute("s", creditRepository.sum());
        return "result";
    }
    @RequestMapping(value = "/searchDtoByName")
    public String searchDto(ModelMap modelMap, @RequestParam("name") String name,@RequestParam(value = "page", required = false) Integer page,
                            @PageableDefault(size = 20) Pageable pageable) {
        PageRequest pageRequest;
        if (page != null && page > 0) {
            pageRequest = PageRequest.of(page, pageable.getPageSize(), JpaSort.unsafe("SUM(value)"));
        } else {
            pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), JpaSort.unsafe("SUM(c.value)"));
        }
        Page<UserSumDto> userList = creditRepository.searchUserSDto(pageRequest,name.trim());
        if (userList.getTotalElements()==0) {
            modelMap.addAttribute("message", name);
        } else {
            modelMap.addAttribute("allDtos", userList);
            modelMap.addAttribute("currentUrl","searchDtoByName");
            modelMap.addAttribute("name",name);
            modelMap.addAttribute("s", creditRepository.sum());
        }
        return "result";

    }
}
