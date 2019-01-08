package app.credit.dto;

import app.credit.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSumDto {
    private User user;
    @NumberFormat(style = NumberFormat.Style.NUMBER,pattern = "#,###.###")
    private Long sum;
}
