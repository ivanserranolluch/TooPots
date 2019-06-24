package es.uji.ei1027.toopots.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.toopots.model.Descuento;

public class DescuentoRowMapper implements RowMapper<Descuento>{

	@Override
	public Descuento mapRow(ResultSet arg0, int arg1) throws SQLException {
		Descuento dev = new Descuento();
		dev.setDiscountValue(arg0.getFloat("discount"));
		dev.setField(arg0.getString("campo"));
		dev.setValue(arg0.getString("valor"));
		return dev;
	}

}
