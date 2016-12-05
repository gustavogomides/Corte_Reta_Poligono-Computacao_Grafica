package pacotes.controle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import pacotes.modelo.*;

public class ControlarSelecao {

	private Util u = new Util();

	private ArrayList<Reta> retasCortadas = new ArrayList<>();
	private ArrayList<Reta> retasSobras = new ArrayList<>();

	private Color cinza = new Color(238, 238, 238);

	private ControlarReta controleReta = new ControlarReta();

	// ****************************************************************************
	// EXCLUIR ELEMENTOS DOS ARRAYSLISTS
	public void limparRetas() {
		retasCortadas.clear();
		retasSobras.clear();
	}

	// ****************************************************************************
	// DESENHAR ÁREA DE CORTE
	public void desenharSelecao(Point pEsq, Point pDir, Color c, Graphics g) {
		int cont = 0, cont2 = 0;
		Point p1 = new Point(0, 0), p2 = new Point(0, 0);

		if (pEsq.getX() >= pDir.getX()) {
			if (pEsq.getY() >= pDir.getY()) {
				p1.setLocation(pDir.getX(), pDir.getY());
				p2.setLocation(pEsq.getX(), pEsq.getY());
			} else {
				p1.setLocation(pDir.getX(), pEsq.getY());
				p2.setLocation(pEsq.getX(), pDir.getY());
			}
		} else {
			if (pEsq.getY() >= pDir.getY()) {
				p1.setLocation(pEsq.getX(), pDir.getY());
				p2.setLocation(pDir.getX(), pEsq.getY());
			} else {
				p1.setLocation(pEsq.getX(), pEsq.getY());
				p2.setLocation(pDir.getX(), pDir.getY());
			}
		}

		if (pEsq.getY() >= pDir.getY()) {
			p1.y = (int) pDir.getY();
			p2.y = (int) pEsq.getY();
		} else {
			p2.y = (int) pDir.getY();
			p1.y = (int) pEsq.getY();
		}

		for (int i = (int) p1.getX(); i <= (int) p2.getX(); i++) {
			if (cont == 2) {
				if (cont2 == 0) {
					cont2 = 1;
				} else {
					cont = 0;
					cont2 = 0;
				}
			} else {
				cont++;
				u.plotarPonto(c, g, i, (int) p1.getY());
				u.plotarPonto(c, g, i, (int) p2.getY());
			}
		}
		cont = 0;
		for (int i = (int) p1.getY(); i <= (int) p2.getY(); i++) {
			if (cont == 2) {
				if (cont2 == 0) {
					cont2 = 1;
				} else {
					cont = 0;
					cont2 = 0;
				}
			} else {
				cont++;
				u.plotarPonto(c, g, (int) p1.getX(), i);
				u.plotarPonto(c, g, (int) p2.getX(), i);
			}
		}
	}

	// ****************************************************************************
	// DETERMINAR REGIÃO
	private int regiao(double x, double y, double xmin, double ymin, double xmax, double ymax) {
		// esquerda = 8
		// direita = 4
		// superior = 2
		// inferior = 6
		int regiao = 0;
		if (x < xmin) { // esquerda
			regiao = 8;
		} else if (x > xmax) { // direita
			regiao = 4;
		} else if (y < ymin) { // inferior
			regiao = 2;
		} else if (y > ymax) { // superior
			regiao = 6;
		}
		return regiao;
	}

	// ****************************************************************************
	// CORTAR RETAS
	public void cortarRetas(Graphics g, double x1, double y1, double x2, double y2, double xmin, double ymin,
			double xmax, double ymax) {

		double m = 0, inversom = 0;

		if (x1 != x2) {
			m = (y2 - y1) / (x2 - x1);
		}

		if (y1 != y2) {
			inversom = 1 / m;
		}

		int r1 = regiao(x1, y1, xmin, ymin, xmax, ymax);
		int r2 = regiao(x2, y2, xmin, ymin, xmax, ymax);

		Point p;

		controleReta.desenharReta(new Reta(new Point((int) x1, (int) y1), new Point((int) x2, (int) y2)), cinza, g);

		double xa = x1, ya = y1, xb = x2, yb = y2;
		while ((r1 | r2) != 0) {
			if ((r1 & r2) != 0) {
				return;
			}
			if (r1 != 0) { // ponto inicial
				if ((r1 & 8) == 8) { // esquerda
					p = corte_esquerda(x1, y1, xmin, m);
					x1 = p.getX();
					y1 = p.getY();
				} else if ((r1 & 4) == 4) { // direita
					p = corte_direita(x1, y1, xmax, m);
					x1 = p.getX();
					y1 = p.getY();
				} else if ((r1 & 2) == 2) { // inferior
					p = corte_inferior(x1, y1, ymin, inversom);
					x1 = p.getX();
					y1 = p.getY();
				} else if ((r1 & 6) == 6) { // superior
					p = corte_superior(x1, y1, ymax, inversom);
					x1 = p.getX();
					y1 = p.getY();
				}
				r1 = regiao(x1, y1, xmin, ymin, xmax, ymax);
			} else { // ponto final
				if (r2 != 0) {
					if ((r2 & 8) == 8) { // esquerda
						p = corte_esquerda(x2, y2, xmin, m);
						x2 = p.getX();
						y2 = p.getY();
					} else if ((r2 & 4) == 4) { // direita
						p = corte_direita(x2, y2, xmax, m);
						x2 = p.getX();
						y2 = p.getY();
					} else if ((r2 & 2) == 2) { // inferior
						p = corte_inferior(x2, y2, ymin, inversom);
						x2 = p.getX();
						y2 = p.getY();
					} else if ((r2 & 6) == 6) { // superior
						p = corte_superior(x2, y2, ymax, inversom);
						x2 = p.getX();
						y2 = p.getY();
					}
					r2 = regiao(x2, y2, xmin, ymin, xmax, ymax);
				}
			}
		}

		Reta reta = new Reta(new Point((int) x1, (int) y1), new Point((int) x2, (int) y2));
		retasCortadas.add(reta);
		controleReta.desenharReta(reta, Color.BLUE, g);

		double xaa = x1, yaa = y1, xbb = x2, ybb = y2;

		Reta retaSobra1 = new Reta(new Point((int) xa, (int) ya), new Point((int) xaa, (int) yaa));
		Reta retaSobra2 = new Reta(new Point((int) xb, (int) yb), new Point((int) xbb, (int) ybb));
		retasSobras.add(retaSobra1);
		retasSobras.add(retaSobra2);

		for (Reta r : retasSobras) {
			controleReta.desenharReta(r, Color.ORANGE, g);
		}
	}

	// ****************************************************************************
	// CORTE ESQUERDA
	public Point corte_esquerda(double x1, double y1, double xe, double m) {
		// x1, y1 -> ponto inicial do segmento de reta
		// xe -> abscissa da borda esquerda da janela
		// m -> inclinação do segmento de reta
		double x, y;
		y = m * (xe - x1) + y1;
		x = xe;

		return new Point((int) x, (int) y);
	}

	// ****************************************************************************
	// CORTE DIREITA
	public Point corte_direita(double x2, double y2, double xd, double m) {
		// x2, y2 -> ponto final do segmento de reta
		// xd -> abscissa da borda direita da janela
		// m -> inclinação do segmento de reta
		double x, y;
		y = m * (xd - x2) + y2;
		x = xd;

		return new Point((int) x, (int) y);
	}

	// ****************************************************************************
	// CORTE SUPERIOR
	public Point corte_superior(double x1, double y1, double ys, double inversom) {
		// x1, y1 -> ponto inicial do segmento de reta
		// ys -> ordenada da borda superior da janela
		// m -> inclinação do segmento de reta
		double x, y;
		y = ys;
		x = (ys - y1) * inversom + x1;

		return new Point((int) x, (int) y);
	}

	// ****************************************************************************
	// CORTE INFERIOR
	public Point corte_inferior(double x2, double y2, double yi, double inversom) {
		// x2, y2 -> ponto final do segmento de reta
		// yi -> ordenada da borda inferior da janela
		// m -> inclinação do segmento de reta
		double x, y;
		y = yi;
		x = (yi - y2) * inversom + x2;

		return new Point((int) x, (int) y);
	}

	// ****************************************************************************
	// MOSTRAR RETAS CORTADAS
	public void mostrarRetasCortadas(Graphics g, Color cor1, Color cor2) {
		for (Reta r : retasCortadas) { // imprimir retas dentro da área de corte
			controleReta.desenharReta(r, cor1, g);
		}

		for (Reta r : retasSobras) { // imprimir retas fora da área de corte
			controleReta.desenharReta(r, cor2, g);
		}
	}

	// ****************************************************************************
	// CORTAR POLIGONOS
	public void cortarPoligonos(Graphics g, ArrayList<Point> verticesInicias, double xmin, double ymin, double xmax,
			double ymax) {
		ArrayList<Point> verticesFinais = new ArrayList<>();

		// borda direita : (xmax, ymin) - (xmax, ymax)

		// borda inferior : (xmax, ymax) - (xmin, ymax)

		// borda esquerda : (xmin, ymax) - (xmin, ymin)

		// borda superior : (xmin, ymin) - (xmax, ymin)

	}

	private void rotinaSH(ArrayList<Point> verticesInicias, ArrayList<Point> verticesFinais) {

	}

	// ****************************************************************************
	// INTERSECÇÃO
	public void intersection(Reta reta1, Reta reta2, Graphics g) {
		double x1, y1, x2, y2, x3, y3, x4, y4;

		// Coordenadas da reta 1.
		x1 = reta1.getPontoInicial().getX();
		y1 = reta1.getPontoInicial().getY();
		x2 = reta1.getPontoFinal().getX();
		y2 = reta1.getPontoFinal().getY();

		// Coordenadas da reta 2.
		x3 = reta2.getPontoInicial().getX();
		y3 = reta2.getPontoInicial().getY();
		x4 = reta2.getPontoFinal().getX();
		y4 = reta2.getPontoFinal().getY();

		double d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
		if (d == 0)
			return;

		double xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
		double yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;

		Util u = new Util();
		u.plotarPonto(Color.ORANGE, g, (int) xi, (int) yi);
	}

	private void dentro(Point p, String borda, Point p1, Point p2) {

		boolean dentro = false;

		switch (borda) {
		case "superior":
			for (int i = (int) p1.getX(); i < p2.getX(); i++) {
				if (p.getX() == i) {
					dentro = true;
				}
			}
			break;

		case "inferior":
			for (int i = (int) p1.getX(); i < p2.getX(); i++) {
				if (p.getX() == i) {
					dentro = true;
				}
			}
			break;

		}

	}
}
