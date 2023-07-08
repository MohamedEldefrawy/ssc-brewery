package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.web.model.BeerStyleEnum;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Random;

import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BeerControllerIT extends BaseIT {

    @Autowired
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
    void findBeersWithHttpBasicWithAdminUser() {
        mockMvc.perform(get("/beers/find").with(httpBasic("spring", "1234")))
                .andExpect(status().isOk()).andExpect(view().name("beers/findBeers"))
                .andExpect(model().attributeExists("beer"));
        verifyZeroInteractions(beerRepository);

    }


    @SneakyThrows
    @Test
    void findBeersWithHttpBasicWithCustomerUser() {
        mockMvc.perform(get("/beers/find").with(httpBasic("scott", "1234")))
                .andExpect(status().isOk()).andExpect(view().name("beers/findBeers"))
                .andExpect(model().attributeExists("beer"));
        verifyZeroInteractions(beerRepository);

    }

    @SneakyThrows
    @Test
    void findBreweriesWithHttpBasicWithAdminRole() {
        mockMvc.perform(get("/brewery/breweries").with(httpBasic("spring", "1234")))
                .andExpect(status().isOk()).andExpect(view().name("breweries/index"))
                .andExpect(model().attributeExists("breweries"));
        verifyZeroInteractions(beerRepository);

    }

    @SneakyThrows
    @Test
    void findBreweriesWithHttpBasicWithCustomerRole() {
        mockMvc.perform(get("/brewery/breweries").with(httpBasic("scott", "1234")))
                .andExpect(status().isOk()).andExpect(view().name("breweries/index"))
                .andExpect(model().attributeExists("breweries"));
        verifyZeroInteractions(beerRepository);

    }

    @SneakyThrows
    @Test
    void findBreweriesWithHttpBasicWithUserRole() {
        mockMvc.perform(get("/brewery/breweries").with(httpBasic("user", "1234")))
                .andExpect(status().isForbidden());
        verifyZeroInteractions(beerRepository);

    }

    @SneakyThrows
    @Test
    void getIndexSlash() {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}
