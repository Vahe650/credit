package app.credit.repository;

import app.credit.dto.CreditDto;
import app.credit.dto.UserSumDto;
import app.credit.model.Credit;
import app.credit.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.NumberFormat;

import java.util.List;
import java.util.Optional;

public interface CreditRepository extends JpaRepository<Credit, Integer> {
    List<Credit> findAllByUser(User user);

    @NumberFormat(style = NumberFormat.Style.NUMBER,pattern = "#,###.###")
    @Query("SELECT SUM(value) FROM Credit t WHERE t.type=('NEW')")
    Integer sum();

    @Query("SELECT c FROM Credit c WHERE c.date LIKE  CONCAT('%', :times, '%') and c.type=('NEW') ")
    Page<Credit> findAllByDate(Pageable var1, @Param("times") String time);

    @Query("SELECT sum(value) FROM Credit c  WHERE c.type=('NEW') and c.user=:user")
    Integer userSum(@Param(value = "user") User user);

    @Query("SELECT c FROM Credit c  WHERE c.type=('NEW') and c.user=:user")
    List<Credit> newCredits(@Param(value = "user") User user);

    @Query("SELECT c FROM Credit c  WHERE c.type=('END') and c.user=:user")
    List<Credit> endCredits(@Param(value = "user") User user);

    @Query("SELECT c from Credit c  WHERE c.user=:user AND c.type=('NEW')")
    List<Integer> findHaveingPrice(@Param("user") User user);

    @Query("SELECT SUM(c.value) FROM Credit c LEFT JOIN c.user u  " +
            " WHERE c.type='NEW' GROUP BY c.user ORDER BY SUM(c.value) DESC")
    List<Integer> allByMaxPrice();

    @Query("SELECT c FROM Credit c LEFT JOIN  c.user u " +
            " WHERE c.type='NEW' GROUP BY c.user ORDER BY SUM(c.value) DESC")
    List<Credit> allUsersByMaxPrice();

    @Query("SELECT new app.credit.dto.UserSumDto(c.user,SUM(c.value)) FROM Credit c  " +
            " WHERE c.type='NEW' GROUP BY c.user ORDER BY SUM(c.value) DESC")
    Page<UserSumDto> createUserSum(Pageable Pageable);

    @Query("SELECT new app.credit.dto.UserSumDto(c.user,SUM(c.value)) FROM Credit c " +
            " WHERE c.type='NEW' and (c.user.name LIKE  CONCAT('%', :name, '%') or c.user.country LIKE  CONCAT('%', :name, '%'))  " +
            " GROUP BY c.user ORDER BY SUM(c.value) DESC")
    Page<UserSumDto> searchUserSDto(Pageable pa,@Param("name") String name);

    @Query("SELECT new app.credit.dto.CreditDto(c.id,c.armDate,c.value,c.type,c.user.name) FROM Credit c " +
            " WHERE c.type='NEW' and c.user=:user")
    List<CreditDto> getCreditDtos(@Param("user") User user);



    Credit findTop1ByUserOrderByIdDesc( User user);



}
