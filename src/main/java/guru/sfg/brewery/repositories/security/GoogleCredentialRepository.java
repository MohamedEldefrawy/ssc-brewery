package guru.sfg.brewery.repositories.security;

import com.warrenstrange.googleauth.ICredentialRepository;
import guru.sfg.brewery.domain.User;
import guru.sfg.brewery.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class GoogleCredentialRepository implements ICredentialRepository {
    private final UserRepository userRepository;

    @Override
    public String getSecretKey(String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow();
        return user.getGoogle2FaSecret();
    }

    @Override
    public void saveUserCredentials(String userName, String secretKey, int i, List<Integer> list) {
        User user = userRepository.findByUsername(userName).orElseThrow();
        user.setGoogle2FaSecret(secretKey);
        user.setUserGoogle2Fa(true);
        userRepository.save(user);

    }
}
