package es.uji.ei1027.toopots.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.toopots.model.Reserva;


public final class ReservaRowMapper implements RowMapper<Reserva> {
	public Reserva mapRow (ResultSet rs, int rowNum) throws SQLException {
		
		Reserva reserva = new Reserva();
        reserva.setId_reserva(rs.getInt("id_reserva"));
        reserva.setEstadoPago(rs.getString("estadoPagado"));
        reserva.setFecha(rs.getDate("fecha"));
        reserva.setNumAsistentes(rs.getInt("numAsistentes"));
        reserva.setPrecioPersona(rs.getInt("precioPersona"));
        reserva.setNumTransacciones(rs.getInt("numTransacciones"));
        reserva.setId_actividad(rs.getInt("id_actividad"));
        reserva.setDni(rs.getString("dni"));
        
        return reserva;
	}
}