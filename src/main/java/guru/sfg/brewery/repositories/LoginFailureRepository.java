package guru.sfg.brewery.repositories;

import guru.sfg.brewery.domain.LoginFailure;
import guru.sfg.brewery.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface LoginFailureRepository extends JpaRepository<LoginFailure, Integer> {
    List<LoginFailure> findAllByUserAndCreatedDateIsAfter(User user, Timestamp timestamp);
}
