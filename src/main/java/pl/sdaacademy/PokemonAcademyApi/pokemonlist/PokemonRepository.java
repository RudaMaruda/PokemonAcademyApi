package pl.sdaacademy.PokemonAcademyApi.pokemonlist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {
    Optional<Pokemon> findByName(String name);
}
