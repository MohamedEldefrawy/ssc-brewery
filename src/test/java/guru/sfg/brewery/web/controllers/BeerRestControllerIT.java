package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.repositories.beer.BeerRepository;
import guru.sfg.brewery.web.model.BeerStyleEnum;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BeerRestControllerIT extends BaseIT {

    @Autowired
    BeerRepository beerRepository;

    @Test
    @SneakyThrows
    void testGetBeers() {
        mockMvc.perform(get("/api/v1/beer").header("Api-Key", "spring").header("Api-Secret", "1234"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testGetBeerByUpc() {
        mockMvc.perform(get("/api/v1/beerUpc/0631234200036")
                        .header("Api-Key", "spring").header("Api-Secret", "1234"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testGetBeerById() {
        mockMvc.perform(get("/api/v1/beer/a3e60036-fe96-4768-a503-a7bb094bf9e1")
                        .header("Api-Key", "spring").header("Api-Secret", "1234"))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void deleteBeerWithHttpBasicWithAdminRoleSadScenario() {
        Beer createdBeer = createBear();
        mockMvc.perform(delete("/api/v1/beer/" + createdBeer.getId()).with(httpBasic("scott", "1234")))
                .andExpect(status().isForbidden());

    }

    @SneakyThrows
    @Test
    void deleteBeerWithHttpBasicWithAdminRoleHappyScenario() {
        Beer createdBeer = createBear();
        mockMvc.perform(delete("/api/v1/beer/" + createdBeer.getId()).with(httpBasic("spring", "1234")))
                .andExpect(status().isNoContent());

    }

    private Beer createBear() {
        Random random = new Random();
        return beerRepository.saveAndFlush(Beer.builder().beerName("test").upc(String.valueOf(random.nextInt(9999999))).quantityToBrew(200)
                .minOnHand(12).beerStyle(BeerStyleEnum.IPA)
                .build());
    }
}
