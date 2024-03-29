package pl.patrykjava.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.patrykjava.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final UserService userService;

    public SecurityConfiguration(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    protected ProviderManager authManagerBean(HttpSecurity security) throws Exception {
        return (ProviderManager) security.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authProvider()).
                build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(staticResources).permitAll()
                .requestMatchers(login).permitAll()
                .requestMatchers(profile).permitAll()
                .requestMatchers(libraryCard).hasAnyAuthority("USER")
                .requestMatchers("/home").hasAnyAuthority("ADMIN", "LIBRARIAN", "USER", "READER")
                .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                .requestMatchers("/librarian/**").hasAnyAuthority("ADMIN", "LIBRARIAN")
                .anyRequest().authenticated();
        http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll();


        http.headers().frameOptions().sameOrigin();

        return http.build();
    }

    String[] staticResources  =  {
            "/css/**", "/images/**", "/scripts/**"
    };
    String[] login = {
            "/register", "/login", "/process_registration", "/register?success"
    };

    String[] libraryCard = {
            "/create-library-card/**", "/process-library-card"
    };

    String[] profile  =  {
            "/profile", "/update-profile", "/process-update-profile"
    };

}