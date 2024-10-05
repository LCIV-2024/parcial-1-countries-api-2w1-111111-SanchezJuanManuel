package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

        private final CountryRepository countryRepository;

        private final RestTemplate restTemplate;

        public List<Country> getAllCountries() {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                return response.stream().map(this::mapToCountry).collect(Collectors.toList());
        }

        /**
         * Agregar mapeo de campo cca3 (String)
         * Agregar mapeo campos borders ((List<String>))
         */
        private Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
                return Country.builder()
                        .name((String) nameData.get("common"))
                        .code((String) countryData.get("cca3"))
                        .population(((Number) countryData.get("population")).longValue())
                        .area(((Number) countryData.get("area")).doubleValue())
                        .region((String) countryData.get("region"))
                        .languages((Map<String, String>) countryData.get("languages"))
                        .borders((List<String>) countryData.get("borders"))
                        .build();
        }


        private CountryDTO mapToDTO(Country country) {
                return new CountryDTO(country.getCode(), country.getName());
        }

        public CountryDTO getCountryByName(String name) {

                List<Country> countries = this.getAllCountries();
                CountryDTO countryDto = new CountryDTO();
                for (Country country : countries) {
                        if (country.getName().equals(name)) {
                                countryDto = this.mapToDTO(country);
                        }
                }

                return countryDto;
        }

        public CountryDTO getCountryByCode(String code) {
                List<Country> countries = this.getAllCountries();
                CountryDTO countryDto = new CountryDTO();
                for(Country country : countries) {
                        if (country.getCode().equals(code)) {
                                countryDto = this.mapToDTO(country);
                        }
                }

                return countryDto;
        }

        public List<CountryDTO> getCountriesByContinent(String continent) {

                List<Country> countries = this.getAllCountries();
                List<CountryDTO> countryDtoList = new ArrayList<>();

                for (Country country : countries) {
                        if(country.getRegion().equals(continent)) {
                                CountryDTO countryDto = this.mapToDTO(country);
                                countryDtoList.add(countryDto);
                        }
                }

                return countryDtoList;
        }

        public List<CountryDTO> getCountriesByLanguage(String language) {

                List<Country> countries = this.getAllCountries();

                List<CountryDTO> countryDtoList = new ArrayList<>();

                for (Country country : countries) {
                        if(country.getLanguages().equals(language) && !country.getLanguages().isEmpty()) {
                                CountryDTO countryDto = this.mapToDTO(country);
                                countryDtoList.add(countryDto);
                        }
                }

                return countryDtoList;
        }
        
        public CountryDTO getCountryMostBorders() {
                
                Integer borders = 0;
                List<Country> countries = this.getAllCountries();
                
                CountryDTO countryDto = new CountryDTO();
                
                for(Country country : countries) {
                        //La primera comparación va a ser true, ya que inicialmente el contador
                        //borders es 0, es decir, que borders tendrá el valor de border del primer 
                        // pais de la lista. Las siguientes comparaciones el valor de 'borders' cambiará
                        // según la cantidad de paises limitrofes que tenga el pais que se esta iterando.
                        if (country.getBorders().size() > borders) {
                                borders = country.getBorders().size();
                                countryDto = this.mapToDTO(country);
                        }
                }

                return countryDto;
        }
}