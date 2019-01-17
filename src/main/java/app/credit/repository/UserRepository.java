package app.credit.repository;

import app.credit.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.LinkedList;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {


    @Query( "SELECT t FROM User t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :searchItem, '%')) OR LOWER(t.country)" +
            "LIKE LOWER(CONCAT('%', :searchItem, '%')) ORDER BY t.name ASC\n" + "  ")
    List<User> findUserByNameLike(@Param("searchItem") String find);

    @Query(value = "select * from user left  join creditor on user.`id`=creditor.`user_id` WHERE creditor.`type`='NEW' " +
            "group by user.id order by user.name", nativeQuery = true)
    LinkedList<User> findAllByOrderByNameAsc(PageRequest pageRequest);



    User findByNameAndCountry(String name, String country);
    User findByEmail(String email);


}
