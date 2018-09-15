package app.credit.repository;

import app.credit.model.Creditor;
import app.credit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreditRepository extends JpaRepository<Creditor, Integer> {
    List<Creditor> findAllByUser(User user);

    @Query("SELECT SUM(value) FROM Creditor t WHERE t.type=('NEW')")
    Integer sum();

    @Query("SELECT c FROM Creditor c WHERE c.date LIKE  CONCAT('%', :times, '%') and c.type=('NEW') ")
    List<Creditor> findAllByDate(@Param("times") String time);

    @Query("SELECT sum(value) FROM Creditor c  WHERE c.type=('NEW') and c.user=:user")
    Integer userSum(@Param(value = "user") User user);

    @Query("SELECT c from Creditor c  WHERE c.user=:user AND c.type=('NEW')")
    List<Integer> findHaveingPrice(@Param("user") User user);

    @Query(value = "SELECT * FROM creditor  LEFT JOIN USER  ON user.`id`=creditor.`user_id` " +
            " WHERE creditor.`type`='NEW' GROUP BY user_id ORDER BY SUM(value) DESC", nativeQuery = true)
    List<Creditor> allUsersByMaxPrice();


}
