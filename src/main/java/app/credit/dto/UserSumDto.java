package app.credit.dto;

import app.credit.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSumDto {
    private User user;
    private Long sum;
}
