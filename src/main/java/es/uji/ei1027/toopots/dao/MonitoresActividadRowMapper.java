package es.uji.ei1027.toopots.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.toopots.model.MonitoresActividad;

public class MonitoresActividadRowMapper implements RowMapper<MonitoresActividad>{

	@Override
	public MonitoresActividad mapRow(ResultSet rs, int rowNum) throws SQLException {
		MonitoresActividad dev = new MonitoresActividad();
		dev.setId_actividad(rs.getInt("id_actividad"));
		dev.setId_monitor(rs.getInt("id_monitor"));
		return dev;
	}

}
