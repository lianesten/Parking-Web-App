package com.laquintaagencia.parkingbackend.exception;


import org.hibernate.HibernateException;

/**
 * Clase que permite controlar las excepciones que se puedan presentar en la aplicaci�n 
 * @author Julianesten
 *
 */

public class DataBaseException extends Exception {
	
	/**
	 * Construcutor que maneja las excepciones generadas cuando se hacen operaciones con la DB
	 * @param e
	 * @param mensaje
	 */
	public DataBaseException(HibernateException e, String mensaje) {
		super(mensaje);
	}

	/**
	 * Construcutor que maneja las excepciones generadas cuando se hacen operaciones con la DB
	 * @param e
	 */
	public DataBaseException(Exception e) {
		super();
	}
	
	/**
	 * Constructor temporal, para el control general de las excepciones que no se especifican
	 * una a una en los constructores
	 * @param e
	 * @param mensaje
	 */
	public DataBaseException(Exception e, String mensaje) {
		super(mensaje);
	}


}