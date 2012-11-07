package sda.client.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import sda.comunication.common.Solicitation;
import sda.comunication.common.Util;
import sda.comunication.especification.Especification;
import sda.comunication.tcp.TCPComunication;
import sda.comunication.udp.UDPComunication;
import sda.comunication.common.Package;

/**
 * Classe que gerencia o Arquivo no modulo do Cliente (Client)
 */
public class UCHandlerArchiveManager {

	/**
	 * Metodo que efetua o upload do arquivo para os repositorios Envia arquivo
	 * passado como parametro para o modulo de Gerenciamento (Manager)
	 * 
	 * @param filePath
	 *            - Arquivo a ser enviado para repositorios
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void sendFile(String filePath) throws UnknownHostException,
			IOException {
		// Cria Socket de comunicação com o modulo Manager
		Socket socket = new Socket(Especification.MANAGER_ADDR,
				Especification.TCP_PORT);

		// Carrega arquivo
		File file = new File(filePath);
		FileInputStream fileInputStream = new FileInputStream(file);

		// Estabelece o canal TCP
		TCPComunication comunicationTCP = new TCPComunication(socket);

		// Instancia variaveis
		byte[] buffer = new byte[Especification.BUFFER_SIZE];
		int read;
		int pakageNumber = 1;
		Package pack;

		// Efetua toda a leitura do arquivo e envia todos os pacotes
		System.out
				.println("[Modulo Client] - Enviando arquivo ao modulo de Gerenciamento");
		do {
			read = fileInputStream.read(buffer);

			pack = new Package();
			pack.setFileName(file.getName());
			pack.setSequenceNumber(pakageNumber++);
			pack.setNext(pakageNumber);
			pack.setPayLoad(buffer);

			if (read == Especification.BUFFER_SIZE) {
				pack.setNotLast(true);
			} else {
				pack.setNotLast(false);
				pack.setPayLoad(Util.copyBytes(buffer, read));
			}

			System.out.println("[Modulo Client] - Nº pacote: "
					+ pack.getSequenceNumber());
			comunicationTCP.sendObject(pack);

		} while (read == Especification.BUFFER_SIZE);
		System.out
				.println("[Modulo Client] - Arquivo enviado ao modulo de Gerenciamento");
		System.out.println();

		fileInputStream.close();
		
		// Fecha os canais de comunicação
		comunicationTCP.close();
		socket.close();
	}

	/**
	 * Metodo que efetua o download do arquivo dos os repositorios Solicita
	 * arquivo passado como parametro para o modulo de Gerenciamento (Manager)
	 * 
	 * @param fileName
	 *            - Nome do arquivo a ser requisitado
	 * @param path
	 *            - Caminho a ser gravado o arquivo
	 */
	public void receiveFile(String fileName, String path) {
		System.out
				.println("[Modulo Client] - Solicitando arquivo ao modulo de Gerenciamento");
		new Thread(new FileReceiver(fileName, path), "file_receiver").start();
	}

	/**
	 * Metodo que localiza o modulo de Gerenciamento na rede
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void locateManager() throws IOException,
			ClassNotFoundException {

		UDPComunication com = new UDPComunication();
		Solicitation solicitation = new Solicitation();

		solicitation.setAddress(InetAddress.getLocalHost().getHostAddress());
		solicitation.setPort(Especification.DISCOVERY_REPLY_PORT);
		solicitation.setCode(Solicitation.DISCOVER);

		com.sendObject(Especification.GROUP, Especification.DISCOVERY_PORT,
				solicitation);

		Solicitation reply = (Solicitation) com
				.readObject(Especification.DISCOVERY_REPLY_PORT);

		Especification.MANAGER_ADDR = reply.getAddress();
	}
}