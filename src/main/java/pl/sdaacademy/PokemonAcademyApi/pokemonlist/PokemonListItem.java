package pl.sdaacademy.PokemonAcademyApi.pokemonlist;

public class PokemonListItem {

    // robimy nowa klase ktora bedzie zawierac obrazek, moglibysmy manipulowac obecnym pokemon
    // ale wtedy wymuszaloby to kazdego zadania pobierania obrazkow na start

    private String name;
    private String imageUrl;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
