package es.uji.ei1027.toopots.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.toopots.model.Reserva;


public final class ReservaRowMapper implements RowMapper<Reserva> {
	public Reserva mapRow (ResultSet rs, int rowNum) throws SQLException {
		
		Reserva reserva = new Reserva();
        reserva.setId_reserva(rs.getInt("id_reserva"));
        reserva.setEstadoPago(rs.getString("estadoPago"));
        reserva.setFecha(rs.getDate("fecha"));
        reserva.setNumAsistentes(rs.getInt("numAsistentes"));
        reserva.setPrecioPersona(rs.getDouble("precioPersona"));
        reserva.setNumTransacciones(rs.getInt("numTransaccion"));
        reserva.setId_actividad(rs.getInt("id_actividad"));
        reserva.setDni(rs.getString("dni"));
        reserva.setPreciototal(rs.getDouble("preciototal"));
        
        return reserva;
	}
}