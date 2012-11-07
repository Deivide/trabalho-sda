package sda.manager.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import sda.comunication.especification.Especification;
import sda.comunication.tcp.TCPComunication;
import sda.manager.handler.UploadHandler;
import sda.manager.manager.Listener;

/**
 * Classe que implementa o servidor de Upload
 */
public class ServerUpload extends Thread {

	private Socket clientSocket;
	private ServerSocket serverSocket;
	private boolean finish = false;
	private TCPComunication socketCommunication;
	private Listener print;

	/**
	 * Construtor da classe
	 * 
	 * @param threadName
	 */
	public ServerUpload(String threadName, Listener l) {
		super(threadName);
		this.print = l;
	}

	/**
	 * Metodo que fica ouvindo a porta do servidor a espera de novas conexões
	 */
	@Override
	public void run() {
		try {
			// Cria canal de comunicação
			this.serverSocket = new ServerSocket(Especification.TCP_PORT);

			// Aguarda nova conexão
			while (!finish) {
				this.clientSocket = this.serverSocket.accept();
				this.socketCommunication = new TCPComunication(
						this.clientSocket);
				new Thread(new UploadHandler(this.socketCommunication, this.print),
						"upload").start();
			}

			// Fecha canal de comunicação
			this.serverSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que finaliza o processo servidor
	 */
	public void finish() {
		this.finish = true;
	}
}