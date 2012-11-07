package sda.manager.rmi;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import sda.bo.Archive;
import sda.bo.User;
import sda.comunication.encryption.Encryption;
import sda.comunication.especification.Especification;
import sda.manager.autentication.UCHandlerAuthenticationManager;
import sda.manager.solicitation.UCHandlerSolicitationManager;

/**
 * Classe que implementa os serviços remotos
 */
public class RemoteService extends UnicastRemoteObject implements
		RemoteServiceInterface {
	private static final long serialVersionUID = -5432021798076668288L;

	private Registry registryServer;

	/**
	 * Metodo construtora da classe RemoteService
	 * 
	 * @throws RemoteException
	 */
	public RemoteService() throws RemoteException {
		super();
		this.initService();
	}

	/**
	 * Metodo que inicializa o serviço remoto
	 */
	private void initService() {
		try {
			this.registryServer = LocateRegistry
					.createRegistry(Especification.RMI_PORT);
			this.registryServer.rebind(Especification.RMI_NAME, this);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo remoto que verifica a autenticacao de um objeto User
	 */
	@Override
	public boolean checkAutentication(User person) throws RemoteException {
		UCHandlerAuthenticationManager uCManterAutenticationManager = new UCHandlerAuthenticationManager();

		// Descriptografa o objeto
		Encryption encryption = new Encryption();
		return uCManterAutenticationManager.checkAutentication(encryption
				.decrypt(person));
	}

	/**
	 * Metodo remoto que inseri um objeto User
	 */
	@Override
	public void insertUser(User person) throws RemoteException {
		UCHandlerAuthenticationManager uCManterAutenticationManager = new UCHandlerAuthenticationManager();

		// Descriptografa o objeto
		Encryption encryption = new Encryption();
		uCManterAutenticationManager.insert(encryption.decrypt(person));
	}

	/**
	 * Metodo remoto que remoto o objeto User
	 */
	@Override
	public void deleteUser(User person) throws RemoteException {
		UCHandlerAuthenticationManager uCManterAutenticationManager = new UCHandlerAuthenticationManager();

		// Descriptografa o objeto
		Encryption encryption = new Encryption();
		uCManterAutenticationManager.delete(encryption.decrypt(person));
	}

	/**
	 * Metodo remoto que consulta o objeto User
	 */
	@Override
	public List<User> retrieveAllUsers(User person) throws RemoteException {
		UCHandlerAuthenticationManager uCManterAutenticationManager = new UCHandlerAuthenticationManager();

		List<User> listPersons = uCManterAutenticationManager.retrieve(person);
		if (listPersons != null) {
                    // Descriptografa o objeto
                    Encryption encryption = new Encryption();
                    for (int i = 0; i < listPersons.size(); i++) {
                            listPersons.set(i, encryption.encrypt(listPersons.get(i)));
                    }
		}

		return listPersons;
	}

	/**
	 * Metodo remoto que retorna todos os objetos Persons
	 */
	@Override
	public List<User> retrieveAllUsers() throws RemoteException {
		UCHandlerAuthenticationManager uCManterAutenticationManager = new UCHandlerAuthenticationManager();

		List<User> listPersons = uCManterAutenticationManager.retrieveAll();
		if (listPersons != null) {
			// Descriptografa o objeto
			Encryption encryption = new Encryption();
			for (int i = 0; i < listPersons.size(); i++) {
				listPersons.set(i, encryption.encrypt(listPersons.get(i)));
			}
		}

		return listPersons;
	}

	/**
	 * Metodo remoto que retorna todos os objetos Archives
	 */
	public List<Archive> retrieveAllArchive() throws RemoteException {
		UCHandlerSolicitationManager uCHandlerSolicitationManager = new UCHandlerSolicitationManager();
		List<Archive> list = null;

		try {
			list = uCHandlerSolicitationManager.listArchives();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return list;
	}
}