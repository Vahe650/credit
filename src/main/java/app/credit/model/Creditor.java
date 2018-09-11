package app.credit.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

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
    @DecimalMin(value = "500",message = "500-ic pakas tiv mi gre")
    @DecimalMax(value = "100000",message = "100,000-ic avel tiv mi gre")
    private int price;
    @Column
    private String date;
    @ManyToOne
    private User user;
    @Column
    @Enumerated(EnumType.STRING)
    private CreditType type;

}
