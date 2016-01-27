package com.laquintaagencia.parkingbackend.dto;

import java.io.Serializable;

public class Usuario implements Serializable{



	 private String numeroId;
     private String nombres;
     private String apellidos;
     private int privilegio;
     private String username;
     private String password;
     private String email;
     private String token;
     /**
      * Constructor vacio de la Clase
      */
     public Usuario() {
     }

 	/**
 	 * Contructor que permite ingresar un 
 	 * @param numeroId
 	 * @param nombres
 	 * @param apellidos
 	 * @param privilegio
 	 * @param username
 	 * @param password
 	 * @param email
 	 */
     public Usuario(String numeroId, String nombres, String apellidos, int privilegio, String username, String password, String email) {
         this.numeroId = numeroId;
         this.nombres = nombres;
         this.apellidos = apellidos;
         this.privilegio = privilegio;
         this.username = username;
         this.password = password;
         this.email = email;
     }

	public String getNumeroId() {
		return numeroId;
	}

	public void setNumeroId(String numeroId) {
		this.numeroId = numeroId;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public int getPrivilegio() {
		return privilegio;
	}

	public void setPrivilegio(int privilegio) {
		this.privilegio = privilegio;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
     
}
