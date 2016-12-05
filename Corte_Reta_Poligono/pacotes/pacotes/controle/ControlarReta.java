package pacotes.controle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import pacotes.modelo.*;

public class ControlarReta {
	private Util util = new Util();

	// *************************************************************************************
	// DESENHAR RETA
	public void desenharReta(Reta r, Color c, Graphics g) {

		Point p1, p2;
		p1 = r.getPontoInicial();
		p2 = r.getPontoFinal();

		int x1 = p1.x;
		int x2 = p2.x;
		int y1 = p1.y;
		int y2 = p2.y;

		int difX = x2 - x1;
		int difY = y2 - y1;
		int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;

		if (difX < 0) {
			dx1 = -1;
		} else if (difX > 0) {
			dx1 = 1;
		}

		if (difY < 0) {
			dy1 = -1;
		} else if (difY > 0) {
			dy1 = 1;
		}

		if (difX < 0) {
			dx2 = -1;
		} else if (difX > 0) {
			dx2 = 1;
		}

		int parteX = Math.abs(difX);
		int parteY = Math.abs(difY);

		if (!(parteX > parteY)) {
			parteX = Math.abs(difY);
			parteY = Math.abs(difX);
			if (difY < 0) {
				dy2 = -1;
			} else if (difY > 0) {
				dy2 = 1;
			}
			dx2 = 0;
		}

		int cima = parteX >> 1;

		for (int i = 0; i <= parteX; i++) {
			util.plotarPonto(c, g, x1, y1);
			cima += parteY;

			if (!(cima < parteX)) {
				cima -= parteX;
				x1 += dx1;
				y1 += dy1;
			} else {
				x1 += dx2;
				y1 += dy2;
			}
		}
	}

	// *************************************************************************************
}
