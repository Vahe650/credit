package app.credit.repository;

import app.credit.dto.CreditDto;
import app.credit.dto.UserSumDto;
import app.credit.model.Credit;
import app.credit.model.CreditType;
import app.credit.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Integer> {

    @Query("SELECT SUM(value) FROM Credit t WHERE t.type=('NEW')")
    Integer sum();

    Page<Credit> findByDateContainingAndType(Pageable var1, String time, CreditType creditType);

    @Query("SELECT sum(value) FROM Credit c  WHERE c.type=('NEW') and c.user=:user")
    Integer userSum(@Param(value = "user") User user);

    List<Credit> findByUserAndType(User user, CreditType type);

    boolean existsByUserAndType(User user, CreditType type);

    @Query("SELECT new app.credit.dto.UserSumDto(c.user.name,c.user.country,c.user.tel,SUM(c.value)) FROM Credit c  " +
            " WHERE c.type='NEW' GROUP BY c.user ORDER BY SUM(c.value) DESC")
    Page<UserSumDto> createUserSum(Pageable pageable);

    @Query("SELECT new app.credit.dto.UserSumDto(c.user.name,c.user.country,c.user.tel,SUM(c.value)) FROM Credit c " +
            " WHERE c.type='NEW' and (c.user.name LIKE  CONCAT('%', :name, '%') or c.user.country LIKE  CONCAT('%', :name, '%'))  " +
            " GROUP BY c.user ORDER BY SUM(c.value) DESC")
    Page<UserSumDto> searchUserSDto(Pageable pa, @Param("name") String name);

    @Query("SELECT new app.credit.dto.CreditDto(c.id,c.date,c.value,c.type,c.user.name) FROM Credit c " +
            " WHERE c.type='NEW' and c.user=:user")
    List<CreditDto> getCreditDtos(@Param("user") User user);

    @Query("SELECT c FROM Credit c " +
            "WHERE c.type='NEW' GROUP BY c.user")
    Page<Credit> findAllByOrderByNameAsc(Pageable var1);

    Credit findTop1ByUserOrderByIdDesc(User user);


//    @Query("SELECT SUM(c.value) FROM Credit c LEFT JOIN c.user u  " +
//            " WHERE c.type='NEW' GROUP BY c.user ORDER BY SUM(c.value) DESC")
//    List<Integer> allByMaxPrice();
//
//    @Query("SELECT c FROM Credit c LEFT JOIN  c.user u " +
//            " WHERE c.type='NEW' GROUP BY c.user ORDER BY SUM(c.value) DESC")
//    List<Credit> allUsersByMaxPrice();


}
