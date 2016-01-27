package com.laquintaagencia.parkingbackend.dao;

import java.util.List;

import com.laquintaagencia.parkingbackend.dto.Usuario;
import com.laquintaagencia.parkingbackend.exception.DataBaseException;
import com.laquintaagencia.parkingbackend.exception.LogicException;

/**
 * 
 * @author Julianesten
 *
 */
public interface UsuarioDAO {
	/**
	 * Se define el metodo encargado de guardar un nuevo usuario en la base de datos
	 * @param usuario POJO que contiene todos los datos de nuevo usuario a almacenar
	 * @throws DataBaseException
	 */
	public void guardarUsuario(Usuario usuario) throws DataBaseException;
	/**
	 * Definicion del metodo que permite obtener un solo usuario de la base de datos dado 
	 * su numero de identificacion
	 * @param numeroId es el numero unico de identificacion de cada persona con el roll usuario
	 * @return Usuario 
	 * @throws DataBaseException
	 */
	public Usuario obtenerUsuario(String numeroId) throws DataBaseException;

	/**
	 * Metodo mediante el cual se obtiene un usuario desde la DB mediante su token
	 * @param token
	 * @return
	 * @throws DataBaseException
	 */
	public Usuario obtenerUsuarioToken(String token) throws DataBaseException, LogicException;
	/**
	 * Definicion del metodo obtener usuario el cual permite obtener todos los usuarios de la bd
	 * @return un lista con todos los usuarios que se encuentran almacenados en la bd
	 * @throws DataBaseException
	 */
	public List<Usuario> obtenerUsuarios() throws DataBaseException;
	/**
	 * DEfinicion del metodo actualizar usuario utilizando el patron de dise�o DAO.
	 * En caso de alguna excepcion, esta es capturada con DataBseException
	 * El parametro usuario es el usuario a actualizar en la BD
	 * @param usuario
	 * @throws DataBaseException
	 */
	public void actualizarUsuario(Usuario usuario) throws DataBaseException;
	/**
	 * Definicion del metodo eliminar usuario de la BD
	 * @param numeroId es la identificacion del usuario a eliminar
	 * @throws DataBaseException
	 */
	public void eliminarUsuario(String numeroId) throws DataBaseException;
	
	/**
	 * Metodo que permite obtener un usuario por medio de su Username
	 * @param username
	 * @return un objeto de la clase Usuario
	 * @throws DataBaseException
	 */
	public Usuario obtenerUsuarioUsername(String username) throws DataBaseException;
	
}