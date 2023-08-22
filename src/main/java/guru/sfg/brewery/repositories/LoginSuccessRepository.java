package guru.sfg.brewery.repositories;

import guru.sfg.brewery.domain.LoginSuccess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginSuccessRepository extends JpaRepository<LoginSuccess, Integer> {
}