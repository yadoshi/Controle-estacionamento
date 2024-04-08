package model;

import java.util.List;

import model.entities.Carro;

public interface CarroDAO {

	void insert(Carro carro);
	void update(Carro carro);
	Carro findByPlaca(String placa);
	void deleteByPlaca(String placa);
	List<Carro> findAll();
}
