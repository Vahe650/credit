package app.credit.dto;

import app.credit.model.Credit;
import app.credit.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreditDto {
    private User user;
    private long sum;
}
