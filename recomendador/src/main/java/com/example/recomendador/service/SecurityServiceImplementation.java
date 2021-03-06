package com.example.recomendador.service;

import com.example.recomendador.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImplementation implements SecurityService{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Qualifier("UserDetailsServiceImp")
    @Autowired
    private UserDetailsService userDetailsService;
    private static final Logger logger= LoggerFactory.getLogger(SecurityServiceImplementation.class);
    @Override
    public String findLoggedInUsername(){
        Object userDetails= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetails instanceof UserDetails){
            String result=((UserDetails)userDetails).getUsername();
            return result;
        }
        return null;
    }
    @Override
    public void autoLogin(String username, String password){
        UserDetails userDetails=userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            logger.debug(String.format("Auto login by %s success",username));
        }
    }
}
