package guru.sfg.brewery.seeder;

import guru.sfg.brewery.domain.Authority;
import guru.sfg.brewery.domain.Role;
import guru.sfg.brewery.domain.User;
import guru.sfg.brewery.repositories.AuthorityRepository;
import guru.sfg.brewery.repositories.RoleRepository;
import guru.sfg.brewery.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
@Slf4j
public class UseSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;

    void loadUsers() {
        // Beer Authorities
        Authority createBeer = this.authorityRepository.saveAndFlush(Authority.builder().permission("create.beer").build());
        Authority deleteBeer = this.authorityRepository.saveAndFlush(Authority.builder().permission("delete.beer").build());
        Authority updateBeer = this.authorityRepository.saveAndFlush(Authority.builder().permission("update.beer").build());
        Authority getBeer = this.authorityRepository.saveAndFlush(Authority.builder().permission("get.beer").build());

        // Brewery Authorities
        Authority createBrewery = this.authorityRepository.saveAndFlush(Authority.builder().permission("create.brewery").build());
        Authority deleteBrewery = this.authorityRepository.saveAndFlush(Authority.builder().permission("delete.brewery").build());
        Authority updateBrewery = this.authorityRepository.saveAndFlush(Authority.builder().permission("update.brewery").build());
        Authority getBrewery = this.authorityRepository.saveAndFlush(Authority.builder().permission("get.brewery").build());


        // Customer Authorities
        Authority createCustomer = this.authorityRepository.saveAndFlush(Authority.builder().permission("create.customer").build());
        Authority deleteCustomer = this.authorityRepository.saveAndFlush(Authority.builder().permission("delete.customer").build());
        Authority updateCustomer = this.authorityRepository.saveAndFlush(Authority.builder().permission("update.customer").build());
        Authority getCustomer = this.authorityRepository.saveAndFlush(Authority.builder().permission("get.customer").build());


        // Create roles
        Role roleAdmin = this.roleRepository.save(Role.builder().name("ROLE_ADMIN").build());
        Role roleUser = this.roleRepository.save(Role.builder().name("ROLE_USER").build());
        Role roleCustomer = this.roleRepository.save(Role.builder().name("ROLE_CUSTOMER").build());

        roleAdmin.setAuthorities(Set.of(createBeer, deleteBeer, updateBeer, getBeer,
                createCustomer, updateCustomer, getCustomer, deleteCustomer,
                createBrewery, getBrewery, updateBrewery, deleteBrewery));
        roleUser.setAuthorities(Set.of(getBeer, getCustomer, getBrewery));
        roleCustomer.setAuthorities(Set.of(getBeer, getCustomer, getBrewery));

        roleRepository.saveAll(List.of(roleAdmin, roleCustomer, roleUser));

        // Create users
        User admin = this.userRepository.save(User.builder().username("spring").password("{bcrypt}$2a$10$l/zJs2Mz/UDMxTxoHCrBiebKmlobzr2CIHs1zE.IA6BXSWRkVHCzK").build());
        User user = this.userRepository.save(User.builder().username("user").password("{sha256}17cf776648dce8a2065ba5b6085901740a7f54939985bb887686c29050f19ad682cced7908ccfcd9").build());
        User customer = this.userRepository.save(User.builder().username("scott").password("{sha256}17cf776648dce8a2065ba5b6085901740a7f54939985bb887686c29050f19ad682cced7908ccfcd9").build());

        admin.setRoles(Set.of(roleAdmin));
        user.setRoles(Set.of(roleUser));
        customer.setRoles(Set.of(roleCustomer));

        this.userRepository.saveAll(List.of(admin, customer, user));
        log.debug(userRepository.count() + "users loaded.");
    }

    @Override
    public void run(String... args) {
        loadUsers();
    }
}
