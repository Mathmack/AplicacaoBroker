package br.com.mackenzie.tcc.ui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.eclipse.paho.client.mqttv3.MqttException;

import br.com.mackenzie.tcc.message.broker.Publisher;
import br.com.mackenzie.tcc.message.broker.Subscriber;

public class JBrokerApplication {

	private static final String TITLE = "MQTT Broker";
	private static Object[] options = new Object[] 
			{"Como iniciar o Broker?", "Publisher", "Subscriber", "Mostrar Mensagem", "Sair"};

	public static void main(String[] args) {
		try {
			start();
		} catch (MqttException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro inesperado ocorreu no sistema. \nRegistro de Erro: " + e.getMessage());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Erro de saida no sistema. \nRegistro de Erro: " + e.getMessage());
		}
	}

	public static void start() throws MqttException, MalformedURLException {
		int i = -1;
		boolean closePubFlag = false;
		boolean closeSubFlag = false;
		boolean pubFlag = false;
		boolean subFlag = false;
		while (i != 4) {
			URL url = new URL(
					"https://lh3.googleusercontent.com/AHIgwQUOeE7xlxdag1j92gz5Rxvgdbuetx5pDoubI8mvnrVxw2TUVyrx47uOit0nDR4=s128");
			ImageIcon icon = new ImageIcon(url);
			i = JOptionPane.showOptionDialog(null, "Selecione a opção para ser executada: ", TITLE,
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, icon, options, 0);
			int result = -1;
			switch (i) {
			case 0:
				getTutorial();
				break;

			case 1:
				if (!pubFlag) {
					result = JOptionPane.showConfirmDialog(null, "O Cliente Publisher será encerrado após usa execução?",
							TITLE, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
					JOptionPane.showMessageDialog(null, "O valor do topico deve ser o mesmo para o Publisher e o Subscriber",
							TITLE, JOptionPane.WARNING_MESSAGE);
					String topico = JOptionPane.showInputDialog("Digite o topico (ID): ");
					String msgEnviada = JOptionPane.showInputDialog("Digite a mensagem (Comando): ");
					Publisher.initPublisherClient(topico, msgEnviada, result == 0 ? true : false);
					closePubFlag = result == 0 ? true : false;
					pubFlag = true;
				} else {
					JOptionPane.showMessageDialog(null, "Você ja iniciou o Publisher. \nSe quiser iniciar outro novamente, encerre o cliente.",
							TITLE, JOptionPane.WARNING_MESSAGE);
				}
				break;

			case 2:
				if (!subFlag) {
					result = JOptionPane.showConfirmDialog(null, "O Cliente Subscriber será encerrado após usa execução?",
							TITLE, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
					JOptionPane.showMessageDialog(null, "O valor do topico deve ser o mesmo para o Publisher e o Subscriber",
							TITLE, JOptionPane.WARNING_MESSAGE);
					String topico = JOptionPane.showInputDialog("Digite o topico (ID): ");
					Subscriber.initSubscriberClient(topico, result == 0 ? true : false).toString();
					closeSubFlag = result == 0 ? true : false;
					subFlag = true;
				} else {
					JOptionPane.showMessageDialog(null, "Você ja iniciou o Subscriber. \nSe quiser iniciar outro novamente, encerre o cliente.",
							TITLE, JOptionPane.WARNING_MESSAGE);
				}
				break;

			case 3:
				JOptionPane.showMessageDialog(null, Subscriber.getReceivedMessage());
				break;
			case 4:
				if (closePubFlag) {
					Publisher.close();
				}
				if (closeSubFlag) {
					Subscriber.close();
				}
				URL url2 = new URL(
						"https://i2.wp.com/www.10bestseo.com/tips/images/10-of-25-best-seo-tips.png?w=1165&ssl=1");
				ImageIcon icon2 = new ImageIcon(url2);
				JOptionPane.showConfirmDialog(null, "Encerrando aplicação TOPLOOSE", TITLE, JOptionPane.CLOSED_OPTION,
						JOptionPane.PLAIN_MESSAGE, icon2);
				break;
			default:
				JOptionPane.showMessageDialog(null, "Opção Invalida. Selecione novamente...");
				break;

			}
		}
		System.exit(0);
	}

	private static void getTutorial() {
		JOptionPane.showMessageDialog(null, 
				"1. Execute o arquivo mosquette.bat, através do prompt de comando. \n"
				+ "Utilize o comando: '.\\mosquette.bat' \n"
				+ "2. Inicie pelo menu principal o Publisher e o Subscriber, respectivamente. \n"
				+ "3. Confirme a mensagem enviada pelo Publisher, através do Subscriver");
	}

}
