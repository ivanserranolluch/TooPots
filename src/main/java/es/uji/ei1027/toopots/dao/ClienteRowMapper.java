package es.uji.ei1027.toopots.dao;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.toopots.model.Cliente;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ClienteRowMapper implements RowMapper<Cliente> {
    public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setNombre(rs.getString("nombre"));
        cliente.setDni(rs.getString("dni"));
        cliente.setEmail(rs.getString("email"));
        cliente.setFechaNacimiento(rs.getDate("fechaNacimiento"));
        cliente.setSexo(rs.getString("sexo"));
        return cliente;
    }
}