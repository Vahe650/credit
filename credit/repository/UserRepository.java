package credit.credit.repository;

import credit.credit.model.Creditor;
import credit.credit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {


    @Query("SELECT t FROM User t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :searchItem, '%')) OR LOWER(t.country)" +
            "LIKE LOWER(CONCAT('%', :searchItem, '%')) ORDER BY t.name ASC\n" + "  ")
    List<User> findUserByNameLike(@Param("searchItem") String find);

    @Query(value = "select * from user left  join creditor on user.`id`=creditor.`user_id` WHERE creditor.`type`='NEW' " +
            "group by user.id order by user.name", nativeQuery = true)
    List<User> findAllByOrderByNameAsc();

    @Query(value = "SELECT SUM(price) FROM creditor  LEFT JOIN USER  " +
            " ON user.`id`=creditor.`user_id`  WHERE creditor.`type`='NEW' GROUP BY user_id ORDER BY SUM(price) DESC", nativeQuery = true)
    List<Integer> allByMaxPrice();


}
