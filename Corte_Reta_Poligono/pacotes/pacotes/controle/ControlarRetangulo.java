package pacotes.controle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import pacotes.modelo.*;

public class ControlarRetangulo {

	private ControlarReta ctrReta = new ControlarReta();

	// *************************************************************************************
	// DESENHAR RETANGULO
	public void desenharRetangulo(Retangulo r, Color c, Graphics g) {
		ctrReta = new ControlarReta();

		Point p1 = new Point(), p2 = new Point();
		p1 = r.getpontoInicial();
		p2 = r.getpontoFinal();

		// Reta de P1 a  P2 sendo o X fixada no P1.
		ctrReta.desenharReta(new Reta(p1, new Point(p2.x, p1.y)), c, g);

		// Reta de P1 a P2 sendo o Y fixada no P1
		ctrReta.desenharReta(new Reta(p1, new Point(p1.x, p2.y)), c, g);

		// Reta de P1 a  P2 sendo o X fixada no P2.
		ctrReta.desenharReta(new Reta(new Point(p1.x, p2.y), p2), c, g);

		// Reta de P1 a  P2 sendo o Y fixada no P2.
		ctrReta.desenharReta(new Reta(p2, new Point(p2.x, p1.y)), c, g);

	}
	// ****************************************************************************
}