package sda.manager.autentication;

import sda.bo.User;
import sda.manager.database.DBConnection;

import com.db4o.ObjectSet;

/**
 * Classe que representa a Collection de User
 */
public class ColUser {

	private DBConnection dbConnection;

	/**
	 * Metodo construtor da classe Colection de User
	 */
	public ColUser() {
		this.dbConnection = new DBConnection();
	}

	/**
	 * Meotod que insere o objeto
	 * 
	 * @param user
	 */
	public void insert(User user) {
		this.dbConnection.getObjectContainer().store(user);
	}

	/**
	 * Metodo que retorna todos os objetos User
	 * 
	 * @return
	 */
	public ObjectSet<User> retrieveAll() {
		ObjectSet<User> result = null;
		result = this.dbConnection.getObjectContainer().queryByExample(
				User.class);
		return result;
	}

	/**
	 * Metodo que retorna um objeto User
	 * 
	 * @param user
	 * @return
	 */
	public ObjectSet<User> retrieve(User user) {
		ObjectSet<User> result = this.dbConnection.getObjectContainer()
				.queryByExample(user);
		return result;
	}

	/**
	 * Metodo que remove um objeto User
	 * 
	 * @param user
	 */
	public void delete(User user) {
		this.dbConnection.getObjectContainer().delete(user);
	}
	
	/**
	 * Metodo que retorna a conexao da base de dados
	 * 
	 * @return
	 */
	public DBConnection getDbConnection() {
		return this.dbConnection;
	}

	/**
	 * Metodo que seta a conexao da base de dados
	 * 
	 * @param dbConnection
	 */
	public void setDbConnection(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}
}