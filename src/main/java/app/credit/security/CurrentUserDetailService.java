package app.credit.security;


import app.credit.model.User;
import app.credit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrentUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User byUsername = userRepository.findByEmail(email);
        if (byUsername == null) {
            throw new UsernameNotFoundException("User does not exist with email:" + email);
        }
        return new CurrentUser(byUsername);
    }


}
