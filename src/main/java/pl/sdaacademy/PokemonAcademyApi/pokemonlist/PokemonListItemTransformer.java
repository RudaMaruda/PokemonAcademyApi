package pl.sdaacademy.PokemonAcademyApi.pokemonlist;

import org.springframework.stereotype.Component;
import pl.sdaacademy.PokemonAcademyApi.pokemondetails.PokemonNewDetails;

@Component
public class PokemonListItemTransformer {
    // zamieni nam obiekt detail na obiekt listyItem
    PokemonListItem toEntity(PokemonNewDetails pokemonNewDetails) {
        PokemonListItem pokemonListItem = new PokemonListItem();
        pokemonListItem.setName(pokemonNewDetails.getName());
        pokemonListItem.setImageUrl(pokemonNewDetails.getImageUrl());
        pokemonListItem.setUrl("localhost:8093/pokemon?names="+pokemonNewDetails.getName());
        return pokemonListItem;
    }
}
