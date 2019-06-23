package es.uji.ei1027.toopots.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.toopots.model.SerialActividad;

public class SerialActividadRowMapper implements RowMapper<SerialActividad> {
	public SerialActividad mapRow (ResultSet rs, int rowNum) throws SQLException {
		
		SerialActividad s = new SerialActividad();
		
		s.setSec_actividad(rs.getInt("sec_actividad"));
		
       
        return s;
	}
}