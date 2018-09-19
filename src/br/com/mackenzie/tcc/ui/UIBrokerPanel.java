package br.com.mackenzie.tcc.ui;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class UIBrokerPanel {

	private static final String TITLE = "MQTT Broker";
	private static final String IMG_MQTT_NAME = "mqtt";
	private static final String IMG_EXIT_NAME = "toploose";

	private static Object[] options;
	
	private ImageIcon icon;
	private ImageIcon exitIcon;
	
	public UIBrokerPanel() {
		this.searchIcon(IMG_MQTT_NAME);
		this.searchIcon(IMG_EXIT_NAME);
		options = new Object[] {"Como iniciar o Broker?", "Publisher", "Subscriber", "Mostrar Mensagem", "Sair"};
	}
	
	private void searchIcon(String name) {
		try {
			ImageIcon icon = new ImageIcon("src/img/" + name + ".png");
			if (icon.getImageLoadStatus() != 8) {
				if (name.equals(IMG_MQTT_NAME)) {
					URL url = new URL("https://lh3.googleusercontent.com/"
							+ "AHIgwQUOeE7xlxdag1j92gz5Rxvgdbuetx5pDoubI8mvnrVxw2TUVyrx47uOit0nDR4=s128");
					icon = new ImageIcon(url);
				} else if (name.equals(IMG_EXIT_NAME)) {
					System.out.println("4");
					URL url = new URL("https://i2.wp.com/www.10bestseo.com/"
							+ "tips/images/10-of-25-best-seo-tips.png?w=1165&ssl=1");
					icon = new ImageIcon(url);
				}
			}
			if (name.equals(IMG_MQTT_NAME)) {
				this.icon = icon;
			} else if (name.equals(IMG_EXIT_NAME)) {
				this.exitIcon = icon;
			}
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar informações do Painel. Erro no Icone: " + e.getMessage());
		}
	}
	
	public void showMessageDialog(Object message) {
		JOptionPane.showMessageDialog(null, message);
	}
	
	public int showOptionDialog(Object message) {
		return JOptionPane.showOptionDialog(null, message, 
				TITLE, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, icon, options, 0);
	}
	
	public int showConfirmDialog(Object message) {
		return JOptionPane.showConfirmDialog(null, message, TITLE, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
	}
	
	public void showWarningDialog(Object message) {
		JOptionPane.showMessageDialog(null, message, TITLE, JOptionPane.WARNING_MESSAGE);
	}
	
	public String showInputDialog(Object message) {
		return JOptionPane.showInputDialog(message);
	}
	
	public int showExitDialog(Object message) {
		return JOptionPane.showConfirmDialog(null, message, TITLE, JOptionPane.CLOSED_OPTION,
				JOptionPane.PLAIN_MESSAGE, exitIcon);
	}
	
	public void showTutorial() {
		JOptionPane.showMessageDialog(null, 
				"1. Execute o arquivo mosquette.bat, através do prompt de comando. \n"
				+ "Utilize o comando: '.\\mosquette.bat' \n"
				+ "2. Inicie pelo menu principal o Publisher e o Subscriber, respectivamente. \n"
				+ "3. Confirme a mensagem enviada pelo Publisher, através do Subscriver");
	}
	
}
