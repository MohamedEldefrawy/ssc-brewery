package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@SpringBootTest
public class EncryptionTest {
    private static final String password = "1234";

    @Test
    void bcryptEncryptionTest()
    {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode(password));
    }

    @Test
    void sha256EncryptionTest()
    {
        PasswordEncoder passwordEncoder = new StandardPasswordEncoder();
        System.out.println(passwordEncoder.encode(password));
    }

    @Test
    void ldapEncryptionTest()
    {
        PasswordEncoder passwordEncoder = new LdapShaPasswordEncoder();
        System.out.println(passwordEncoder.encode(password));
    }
}
