package guru.sfg.brewery.repositories;

import guru.sfg.brewery.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
