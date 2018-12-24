package app.credit.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.scheduling.annotation.Async;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "creditor")
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;
    @Column
    @DecimalMin(value = "500",message = "500-ic pakas tiv mi gre")
    @DecimalMax(value = "100000",message = "100,000-ic avel tiv mi gre")
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
