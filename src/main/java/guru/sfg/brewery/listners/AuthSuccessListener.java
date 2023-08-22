package guru.sfg.brewery.listners;

import guru.sfg.brewery.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthSuccessListener {
    @EventListener
    public void listen(AuthenticationSuccessEvent authenticationSuccessEvent) {
        log.debug("User logged in successfully");
        if (authenticationSuccessEvent.getSource() instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authenticationSuccessEvent.getSource();
            if (token.getPrincipal() instanceof User) {
                User user = (User) token.getPrincipal();
                log.debug("Logged in username: " + user.getUsername());
            }

            if (token.getDetails() instanceof WebAuthenticationDetails) {
                WebAuthenticationDetails details = (WebAuthenticationDetails) token.getDetails();
                log.debug("Logged in user address: " + details.getRemoteAddress());
            }
        }
    }
}
