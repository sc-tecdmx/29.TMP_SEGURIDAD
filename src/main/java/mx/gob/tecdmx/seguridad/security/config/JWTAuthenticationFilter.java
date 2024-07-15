/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.tecdmx.seguridad.security.config;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import mx.gob.tecdmx.seguridad.entity.SegUsuarios;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
        	SegUsuarios credenciales = new ObjectMapper().readValue(request.getInputStream(), SegUsuarios.class);
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    credenciales.getsEmail(), credenciales.getsContrasenia(), new ArrayList<>()));
            return authentication;
        } catch (IOException e) {
            throw new RuntimeException("log-attemptAuth: "+e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        UsuarioSecurityDTO usuario = (UsuarioSecurityDTO) auth.getPrincipal();
        response.addHeader("email", usuario.getEmail());
        response.addHeader("usuario", usuario.getUsername().toString());
        response.addHeader("nomb", auth.getAuthorities().toString());
        String token = Jwts.builder().setIssuedAt(new Date()).setIssuer(Constants.ISSUER_INFO)
                .setSubject(((User) auth.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, Constants.SUPER_SECRET_KEY).compact();
        response.addHeader(Constants.HEADER_AUTHORIZACION_KEY, Constants.TOKEN_BEARER_PREFIX + " " + token);
    }
}
