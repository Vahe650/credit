package app.credit.controller;

import app.credit.dto.UserSumDto;
import app.credit.repository.CreditRepository;
import app.credit.repository.UserRepository;
import app.credit.service.PaginationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private UserRepository userRepository;
    private PaginationService paginationService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/")
    public String allByMax(ModelMap modelMap,
                           @RequestParam(value = "page", required = false) Integer page,
                           @PageableDefault(size = 200) Pageable pageable) throws JsonProcessingException {
        PageRequest pagination = paginationService.pagination(pageable, page, JpaSort.unsafe("SUM(value)"));
        Page<UserSumDto> userSum = creditRepository.createUserSum(pagination);
        modelMap.addAttribute("currentUrl", "");
        modelMap.addAttribute("userSumDto", userSum);
        modelMap.addAttribute("s", creditRepository.sum());

        ObjectMapper mapper = new ObjectMapper();
        Object[] strings = new Object[userSum.getNumberOfElements() * 5];
        Object[] xy = {
                1, 1,

                3, 1,

                5, 1,

                7, 1,

                9, 1,

                11,1,

                13,1,

                15,1,

                17,1,


                2, 2,

                4, 2,

                6, 2,

                8, 2,

                10,2,

                12,2,

                14,2,

                16,2,

                18,2,

                1, 3,

                3, 3,

                5, 3,

                7, 3,

                9, 3,

                11,3,

                13,3,

                15,3,

                17,3,



                2, 4,

                4, 4,

                6, 4,

                8, 4,

                10,4,

                12,4,

                14,4,

                16,4,

                18,4,

                1, 5,

                3, 5,

                5, 5,

                7, 5,

                9, 5,

                11,5,

                13,5,

                15,5,

                17,5,



                2, 6,

                4, 6,

                6, 6,

                8, 6,

                10,6,

                12,6,

                14,6,

                16,6,

                18,6,

                1, 7,
                2, 7,
                3, 7,
                4, 7,
                5, 7,
                6, 7,
                7, 7,
                8, 7,
                9, 7,
                10,7,
                11,7,
                12,7,
                13,7,
                14,7,
                15,7,
                16,7,
                17,7,
                18,7,

                1, 8,
                2, 8,
                3, 8,
                4, 8,
                5, 8,
                6, 8,
                7, 8,
                8, 8,
                9, 8,
                10,8,
                11,8,
                12,8,
                13,8,
                14,8,
                15,8,
                16,8,
                17,8,
                18,8,

                1, 9,
                2, 9,
                3, 9,
                4, 9,
                5, 9,
                6, 9,
                7, 9,
                8, 9,
                9, 9,
                10,9,
                11,9,
                12,9,
                13,9,
                14,9,
                15,9,
                16,9,
                17,9,
                18,9,

                1, 10,
                2, 10,
                3, 10,
                4, 10,
                5, 10,
                6, 10,
                7, 10,
                8, 10,
                9, 10,
                10,10,
                11,10,
                12,10,
                13,10,
                14,10,
                15,10,
                16,10,
                17,10,
                18,10,








                1, 11, 2, 11, 3, 11, 4, 11, 5, 11, 6, 11, 7, 11, 8, 11, 9, 11, 10, 11, 11, 11, 12, 11, 13, 11, 14, 11, 15, 11, 16, 11, 17, 11, 18, 11,
                1, 12, 2, 12, 3, 12, 4, 12, 5, 12, 6, 12, 7, 12, 8, 12, 9, 12, 10, 12, 11, 12, 12, 12, 13, 12, 14, 12, 15, 12, 17, 12, 18, 12, 16, 12,
                1, 13, 2, 13, 3, 13, 4, 13, 5, 13, 6, 13, 7, 13, 8, 13, 9, 13, 10, 13, 11, 13, 12, 13, 13, 13, 14, 13, 15, 13, 16, 13, 17, 13, 18, 13,
                1, 14, 2, 14, 3, 14, 4, 14, 5, 14, 6, 14, 7, 14, 8, 14, 9, 14, 10, 14, 11, 14, 12, 14, 13, 14, 14, 14, 15, 14, 16, 14, 17, 14, 18, 14
        };
        int index = 0;
        int cord = 0;
        for (UserSumDto userSumDto : userSum) {
            strings[index] = userSumDto.getUserName();
            index++;
            strings[index] = userSumDto.getCountry();
            index++;
            strings[index] = userSumDto.getSum();
            index++;
            strings[index] = xy[cord];
            cord++;
            index++;
            strings[index] = xy[cord];
            index++;
            cord++;
        }

        String json = mapper.writeValueAsString(strings);

        modelMap.addAttribute("json", strings);
        return "result";
    }

    @RequestMapping(value = "/searchDtoByName")
    public String searchDto(ModelMap modelMap, @RequestParam("name") String name,
                            @RequestParam(value = "page", required = false) Integer page,
                            @PageableDefault(size = 99) Pageable pageable) {
        PageRequest pagination = paginationService.pagination(pageable, page, JpaSort.unsafe("SUM(value)"));
        Page<UserSumDto> userList = creditRepository.searchUserSDto(pagination, name.trim());
        if (userList.getTotalElements() == 0) {
            modelMap.addAttribute("message", name);
        } else {
            modelMap.addAttribute("allDtos", userList);
            modelMap.addAttribute("currentUrl", "searchDtoByName");
            modelMap.addAttribute("name", name);
            modelMap.addAttribute("s", creditRepository.sum());
        }
        return "result";
    }
}
