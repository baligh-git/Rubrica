package it.corso.java.rubrica.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import it.corso.java.rubrica.model.Contatto;

public class RubricaBusiness {
	private Connection con;
	private static RubricaBusiness rb;
	
	public static RubricaBusiness getInstance() {
		if(rb == null) {
			rb = new RubricaBusiness();
		}
		
		return rb;
	}

	private Connection getConnection() throws SQLException {
		if(con == null) {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setServerName("127.0.0.1");
			dataSource.setPortNumber(3306);
			dataSource.setUser("root");
			dataSource.setPassword("root");
			dataSource.setDatabaseName("corso_java_rubrica");
			
			con = dataSource.getConnection();
		}
		
		return con;
	}
	
	public int aggiungiContatto(Contatto c) throws SQLException {
		
		String sql = "INSERT INTO contatti(nome, cognome, telefono) VALUES(?, ?, ?)";
		
		PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, c.getNome());
		ps.setString(2, c.getCognome());
		ps.setString(3, c.getTelefono());
		
		ps.executeUpdate();
		
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		
		return rs.getInt(1);
	}
	
	public List<Contatto> ricercaContatti() throws SQLException {
		String sql = "SELECT id, nome, cognome, telefono FROM contatti";
		
		PreparedStatement ps = getConnection().prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		List<Contatto> contatti = new ArrayList<Contatto>();
		while(rs.next()) {
			Contatto c = new Contatto();
			c.setId(rs.getInt(1));
			c.setNome(rs.getString(2));
			c.setCognome(rs.getString(3));
			c.setTelefono(rs.getString(4));
			
			contatti.add(c);
		}
		
		return contatti;
	}
}
