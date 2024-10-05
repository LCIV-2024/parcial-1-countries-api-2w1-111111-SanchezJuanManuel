package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    ResponseEntity<List<CountryDTO>> getCountries() {

        List<Country> countries = countryService.getAllCountries();
        List<CountryDTO> countriesDtoList = new ArrayList<>();

        for(Country country : countries) {
            CountryDTO countryDto = new CountryDTO();
            countryDto.setCode(country.getCode());
            countryDto.setName(country.getName());
            countriesDtoList.add(countryDto);
        }

        return ResponseEntity.ok(countriesDtoList);
    }
    
    @GetMapping("/?name={name}")
    public ResponseEntity<CountryDTO> getCountryByName(@PathVariable("name") @RequestParam String name) {

        CountryDTO countryDto = countryService.getCountryByName(name);

        return ResponseEntity.ok(countryDto);
    }

    @GetMapping("/?code={code}")
    public ResponseEntity<CountryDTO> getCountryByCode (@PathVariable("code") @RequestParam String code) {

        CountryDTO countryDto = countryService.getCountryByCode(code);

        return ResponseEntity.ok(countryDto);
    }

    @GetMapping("/{continent}/continent")
    public ResponseEntity<List<CountryDTO>> getCountriesByContinent(@PathVariable String continent) {

        List<CountryDTO> countries = countryService.getCountriesByContinent(continent);

        return ResponseEntity.ok(countries);
    }

    @GetMapping("/{language}/language")
    public ResponseEntity<List<CountryDTO>> getCountriesByLanguages(@PathVariable String language) {

        List<CountryDTO> countries = countryService.getCountriesByLanguage(language);

        return ResponseEntity.ok(countries);
    }




}