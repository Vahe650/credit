package app.credit.repository;

import app.credit.model.User;
import app.credit.model.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

    Page<User> findByTypeAndNameContainingOrCountryContaining(Pageable var1,UserType type, String name,String country );
    User findByNameAndCountry(String name, String country);
    User findByEmail(String email);
    User findTop1ByOrderByIdAsc();
    User findTop1ByOrderByIdDesc();



}
