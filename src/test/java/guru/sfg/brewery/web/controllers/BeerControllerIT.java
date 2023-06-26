package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.services.BeerService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class BeerControllerIT {
    @Autowired
    WebApplicationContext webApplicationContext;
    MockMvc mockMvc;
    @Mock
    BeerRepository beerRepository;
    @Mock
    BeerService beerService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

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
    void findBeersWithHttpBasic() {
        mockMvc.perform(get("/beers/find").with(httpBasic("admin","1234")))
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
