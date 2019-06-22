package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Monitor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class MonitorRowMapper implements RowMapper<Monitor> {
    public Monitor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Monitor monitor= new Monitor();
        monitor.setId(rs.getString("id_monitor"));
        monitor.setNombre(rs.getString("nombre"));
        monitor.setEmail(rs.getString("email"));
        monitor.setIban(rs.getString("iban"));
        monitor.setEstado(rs.getString("estado"));
        monitor.setDomicilio(rs.getString("domicilio"));
        monitor.setUrlImg(rs.getString("foto"));
        return monitor;
    }
}
