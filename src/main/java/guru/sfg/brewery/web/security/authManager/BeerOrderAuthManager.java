package guru.sfg.brewery.web.security.authManager;

import guru.sfg.brewery.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class BeerOrderAuthManager {
    public boolean customerIdMatcher(Authentication authentication, UUID customerId) {
        User authintecatedUser = (User) authentication.getPrincipal();
        log.debug(String.format("Auth user customer Id: %s", authintecatedUser.getCustomer().getId()), "customerId: " + customerId);
        return authintecatedUser.getCustomer().getId().equals(customerId);
    }
}
