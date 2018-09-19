package br.com.mackenzie.tcc.ui.app;

import java.io.IOException;
import java.net.MalformedURLException;

import org.eclipse.paho.client.mqttv3.MqttException;

import br.com.mackenzie.tcc.message.broker.Publisher;
import br.com.mackenzie.tcc.message.broker.Subscriber;
import br.com.mackenzie.tcc.ui.UIBrokerPanel;

public class JBrokerApplication {

	private static UIBrokerPanel panel;

	public static void main(String[] args) {
		try {
			panel = new UIBrokerPanel();
			start();
		} catch (MqttException e) {
			panel.showMessageDialog("Um erro inesperado ocorreu no sistema. \nRegistro de Erro: " + e.getMessage());
		} catch (IOException e) {
			panel.showMessageDialog("Erro de saida no sistema. \nRegistro de Erro: " + e.getMessage());
		}
	}

	private static void start() throws MqttException, MalformedURLException {
		int i = -1;
		boolean closePubFlag = false;
		boolean closeSubFlag = false;
		boolean pubFlag = false;
		boolean subFlag = false;
		while (i != 4) {
			i = panel.showOptionDialog("Selecione a opção para ser executada: ");
			int result = -1;
			switch (i) {
			case 0:
				panel.showTutorial();
				break;

			case 1:
				if (!pubFlag) {
					result = panel.showConfirmDialog("O Cliente Publisher será encerrado após usa execução?");
					panel.showWarningDialog("O valor do topico deve ser o mesmo para o Publisher e o Subscriber");
					String topico = panel.showInputDialog("Digite o topico (ID): ");
					String msgEnviada = panel.showInputDialog("Digite a mensagem (Comando): ");
					Publisher.initPublisherClient(topico, msgEnviada, result == 0 ? Boolean.TRUE : Boolean.FALSE);
					closePubFlag = result == 0 ? Boolean.TRUE : Boolean.FALSE;
					pubFlag = true;
				} else {
					panel.showWarningDialog("Você ja iniciou o Publisher. \nSe quiser iniciar outro novamente, encerre o cliente.");
				}
				break;

			case 2:
				if (!subFlag) {
					result = panel.showConfirmDialog("O Cliente Subscriber será encerrado após usa execução?");
					panel.showWarningDialog("O valor do topico deve ser o mesmo para o Publisher e o Subscriber");
					String topico = panel.showInputDialog("Digite o topico (ID): ");
					Subscriber.initSubscriberClient(topico, result == 0 ? Boolean.TRUE : Boolean.FALSE);
					closeSubFlag = result == 0 ? Boolean.TRUE : Boolean.FALSE;
					subFlag = true;
				} else {
					panel.showWarningDialog("Você ja iniciou o Subscriber. \nSe quiser iniciar outro novamente, encerre o cliente.");
				}
				break;

			case 3:
				panel.showMessageDialog("Mensagem Recebida: \n" + Subscriber.getReceivedMessage());
				break;
			case 4:
				if (closePubFlag) {
					Publisher.close();
				}
				if (closeSubFlag) {
					Subscriber.close();
				}
				panel.showExitDialog("Encerrando aplicação TOPLOOSE");
				break;
			default:
				panel.showMessageDialog("Opção Invalida. Selecione novamente...");
				break;

			}
		}
		System.exit(0);
	}


}
