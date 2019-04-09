package es.uji.ei1027.toopots.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.toopots.model.User;

public class UserRowMapper implements RowMapper<User> {
	public User mapRow (ResultSet rs, int rowNum) throws SQLException {
		
		User usuario = new User();
		usuario.setEmail(rs.getString("username"));
		usuario.setPassword(rs.getString("password"));
		usuario.setTipoUsuario(rs.getString("typeUser"));
       
        return usuario;
	}
}
