package es.uji.ei1027.toopots.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import es.uji.ei1027.toopots.model.CertificacionesTipoActividad;

public class CertificacionesTipoActividadDao {
	private JdbcTemplate jdbcTemplate;
	
	// Obté el jdbcTemplate a partir del Data Source
	@Autowired
	public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	/* Añade las certificaciones de los tipos de actividades a la base de datos */
    public void addCertificacionesTipoActividad(CertificacionesTipoActividad certificacionesTipoActividad) {
        jdbcTemplate.update("INSERT INTO CertificacionesTipoActividad VALUES(?, ?)",
        		certificacionesTipoActividad.getId_certificacion(),certificacionesTipoActividad.getId_tipoActividad());
    }
	
    /* Obtiene todas las certificaciones de los tipos de actividad. Devuelve una lista vacia si no encuentra ninguno */
    public List<CertificacionesTipoActividad> getCertificacionesTipoActividad() {
        try {
            return jdbcTemplate.query("SELECT * from CertificacionesTipoActividad",
                     new CertificacionesTipoActividadRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<CertificacionesTipoActividad>();
        }
    }
}
