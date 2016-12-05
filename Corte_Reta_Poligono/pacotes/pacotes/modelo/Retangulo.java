package pacotes.modelo;

import java.awt.Point;

public class Retangulo {

	private Point pontoInicial;
	private Point pontoFinal;
	private boolean rotacionado = false;

	// ****************************************************************************************
	public Retangulo(Point pontoInicial, Point pontoFinal) {
		super();
		this.pontoInicial = pontoInicial;
		this.pontoFinal = pontoFinal;
	}

	// ****************************************************************************************
	public Point getpontoInicial() {
		return pontoInicial;
	}

	// ****************************************************************************************
	public void setpontoInicial(Point pontoInicial) {
		this.pontoInicial = pontoInicial;
	}

	// ****************************************************************************************
	public Point getpontoFinal() {
		return pontoFinal;
	}

	// ****************************************************************************************
	public void setpontoFinal(Point pontoFinal) {
		this.pontoFinal = pontoFinal;
	}

	// ****************************************************************************************
	public boolean isRotacionado() {
		return rotacionado;
	}

	// ****************************************************************************************
	public void setRotacionado(boolean rotacionado) {
		this.rotacionado = rotacionado;
	}

	// ****************************************************************************************

}
