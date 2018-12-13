package app.credit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;
    @Column
    @NotEmpty(message = "anuny partadir lracrac bdi exni!!!")
    private String name;
    @Column
    private String tel;
    @Column
    private String country;
    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL)
    private List<Credit> credits;
}
