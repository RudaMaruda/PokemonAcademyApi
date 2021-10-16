package pl.sdaacademy.PokemonAcademyApi.pokemonlist;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PokemonListLoader implements CommandLineRunner {

    private final PokemonListService pokemonListService;

    public PokemonListLoader(PokemonListService pokemonListService) {
        this.pokemonListService = pokemonListService;
    }

    // zaladowanie listy i zapisanie ich do bazy danych
    @Override
    public void run(String... args) throws Exception {
        pokemonListService.getPokemonList();
    }
}
