package com.huchenyuan.authJwt.configruation;

import com.huchenyuan.authJwt.repository.UserDetailsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import java.nio.file.AccessDeniedException;

public class UserDetailsManagerImpl implements UserDetailsManager {
    UserDetailsRepository userDetails;
    public UserDetailsManagerImpl(UserDetailsRepository userDetailsRepository) {
        userDetails = userDetailsRepository;
    }
    //    @Autowired
//    UserDetailsRepository userDetails;

    @Override
    public void createUser(UserDetails user) {
        userDetails.createUser(user);

    }

    @Override
    public void updateUser(UserDetails user) {
        userDetails.updateUser(user);

    }

    @Override
    public void deleteUser(String username) {
        userDetails.deleteUser(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        try {
            userDetails.changePassword(oldPassword, newPassword);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean userExists(String username) {
        return userDetails.userExists(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDetails.loadUserByUsername(username);
    }
}
