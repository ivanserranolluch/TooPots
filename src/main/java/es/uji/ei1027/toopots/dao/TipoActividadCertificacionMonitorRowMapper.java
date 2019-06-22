package es.uji.ei1027.toopots.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.toopots.model.TipoActividad;
import es.uji.ei1027.toopots.model.TipoActividadCertificacionMonitor;

public final class TipoActividadCertificacionMonitorRowMapper implements RowMapper<TipoActividadCertificacionMonitor> {
    public TipoActividadCertificacionMonitor mapRow(ResultSet rs, int rowNum) throws SQLException {
    	TipoActividadCertificacionMonitor dev= new TipoActividadCertificacionMonitor();
        dev.setId(rs.getInt("id_tipoActividad"));
        dev.setNombre(rs.getString("nombre"));
        dev.setNivel(rs.getString("nivel"));
        dev.setId_certificacion(rs.getInt("id_certificacion"));
		dev.setEstado(rs.getString("estado"));
		dev.setId_monitor(rs.getInt("id_monitor"));
		dev.setNombreMonitor(rs.getString("nombreMonitor"));
		dev.setEmail(rs.getString("email"));
        
        return dev;
    }
}
