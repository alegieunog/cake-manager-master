package com.waracle.cakemgr.security;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class CakeRemoteTokenServices implements ResourceServerTokenServices {

    private RestOperations restTemplate = new RestTemplate();
    private String checkTokenEndpointUrl;
    private String createTokenEndpointUrl;
    private String clientId;
    private String clientSecret;
    private DefaultAccessTokenConverter tokenConverter;
    private String tokenName = "token";

    private Map<String, Object> map;

    public CakeRemoteTokenServices() {
        tokenConverter = new DefaultAccessTokenConverter();
        map = new HashMap<>();
    }

    public void setCheckTokenEndpointUrl(String checkTokenEndpointUrl) {
        this.checkTokenEndpointUrl = checkTokenEndpointUrl;
    }

    public void setCreateTokenEndpointUrl(String createTokenEndpointUrl) {
        this.createTokenEndpointUrl = createTokenEndpointUrl;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add(tokenName, accessToken);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", getAuthorizationHeader(clientId, clientSecret));

        Map<String, Object> map = postForMap(checkTokenEndpointUrl, formData, headers);
        if (map.containsKey("error")){
            throw new InvalidTokenException(accessToken);
        }

        return tokenConverter.extractAuthentication(map);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String s) {
        throw new UnsupportedOperationException("Not supported: read access token");
    }

    private String getAuthorizationHeader(String clientId, String clientSecret){
        String creds = String.format("%s:%s", clientId, clientSecret);
        try{
            return "Basic " + new String(Base64Utils.encode(creds.getBytes("UTF-8")));
        }
        catch (UnsupportedEncodingException e){
            throw new IllegalStateException("Could not convert String");
        }
    }

    private Map<String, Object> postForMap(String path, MultiValueMap<String, String> formData, HttpHeaders headers){
        if(headers.getContentType() == null){
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        }
        Map<String, Object> map =
                restTemplate.exchange(path, HttpMethod.POST, new HttpEntity<>(formData, headers), Map.class).getBody();
        return map;
    }

    /**
     * Creates and retrieves access token using username and password.
     *
     * @param username the username
     * @param password the password
     * @return the access token
     */
    public String createToken(String username, String password){
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("grant_type", "password");
        formData.add("username", username);
        formData.add("password", password);

        Map<String, Object> map = null;
        try{
            map = postForMap(createTokenEndpointUrl, formData, new HttpHeaders());
        }
        catch(HttpClientErrorException e){
            throw new OAuth2Exception(e.getMessage());
        }

        Assert.state(map.containsKey("access_token"), "Access token must be present in response from auth server");
        setMap(map);

        return (String) map.get("access_token");
    }
}
