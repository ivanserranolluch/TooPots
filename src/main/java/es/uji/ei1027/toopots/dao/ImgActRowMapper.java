package es.uji.ei1027.toopots.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.toopots.model.ImgAct;

public final class ImgActRowMapper implements RowMapper<ImgAct>{

	@Override
	public ImgAct mapRow(ResultSet rs, int rowNum) throws SQLException {
		ImgAct dev = new ImgAct();
		dev.setId_actividad(rs.getInt("id_actividad"));
		dev.setId_imagen(rs.getInt("id_imagen"));
		dev.setUrl(rs.getString("url"));
		return dev;
	}

}
