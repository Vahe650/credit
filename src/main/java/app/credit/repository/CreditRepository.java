package app.credit.repository;

import app.credit.model.Credit;
import app.credit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Integer> {
    List<Credit> findAllByUser(User user);

    @Query("SELECT SUM(value) FROM Credit t WHERE t.type=('NEW')")
    Integer sum();

    @Query("SELECT c FROM Credit c WHERE c.date LIKE  CONCAT('%', :times, '%') and c.type=('NEW') ")
    List<Credit> findAllByDate(@Param("times") String time);

    @Query("SELECT sum(value) FROM Credit c  WHERE c.type=('NEW') and c.user=:user")
    Integer userSum(@Param(value = "user") User user);

    @Query("SELECT c from Credit c  WHERE c.user=:user AND c.type=('NEW')")
    List<Integer> findHaveingPrice(@Param("user") User user);

    @Query("SELECT SUM(c.value) FROM Credit c LEFT JOIN c.user u  " +
            " WHERE c.type='NEW' GROUP BY c.user ORDER BY SUM(c.value) DESC")
    List<Integer> allByMaxPrice();

    @Query("SELECT c FROM Credit c LEFT JOIN  c.user u " +
            " WHERE c.type='NEW' GROUP BY c.user ORDER BY SUM(c.value) DESC")
    List<Credit> allUsersByMaxPrice();


    Credit findTop1ByUserOrderByIdDesc( User user);




}
