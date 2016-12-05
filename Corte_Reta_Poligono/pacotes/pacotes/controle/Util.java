package pacotes.controle;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JButton;

public class Util {

	// *************************************************************************************
	// PLOTAR PONTO
	public void plotarPonto(Color cor, Graphics g, int x, int y) {
		g.setColor(cor);
		g.drawLine(x, y, x, y);
	}

	// *************************************************************************************
	// ADICONAR BOTÃO
	public JButton addAButton(String textoBotao, String textoControle, Container container) {
		JButton botao;

		botao = new JButton(textoBotao);
		botao.setAlignmentX(Component.CENTER_ALIGNMENT);
		container.add(botao);

		botao.setActionCommand(textoControle);

		return (botao);
	}

}
