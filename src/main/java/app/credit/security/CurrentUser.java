package app.credit.security;


import app.credit.model.User;

import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getType().name()));
        this.user = user;
    }


    /*This is need to get current user for checking*/
    public User getUser() {
        return user;
    }


}
