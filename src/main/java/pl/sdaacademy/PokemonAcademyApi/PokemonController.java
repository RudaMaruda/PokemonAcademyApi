package pl.sdaacademy.PokemonAcademyApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sdaacademy.PokemonAcademyApi.pokemondetails.NoPokemonFoundException;
import pl.sdaacademy.PokemonAcademyApi.pokemondetails.PokemonDetailsService;
import pl.sdaacademy.PokemonAcademyApi.pokemondetails.PokemonNewDetails;
import pl.sdaacademy.PokemonAcademyApi.pokemonlist.Pokemon;
import pl.sdaacademy.PokemonAcademyApi.pokemonlist.PokemonListEnvelop;
import pl.sdaacademy.PokemonAcademyApi.pokemonlist.PokemonListItem;
import pl.sdaacademy.PokemonAcademyApi.pokemonlist.PokemonListService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/pokemon")
class PokemonController {
    private final PokemonListService pokemonListService;
    private final PokemonDetailsService pokemonDetailsService;

    @Autowired
    PokemonController(PokemonListService pokemonListService, PokemonDetailsService pokemonDetailsService) {
        this.pokemonListService = pokemonListService;
        this.pokemonDetailsService = pokemonDetailsService;
    }

    // do sprawdzenia z uzyciem limitow przekazywanych w parametrach uzyj w postman:
    // localhost:8093/pokemon/list?offset=1&limit=20

    @GetMapping("/list")
    PokemonListEnvelop getPokemonItemList(@RequestParam(defaultValue = "0") int offset,
                                          @RequestParam(defaultValue = "20") int limit) {
        return pokemonListService.getPokemonListItem(offset,limit);
    }

    // parametr zadania: bulbasaur/pikachu/chalender
    @GetMapping
    List<PokemonNewDetails> getPokemonDetails(@RequestParam String name) {
        return pokemonDetailsService.getListOfPokemonDetails(name);
    }

    @ExceptionHandler(value = NoPokemonFoundException.class)
    public ResponseEntity<ErrorMessage> handleNoPokemonFoundException(NoPokemonFoundException exception) {
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
