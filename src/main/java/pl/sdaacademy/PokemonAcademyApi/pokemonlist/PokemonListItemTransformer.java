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
        return pokemonListItem;
    }
}
