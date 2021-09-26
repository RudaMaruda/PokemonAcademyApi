package pl.sdaacademy.PokemonAcademyApi.pokemondetails;


import org.springframework.stereotype.Component;
import pl.sdaacademy.PokemonAcademyApi.pokemondetails.PokemonDetailsResponse;
import pl.sdaacademy.PokemonAcademyApi.pokemondetails.PokemonNewDetails;
import pl.sdaacademy.PokemonAcademyApi.pokemonlist.Pokemon;
import pl.sdaacademy.PokemonAcademyApi.pokemonlist.PokemonItem;

import java.util.stream.Collectors;

@Component
public class PokemonTransformerDetailResponse {
    PokemonNewDetails toEntity(PokemonDetailsResponse pokemonDetailsResponse){
        PokemonNewDetails pokemonNew = new PokemonNewDetails();
        pokemonNew.setHeight(pokemonDetailsResponse.getHeight());
        pokemonNew.setName(pokemonDetailsResponse.getName());
        pokemonNew.setImageUrl(pokemonDetailsResponse.getSprites().getImage());
        pokemonNew.setAbilities(pokemonDetailsResponse.getAbilities().stream()
        .map(abilities -> abilities.getAbility())
        .map(abilityItem-> abilityItem.getName())
        .collect(Collectors.toList()));
        pokemonNew.setTypes(pokemonDetailsResponse.getTypes().stream()
        .map(types -> types.getType())
        .map(type -> type.getName())
        .collect(Collectors.toList()));


        return pokemonNew;
    }
}
