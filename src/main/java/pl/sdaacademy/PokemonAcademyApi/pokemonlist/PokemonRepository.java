package pl.sdaacademy.PokemonAcademyApi.pokemonlist;

import org.springframework.data.jpa.repository.JpaRepository;

interface PokemonRepository extends JpaRepository<Pokemon,Integer> {
}
