package pl.sdaacademy.PokemonAcademyApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import pl.sdaacademy.PokemonAcademyApi.pokemondetails.StringListConverter;
import pl.sdaacademy.PokemonAcademyApi.security.AuthenticationFilter;
import pl.sdaacademy.PokemonAcademyApi.security.AuthorizationFilter;


@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    //kot->2924974744
    //haslo->43sufuf

    private final PasswordEncoder passwordEncoder;
    private final String headerKey;
    private final String signature;
    private final String tokenType;
    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(@Value("${paa.authorization-key}") String headerKey,
                                 @Value("${paa.signature}") String signature,
                                 @Value("${paa.token-type}") String tokenType,
                                 PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.headerKey = headerKey;
        this.signature = signature;
        this.tokenType = tokenType;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                //sluzy do odblokowania konsoli h2
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/pokemon/**").authenticated()
                .antMatchers("/user").permitAll()
                .and()
                //wiaze rejestracje z formularzem
                // jezeli formularz nie zostal wyslany specjalny tzn formularz zaufany(posiada id z banku)
                // to nie przyjmij takiego zapytania
                .csrf().disable()
                .cors()
                .and()
                //basic authentication
//                .httpBasic()
//                .and()
                // musimy przekazywac token za kazdym razem
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new AuthenticationFilter(headerKey,signature,tokenType, authenticationManager()))
                .addFilter(new AuthorizationFilter(headerKey,signature,tokenType, authenticationManager()))
                // obsluga h2 (wyswietlanie bazy h2)
                .headers().frameOptions().disable();
    }
// stary sposob konfiguracji:
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.inMemoryAuthentication()
//                .withUser("u1").password(passwordEncoder.encode("u1")).roles("user")
//                .and()
//                .withUser("u2").password(passwordEncoder.encode("u2")).roles("user");
//    }

    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsService;
    }
}
