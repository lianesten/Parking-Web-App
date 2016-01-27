package com.laquintaagencia.parkingbackend.security;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.json.Json;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jose4j.base64url.internal.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;
import com.laquintaagencia.parkingbackend.dto.Usuario;

/**
 * Permite administrar la sesion del usuario.
 * @author julianesten
 *
 */
public class Autenticator {

    /**
     * Almacenamiento de todos los parametros seteados en el token
     */
    private Map<String, String> parametros = new HashMap<String, String>();
    
    /**
     * Token de sesion generado
     */
    private String token;
    
    /**
     * Clave para obtener el Rol
     */
    public static final String ROL = "rol";
    
    /**
     * Clave para obtener el identificador del empleado
     */
    public static final String USER_ID = "identificacion";
    
    /**
     * Clave para obtener el nombre de empleado
     */
    public static final String USERNAME = "username";

    

    
    /*GETTERS & SETTERS*/
    public String addParam(String clave, String valor){
        //TODO Codificar cada param
        return parametros.put(clave, valor);
    }
    
    public String getParam(String clave) {
        String valor = null;
        try {
            valor = parametros.get(clave);
            
            if (valor == null)
                throw new NoSuchFieldError("La clave no esta mapeada");
        } catch (NullPointerException e){
        	Logger log = Logger.getLogger(this.getClass());
            log.error("La clave no esta mapeada"+ e.toString());
        }
        return valor;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
     /**
     *  method to generate a JSON Web Token
     *
     * @param payload   JSON to attach
     * @param key       Key used for the signature
     *
     * @return token
     *
     * @throws IllegalArgumentException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     */
    public  String generarToken(String key) throws IllegalArgumentException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
      
        // verificar la Llave 
        if(key == null || key.length() == 0) {
            throw new IllegalArgumentException("La clave no puede ser vacia ni nula");
        }

        AlgoritmoCifradoJWT algorithm = AlgoritmoCifradoJWT.HS512;
        // Header, typ is fixed value
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("typ", "JWT");
        header.put("alg", algorithm.name());

        // Crear segmentos , todos los segmentos deben ser de cadena base64
        ArrayList<String> segments = new ArrayList<String>();
        Gson gson = new Gson();
        segments.add(base64Encode(gson.toJson(header).getBytes()));
        segments.add(base64Encode(gson.toJson(parametros).getBytes()));
        segments.add(base64Encode(sign(StringUtils.join(segments, "."), key, algorithm.getValue())));
        
        // Retornamos el Token
        return StringUtils.join(segments, ".");
     
        
       
    }
    
    /**
     * Metodo paradecodificar un token pasado como parametro, regresa ub objeto de la clase Usuario
     * @param token
     * @return
     */
    public  Usuario  decodeToken (String token){
        
        String[] jwtParts = token.split("\\.");

         javax.json.JsonObject jwtHeader;
         javax.json.JsonObject jwtparametros;
            jwtHeader = Json.createReader(new ByteArrayInputStream(Base64.decodeBase64(jwtParts[0])))
                .readObject();
        System.out.println(jwtHeader.toString());
        jwtparametros = Json.createReader(new ByteArrayInputStream(Base64.decodeBase64(jwtParts[1])))
                .readObject();
         System.out.println(jwtparametros.toString());
         Usuario usuario = new Usuario();
     
         usuario.setToken(token);
         usuario.setNumeroId(jwtparametros.getString("idUsuario"));
         usuario.setUsername(jwtparametros.getString("username"));
         usuario.setPrivilegio(Integer.parseInt(jwtparametros.getString("rol")));
         System.out.println("Identificaci�n del usuario: " + usuario.getNumeroId());
         System.out.println("Rol: " + usuario.getPrivilegio());
         System.out.println("Username: " + usuario.getUsername());
         return usuario;
         
    }
    
     /**
     *  method to encode a String in base64
     *
     * @param bytes   Bytes to encode
     *
     * @return String
     *
     * @throws UnsupportedEncodingException
     */
    private  String base64Encode (byte[] bytes) throws UnsupportedEncodingException {
        return Base64.encodeBase64URLSafeString(bytes);
    }
    
    
     /**
     *  method to generate a signature from a key
     *
     * @param input     Data to sign
     * @param key       Key used for the signature
     * @param method    Algorithm
     *
     * @return Signature
     *
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     */
    private  byte[] sign (String input, String key, String method) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException{
        Mac hmac = Mac.getInstance(method);
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), method);
        hmac.init(secretKey);

        return hmac.doFinal(input.getBytes());
    }
}
