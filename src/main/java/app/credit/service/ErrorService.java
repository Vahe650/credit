package app.credit.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Service
public class ErrorService {

    public StringBuilder hasErrors(BindingResult result) {
        StringBuilder sb = new StringBuilder();
            for (ObjectError objectError : result.getAllErrors()) {
                sb.append(objectError.getDefaultMessage());
                if (objectError.toString().contains(NumberFormatException.class.getName())) {
                    sb = new StringBuilder("big");
            }
        }
        return sb;
    }
}
