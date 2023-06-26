package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.repositories.BeerRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BeerControllerIT extends BaseIT {

    @Mock
    BeerRepository beerRepository;


    @SneakyThrows
    @Test
    @WithMockUser("admin")
    void findBeers() {
        mockMvc.perform(get("/beers/find"))
                .andExpect(status().isOk()).andExpect(view().name("beers/findBeers"))
                .andExpect(model().attributeExists("beer"));
        verifyZeroInteractions(beerRepository);

    }

    @SneakyThrows
    @Test
    void findBeersWithHttpBasicWithADminUser() {
        mockMvc.perform(get("/beers/find").with(httpBasic("admin", "1234")))
                .andExpect(status().isOk()).andExpect(view().name("beers/findBeers"))
                .andExpect(model().attributeExists("beer"));
        verifyZeroInteractions(beerRepository);

    }

    @SneakyThrows
    @Test
    void findBeersWithHttpBasicWithCustomerUser() {
        mockMvc.perform(get("/beers/find").with(httpBasic("customer", "1234")))
                .andExpect(status().isOk()).andExpect(view().name("beers/findBeers"))
                .andExpect(model().attributeExists("beer"));
        verifyZeroInteractions(beerRepository);

    }

    @SneakyThrows
    @Test
    void getIndexSlash() {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}
