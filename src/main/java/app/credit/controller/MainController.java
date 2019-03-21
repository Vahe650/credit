package app.credit.controller;


import app.credit.dto.UserSumDto;
import app.credit.repository.CreditRepository;
import app.credit.service.PaginationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@AllArgsConstructor
public class MainController {
    private CreditRepository creditRepository;
    private PaginationService paginationService;
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @RequestMapping(value = "/")
    public String allByMax(ModelMap modelMap,
                           @RequestParam(value = "page", required = false) Integer page,
                           @PageableDefault(size = 99) Pageable pageable) {
        PageRequest pagination = paginationService.pagination(pageable, page,  JpaSort.unsafe("SUM(value)"));
        modelMap.addAttribute("currentUrl","");
        modelMap.addAttribute("userSumDto", creditRepository.createUserSum(pagination));
        modelMap.addAttribute("s", creditRepository.sum());
        return "result";
    }
    @RequestMapping(value = "/searchDtoByName")
    public String searchDto(ModelMap modelMap, @RequestParam("name") String name,
                            @RequestParam(value = "page", required = false) Integer page,
                            @PageableDefault(size = 99) Pageable pageable) {
        PageRequest pagination = paginationService.pagination(pageable, page,  JpaSort.unsafe("SUM(value)"));
        Page<UserSumDto> userList = creditRepository.searchUserSDto(pagination,name.trim());
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
