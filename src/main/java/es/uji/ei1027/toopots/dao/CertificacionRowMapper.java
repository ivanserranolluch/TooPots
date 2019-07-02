package es.uji.ei1027.toopots.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.toopots.model.Certificacion;

public class CertificacionRowMapper implements RowMapper<Certificacion>{

	@Override
	public Certificacion mapRow(ResultSet rs, int arg1) throws SQLException {
		Certificacion dev = new Certificacion();
		dev.setId_certificacion(rs.getInt("id_certificacion"));
		dev.setEstado(rs.getString("estado"));
		dev.setId_monitor(rs.getString("id_monitor"));
		dev.setRutaCertificado(rs.getString("rutaCertificado"));
		return dev;
	}

}
