package guru.sfg.brewery.web.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BeerRestControllerIT extends BaseIT {

    @Test
    @SneakyThrows
    void testGetBeers() {
        mockMvc.perform(get("/api/v1/beer").header("Api-Key", "admin").header("Api-Secret", "1234"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testGetBeerByUpc() {
        mockMvc.perform(get("/api/v1/beerUpc/0631234200036")
                        .header("Api-Key", "admin").header("Api-Secret", "1234"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testGetBeerById() {
        mockMvc.perform(get("/api/v1/beer/a3e60036-fe96-4768-a503-a7bb094bf9e1")
                .header("Api-Key", "admin").header("Api-Secret", "1234")).andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testDeleteBeerById() {
        mockMvc.perform(delete("/api/v1/beer/06aed155-336c-4e04-aca4-fd8da0e6c772")
                        .header("Api-Key", "admin").header("Api-Secret", "1234"))
                .andExpect(status().isOk());
    }

}
