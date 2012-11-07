package sda.manager.manager;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

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
	public Manager(Listener l) throws RemoteException {
		addEventListener(l);
		// Inicia serviço RMI
		new RemoteService();
		// Inicia Serviço de Upload
		new ServerUpload("upload_server", listeners.get(0)).start();
		// Inicia Serviço de Download
		new ServerDownload("download_server", listeners.get(0)).start();
		// Inicia Serviço de Descobrimento dinamico de modulo Manager
		new ServerDiscovery("discovery_server").start();
	}
	
	private ArrayList<Listener> listeners = new ArrayList<Listener>();

	public void addEventListener(Listener l) {
		listeners.add(l);
	}
	
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		try {
//			System.out
//					.println("Iniciando modulo de Gerenciamento (MANAGER)          [OK]");
//			new Manager();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

}