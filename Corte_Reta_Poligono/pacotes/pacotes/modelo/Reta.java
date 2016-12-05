package pacotes.modelo;

import java.awt.Point;

public class Reta {

	private Point pontoInicial;
	private Point pontoFinal;

	// ****************************************************************************************
	public Reta(Point pInicial, Point pFinal) {
		pontoInicial = pInicial;
		pontoFinal = pFinal;
	}

	// ****************************************************************************************
	public Point getPontoInicial() {
		return (pontoInicial);
	}

	// ****************************************************************************************
	public Point getPontoFinal() {
		return (pontoFinal);
	}

	// ****************************************************************************************
}
