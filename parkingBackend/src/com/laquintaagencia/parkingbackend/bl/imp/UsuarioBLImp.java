package com.laquintaagencia.parkingbackend.bl.imp;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.laquintaagencia.parkingbackend.bl.UsuarioBl;
import com.laquintaagencia.parkingbackend.dao.UsuarioDAO;
import com.laquintaagencia.parkingbackend.dto.Usuario;
import com.laquintaagencia.parkingbackend.exception.DataBaseException;
import com.laquintaagencia.parkingbackend.exception.LogicException;
/**
 * Clase que implementa todos los metodos definidos en la interface UsuarioBl
 * @author Julianesten
 *
 */
public class UsuarioBLImp implements UsuarioBl{
	
	UsuarioDAO usuarioDAO;
	private Usuario usuario;
	
	public UsuarioDAO getUsuarioDAO(){
		return usuarioDAO;
	}
	
	public void setUsuarioDAO(UsuarioDAO usuarioDAO){
		this.usuarioDAO = usuarioDAO;
	}
	/**
	 * Metodo para almacenar un nuevo usuario en la Base de datos
	 */
	@Override
	public void guardarUsuario(String numeroId, String nombres,
			String apellidos, int privilegio, String username, String password,
			String email) throws DataBaseException, LogicException {
			
		if(numeroId==null|| "".equals(numeroId)){
			throw new LogicException("La cedula no puede ser vacia ni Nula");
		}
		if (!numeroId.matches("[0-9]*")){
			throw new LogicException("La cedula no puede contener letras");
		}
		if (nombres == null || "".equals(nombres)) {
			throw new LogicException("Los nombres no pueden ser vacio ni Nulo");
		}
		if (apellidos == null || "".equals(apellidos)) {
			throw new LogicException("Los apellidos no pueden ser vacio ni Nulo");
		}
		if (username == null || "".equals(username)) {
			throw new LogicException("El nombre de usuario no pueden ser vacio ni Nulo");
		}
		
		if (password == null || "".equals(password)) {
			throw new LogicException("El password no pueden ser vacio ni Nulo");
		}
		if (email == null || "".equals(email)) {
			throw new LogicException("El email no puede ser vacio ni Nulo");
		}
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		Boolean b = email.matches(EMAIL_REGEX);
		if (b == false ) {
			throw new LogicException("El email no tiene un formato valido");
		}
		
		
		usuario = new Usuario();
		usuario.setNumeroId(numeroId);
		usuario.setNombres(nombres);
		usuario.setApellidos(apellidos);
		usuario.setPrivilegio(privilegio);
		usuario.setUsername(username);
		usuario.setPassword(password);
		usuario.setEmail(email);
		try{
			usuarioDAO.guardarUsuario(usuario);
		}catch(Exception e){
			Logger  log = Logger.getLogger(this.getClass());
			log.error("Error en el almacenamiento de Cliente: " +e);
			new DataBaseException(e,"Error almacenando un Usuario");
			
		}
		
	}
	/**
	 * Metodo para obtener un usuario en la Bd dado su numeroId
	 */
	@Override
	public Usuario obtenerUsuario(String numeroId) throws DataBaseException,
			LogicException {
		if(numeroId==null || "".equals(numeroId)){
			throw new LogicException("Se debe digitar la identificacion del usuario a obtener");
		}
		
		try{
		usuario = usuarioDAO.obtenerUsuario(numeroId);
		}catch(DataBaseException e){
			Logger  log = Logger.getLogger(this.getClass());
			log.error("Error UsuarioBlImp obteniendo un usuario: " +e);
			throw new DataBaseException(e, "Error UsuarioBlImp obteniendo un Usuario en la DB");
			
		}
		return usuario;
	}
	/**
	 * Implementaci�n del metodo actualizrUsuario definido en la interface, este metodo hace uso del 
	 * POJO Usuario para enviarlo al DAO y hacer la respectiva operacion de UPDATe en la BD
	 */
	@Override
	public void actualizarUsuario(String numeroId,String nombres,String apellidos,
			int privilegio,String username,String password,String email) throws DataBaseException,
			LogicException {
		
		usuario = new Usuario();
		usuario.setNumeroId(numeroId);
		usuario.setNombres(nombres);
		usuario.setApellidos(apellidos);
		usuario.setPrivilegio(privilegio);
		usuario.setUsername(username);
		usuario.setPassword(password);
		usuario.setEmail(email);
		
		try{
			 usuarioDAO.actualizarUsuario(usuario);
		}catch(DataBaseException e){
			Logger  log = Logger.getLogger(this.getClass());
			log.error("Error actualizando usuario: " +e);
			throw new DataBaseException(e, "Error actualizando un Usuario en la BD");
			
		}catch(Exception e){
			Logger  log = Logger.getLogger(this.getClass());
			log.error("Error actualizando usuario: " +e);
			throw new DataBaseException(e, "Error actualizando un Usuario en la BD");
		}
		
		
		
	}
	/**
	 * Implementacion del metodo definido en la interface que permite obtener en un array de tipo Usurio
	 * todos los usuarios registrdos
	 * previamente en la base de datos 
	 */
	@Override
	public List<Usuario> obtenerUsuarios() throws DataBaseException,
			LogicException {
		List<Usuario> listaUsuarios= new List<Usuario>();
		try{
		listaUsuarios = usuarioDAO.obtenerUsuarios();
		}catch(Exception e){
			e.printStackTrace();
			throw new DataBaseException(e, "Error obteniendo la lista de Clientes en la BD");
		}
		return listaUsuarios;
	}
	/**
	 * Implementacion del metodo definido en la interface que permite eliminar un usuario dado su numeroId 
	 * y registrado previamente en la base de datos.
	 */
	@Override
	public void eliminarUsuario(String numeroId) throws DataBaseException,
			LogicException {
		if(numeroId==null || "".equals(numeroId)){
			throw new LogicException("Se debe digitar la identificacion del usuario a eliminar");
		}
		
		try{
			usuarioDAO.eliminarUsuario(numeroId);
		}catch(DataBaseException e){
			Logger  log = Logger.getLogger(this.getClass());
			log.error("Error eliminando usuario: " +e);
			throw new DataBaseException(e, "Error eliminando un Usuario en la BD");
			
		}
		
	}

	

}

