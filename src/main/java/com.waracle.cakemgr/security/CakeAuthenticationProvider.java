package com.waracle.cakemgr.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CakeAuthenticationProvider implements AuthenticationProvider {

    private CakeRemoteTokenServices tokenServices;

    public CakeAuthenticationProvider(CakeRemoteTokenServices tokenServices) {
        this.tokenServices = tokenServices;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        try{
            String accessToken = tokenServices.createToken(username, password);
            return tokenServices.loadAuthentication(accessToken);
        }
        catch(Exception e){
            throw new AuthenticationServiceException("Failed to authenticate '" + username + "' user.", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
