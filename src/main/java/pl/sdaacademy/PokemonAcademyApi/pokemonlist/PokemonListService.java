package pl.sdaacademy.PokemonAcademyApi.pokemonlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PokemonListService {
    private final PokemonRepository pokemonRepository;

    private final PokemonListNetworkRepository pokemonListNetworkRepository;


    @Autowired
    public PokemonListService(PokemonListNetworkRepository pokemonListNetworkRepository,PokemonRepository pokemonRepository) {
        this.pokemonListNetworkRepository = pokemonListNetworkRepository;
        this.pokemonRepository = pokemonRepository;
    }

    public List<Pokemon> getPokemonList(){

        final List<Pokemon> pokemons = new ArrayList<>();
        int offset = 0;
        int limit =100;
        PokemonListResult pokemonListResult;
        if(pokemonRepository.count() != 0 ){
            return pokemonRepository.findAll();
        }

        do{
            pokemonListResult = pokemonListNetworkRepository.fetchPokemonList(offset,limit);
            pokemonListResult.getResults().forEach(pokemon -> {
                String[] urlData = pokemon.getUrl().split("/");
                pokemon.setId(Integer.parseInt(urlData[urlData.length-1]));
            });
            pokemons.addAll(pokemonListResult.getResults());
            offset+=limit;

        } while (pokemonListResult.getNext() != null);
        pokemonRepository.saveAll(pokemons);
        return pokemons;

    }
}
