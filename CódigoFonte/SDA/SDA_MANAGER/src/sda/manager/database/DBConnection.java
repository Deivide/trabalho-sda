package sda.manager.database;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

/**
 * Classe que faz os tratamentos refente as conexoes com a base de dados (DB4O)
 */
public class DBConnection {

	private ObjectContainer objectContainer;

	/**
	 * Metodo que efetua a conexao com a base de dados
	 */
	public void connect() {
		this.objectContainer = Db4oEmbedded.openFile(
				Db4oEmbedded.newConfiguration(), DBEspecification.FILE_NAME);
	}

	/**
	 * Metodo que fecha a conexao com a base de dados
	 */
	public void disconnect() {
		this.objectContainer.close();
	}

	/**
	 * Metodo que efetua a operacao de commit na base de dados
	 */
	public void commit() {
		this.objectContainer.commit();
	}

	/**
	 * Metodo que retorna a conexao da base de dados
	 * 
	 * @return
	 */
	public ObjectContainer getObjectContainer() {
		return this.objectContainer;
	}

	/**
	 * Metodo que seta a conexao da base de dados
	 * 
	 * @param dbConnection
	 */
	public void setObjectContainer(ObjectContainer objectContainer) {
		this.objectContainer = objectContainer;
	}
}