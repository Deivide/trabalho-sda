package sda.manager.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import sda.bo.Archive;
import sda.bo.User;

/**
 * Classe que implementa a Interface dos serviços remotos 
 */
public interface RemoteServiceInterface extends Remote {

	
	/**
	 * Metodo remoto que verifica a autenticacao de um objeto User 
	 */
	public boolean checkAutentication(User person) throws RemoteException;

	
	/**
	 * Metodo remoto que inseri um objeto User
	 */
	public void insertUser(User person) throws RemoteException;
	
	
	/**
	 * Metodo remoto que remoto o objeto User
	 */
	public void deleteUser(User person) throws RemoteException;
	
	
	/**
	 * Metodo remoto que consulta o objeto User
	 */
	public List<User> retrieveAllUsers(User person) throws RemoteException;
	
	
	/**
	 * Metodo remoto que retorna todos os objetos Persons
	 */
	public List<User> retrieveAllUsers() throws RemoteException;

	
	/**
	 * Metodo remoto que retorna todos os objetos Archives
	 */
	public List<Archive> retrieveAllArchive() throws RemoteException;
}