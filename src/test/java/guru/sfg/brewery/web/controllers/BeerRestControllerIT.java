package guru.sfg.brewery.web.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BeerRestControllerIT extends BaseIT {

    @Test
    @SneakyThrows
    void testGetBeers()
    {
        mockMvc.perform(get("/api/v1/beer")).andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testGetBeerById()
    {
        mockMvc.perform(get("/api/v1/beerUpc/0631234200036")).andExpect(status().isOk());
    }
}
