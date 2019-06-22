package es.uji.ei1027.toopots.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


import es.uji.ei1027.toopots.model.ReservaClienteActividad;

public class ReservaClienteActividadRowMapper implements RowMapper<ReservaClienteActividad> {
	@Override
	public ReservaClienteActividad mapRow(ResultSet rs, int rowNum) throws SQLException {
		ReservaClienteActividad dev = new ReservaClienteActividad();
		dev.setId_actividad(rs.getInt("id_actividad"));
		dev.setNombreActividad(rs.getString("nombreActividad"));
		dev.setDescripcion(rs.getString("descripcion"));
		dev.setDuracionDias(rs.getInt("duracionDias"));
		dev.setFechaActividad(rs.getDate("fechaActividad"));
		dev.setPrecio(rs.getInt("precio"));
		dev.setMinAsistentes(rs.getInt("minAsistentes"));
		dev.setMaxAsistentes(rs.getInt("maxAsistentes"));
		dev.setLugar(rs.getString("lugar"));
		dev.setPuntoEncuentro(rs.getString("puntoEncuento"));
		dev.setTextoCliente(rs.getString("textoCliente"));
		dev.setEstado(rs.getString("estado"));
		dev.setId_tipoActividad(rs.getInt("id_tipoActividad"));
		
        dev.setNombreCliente(rs.getString("nombreCliente"));
        dev.setDni(rs.getString("dni"));
        dev.setEmail(rs.getString("email"));
        dev.setFechaNacimiento(rs.getDate("fechaNacimiento"));
        dev.setSexo(rs.getString("sexo"));
        
        dev.setId_reserva(rs.getInt("id_reserva"));
        dev.setEstadoPago(rs.getString("estadoPago"));
        dev.setNumAsistentes(rs.getInt("numAsistentes"));
        dev.setPrecioPersona(rs.getInt("precioPersona"));
        
		return dev;
	}
}
