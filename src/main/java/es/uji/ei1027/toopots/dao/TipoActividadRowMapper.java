package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.TipoActividad;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class TipoActividadRowMapper implements RowMapper<TipoActividad> {
    public TipoActividad mapRow(ResultSet rs, int rowNum) throws SQLException {
        TipoActividad tAct= new TipoActividad();
        tAct.setId(rs.getInt("id_tipoActividad"));
        tAct.setNombre(rs.getString("nombre"));
        tAct.setNivel(rs.getString("nivel"));
        tAct.setIdActividad(rs.getInt("id_actividad"));
        return tAct;
    }
}
