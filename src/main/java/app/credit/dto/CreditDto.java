package app.credit.dto;

import app.credit.model.CreditType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditDto {
    private int id;
    private String armdate;
    private int value;
    private CreditType type;
    private String userName;
}
