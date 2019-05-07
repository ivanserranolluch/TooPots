package es.uji.ei1027.toopots.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.toopots.model.Actividad;

public final class ActividadRowMapper implements RowMapper<Actividad> {

	@Override
	public Actividad mapRow(ResultSet rs, int rowNum) throws SQLException {
		Actividad dev = new Actividad();
		dev.setId_actividad(rs.getInt("id_actividad"));
		dev.setNombre(rs.getString("nombre"));
		dev.setDescripcion(rs.getString("descripcion"));
		dev.setDuracionDias(rs.getInt("duracionDias"));
		dev.setFecha(rs.getDate("fecha"));
		dev.setPrecio(rs.getInt("precio"));
		dev.setMinAsistentes(rs.getInt("minAsistentes"));
		dev.setMaxAsistentes(rs.getInt("maxAsistentes"));
		dev.setLugar(rs.getString("lugar"));
		dev.setPuntoEncuentro(rs.getString("puntoEncuento"));
		dev.setHoraEncuentro(rs.getTime("horaEncuentro"));
		dev.setTextoCliente(rs.getString("textoCliente"));
		dev.setEstado(rs.getString("estado"));
		dev.setId_tipoActividad(rs.getInt("id_tipoActividad"));
		return dev;
	}

}
