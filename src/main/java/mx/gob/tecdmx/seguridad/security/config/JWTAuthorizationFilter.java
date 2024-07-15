/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.tecdmx.seguridad.security.config;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	private UsuarioImplementationSrv userv;
	
    public JWTAuthorizationFilter(AuthenticationManager authManager, UsuarioImplementationSrv userv) {
        super(authManager);
        this.userv = userv;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String header = req.getHeader(Constants.HEADER_AUTHORIZACION_KEY);
        if (header == null || !header.startsWith(Constants.TOKEN_BEARER_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        String idSession = getIdSession(req);
        String sys = getSystem(req);
        UserDetails userDetail = userv.loadUserByUsername(authentication.getName()+":"+idSession+":"+sys);
        authentication.setDetails(userDetail);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(Constants.HEADER_AUTHORIZACION_KEY);
        if (token != null) {
            // Se procesa el token y se recupera el usuario.
            String user = Jwts.parser()
                    .setSigningKey(Constants.SUPER_SECRET_KEY)
                    .parseClaimsJws(token.replace(Constants.TOKEN_BEARER_PREFIX, ""))
                    .getBody()
                    .getSubject();
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
    
    private String getIdSession(HttpServletRequest request) {
        String token = request.getHeader(Constants.HEADER_AUTHORIZACION_KEY);
        if (token != null) {
            // Se procesa el token y se recupera el usuario.
            String idSession = Jwts.parser()
                    .setSigningKey(Constants.SUPER_SECRET_KEY)
                    .parseClaimsJws(token.replace(Constants.TOKEN_BEARER_PREFIX, ""))
                    .getBody()
                    .getId();
            if (idSession != null) {
                return idSession;
            }
            return null;
        }
        return null;
    }
    
    private String getSystem(HttpServletRequest request) {
        String token = request.getHeader(Constants.HEADER_AUTHORIZACION_KEY);
        if (token != null) {
            // Se procesa el token y se recupera el usuario.
            String idSession = Jwts.parser()
                    .setSigningKey(Constants.SUPER_SECRET_KEY)
                    .parseClaimsJws(token.replace(Constants.TOKEN_BEARER_PREFIX, ""))
                    .getBody()
                    .get("sys").toString();
            if (idSession != null) {
                return idSession;
            }
            return null;
        }
        return null;
    }
}
