package credit.credit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "creditor")
public class Creditor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;
    @Column
    private int price;
    @Column
    private String date;
    @ManyToOne
//            (fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private User user;
    @Column
    @Enumerated(EnumType.STRING)
    private CreditType type;

}
