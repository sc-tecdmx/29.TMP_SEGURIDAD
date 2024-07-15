/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.tecdmx.seguridad.security.config;

public class Constants  {

    public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";
    
    // JWT
    public static final String ISSUER_INFO = "https://www.tecdmx.org.mx/";
    public static final String SUPER_SECRET_KEY = "tecdmx";
    public static final long TOKEN_EXPIRATION_TIME = 864_000_000; // 10 day
     public static final long EXPIRATION_TEMPORARY_TOKEN_TIME = 1 * 60 * 30 * 1000; //30 min
}
