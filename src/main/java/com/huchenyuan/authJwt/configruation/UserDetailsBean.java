package com.huchenyuan.authJwt.configruation;

import com.huchenyuan.authJwt.repository.UserDetailsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;

import static org.springframework.security.core.authority.AuthorityUtils.NO_AUTHORITIES;

@Configuration
public class UserDetailsBean {

    @Bean
    public UserDetailsRepository userDetailsRepository() {
        UserDetailsRepository userDetailsRepository = new UserDetailsRepository();
        UserDetails defaultUser = User.withUsername("user").password("{noop}password").authorities(NO_AUTHORITIES).build();
        System.out.println("create user");
        userDetailsRepository.createUser(defaultUser);
        return userDetailsRepository;
    }

    @Bean
    public UserDetailsManager userDetailsManager(UserDetailsRepository userDetailsRepository) {
        return new UserDetailsManagerImpl(userDetailsRepository);
    }
}
