package guru.sfg.brewery.config;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig;
import com.warrenstrange.googleauth.ICredentialRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class GoogleAuthConfig {
    @Bean
    public GoogleAuthenticator googleAuthenticator(ICredentialRepository credentialRepository) {
        GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder configBuilder
                = new GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder();

        configBuilder
                .setTimeStepSizeInMillis(TimeUnit.SECONDS.toMillis(60))
                .setWindowSize(10)
                .setNumberOfScratchCodes(0);

        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator(configBuilder.build());
        googleAuthenticator.setCredentialRepository(credentialRepository);
        return googleAuthenticator;

    }
}
