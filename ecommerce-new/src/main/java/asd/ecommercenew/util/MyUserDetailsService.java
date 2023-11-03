package asd.ecommercenew.util;

import asd.ecommercenew.entity.Role;
import asd.ecommercenew.entity.User;
import asd.ecommercenew.repository.RoleRepository;
import asd.ecommercenew.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    private final RoleRepository roleRepository;

    @Override
    public synchronized MyUserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepo.findByUsername(userName))
                .map(MyUserDetails::new)
                .orElse(null);
    }

    public MyUserDetails cloneNewUser(JwtAuthenticationToken jwtAuthToken) {
        User newUser = new User();
        newUser.setEmail((String)jwtAuthToken.getTokenAttributes().get("email"));
        newUser.setUsername((String)jwtAuthToken.getTokenAttributes().get("preferred_username"));
        newUser.setFirstName((String)jwtAuthToken.getTokenAttributes().get("given_name"));
        newUser.setLastName((String)jwtAuthToken.getTokenAttributes().get("family_name"));
        newUser.setExternalId((String)jwtAuthToken.getTokenAttributes().get("sid"));
        newUser.setExternalType("KEY_CLOAK");
        newUser.setActive(true);
        List<Role> roles = new ArrayList<>();
        Map<String, Object> realmAccess = (Map<String, Object>) jwtAuthToken.getTokenAttributes().get("realm_access");
        Collection<String> authorities = new ArrayList<>();
        if (realmAccess != null) {
            if (realmAccess.containsKey("roles") && realmAccess.get("roles") instanceof Collection) {
                for (Object role : (Collection<?>) realmAccess.get("roles")) {
                    authorities.add((String)role);
                }
            }
        }
        roleRepository.findAll().forEach(role -> {
            if (authorities.contains(role.getRole()))
                roles.add(role);
        });

        User savedUser = userRepo.save(newUser);
        return new MyUserDetails(savedUser);
    }
}
