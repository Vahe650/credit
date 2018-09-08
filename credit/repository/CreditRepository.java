package credit.credit.repository;

import credit.credit.model.Creditor;
import credit.credit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreditRepository  extends JpaRepository<Creditor, Integer> {
    List<Creditor> findAllByUser(User user);

    @Query("SELECT SUM(price) FROM Creditor t WHERE t.type=('NEW')")
     Integer sum();
    @Query(value = "SELECT * FROM Creditor WHERE date LIKE %:times% and type=('NEW') ",nativeQuery = true)
    List<Creditor> findAllByDate( @Param("times") String time);



 @Query(value = "SELECT sum(price) FROM Creditor  WHERE type=('NEW') and user_id=:id",nativeQuery = true)
// @Query("SELECT sum(price) FROM Creditor t WHERE t.type=('NEW') and t.user like(concat( '%', :id ,'%'))")
    Integer userSum(@Param(value = "id") int id);
 @Query(value = "SELECT * from Creditor  WHERE user_id=:id AND type=('NEW')",nativeQuery = true)

   List <Integer> findNotNull(@Param("id") int id);

    @Query(value = "SELECT * FROM creditor  LEFT JOIN USER  " +
            " ON user.`id`=creditor.`user_id`  WHERE creditor.`type`='NEW' GROUP BY user_id ORDER BY SUM(price) DESC",nativeQuery = true)
    List<Creditor> allUsersByMaxPrice();



}
