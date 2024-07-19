package learn.microservices.gatewayserver.config;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        Object realmAccessObj = source.getClaims().get("realm_access");
        if (!(realmAccessObj instanceof Map<?, ?>)) {
            return new ArrayList<>();
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> realmAccess = (Map<String, Object>) realmAccessObj;
        if (realmAccess.isEmpty()) {
            return new ArrayList<>();
        }

        Object rolesObj = realmAccess.get("roles");
        if (!(rolesObj instanceof List<?> rolesRawList)) {
            return new ArrayList<>();
        }

        List<String> roles = rolesRawList.stream()
                .filter(role -> role instanceof String)
                .map(role -> (String) role)
                .toList();

        return roles.stream()
                .map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}