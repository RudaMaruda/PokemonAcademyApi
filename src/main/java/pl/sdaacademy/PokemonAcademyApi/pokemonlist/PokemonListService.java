package pl.sdaacademy.PokemonAcademyApi.pokemonlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
class PokemonListService {
    private final PokemonRepository pokemonRepository;
private final PokemonTransformer pokemonTransformer;
    private final PokemonListNetworkRepository pokemonListNetworkRepository;


    @Autowired
    PokemonListService(PokemonTransformer pokemonTransformer,PokemonListNetworkRepository pokemonListNetworkRepository,PokemonRepository pokemonRepository) {
        this.pokemonListNetworkRepository = pokemonListNetworkRepository;
        this.pokemonRepository = pokemonRepository;
        this.pokemonTransformer = pokemonTransformer;
    }

    List<Pokemon> getPokemonList(){
        if(pokemonRepository.count() != 0 ){
            return pokemonRepository.findAll();
        }
        final List<Pokemon> pokemons = new ArrayList<>();
        int offset = 0;
        int limit =100;
        PokemonListResult pokemonListResult;

// w logice biznesowej- konwersja z jednego typu na drugi
        do{
            pokemonListResult = pokemonListNetworkRepository.fetchPokemonList(offset,limit);
            pokemons.addAll(pokemonListResult.getResults().stream()
            .map(pokemonTransformer::toEntity)
            .collect(Collectors.toList()));
            offset+=limit;

        } while (pokemonListResult.getNext() != null);
        pokemonRepository.saveAll(pokemons);
        return pokemons;

    }
}