package pl.sdaacademy.PokemonAcademyApi.pokemonlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import pl.sdaacademy.PokemonAcademyApi.pokemondetails.PokemonDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonListService {

    private final PokemonDetailsService pokemonDetailsService;
    private final PokemonRepository pokemonRepository;
    private final PokemonTransformer pokemonTransformer;
    private final PokemonListNetworkRepository pokemonListNetworkRepository;

    private final PokemonListItemTransformer pokemonListItemTransformer;

    @Autowired
    public PokemonListService(PokemonDetailsService pokemonDetailsService, PokemonRepository pokemonRepository, PokemonTransformer pokemonTransformer, PokemonListNetworkRepository pokemonListNetworkRepository, PokemonListItemTransformer pokemonlistItemTransformer) {
        this.pokemonDetailsService = pokemonDetailsService;
        this.pokemonRepository = pokemonRepository;
        this.pokemonTransformer = pokemonTransformer;
        this.pokemonListNetworkRepository = pokemonListNetworkRepository;
        this.pokemonListItemTransformer = pokemonlistItemTransformer;
    }

    public List<Pokemon> getPokemonList() {
        if (pokemonRepository.count() != 0) {
            return pokemonRepository.findAll();
        }
        final List<Pokemon> pokemons = new ArrayList<>();
        int offset = 0;
        int limit = 100;
        PokemonListResult pokemonListResult;

// w logice biznesowej- konwersja z jednego typu na drugi
        do {
            pokemonListResult = pokemonListNetworkRepository.fetchPokemonList(offset, limit);
            pokemons.addAll(pokemonListResult.getResults().stream()
                    .map(pokemonTransformer::toEntity)
                    .collect(Collectors.toList()));
            offset += limit;

        } while (pokemonListResult.getNext() != null);
        pokemonRepository.saveAll(pokemons);
        return pokemons;
    }

    // metoda ktora bedzie pobierala liste pokemonow z obrazkami
    public List<PokemonListItem> getPokemonListItem() {
        final List<PokemonListItem> pokemonListItems = new ArrayList<>();
        //pobierzemy tylko 20 rekordow
        int offset = 0;
        int limit = 20;
        // obiekt ktory definiuje ile rekordow powinno byc pobrane z bazy danych, wtedy metoda findAll zwraca nam tylko pierwsze 20 rekordow
        Pageable pageable = (Pageable) PageRequest.of(offset, limit);
        List<Pokemon> pokemons = pokemonRepository.findAll(pageable).getContent();
        return pokemons.stream()
                .map(pokemon -> pokemonDetailsService.getPokemonDetails(pokemon.getName()))
                .map(pokemonNewDetails -> {
                    return pokemonListItemTransformer.toEntity(pokemonNewDetails);
                })
                .collect(Collectors.toList());
    }
}
