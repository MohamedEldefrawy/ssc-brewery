package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.repositories.BeerRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BeerControllerIT extends BaseIT {
    @Mock
    BeerRepository beerRepository;


    @SneakyThrows
    @Test
    void findBeersWithHttpBasicWithAdminUser() {
        mockMvc.perform(get("/beers/find").with(httpBasic("spring", "1234")))
                .andExpect(status().isOk()).andExpect(view().name("beers/findBeers"))
                .andExpect(model().attributeExists("beer"));
        verifyNoInteractions(beerRepository);

    }


    @SneakyThrows
    @Test
    void findBeersWithHttpBasicWithCustomerUser() {
        mockMvc.perform(get("/beers/find").with(httpBasic("scott", "1234")))
                .andExpect(status().isOk()).andExpect(view().name("beers/findBeers"))
                .andExpect(model().attributeExists("beer"));
        verifyNoInteractions(beerRepository);

    }
}
