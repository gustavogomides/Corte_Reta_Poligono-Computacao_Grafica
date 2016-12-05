package pacotes.view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import pacotes.controle.*;
import pacotes.modelo.*;

public class MontarPainelInicial implements MouseListener, MouseMotionListener {

	public JFrame baseFrame;
	private JPanel basePanel;
	private JPanel outputPanel;
	private JPanel buttonPanel;

	private JButton btEnd;
	private JButton btLimparTela;
	private JButton btReta;
	private JButton btPoligono;
	private JButton btAreaCorte;
	private JButton btLimparCorte;
	private JButton btCortarReta;
	private JButton btCortarPoligono;
	private JButton btMostrarCortadas;

	private JLabel labelVisor;

	private Graphics desenho;

	private ControlarAplicativo controlePrograma;

	private Point clique1 = null;
	private Point clique2 = null;
	private Point pInicio = null;

	private boolean primeiroClique = true;

	private Color cinza = new Color(238, 238, 238);

	ArrayList<Point> vertices = new ArrayList<>();

	// ******************************************************************************************************************
	// MONTAR PAINEL INICIAL
	public MontarPainelInicial(ControlarAplicativo controlePrograma) {
		this.controlePrograma = controlePrograma;
		Util u = new Util();

		// LAYOUT
		baseFrame = new JFrame();
		baseFrame.setLayout(new BoxLayout(baseFrame.getContentPane(), BoxLayout.Y_AXIS));

		baseFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // FITS PANEL TO THE
															// ACTUAL MONITOR
		baseFrame.setUndecorated(true); // TURN OFF ALL THE PANEL BORDERS

		basePanel = new JPanel();
		basePanel.setLayout(new BorderLayout());

		// OUTPUT PANEL
		outputPanel = new JPanel();
		outputPanel.setLayout(new BorderLayout());

		// BUTTON PANEL
		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(0, 70));
		buttonPanel.setBackground(Color.BLUE);

		JPanel panel = new JPanel(new BorderLayout()); // LABEL VISOR
		labelVisor = new JLabel("");
		labelVisor.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(labelVisor, BorderLayout.NORTH);
		buttonPanel.add(panel);
		labelVisor.setBackground(Color.WHITE);
		labelVisor.setForeground(Color.BLACK);
		labelVisor.setBorder(new EmptyBorder(5, 5, 5, 5));

		// ADICIONAR BOTÕES
		// Desenhar Reta
		btReta = u.addAButton("Desenhar Reta", "botaoReta", buttonPanel);
		btReta.addActionListener(controlePrograma);
		btReta.setBackground(Color.CYAN);
		btReta.setForeground(Color.BLACK);

		// Desenhar Polígono
		btPoligono = u.addAButton("Desenhar Polígono", "botaoPoligono", buttonPanel);
		btPoligono.addActionListener(controlePrograma);
		btPoligono.setBackground(Color.CYAN);
		btPoligono.setForeground(Color.BLACK);

		// Área de Corte
		btAreaCorte = u.addAButton("Área de Corte", "botaoSelecao", buttonPanel);
		btAreaCorte.addActionListener(controlePrograma);
		btAreaCorte.setBackground(Color.CYAN);
		btAreaCorte.setForeground(Color.BLACK);

		// Cortar Reta
		btCortarReta = u.addAButton("Realizar Corte Reta", "botaoRealizarCorteReta", buttonPanel);
		btCortarReta.addActionListener(controlePrograma);
		btCortarReta.setBackground(Color.CYAN);
		btCortarReta.setForeground(Color.BLACK);

		// Cortar Poligono
		btCortarPoligono = u.addAButton("Realizar Corte Polígono", "botaoRealizarCortePoligono", buttonPanel);
		btCortarPoligono.addActionListener(controlePrograma);
		btCortarPoligono.setBackground(Color.CYAN);
		btCortarPoligono.setForeground(Color.BLACK);

		// Mostrar Cortadas
		btMostrarCortadas = u.addAButton("Mostrar Segmentos Cortados", "botaoMostrarCortados", buttonPanel);
		btMostrarCortadas.addActionListener(controlePrograma);
		btMostrarCortadas.setBackground(Color.CYAN);
		btMostrarCortadas.setForeground(Color.BLACK);

		// Limpar Tela
		btLimparTela = u.addAButton("Limpar Tela", "botaoLimpar", buttonPanel);
		btLimparTela.addActionListener(controlePrograma);
		btLimparTela.setBackground(new Color(219, 61, 61));
		btLimparTela.setForeground(Color.WHITE);

		// Limpar Área de Corte
		btLimparCorte = u.addAButton("Limpar Área de Corte", "botaoLimparCorte", buttonPanel);
		btLimparCorte.addActionListener(controlePrograma);
		btLimparCorte.setBackground(new Color(219, 61, 61));
		btLimparCorte.setForeground(Color.WHITE);

		// Sair do Programa
		btEnd = u.addAButton("Sair", "botaoFim", buttonPanel);
		btEnd.addActionListener(controlePrograma);
		btEnd.setBackground(new Color(219, 61, 61));
		btEnd.setForeground(Color.WHITE);

		// OUVINTES DO MOUSE
		outputPanel.addMouseListener(this);
		outputPanel.addMouseMotionListener(this);

		// VISIBLE PANELS
		baseFrame.add(basePanel);
		basePanel.add(outputPanel, BorderLayout.CENTER);
		basePanel.add(buttonPanel, BorderLayout.PAGE_END);

		baseFrame.setVisible(true);
	}

	// ******************************************************************************************************************
	// METODO PARA MOSTRAR O FRAME BASICO
	public void showPanel() {
		basePanel.setVisible(true);
	}

	// ******************************************************************************************************************
	public void mouseClicked(MouseEvent evento) {
		int X = evento.getX();
		int Y = evento.getY();
		Point P = new Point(X, Y);

		if (controlePrograma.desenharReta || controlePrograma.desenharPoligono || controlePrograma.selecao) {
			if (controlePrograma.desenharReta) { // DESENHAR RETA
				if (primeiroClique) {
					controlePrograma.desenharCirculoMarcacao(Color.RED, desenho, P.getX(), P.getY());
					clique1 = P;
					primeiroClique = false;
					pInicio = P;
				} else {
					clique2 = P;
					controlePrograma.desenharCirculoMarcacao(cinza, desenho, clique1.getX(), clique1.getY());
					Reta reta = new Reta(this.clique1, this.clique2);
					controlePrograma.retas.add(reta);
					controlePrograma.desenharRetas(reta, Color.BLACK, desenho);
					clique1 = null;
					clique2 = null;
					primeiroClique = true;
				}

			} else if (controlePrograma.desenharPoligono) { // DESENHAR POLIGONO
				if (primeiroClique) { // vértice inicial
					controlePrograma.desenharCirculoMarcacao(Color.RED, desenho, P.getX(), P.getY());
					this.clique2 = P;
					this.primeiroClique = false;
					pInicio = P;
					vertices.add(P);
				} else {
					if (P.getX() >= pInicio.getX() - 4 && P.getX() <= pInicio.getX() + 4
							&& P.getY() >= pInicio.getY() - 4 && P.getY() <= pInicio.getY() + 4) {
						controlePrograma.desenharCirculoMarcacao(cinza, desenho, pInicio.getX(), pInicio.getY());
						controlePrograma.desenharRetas(new Reta(clique2, pInicio), Color.BLACK, desenho);
						pInicio = null;
						clique2 = null;
						this.primeiroClique = true;
						controlePrograma.adicionarPoligono(vertices);
					} else {
						controlePrograma.desenharRetas(new Reta(clique2, P), Color.BLACK, desenho);
						this.clique2 = P;
						vertices.add(clique2);
					}
				}
			} else if (controlePrograma.selecao) { // SELECIONAR PORÇÃO DA TELA
				if (primeiroClique) {
					controlePrograma.desenharCirculoMarcacao(Color.RED, desenho, P.getX(), P.getY());
					this.clique1 = P;
					primeiroClique = false;
					pInicio = P;
				} else {
					controlePrograma.desenharCirculoMarcacao(cinza, desenho, this.clique1.getX(), this.clique1.getY());
					clique2 = P;
					controlePrograma.desenharSelecao(this.clique1, this.clique2, Color.RED, desenho);
					Retangulo r = new Retangulo(this.clique1, this.clique2);
					controlePrograma.selecoes.add(r);
					clique1 = null;
					clique2 = null;
					primeiroClique = true;
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Deve escolher um dos botões da barra inferior para desenhar na tela!");
		}
	}

	// ******************************************************************************************************************
	public void mouseEntered(MouseEvent evento) {
	}

	// ******************************************************************************************************************
	public void mouseMoved(MouseEvent e) {
		Point P = new Point(e.getX(), e.getY());

		this.labelVisor.setText("Ponto: ( " + (int) P.getX() + " ; " + (int) P.getY() + " )");
	}

	// ******************************************************************************************************************
	public void mouseExited(MouseEvent evento) {
	}

	// ******************************************************************************************************************
	public void mousePressed(MouseEvent evento) {
	}

	// ******************************************************************************************************************
	public void mouseReleased(MouseEvent evento) {
	}

	// ******************************************************************************************************************
	public void mouseDragged(MouseEvent e) {
	}

	// ******************************************************************************************************************
	public Graphics iniciarGraphics() {
		desenho = outputPanel.getGraphics();
		return (desenho);
	}

	// ******************************************************************************************************************
	// OCULTAR O DESENHO
	public void ocultarDesenho() {
		desenho.clearRect(0, 0, outputPanel.getWidth(), outputPanel.getHeight());
		desenho.setColor(cinza);
	}

	// ******************************************************************************************************************
	// MENSAGEM INICIAL
	public void mensagemInicial() {
		JOptionPane.showMessageDialog(null, "Aula 09.", "Bem-vindo!", JOptionPane.INFORMATION_MESSAGE);
	}

	// ******************************************************************************************************************

}
