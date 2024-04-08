package model.dao;

import db.DB;
import model.CarroDAO;
import model.dao.impl.CarroDaoJDBC;

public class DaoFactory {

	public static CarroDAO createCarroDao() {
		return new CarroDaoJDBC(DB.getConnection());
	}
}
