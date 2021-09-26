package pl.sdaacademy.PokemonAcademyApi.pokemonlist;

import org.springframework.stereotype.Component;

@Component
class PokemonTransformer {
    //komponent mapujacy z jednego do drugiego, z odpowiedzi z api do encji
    Pokemon toEntity(PokemonItem pokemonItem){
        Pokemon pokemon = new Pokemon();
        String[] urlData = pokemonItem.getUrl().split("/");
        pokemon.setId(Integer.parseInt(urlData[urlData.length-1]));
        pokemon.setName(pokemonItem.getName());
        pokemon.setUrl(pokemonItem.getUrl());
        return pokemon;
    }
}
