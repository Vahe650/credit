package app.credit;

import app.credit.model.Credit;
import app.credit.model.CreditType;
import app.credit.model.User;
import app.credit.model.UserType;
import app.credit.repository.CreditRepository;
import app.credit.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.LinkedList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CreditApplicationTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CreditRepository creditRepository;

    @Test
    public void saveUsersAndCredits() {
        User alex = new User();
        alex.setName("Alex");
        alex.setCountry("Gyumri");
        alex.setTel("0988828888");
        User armen = new User();
        armen.setName("armen");
        armen.setCountry("Gyumri");
        armen.setTel("098888888");
        User sergey = new User();
        sergey.setName("sergey");
        sergey.setCountry("Gyumri");
        sergey.setTel("0988688888");
        List<User> all = new LinkedList<>();
        all.add(alex);
        all.add(armen);
        all.add(sergey);
        userRepository.saveAll(all);
        int value=1000;
        for (User user : all) {
            Credit credit = new Credit();
            credit.setType(CreditType.NEW);
            credit.setValue(value);
            credit.setUser(user);
            creditRepository.save(credit);
            value+=500;
        }

    }

    @Test
    public void findAllUsers() {
        userRepository.findAll();
    }

    @Test
    public void editUser() {
        User byNameAndCountry = userRepository.findByEmail("ando");
        byNameAndCountry.setName("Ando");
        userRepository.save(byNameAndCountry);
    }

    @Test
    public void searchpage() {
        User admin = userRepository.findTop1ByOrderByIdAsc();
        PageRequest of = PageRequest.of(1, 1);
        userRepository.findByNameContainingAndType(of, admin.getName(), UserType.ADMIN);
    }

    @Test
    public void findAllCredits() {
        creditRepository.findAll();
    }

}
