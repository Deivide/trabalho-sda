package sda.manager.manager;

import java.io.IOException;
import java.rmi.RemoteException;

import sda.manager.rmi.RemoteService;
import sda.manager.server.ServerDiscovery;
import sda.manager.server.ServerDownload;
import sda.manager.server.ServerUpload;

/**
 * Classe que gerencia o modulo de Gerenciamento
 */
public class Manager {

	/**
	 * Construtor da classe
	 * 
	 * @throws RemoteException
	 */
	public Manager() throws RemoteException {
		// Inicia serviço RMI
		new RemoteService();
		// Inicia Serviço de Upload
		new ServerUpload("upload_server").start();
		// Inicia Serviço de Download
		new ServerDownload("download_server").start();
		// Inicia Serviço de Descobrimento dinamico de modulo Manager
		new ServerDiscovery("discovery_server").start();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out
					.println("Iniciando modulo de Gerenciamento (MANAGER)          [OK]");
			new Manager();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}