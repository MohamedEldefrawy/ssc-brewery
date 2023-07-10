package guru.sfg.brewery.web.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BreweryControllerIT extends BaseIT {

    @SneakyThrows
    @Test
    void findBreweriesWithHttpBasicWithAdminRole() {
        mockMvc.perform(get("/brewery/breweries").with(httpBasic("spring", "1234")))
                .andExpect(status().isOk()).andExpect(view().name("breweries/index"))
                .andExpect(model().attributeExists("breweries"));
    }

    @SneakyThrows
    @Test
    void findBreweriesWithHttpBasicWithCustomerRole() {
        mockMvc.perform(get("/brewery/breweries").with(httpBasic("scott", "1234")))
                .andExpect(status().isOk()).andExpect(view().name("breweries/index"))
                .andExpect(model().attributeExists("breweries"));
    }

    @SneakyThrows
    @Test
    void findBreweriesWithHttpBasicWithUserRole() {
        mockMvc.perform(get("/brewery/breweries").with(httpBasic("user", "1234")))
                .andExpect(status().isForbidden());
    }
}
