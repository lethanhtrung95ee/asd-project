package asd.ecommercenew.security;

import asd.ecommercenew.util.MyUserDetails;
import asd.ecommercenew.util.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@EnableWebSecurity
public class SecurityConfiguration extends OncePerRequestFilter {

    private final MyUserDetailsService myUserDetailsService;

    public SecurityConfiguration(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .mvcMatchers("/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new GrantedAuthoritiesExtractor());
        return jwtAuthenticationConverter;
    }

    public synchronized MyUserDetails getCurrentUser(){
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map( context -> {
                    if(context.getAuthentication() instanceof UsernamePasswordAuthenticationToken authToken){
                        return (MyUserDetails) authToken.getPrincipal();
                    }else if(context.getAuthentication() instanceof JwtAuthenticationToken jwtAuthToken){
                        String userName = (String) jwtAuthToken.getTokenAttributes().get("preferred_username");

                        MyUserDetails userDetails = myUserDetailsService.loadUserByUsername(userName);
                        if(userDetails == null)
                            userDetails = myUserDetailsService.cloneNewUser(jwtAuthToken);
                        return userDetails;
                    }else{
                        return null;
                    }
                })
                .orElse(null);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        getCurrentUser();
        filterChain.doFilter(request,response);
    }
}
