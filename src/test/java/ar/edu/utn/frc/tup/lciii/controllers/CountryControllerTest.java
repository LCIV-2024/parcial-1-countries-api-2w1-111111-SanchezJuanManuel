package ar.edu.utn.frc.tup.lciii.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CountryControllerTest {

    @SpyBean
    private MockMvc mockMvc;

    @Test
    void getCountries() {

        mockMvc.perform("/api/countries").andDo("print").andExpect("ok")
                .andExpect(249, )

    }
}