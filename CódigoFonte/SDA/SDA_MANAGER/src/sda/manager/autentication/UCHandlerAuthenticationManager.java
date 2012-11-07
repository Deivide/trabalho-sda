package sda.manager.autentication;

import java.util.LinkedList;
import java.util.List;

import sda.bo.User;

import com.db4o.ObjectSet;

/**
 * Classe que gerencia os metodos de autenticação de usuarios
 */
public class UCHandlerAuthenticationManager {

	/**
	 * Metodo que faz a insercao de um objeto User
	 * 
	 * @param user
	 */
	public void insert(User user) {
		ColUser colUser = new ColUser();
		colUser.getDbConnection().connect();
		colUser.insert(user);
		colUser.getDbConnection().disconnect();
	}
	
	/**
	 * Metodo de consulta que retorna uma lista com todos os objetos User
	 * inseridos no banco
	 * 
	 * @return
	 */
	public List<User> retrieveAll() {
		ObjectSet<User> result = null;
		List<User> lista = null;

		ColUser colUser = new ColUser();
		colUser.getDbConnection().connect();

		result = colUser.retrieveAll();
		if (result.hasNext()) {
			lista = new LinkedList<User>();

			while (result.hasNext()) {
				User p = (User) result.next();
				lista.add(p);
			}
		}
		colUser.getDbConnection().disconnect();

		return lista;
	}

	/**
	 * Metodo que consulta um objeto User
	 * 
	 * @param user
	 * @return
	 */
	public List<User> retrieve(User user) {
		ObjectSet<User> result = null;
		List<User> lista = null;

		ColUser colUser = new ColUser();
		colUser.getDbConnection().connect();

		result = colUser.retrieve(user);
		if (result.hasNext()) {
			lista = new LinkedList<User>();

			while (result.hasNext()) {
				User p = (User) result.next();
				lista.add(p);
			}
		}
		colUser.getDbConnection().disconnect();

		return lista;
	}

	/**
	 * Metodo que remove um objeto User
	 * 
	 * @param user
	 */
	public void delete(User user) {
		ColUser colUser = new ColUser();

		colUser.getDbConnection().connect();

		ObjectSet<User> result = colUser.retrieve(user);

		if (result != null) {
			while (result.hasNext()) {
				User p = (User) result.next();
				colUser.delete(p);
			}
		}

		colUser.getDbConnection().disconnect();
	}

	/**
	 * Metodo que verifica se o objeto User esta cadastrado
	 * 
	 * @param user
	 * @return
	 */
	public boolean checkAutentication(User user) {
		List<User> list = this.retrieve(user);

		if (list != null) {
			return true;
		} else {
			return false;
		}
	}
}
