package pacotes.controle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import pacotes.modelo.*;
import pacotes.view.*;

public class ControlarAplicativo implements ActionListener {

	private MontarPainelInicial pnCenario;
	public ArrayList<Reta> retas = new ArrayList<>();
	public ArrayList<Poligono> poligonos = new ArrayList<>();
	public ArrayList<Retangulo> selecoes = new ArrayList<>();

	private ControlarCirculo controleCirculo = new ControlarCirculo();
	private ControlarReta controleReta = new ControlarReta();
	private ControlarPoligono controlePoligono = new ControlarPoligono();
	private ControlarSelecao controleSelecao = new ControlarSelecao();

	public boolean desenharReta = false;
	public boolean desenharPoligono = false;
	public boolean selecao = false;

	private Graphics desenho;

	private Color cinza = new Color(238, 238, 238);

	// ****************************************************************************
	// CONSTRUTORES DA CLASSE ControlarAplicativo
	public ControlarAplicativo() {
		pnCenario = new MontarPainelInicial(this);
		pnCenario.showPanel();
		pnCenario.mensagemInicial();
		desenho = pnCenario.iniciarGraphics();
	}

	public ControlarAplicativo(boolean opcao) {

	}

	// ****************************************************************************
	// ACTION PERFORMED - CAPTURAR E TRATAR CLIQUE DOS BOTÕES
	public void actionPerformed(ActionEvent e) {
		String comando;
		comando = e.getActionCommand();

		// DESENHAR RETA
		if (comando.equals("botaoReta")) {
			desenharReta = true;
			desenharPoligono = false;
			selecao = false;
		}

		// DESENHAR POLIGONO
		if (comando.equals("botaoPoligono")) {
			desenharReta = false;
			desenharPoligono = true;
			selecao = false;
		}

		// FAZER SELEÇÃO
		if (comando.equals("botaoSelecao")) {
			desenharReta = false;
			desenharPoligono = false;
			selecao = true;
		}

		// REALIZAR CORTE RETA
		if (comando.equals("botaoRealizarCorteReta")) {
			desenho = pnCenario.iniciarGraphics();
			cortarRetas(desenho);
		}

		// REALIZAR CORTE POLIGONO
		if (comando.equals("botaoRealizarCortePoligono")) {
			desenho = pnCenario.iniciarGraphics();
			// cortarPoligonos(desenho);

			double x1, y1, x2, y2;
			x1 = selecoes.get(0).getpontoInicial().getX();
			y1 = selecoes.get(0).getpontoInicial().getY();
			x2 = x1;
			y2 = selecoes.get(0).getpontoFinal().getY();

			for (Reta reta : retas) {
				controleSelecao.intersection(reta,
						new Reta(new Point((int) x1, (int) y1), new Point((int) x2, (int) y2)), desenho);

			}

		}

		// MOSTRAR RETAS CORTADAS
		if (comando.equals("botaoMostrarCortados")) {
			desenho = pnCenario.iniciarGraphics();
			mostrarRetasCortadas(desenho);
		}

		// LIMPAR TELA
		if (comando.equals("botaoLimpar")) {
			desenharReta = false;
			desenharPoligono = false;
			selecao = false;
			if (retas.size() != 0 || poligonos.size() != 0 || selecoes.size() != 0) {
				int option;

				option = JOptionPane.showConfirmDialog(null, "Deseja limpar a tela (excluir o desenho)?", "Limpar",
						JOptionPane.YES_NO_OPTION);

				if (option == JOptionPane.YES_OPTION) {
					pnCenario.ocultarDesenho();
					retas.clear();
					poligonos.clear();
					selecoes.clear();
					controleSelecao.limparRetas();
				}
			}
		}

		// LIMPAR AREA DE CORTE
		if (comando.equals("botaoLimparCorte")) {
			desenho = pnCenario.iniciarGraphics();
			limparAreaCorte(desenho);
		}

		// FIM DO PROGRAMA
		if (comando.equals("botaoFim")) {
			int option;

			option = JOptionPane.showConfirmDialog(null, "Deseja sair da aplicação?", "Sair",
					JOptionPane.YES_NO_OPTION);

			if (option == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}

	}

	// ****************************************************************************
	// CORTAR POLÍGONOS
	private void cortarPoligonos(Graphics g) {
		// controleSelecao.cortarPoligonos(g, poligonos);

		for (Poligono p : poligonos) {
			double xmin = 0, ymin = 0, xmax = 0, ymax = 0;

			for (Retangulo r : selecoes) {
				if (r.getpontoInicial().getX() < r.getpontoFinal().getX()) {
					xmin = r.getpontoInicial().getX();
					xmax = r.getpontoFinal().getX();
				} else {
					xmax = r.getpontoInicial().getX();
					xmin = r.getpontoFinal().getX();
				}

				if (r.getpontoInicial().getY() < r.getpontoFinal().getY()) {
					ymin = r.getpontoInicial().getY();
					ymax = r.getpontoFinal().getY();
				} else {
					ymax = r.getpontoInicial().getY();
					ymin = r.getpontoFinal().getY();
				}
				controleSelecao.cortarPoligonos(g, p.getVerticesIniciais(), xmin, ymin, xmax, ymax);
			}
		}

	}

	// ****************************************************************************
	// LIMPAR ÁREA DE CORTE
	private void limparAreaCorte(Graphics g) {
		for (Retangulo s : selecoes) {
			controleSelecao.desenharSelecao(s.getpontoInicial(), s.getpontoFinal(), cinza, g);
		}
	}

	// ****************************************************************************
	// MOSTRAR RETAS CORTADAS
	private void mostrarRetasCortadas(Graphics g) {
		controleSelecao.mostrarRetasCortadas(g, Color.BLUE, Color.ORANGE);
	}

	// ****************************************************************************
	// CORTAR RETAS
	public void cortarRetas(Graphics desenho) {
		for (Reta reta : retas) {
			double xmin = 0, ymin = 0, xmax = 0, ymax = 0;

			for (Retangulo r : selecoes) {
				if (r.getpontoInicial().getX() < r.getpontoFinal().getX()) {
					xmin = r.getpontoInicial().getX();
					xmax = r.getpontoFinal().getX();
				} else {
					xmax = r.getpontoInicial().getX();
					xmin = r.getpontoFinal().getX();
				}

				if (r.getpontoInicial().getY() < r.getpontoFinal().getY()) {
					ymin = r.getpontoInicial().getY();
					ymax = r.getpontoFinal().getY();
				} else {
					ymax = r.getpontoInicial().getY();
					ymin = r.getpontoFinal().getY();
				}

				controleSelecao.cortarRetas(desenho, reta.getPontoInicial().getX(), reta.getPontoInicial().getY(),
						reta.getPontoFinal().getX(), reta.getPontoFinal().getY(), xmin, ymin, xmax, ymax);
			}
		}
	}

	// ****************************************************************************
	// ADICIONAR POLIGONO AO ARRAYLIST
	public void adicionarPoligono(ArrayList<Point> vertices) {
		Poligono poligono = new Poligono(vertices);
		poligonos.add(poligono);
	}

	// ****************************************************************************
	// DESENHAR CORTE SELECAO
	public void desenharSelecao(Point p1, Point p2, Color c, Graphics g) {
		controleSelecao.desenharSelecao(p1, p2, c, g);
	}

	// ****************************************************************************
	// DESENHAR RETA
	public void desenharRetas(Reta reta, Color cor, Graphics g) {
		controleReta.desenharReta(reta, cor, g);
	}

	// ****************************************************************************
	// DESENHAR CÍRCULO MARCAÇÃO
	public void desenharCirculoMarcacao(Color cor, Graphics g, double x, double y) {
		controleCirculo.desenharCirculo(g, cor, 3, new Circulo(x, y, 3));
	}

}
