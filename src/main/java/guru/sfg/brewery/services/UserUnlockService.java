package guru.sfg.brewery.services;

import guru.sfg.brewery.domain.User;
import guru.sfg.brewery.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by jt on 7/21/20.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserUnlockService {

    private final UserRepository userRepository;

    @Scheduled(fixedRate = 5000)
    public void unlockAccounts() {
        log.debug("Running Unlock Accounts");

        List<User> lockedUsers = userRepository
                .findAllByAccountNonLockedAndLastModifiedDateIsBefore(false,
                        Timestamp.valueOf(LocalDateTime.now().minusSeconds(30)));

        if (!lockedUsers.isEmpty()) {
            log.debug("Locked Accounts Found, Unlocking");
            lockedUsers.forEach(user -> user.setAccountNonLocked(true));

            userRepository.saveAll(lockedUsers);
        }
    }

}