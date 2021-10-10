package pl.sdaacademy.PokemonAcademyApi.user;


import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//klasa przetrzemujaca informacje o uzytkowniku
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
