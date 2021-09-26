package pl.sdaacademy.PokemonAcademyApi.pokemonlist;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
class PokemonListNetworkRepository {

    private final static String ENDPOINT = "pokemon/?offset=%d&limit=%d";
    private final String endpointUrl;
    private final RestTemplate restTemplate;

    PokemonListNetworkRepository(@Value("${pokeapi.url}") String baseUrl, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.endpointUrl = baseUrl + ENDPOINT;
    }

    PokemonListResult fetchPokemonList(int offset, int limit) {
        String url = String.format(endpointUrl, offset, limit);
        return restTemplate.getForObject(url, PokemonListResult.class);
    }


}
