package app.credit.dto;

import app.credit.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.bind.annotation.ModelAttribute;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSumDto {
    private String userName;
    private String country;
    private String tel;
    private Long sum;


}
