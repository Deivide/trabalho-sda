package sda.manager.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import sda.comunication.especification.Especification;
import sda.comunication.tcp.TCPComunication;
import sda.manager.handler.DownloadHandler;

/**
 * Classe que implementa o servidor de Download
 */
public class ServerDownload extends Thread {

	private Socket clientSocket;
	private ServerSocket serverSocket;
	private boolean finish = false;
	private TCPComunication comunicationTCP;

	/**
	 * Construtor da classe
	 * 
	 * @param threadName
	 */
	public ServerDownload(String threadName) {
		super(threadName);
	}

	/**
	 * Metodo que fica ouvindo a porta do servidor a espera de novas conexões
	 */
	@Override
	public void run() {
		try {
			// Cria canal de comunicação
			this.serverSocket = new ServerSocket(Especification.DOWNLOAD_PORT);

			// Aguarda nova conexão
			while (!finish) {
				this.clientSocket = this.serverSocket.accept();
				this.comunicationTCP = new TCPComunication(this.clientSocket);
				new Thread(new DownloadHandler(this.comunicationTCP),
						"download").start();
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