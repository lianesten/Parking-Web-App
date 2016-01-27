package com.laquintaagencia.parkingbackend.ws;



import java.rmi.RemoteException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.laquintaagencia.parkingbackend.bl.UsuarioBl;
import com.laquintaagencia.parkingbackend.dto.Usuario;
import com.laquintaagencia.parkingbackend.exception.DataBaseException;
import com.laquintaagencia.parkingbackend.exception.LogicException;

/**
 * Clase donde se implementan los servicios RestFul para la entidad usuario
 * @author julianesten
 *
 */

@Component
@Path("usuario")
public class UsuarioResource {
	/**
	 * Inyectamos el bean desde Spring FW
	 */
	@Autowired
	private UsuarioBl usuarioBl;
	private Usuario usuario;


	@GET
	@Path("saludar")
	@Produces(MediaType.APPLICATION_JSON)
	public String helloWordlWs(){
		return "Hello world from parking backend";
	}
	

	/**
	 * @autor Julian Esteban Montoya Cc: 11522686066
	 * Servicio que retorna en formato json un usuario dado su id previamente registrado en la bd
	 *  ejemplo : http://localhost:8080/XsoftBackend/rest/usuario/obtenerUsuario?numeroId=321
	 * @param numeroId identificacion del usuario
	 * @return usuario
	 * 
	 */
	@GET
	@Path("obtenerUsuario")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerUsuarioId(@QueryParam("numeroId") String numeroId) throws RemoteException{
		Logger log = Logger.getLogger(this.getClass());
		Gson gson = new Gson(); 
		if(numeroId==null || numeroId.trim().equals("")){
			Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		try{
			usuario = usuarioBl.obtenerUsuario(numeroId);
		}catch(DataBaseException e){
			log.error(e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}catch(LogicException e){
			log.error(e);
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		if(usuario == null){
			return Response.ok(gson.toJson("El usuario con id: "+numeroId+" no se enceuntra en la base de datos")).build();
		}
		return Response.ok(gson.toJson(usuario)).build();
		
		
	}
	
	
	
	
	/**
	 * @autor Julian Esteban Montoya Cc: 11522686066
	 * Servicio que almacena un nuevo usuario en la BD
	 * @param numeroId
	 * @param nombres
	 * @param apellidos
	 * @param privilegio
	 * @param username
	 * @param password
	 * @param email
	 * @return un mensaje en caso de que se presente excepcion alguna o un string vacio en caso de exito
	 * @throws LogicException
	 * @throws DataBaseException
	 * 
	 * guardarUsuario?numeroId=111?nombres=JuanPablo?apellidos=MontoyaRamirez?privilegio=1?username=juanpa?password=587854?email=juan@juan.com
	 */
	@Path("guardarUsuario")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response guardarUsuario(String userJson) throws RemoteException{
		Logger log = Logger.getLogger(this.getClass());
		Gson gson = new Gson();
		Usuario newUser = null, auxUser=null;
		try{
			newUser = gson.fromJson(userJson, Usuario.class);
			String id = newUser.getNumeroId();
			auxUser = usuarioBl.obtenerUsuario(id);
			if(auxUser!=null){
				return Response.ok(gson.toJson("El usuario  con id: "+id+" ya se enceuntra registrado en la base de datos, por lo ytanto no fue almacenado")).build();
			}
			usuarioBl.guardarUsuario(newUser.getNumeroId(),
					newUser.getNombres(),
					newUser.getApellidos(),
					newUser.getPrivilegio(), 
					newUser.getUsername(), 
					newUser.getPassword(), 
					newUser.getEmail());
		}catch(LogicException e){
			log.error(e);
			return Response.status(Response.Status.BAD_REQUEST).build();
		}catch(DataBaseException e){
			log.error(e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}catch(Exception e){
			log.error(e);
		}
		
		return Response.ok(gson.toJson("El usuario  ha sido alamcenado exitosamente en el sistema")).build();
		
	}
	
	/*==================================================*/
	
	
	/**
	 * @autor Julian Esteban Montoya Cc: 115686066
	 * Servicio para actualizar un usuario en la base de datos
	 * @param numeroId
	 * @param nombres
	 * @param apellidos
	 * @param privilegio
	 * @param username
	 * @param password
	 * @param email
	 * @return retorna un mensaje en caso de excepcion alguna, de lo contrario retorna un string vacio en caso de exito
	 * @throws LogicException
	 * @throws DataBaseException
	 */
	
	/*
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String actualizarUsuario(@QueryParam("numeroId") String numeroId,
			@QueryParam("nombres") String nombres, @QueryParam("apellidos") String apellidos,
			@QueryParam("privilegio") int privilegio, @QueryParam("username") String username,
			@QueryParam("password") String password, @QueryParam("email") String email) throws RemoteException{
		Logger log = Logger.getLogger(this.getClass());
		try{
			usuarioBl.actualizarUsuario(numeroId, nombres, apellidos, privilegio, username, password, email);
		}catch(LogicException e){
			log.error(e);
			return e.getMessage();
		}catch(DataBaseException e){
			log.error(e);
			return e.getMessage();
		}
		return "";
	}
	*/
	
	/**
	 * Servicio para retornar al front end en formato Json una lista de todos los 
	 * usuario registrados en el sistema
	 * @return
	 * @throws RemoteException
	 */
	@Path("listarUsuarios")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarUsuarios() throws RemoteException{
		List listaUsuarios =null;
		Logger log = Logger.getLogger(this.getClass());
		Gson gson = new Gson();
		try{
			listaUsuarios = usuarioBl.obtenerUsuarios();
			if(listaUsuarios==null){
				return Response.ok(gson.toJson("No hay usuarios registrados en el sistema!")).build();
			}
		}catch(LogicException e){
			log.error(e);
			return Response.status(Response.Status.BAD_REQUEST).build();
		}catch(DataBaseException e){
			log.error(e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}catch(Exception e){
			log.error(e);
		}
		
		return Response.ok(gson.toJson(listaUsuarios)).build();

	}

}
