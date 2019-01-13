package app.credit.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;
import javax.persistence.*;
import javax.validation.constraints.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "creditor")
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    @DecimalMin(value = "500", message = "min")
    @DecimalMax(value = "100000",message = "max")
    @NumberFormat(style = NumberFormat.Style.NUMBER,pattern = "#,###.###")
    private int value;
    @Column
    private String date;
    @Column
    private String armDate;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @Column
    @Enumerated(EnumType.STRING)
    private CreditType type;

}
