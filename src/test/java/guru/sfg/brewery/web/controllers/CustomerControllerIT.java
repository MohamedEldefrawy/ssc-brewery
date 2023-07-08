package guru.sfg.brewery.web.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerIT extends BaseIT {
    @SneakyThrows
    @ParameterizedTest
    @CsvSource({"spring,1234", "scott,1234", "user,1234"})
    void findBreweriesWithHttpBasicAuthorization(String username, String password) {
        if (username.equals("spring") || username.equals("scott"))
            mockMvc.perform(get("/customers").with(httpBasic(username, password)))
                    .andExpect(status().isOk());
        else
            mockMvc.perform(get("/customers").with(httpBasic(username, password)))
                    .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    void findBreweriesNotLoggedIn() {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isUnauthorized());
    }
}
