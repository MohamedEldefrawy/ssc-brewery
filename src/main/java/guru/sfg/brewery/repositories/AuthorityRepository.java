package guru.sfg.brewery.repositories;

import guru.sfg.brewery.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
