package es.uji.ei1027.toopots.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.toopots.model.PreferenciasCliente;

public final class PreferenciasClienteRowMapper implements RowMapper<PreferenciasCliente> {
public PreferenciasCliente mapRow (ResultSet rs, int rowNum) throws SQLException {
		
	    PreferenciasCliente preferenciasCliente = new PreferenciasCliente();
	    preferenciasCliente.setDni(rs.getString("dni"));
	    preferenciasCliente.setId_tipoActividad(rs.getInt("id_tipoActividad"));
	    preferenciasCliente.setFecha(rs.getDate("fecha"));
      
	    return preferenciasCliente;
	}

}
