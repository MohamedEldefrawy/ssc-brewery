package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.Role;
import guru.sfg.brewery.domain.User;
import guru.sfg.brewery.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class UserDataLoader implements CommandLineRunner {
    private final UserRepository userRepository;

    void loadUsers() {
        User admin = User.builder().username("spring").role(Role.builder().name("ROLE_ADMIN").build()).password("{bcrypt}$2a$10$l/zJs2Mz/UDMxTxoHCrBiebKmlobzr2CIHs1zE.IA6BXSWRkVHCzK").build();
        User customer = User.builder().username("scott").role(Role.builder().name("ROLE_CUSTOMER").build()).password("{sha256}17cf776648dce8a2065ba5b6085901740a7f54939985bb887686c29050f19ad682cced7908ccfcd9").build();
        User user = User.builder().username("user").role(Role.builder().name("ROLE_USER").build()).password("{sha256}17cf776648dce8a2065ba5b6085901740a7f54939985bb887686c29050f19ad682cced7908ccfcd9").build();

        userRepository.save(admin);
        userRepository.save(user);
        userRepository.save(customer);
        log.debug(userRepository.count() + "users loaded.");
    }

    @Override
    public void run(String... args) throws Exception {
        loadUsers();
    }
}
