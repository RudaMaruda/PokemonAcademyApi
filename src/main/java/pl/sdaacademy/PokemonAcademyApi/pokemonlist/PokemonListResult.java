package pl.sdaacademy.PokemonAcademyApi.pokemonlist;

import java.util.List;
// odpowiedz z api
class PokemonListResult {
    private String next;


    private List<PokemonItem> results;

    List<PokemonItem> getResults() {
        return results;
    }

    void setResults(List<PokemonItem> results) {
        this.results = results;
    }

    String getNext() {
        return next;
    }

    void setNext(String next) {
        this.next = next;
    }
}


