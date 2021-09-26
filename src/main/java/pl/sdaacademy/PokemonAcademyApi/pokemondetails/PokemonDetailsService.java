package pl.sdaacademy.PokemonAcademyApi.pokemondetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sdaacademy.PokemonAcademyApi.pokemonlist.Pokemon;
import pl.sdaacademy.PokemonAcademyApi.pokemonlist.PokemonRepository;

@Service
public class PokemonDetailsService {
    private final PokemonDetailsNetworkRepository pokemonDetailsNetworkRepository;
    private final PokemonRepository pokemonRepository;
    private final PokemonTransformerDetailResponse pokemonTransformerDetailResponse;

    @Autowired
    public PokemonDetailsService(PokemonDetailsNetworkRepository pokemonDetailsNetworkRepository, PokemonRepository pokemonRepository, PokemonTransformerDetailResponse pokemonTransformerDetailResponse) {
        this.pokemonDetailsNetworkRepository = pokemonDetailsNetworkRepository;
        this.pokemonRepository = pokemonRepository;
        this.pokemonTransformerDetailResponse = pokemonTransformerDetailResponse;
    }


    public PokemonNewDetails getPokemonDetails(String pokemonName) {
        Pokemon pokemon = pokemonRepository.findByName(pokemonName).orElseThrow(() -> {
            return new NoPokemonFoundException(pokemonName);

        });
        PokemonDetailsResponse pokemonDetailsResponse = pokemonDetailsNetworkRepository.fetchPokemonDetails(pokemon.getId());
        PokemonNewDetails pokemonNewDetails = pokemonTransformerDetailResponse.toEntity(pokemonDetailsResponse);
        return pokemonNewDetails;
    }
}
