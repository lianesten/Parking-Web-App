package com.laquintaagencia.parkingbackend.bl;

import java.util.List;

import com.laquintaagencia.parkingbackend.dto.Usuario;
import com.laquintaagencia.parkingbackend.exception.DataBaseException;
import com.laquintaagencia.parkingbackend.exception.LogicException;

/**
 * Interface que contiene la logica del negocio que se va  aimplemenar sobre la tabla Usuario
 * @author Julianesten
 */


public interface UsuarioBl {
/**
 * Este metodo permite ingresar un nuevo usuario al sistema, diferenciando entre roles a traves de privilegio,
 * si ocurre un error durante el proceso, este es capturado con las excepciones DataBaseException, LogicException
 * 
 * @param numeroId 
 * @param nombres
 * @param apellidos
 * @param privilegio
 * @param username
 * @param password
 * @param email
 * @throws DataBaseException
 * @throws LogicException
 */
	public void guardarUsuario(String numeroId,String nombres,String apellidos,
			int privilegio,String username,String password,String email) throws DataBaseException, LogicException;
	/**
	 * Metodo para obtener un usuario de la tabla usuarios dado su id y registrado previamente en la base de datos
	 * @param numeroId
	 * @return Usuario
	 * @throws DataBaseException
	 * @throws LogicException
	 */
	
	public Usuario obtenerUsuario(String numeroId) throws DataBaseException, LogicException;
	/**
	 * Metodo para actualizar los datos de un usuario  registrado previamente en la BD
	 * @param numeroId
	 * @throws DataBaseException
	 * @throws LogicException
	 */
	public void actualizarUsuario(String numeroId,String nombres,String apellidos,
			int privilegio,String username,String password,String email) throws DataBaseException, LogicException;
	/**
	 *Este metodo permite obtener todos los usuarios regisrados previamente en el sistema
	 * @return Una lista con todos los usuarios registrdos en la BD
	 * @throws DataBaseException si ocurre alguna excepcion con la BD
	 * @throws LogicException si ocurre alguna excepcion en la capa de la logica del negocio
	 */
	public List<Usuario> obtenerUsuarios() throws DataBaseException, LogicException;
	/**
	 * Metodo que permite eliminar un usuario previamente registrado en el sistema dado su numero de identificacion
	 * @param numeroId
	 * @throws DataBaseException
	 * @throws LogicException
	 */
	public void eliminarUsuario(String numeroId) throws DataBaseException, LogicException;
	
		
	
}
