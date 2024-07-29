package mx.gob.tecdmx.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import mx.gob.tecdmx.seguridad.security.config.JWTAuthenticationFilter;
import mx.gob.tecdmx.seguridad.security.config.JWTAuthorizationFilter;
import mx.gob.tecdmx.seguridad.security.config.UsuarioImplementationSrv;

@SpringBootApplication
public class SeguridadApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SeguridadApplication.class, args);
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addExposedHeader("Authorization");
        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }

    @EnableWebSecurity
    @Configuration
    @EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private CorsFilter corsFilter;

        @Autowired
        private UsuarioImplementationSrv userService;

        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers(
                            "/api/v1/version",
                            "/api/v1/create-menu",
                            "/api/v1/create-modulo",
                            "/api/v1/registrar-usuario-no-auth",
                            "/api/v1/login",
                            "/api/v1/email/solicitud-reset-password",
                            "/api/v1/email/reset-password"
                            )
                    .permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                    .addFilter(new JWTAuthorizationFilter(authenticationManager(), userService))
                    .addFilter(corsFilter);
            http.cors();
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            // Se define la clase que recupera los usuarios y el algoritmo para procesar las
            // passwords
            auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
        }

    }

}