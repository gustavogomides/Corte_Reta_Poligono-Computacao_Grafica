package pacotes.controle;

import java.awt.Color;
import java.awt.Graphics;

import pacotes.modelo.*;

public class ControlarCirculo {

	private Util util = new Util();

	// *************************************************************************************
	// DESENHAR CIRCULO
	// opcao = 1 -> método clássico
	// opcao = 2 -> método polar simples
	// opcao = 3 -> método polar incremental
	// opcao = 4 -> método DDA

	public void desenharCirculo(Graphics g, Color cor, int opcao, Circulo circulo) {
		if (opcao == 1) {
			desenharCirculoClassico(cor, g, circulo);
		} else if (opcao == 2) {
			desenharCirculoPolarSimples(cor, g, circulo);
		} else if (opcao == 3) {
			desenharCirculoPolarIncremental(cor, g, circulo);
		} else {
			desenharCirculoDDA(cor, g, circulo);
		}
	}

	// *************************************************************************************
	// DESENHAR CIRCULO UTILIZANDO MÉTODO CLÁSSICO
	private void desenharCirculoClassico(Color cor, Graphics g, Circulo circulo) {
		double xc = circulo.getXc();
		double yc = circulo.getYc();
		int r = circulo.getRaio();
		double x, y1, y2;
		double expressao;

		for (x = xc - r; x <= xc + r; x++) {
			expressao = Math.sqrt((r * r) - ((x - xc) * (x - xc)));
			y1 = yc + expressao;
			y2 = yc - expressao;
			util.plotarPonto(cor, g, (int) x, (int) y1);
			util.plotarPonto(cor, g, (int) x, (int) y2);
		}
	}

	// *************************************************************************************
	// DESENHAR CIRCULO UTILIZANDO MÉTODO POLAR SIMPLES
	private void desenharCirculoPolarSimples(Color cor, Graphics g, Circulo circulo) {
		int teta = 0;
		double xc = circulo.getXc();
		double yc = circulo.getYc();
		double r = circulo.getRaio();
		int graus = 45;
		double x, y;
		for (int i = 0; i < 360; i++) {
			x = xc + r * Math.cos(teta);
			y = yc + r * Math.sin(teta);
			util.plotarPonto(cor, g, (int) x, (int) y);
			teta = graus--;
		}
	}

	// *************************************************************************************
	// DESENHAR CIRCULO UTILIZANDO MÉTODO POLAR INCREMENTAL
	private void desenharCirculoPolarIncremental(Color cor, Graphics g, Circulo circulo) {
		double xc = circulo.getXc();
		double yc = circulo.getYc();
		int R = circulo.getRaio();

		double x = R, y = 0;
		double theta = 0, dtheta = 1.0 / R;
		desenhar8pontos(cor, g, (int) xc, (int) yc, (int) x, (int) y);

		while (x > y) {
			theta += dtheta;
			x = Math.round(R * Math.cos(theta));
			y = Math.round(R * Math.sin(theta));
			desenhar8pontos(cor, g, (int) xc, (int) yc, (int) x, (int) y);
		}
	}

	// *************************************************************************************
	// DESENHAR CIRCULO UTILIZANDO METODO DDA
	private void desenharCirculoDDA(Color cor, Graphics g, Circulo circulo) {
		int r, x, y, novoX, novoY, erroRaio, xc, yc;
		xc = (int) circulo.getXc();
		yc = (int) circulo.getYc();
		r = circulo.getRaio();
		x = r;
		y = 0;
		novoX = 1 - 2 * r;
		novoY = 1;
		erroRaio = 0;
		while (x >= y) {
			desenhar8pontos(cor, g, xc, yc, x, y);
			y++;
			erroRaio += novoY;
			novoY += 2;
			if ((2 * erroRaio + novoX) > 0) {
				x--;
				erroRaio += novoX;
				novoX += 2;
			}
		}
	}

	// *************************************************************************************
	// DESENHAR PONTOS SIMÉTRICOS
	private void desenhar8pontos(Color cor, Graphics g, int xc, int yc, int x, int y) {
		util.plotarPonto(cor, g, xc + x, yc + y);
		util.plotarPonto(cor, g, xc - x, yc + y);
		util.plotarPonto(cor, g, xc - x, yc - y);
		util.plotarPonto(cor, g, xc + x, yc - y);
		util.plotarPonto(cor, g, xc + y, yc + x);
		util.plotarPonto(cor, g, xc - y, yc + x);
		util.plotarPonto(cor, g, xc - y, yc - x);
		util.plotarPonto(cor, g, xc + y, yc - x);
	}

	// *************************************************************************************
	// DESENHAR BORDA ARREDONDADAD RETANGULO
	public void desenharBorda(Circulo circ, Color c, int caso, Graphics g) {
		double xCentro = circ.getXc();
		double yCentro = circ.getYc();
		int r = circ.getRaio();
		double x = 0;
		double y = r;
		double u = 1;
		double v = 2 * r - 1;
		double E = 0;
		while (x < y) {
			switch (caso) {
			case 1:
				util.plotarPonto(c, g, (int) (xCentro - x), (int) (yCentro - y));
				x++;
				E += u;
				u += 2;
				if (v < 2 * E) {
					y--;
					E -= v;
					v -= 2;
				}
				if (x > y)
					break;
				util.plotarPonto(c, g, (int) (xCentro - y), (int) (yCentro - x));
				break;
			case 2:
				util.plotarPonto(c, g, (int) (xCentro - y), (int) (yCentro + x));
				x++;
				E += u;
				u += 2;
				if (v < 2 * E) {
					y--;
					E -= v;
					v -= 2;
				}
				if (x > y)
					break;
				util.plotarPonto(c, g, (int) (xCentro - x), (int) (yCentro + y));
				break;
			case 3:
				util.plotarPonto(c, g, (int) (xCentro + y), (int) (yCentro - x));
				x++;
				E += u;
				u += 2;
				if (v < 2 * E) {
					y--;
					E -= v;
					v -= 2;
				}
				if (x > y)
					break;
				util.plotarPonto(c, g, (int) (xCentro + x), (int) (yCentro - y));
				break;
			case 4:
				util.plotarPonto(c, g, (int) (xCentro + x), (int) (yCentro + y));
				x++;
				E += u;
				u += 2;
				if (v < 2 * E) {
					y--;
					E -= v;
					v -= 2;
				}
				if (x > y)
					break;
				util.plotarPonto(c, g, (int) (xCentro + y), (int) (yCentro + x));
				break;
			}
		}
	}
}
