package app.credit.repository;
import app.credit.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.LinkedList;


public interface UserRepository extends JpaRepository<User, Integer> {


    @Query( "SELECT t FROM User t WHERE (t.email is  null) and  (LOWER(t.name) " +
            "LIKE LOWER(CONCAT('%', :searchItem, '%')) OR LOWER(t.country)" +
            "LIKE LOWER(CONCAT('%', :searchItem, '%')))   ")
    Page<User> findUserByNameLike(Pageable var1,@Param("searchItem") String find);




    User findByNameAndCountry(String name, String country);
    User findByEmail(String email);
}
