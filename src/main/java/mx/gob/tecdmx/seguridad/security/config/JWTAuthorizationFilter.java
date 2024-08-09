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

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

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
        
        String token = req.getHeader(Constants.HEADER_AUTHORIZACION_KEY);
        
        if (token != null) {
        	String issuer = identifyOrigin(token);
        	
        	if (issuer != null) {
        		
        		if(issuer.contains("tecdmx.org.mx")) {
                	String user = getUserTecdmx(token);
                	UsernamePasswordAuthenticationToken authentication = getAuthentication(user);
                    String idSession = getIdSession(token);
                    String sys = getSystemTecdmx(token);
                    UserDetails userDetail = userv.loadUserByUsername(authentication.getName()+":"+idSession+":"+sys);
                    if(userDetail!=null) {
                		authentication.setDetails(userDetail);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        chain.doFilter(req, res);
                	}else {
                		chain.doFilter(req, res);
                        return;
                	}
                    
                }else if (issuer.contains("sts.windows.net")) {
                	String user = getUserMicrosoft365(token);
                	UsernamePasswordAuthenticationToken authentication = getAuthentication(user);
                	String idSession = "-1";
                	String sys = getSystemMicrosoft365(token);
                	UserDetails userDetail = userv.loadUserByUsername(authentication.getName()+":"+idSession+":"+sys);
                	if(userDetail!=null) {
                		authentication.setDetails(userDetail);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        chain.doFilter(req, res);
                	}else {
                		chain.doFilter(req, res);
                        return;
                	}
                }
        	}
            
        }
        
    }
   
    public static String identifyOrigin(String token) {
        try {
            token = token.replace(Constants.TOKEN_BEARER_PREFIX, "");
            // Decodificar el token sin verificar la firma
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getIssuer();
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
      
    private String getUserMicrosoft365(String token) {
    	
    	try {
            token = token.replace(Constants.TOKEN_BEARER_PREFIX, "");
            // Decodificar el token sin verificar la firma
            DecodedJWT decodedJWT = JWT.decode(token);
            Claim uniqueNameClaim = decodedJWT.getClaim("unique_name");
            return uniqueNameClaim.asString();
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
    
    private String getUserTecdmx(String token) {
    	return Jwts.parser()
                .setSigningKey(Constants.SUPER_SECRET_KEY)
                .parseClaimsJws(token.replace(Constants.TOKEN_BEARER_PREFIX, ""))
                .getBody()
                .getSubject();
    }
    

    private UsernamePasswordAuthenticationToken getAuthentication(String user) {
    	return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
    }
    
    private String getIdSession(String token) {
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
    
    private String getSystemTecdmx(String token) {
        if (token != null) {
            // Se procesa el token y se recupera el usuario.
            String system = Jwts.parser()
                    .setSigningKey(Constants.SUPER_SECRET_KEY)
                    .parseClaimsJws(token.replace(Constants.TOKEN_BEARER_PREFIX, ""))
                    .getBody()
                    .get("sys").toString();
            if (system != null) {
                return system;
            }
            return null;
        }
        return null;
    }
    
    private String getSystemMicrosoft365(String token) {
        
        try {
            token = token.replace(Constants.TOKEN_BEARER_PREFIX, "");
            // Decodificar el token sin verificar la firma
            DecodedJWT decodedJWT = JWT.decode(token);

            Claim uniqueNameClaim = decodedJWT.getClaim("app_displayname");

            return uniqueNameClaim.asString();
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
}
