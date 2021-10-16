package pl.sdaacademy.PokemonAcademyApi.pokemondetails;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;

// klasa ktora dzieli nam lub laczy liste w stringi, tak zeby liste stringow podzielic i zapisac do stringa z separatorem ','
@Converter
public class StringListConverter implements AttributeConverter<List<String>,String> {
    private final static String SEPARATOR = ",";
    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        return String.join(SEPARATOR,strings); //->pikachu,bulbasaur,charmander
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        return Arrays.asList(s.split(SEPARATOR));
    }
}
