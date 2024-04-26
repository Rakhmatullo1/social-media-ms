package com.example.chatservice.security;

import com.example.chatservice.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class SecurityUtils {

    private static final String USER_ID_CLAIM_NAME= "sub";

    public static UUID getCurrentUserId() {
        String userIdAsString = (String) getClaimByName(USER_ID_CLAIM_NAME)
                .orElseThrow(() -> new NotFoundException("UserID(sub claim) is not found in token!"));

        return UUID.fromString(userIdAsString);
    }

    private static Optional<Object> getClaimByName(String userIdClaimName) {
        Map<String, Object> claims = getAllClaims();
        return Optional.ofNullable(claims.get(userIdClaimName));
    }

    public static Map<String, Object> getAllClaims() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(Objects.isNull(auth)) {
            log.warn("Authentication is not found");
            throw new NotFoundException("Authentication is not found");
        }

        return ((JwtAuthenticationToken) auth).getToken().getClaims();
    }

    public static Collection<GrantedAuthority> extractAuthoritiesByClaim(Map<String, Object> claims) {
        return mapRoles2Authorities(getRoles(claims));
    }

    @SuppressWarnings("unchecked")
    private static Collection<String> getRoles(Map<String, Object> claims){
        Map<String, Object> realmAccess = ((Map<String, Object>) claims.get("realm_access"));
        return ((Collection<String>) realmAccess.get("roles"));
    }


    private static List<GrantedAuthority> mapRoles2Authorities(Collection<String> roles) {
        return roles.stream().filter(role -> role.startsWith("ROLE_")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
