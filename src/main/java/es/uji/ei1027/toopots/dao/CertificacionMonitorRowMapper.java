package es.uji.ei1027.toopots.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.toopots.model.CertificacionMonitor;

public class CertificacionMonitorRowMapper implements RowMapper<CertificacionMonitor>{

	@Override
	public CertificacionMonitor mapRow(ResultSet rs, int arg1) throws SQLException {
		CertificacionMonitor dev = new CertificacionMonitor();
		dev.setId_certificacion(rs.getInt("id_certificacion"));
		dev.setEstado(rs.getString("estado"));
		dev.setId_monitor(rs.getInt("id_monitor"));
		dev.setNombre(rs.getString("nombre"));
		dev.setEmail(rs.getString("email"));
        dev.setIban(rs.getString("iban"));
        //dev.setEstado(rs.getString("estadoMonitor"));
        dev.setDomicilio(rs.getString("domicilio"));
		return dev;
	}

}