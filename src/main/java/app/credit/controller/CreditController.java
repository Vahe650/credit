package app.credit.controller;

import app.credit.model.Credit;
import app.credit.model.CreditType;
import app.credit.model.User;
import app.credit.repository.CreditRepository;
import app.credit.repository.UserRepository;
import app.credit.service.CreditService;
import app.credit.service.ErrorService;
import app.credit.service.PaginationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class CreditController {

    //    private JmsConsumer jmsConsumer;
//    private JmsProducer producer;
    private PaginationService paginationService;
    private CreditRepository creditRepository;
    private UserRepository userRepository;
    private CreditService creditService;
    private ErrorService errorService;

    private Sort sortByDateAsc() {
        return new Sort(Sort.Direction.ASC, "date");
    }

    @RequestMapping(value = "/app")
    public String add(ModelMap map,
                      @RequestParam(value = "message", required = false) String message,
                      @RequestParam(name = "id", required = false) int userId) {
        Optional<User> one = userRepository.findById(userId);
        if (one.isPresent()) {
//        List<CreditDto> creditDtos = creditRepository.getCreditDtos(one.get());
//        for (CreditDto creditDto : creditDtos) {
//            producer.send(creditDto, creditDto.getUserName());
//            jmsConsumer.sending("app with '" + creditDto.getId() + "' is exist", creditDto.getUserName());
//        }

            final List<Credit> news = creditRepository.findByUserAndType(one.get(), CreditType.NEW);
            final List<Credit> ends = creditRepository.findByUserAndType(one.get(), CreditType.END);
            map.addAttribute("message", message != null ? message : " ");
            map.addAttribute("creditor", new Credit());
            map.addAttribute("user", one.get());
            map.addAttribute("news", news);
            map.addAttribute("ends", ends);
            map.addAttribute("userSum", creditRepository.userSum(one.get()));
            return "details";
        }
        return "redirect:/error";
    }

    @RequestMapping(value = "/addCredit")
    public String cred(@Valid @ModelAttribute(name = "creditor") Credit credit,
                       BindingResult result,
                       @RequestParam(name = "userId", required = false) int userId) {
        if (result.hasErrors()) {
            StringBuilder sb = errorService.hasErrors(result);
            return "redirect:/app?id=" + userId + "&message=" + sb.toString();
        }
        credit.setType(CreditType.NEW);
        final Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            credit.setUser(user.get());
            creditRepository.save(credit);
            return "redirect:/app" + "?id=" + credit.getUser().getId();
        }
        return "redirect:/error";
    }

    @RequestMapping(value = "/changeType")
    public String type(@RequestParam("id") int id) {
        Optional<Credit> one = creditRepository.findById(id);
        if (one.isPresent()) {
            one.get().setType(CreditType.END);
            creditRepository.save(one.get());
            return "redirect:/app?id=" + one.get().getUser().getId();
        }
        return "redirect:/error";
    }

    @RequestMapping(value = "/deletePrice")
    public String delete(@RequestParam("id") int id) {
        Optional<Credit> one = creditRepository.findById(id);
        if (one.isPresent()) {
            creditRepository.delete(one.get());
            return "redirect:/app?id=" + one.get().getUser().getId();
        }
        return "redirect:/error";
    }

    @RequestMapping(value = "/searchByDate")
    public String date(ModelMap map,
                       @RequestParam("date") String date,
                       @RequestParam(value = "page", required = false) Integer page,
                       @PageableDefault(size = 51) Pageable pageable) {
        PageRequest pagination = paginationService.pagination(pageable, page, sortByDateAsc());
        Page<Credit> allByDate = creditRepository.findByDateContainingAndType(pagination, date, CreditType.NEW);
        if (allByDate.getTotalElements() == 0) {
            map.addAttribute("message", creditService.getDates(date));
        } else {
            map.addAttribute("allByDate", allByDate);
            map.addAttribute("date", date);
            map.addAttribute("currentUrl", "searchByDate");
            map.addAttribute("s", creditRepository.sum());
        }
        return "index";
    }

    @RequestMapping(value = "/change")
    public String change(ModelMap map,
                         @RequestParam(value = "message", required = false) String message,
                         @RequestParam("id") int id) {
        Optional<Credit> one = creditRepository.findById(id);
        if (one.isPresent()) {
            map.addAttribute("creditor", one.get());
            map.addAttribute("message", message != null ? message : "");
            return "changePrice";
        }
        return "redirect:/error";
    }

    @RequestMapping(value = "/updatePrice")
    public String updatePrice(@Valid @ModelAttribute(name = "creditor") Credit credit,
                              BindingResult result,
                              @RequestParam(name = "id", required = false) int id) {
        Optional<Credit> one = creditRepository.findById(credit.getId());
        if (one.isPresent()) {
            if (result.hasErrors()) {
                StringBuilder sb = errorService.hasErrors(result);
                return "redirect:/change?id=" + one.get().getId() + "&message=" + sb.toString();
            }
            one.get().setValue(credit.getValue());
            if (!credit.getDate().equals("")) {
                one.get().setDate(credit.getDate());
            }
            one.get().setId(id);
            creditRepository.save(one.get());
            return "redirect:/app?id=" + one.get().getUser().getId();
        }
        return "redirect:/error";
    }
}
