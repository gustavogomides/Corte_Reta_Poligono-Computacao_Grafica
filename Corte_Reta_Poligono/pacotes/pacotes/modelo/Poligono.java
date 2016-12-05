package pacotes.modelo;

import java.awt.Point;
import java.util.ArrayList;

public class Poligono {

	private ArrayList<Point> verticesIniciais = new ArrayList<>();
	private ArrayList<Point> verticesFinais = new ArrayList<>();

	public Poligono(ArrayList<Point> verticesIniciais) {
		super();
		this.verticesIniciais = verticesIniciais;
	}

	public Poligono() {
	}

	public ArrayList<Point> getVerticesIniciais() {
		return verticesIniciais;
	}

	public ArrayList<Point> getVerticesFinais() {
		return verticesFinais;
	}

	public void setVerticesInicias(ArrayList<Point> vertices) {
		this.verticesIniciais = vertices;
	}

	public void addVerticeInicial(Point vertice) {
		this.verticesIniciais.add(vertice);
	}

	public void addVerticeFinal(Point vertice) {
		this.verticesFinais.add(vertice);
	}

	public void add(ArrayList<Point> pontos) {
		verticesIniciais = pontos;
	}
}
