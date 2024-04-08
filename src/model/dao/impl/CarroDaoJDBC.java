package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.CarroDAO;
import model.entities.Carro;

public class CarroDaoJDBC implements CarroDAO{
	
	private Connection conn;
	
	public CarroDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public Carro findByPlaca(String placa) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM carros "
					+ "WHERE placa = ?");
			st.setString(1, placa);
			rs = st.executeQuery();
			if(rs.next()) {
				Carro carro = instantiateCarro(rs);
				return carro;
			}
			return null;

		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}


	@Override
	public void insert(Carro carro) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO carros"
					+ "(marca,placa,cor,horarioEntrada,horarioSaida)"
					+ "VALUES "
					+"(?,?,?,?,?)", 
					Statement.RETURN_GENERATED_KEYS);
				st.setString(1, carro.getMarca());
				st.setString(2, carro.getPlaca());
				st.setString(3, carro.getCor());
				st.setInt(4, carro.getHorarioEntrada());
				st.setInt(5, carro.getHorarioSaida());
				
				int rowsAffected = st.executeUpdate();
				
				if(rowsAffected > 0) {
					ResultSet rs = st.getGeneratedKeys();
					if(rs.next()) {
						int id = rs.getInt(1);
						carro.setId(id);
					}
				}
				else {
					throw new DbException("Unexpected error! No rows affected!");
				}
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			} 
			finally {
				DB.closeStatement(st);
			}
	}

	@Override
	public void update(Carro carro) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE carros "
					+ "SET marca = ?, placa =?, cor = ?, horarioEntrada = ? , horarioSaida = ? "
					+ "WHERE placa = ?");
				st.setString(1, carro.getMarca());
				st.setString(2, carro.getPlaca());
				st.setString(3, carro.getCor());
				st.setInt(4, carro.getHorarioEntrada());
				st.setInt(5, carro.getHorarioSaida());
				st.setString(6, carro.getPlaca());
				
				st.executeUpdate();
				
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			} 
			finally {
				DB.closeStatement(st);
			}
	}

	@Override
	public void deleteByPlaca(String placa) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM carros WHERE placa = ?");
			st.setString(1, placa);
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Carro> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM carros ORDER BY idcarros"
					);
			rs = st.executeQuery();
			
			List<Carro> list = new ArrayList<>();
			
			while(rs.next()) {
				Carro carro = instantiateCarro(rs);
				list.add(carro);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Carro instantiateCarro(ResultSet rs) throws SQLException {
		Carro carro = new Carro();
		carro.setId(rs.getInt("idcarros"));
		carro.setMarca(rs.getString("marca"));
		carro.setPlaca(rs.getString("placa"));
		carro.setCor(rs.getString("cor"));
		carro.setHorarioEntrada(rs.getInt("horarioEntrada"));
		carro.setHorarioSaida(rs.getInt("horarioSaida"));
		return carro;
	}
}
