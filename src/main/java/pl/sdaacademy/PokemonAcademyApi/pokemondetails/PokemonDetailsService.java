package pl.sdaacademy.PokemonAcademyApi.pokemondetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sdaacademy.PokemonAcademyApi.pokemonlist.Pokemon;
import pl.sdaacademy.PokemonAcademyApi.pokemonlist.PokemonRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonDetailsService {
    private final PokemonDetailsNetworkRepository pokemonDetailsNetworkRepository;
    private final PokemonRepository pokemonRepository;
    private final PokemonTransformerDetailResponse pokemonTransformerDetailResponse;
    private final PokemonDetailsRepository pokemonDetailsRepository;

    @Autowired
    public PokemonDetailsService(PokemonDetailsNetworkRepository pokemonDetailsNetworkRepository, PokemonRepository pokemonRepository, PokemonTransformerDetailResponse pokemonTransformerDetailResponse, PokemonDetailsRepository pokemonDetailsRepository) {
        this.pokemonDetailsNetworkRepository = pokemonDetailsNetworkRepository;
        this.pokemonRepository = pokemonRepository;
        this.pokemonTransformerDetailResponse = pokemonTransformerDetailResponse;
        this.pokemonDetailsRepository = pokemonDetailsRepository;
    }

    // parametr zadania: bulbasaur/pikachu/chalender
    public List<PokemonNewDetails> getListOfPokemonDetails(String pokemonNames) {
        String[] names = pokemonNames.split(",");
        return Arrays.stream(names).map(name -> {
            return getPokemonDetails(name);
        }).collect(Collectors.toList());
    }

    public PokemonNewDetails getPokemonDetails(String pokemonName) {
        //mechanizm cachowania, sprawdzamy czy nasze repozytorium z detalami juz zawiera te detale
        return pokemonDetailsRepository.findById(pokemonName).orElseGet(() -> {
            Pokemon pokemon = pokemonRepository.findByName(pokemonName).orElseThrow(() -> new NoPokemonFoundException(pokemonName));
            PokemonDetailsResponse pokemonDetailsResponse = pokemonDetailsNetworkRepository.fetchPokemonDetails(pokemon.getId());
            PokemonNewDetails pokemonNewDetails = pokemonTransformerDetailResponse.toEntity(pokemonDetailsResponse);
            return pokemonDetailsRepository.save(pokemonNewDetails);
        });
    }
}
