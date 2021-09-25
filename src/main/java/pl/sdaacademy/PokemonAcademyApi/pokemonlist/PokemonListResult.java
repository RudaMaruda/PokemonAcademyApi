package pl.sdaacademy.PokemonAcademyApi.pokemonlist;

import java.util.List;

public class PokemonListResult {

    private List<Pokemon> results;

    public List<Pokemon> getResult() {
        return results;
    }

    public void setResult(List<Pokemon> results) {
        this.results = results;
    }
}


