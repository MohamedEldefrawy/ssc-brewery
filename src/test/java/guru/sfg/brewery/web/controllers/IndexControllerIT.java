package guru.sfg.brewery.web.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IndexControllerIT extends BaseIT {
    @SneakyThrows
    @Test
    void getIndexSlash() {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}
