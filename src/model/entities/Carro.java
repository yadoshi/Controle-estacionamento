package model.entities;

public class Carro {
	
	private int id;
    private String marca;
    private String placa;
    private String cor;
    private int horarioEntrada;
    private int horarioSaida;
    
    public Carro() {}
    
    public Carro(int id) {
    	this.id = id;
    }
    
    public Carro(String placa) {
    	this.placa = placa;
    }
    
    public Carro(String marca, String placa, String cor, int horarioEntrada, int horarioSaida) {
		this.marca = marca;
		this.placa = placa;
		this.cor = cor;
		this.horarioEntrada = horarioEntrada;
		this.horarioSaida = horarioSaida;
	}
    
	public Carro(int id, String marca, String placa, String cor, int horarioEntrada, int horarioSaida) {
		this.id = id;
		this.marca = marca;
		this.placa = placa;
		this.cor = cor;
		this.horarioEntrada = horarioEntrada;
		this.horarioSaida = horarioSaida;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	
	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public int getHorarioEntrada() {
		return horarioEntrada;
	}
	public void setHorarioEntrada(int horarioEntrada) {
		this.horarioEntrada = horarioEntrada;
	}
	public int getHorarioSaida() {
		return horarioSaida;
	}
	public void setHorarioSaida(int horarioSaida) {
		this.horarioSaida = horarioSaida;
	}

}
