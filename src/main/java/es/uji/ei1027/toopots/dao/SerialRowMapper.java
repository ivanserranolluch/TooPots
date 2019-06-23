package es.uji.ei1027.toopots.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.toopots.model.Serial;


public class SerialRowMapper implements RowMapper<Serial> {
	public Serial mapRow (ResultSet rs, int rowNum) throws SQLException {
		
		Serial s = new Serial();
		s.setSec_certificacion(rs.getInt("sec_certificacion"));
		//s.setSec_actividad(rs.getInt("sec_actividad"));
		
       
        return s;
	}
}
