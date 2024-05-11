package com.example.chess.game;

import com.example.chess.game.Models.UserModel;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class JpaUserDetailManager implements UserDetailsManager {

    private final UserRepository UserRepository;

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    private AuthenticationManager authenticationManager;


    public JpaUserDetailManager(UserRepository UserRepository) {
        this.UserRepository =UserRepository;
    }

    @Override
    public void createUser(UserDetails user) {

        UserModel userModel=(UserModel)user;

        UserRepository.save(userModel);

    }

    @Override
    public void updateUser(UserDetails user) {

        createUser(user);

    }

    @Override
    public void deleteUser(String username) {
        UserRepository.delete(UserRepository.findByEmail(username));
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

        Authentication currentUser = this.securityContextHolderStrategy.getContext().getAuthentication();
        if (currentUser == null) {
            throw new AccessDeniedException("Can't change password as no Authentication object found in context for current user.");
        } else {
            String username = currentUser.getName();
            if (this.authenticationManager != null) {
                this.authenticationManager.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(username, oldPassword));
            } else {
            }

            UserModel user= (UserModel) loadUserByUsername(username);
            Assert.state(user != null, "Current user doesn't exist in database.");
            user.setPassword(newPassword);

            updateUser(user);
        }

    }

    public  void changePassword(String newPassword){
        Authentication currentUser = this.securityContextHolderStrategy.getContext().getAuthentication();
        String username = currentUser.getName();

        UserModel userModel=(UserModel) loadUserByUsername(username);
        changePassword(userModel.getPassword(),newPassword);

    }

    @Override
    public boolean userExists(String username) {

        return UserRepository.findByEmail(username) != null;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         if(UserRepository.findByEmail(username)==null){
            throw new UsernameNotFoundException(this.messages.getMessage("notFound", new Object[]{username}, "Username {0} not found"));

        }else
            return UserRepository.findByEmail(username);
    }
}
