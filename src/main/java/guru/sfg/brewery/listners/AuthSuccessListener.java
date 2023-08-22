package guru.sfg.brewery.listners;

import guru.sfg.brewery.domain.LoginSuccess;
import guru.sfg.brewery.domain.User;
import guru.sfg.brewery.repositories.security.LoginSuccessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthSuccessListener {
    private final LoginSuccessRepository loginSuccessRepository;

    @EventListener
    public void listen(AuthenticationSuccessEvent authenticationSuccessEvent) {
        log.debug("User logged in successfully");

        if (authenticationSuccessEvent.getSource() instanceof UsernamePasswordAuthenticationToken) {
            LoginSuccess.LoginSuccessBuilder loginSuccessBuilder = LoginSuccess.builder();
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authenticationSuccessEvent.getSource();
            if (token.getPrincipal() instanceof User) {
                User user = (User) token.getPrincipal();
                loginSuccessBuilder.user(user);
                var loggedInUser = this.loginSuccessRepository.save(loginSuccessBuilder.build());

                log.debug("Logged in username: " + user.getUsername());
                log.debug("Logged in user id: " + loggedInUser.getId());
            }

            if (token.getDetails() instanceof WebAuthenticationDetails) {
                WebAuthenticationDetails details = (WebAuthenticationDetails) token.getDetails();
                log.debug("Logged in user address: " + details.getRemoteAddress());
            }
        }

    }
}
