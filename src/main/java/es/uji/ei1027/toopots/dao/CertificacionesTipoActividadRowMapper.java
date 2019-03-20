package es.uji.ei1027.toopots.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.toopots.model.CertificacionesTipoActividad;

public final class CertificacionesTipoActividadRowMapper implements RowMapper<CertificacionesTipoActividad> {
	public CertificacionesTipoActividad mapRow (ResultSet rs, int rowNum) throws SQLException {
		
		CertificacionesTipoActividad certificacion = new CertificacionesTipoActividad();
        certificacion.setId_certificacion(rs.getInt("id_certificacion"));
        certificacion.setId_tipoActividad(rs.getInt("id_tipoActividad"));
        
        return certificacion;
	}
}
