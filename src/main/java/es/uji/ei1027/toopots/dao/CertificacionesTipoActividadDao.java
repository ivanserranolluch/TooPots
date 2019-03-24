package es.uji.ei1027.toopots.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.toopots.model.CertificacionesTipoActividad;
@Repository
public class CertificacionesTipoActividadDao {
	private JdbcTemplate jdbcTemplate;
	
	// Obté el jdbcTemplate a partir del Data Source
	@Autowired
	public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	/* Añade las certificaciones de los tipos de actividades a la base de datos */
    public void addCertificacionesTipoActividad(CertificacionesTipoActividad certificacionesTipoActividad) {
        jdbcTemplate.update("INSERT INTO certificacionestipoactividad VALUES(?, ?)",
        		certificacionesTipoActividad.getId_certificacion(),certificacionesTipoActividad.getId_tipoActividad());
    }
	/* Actualiza las certificaciones de los tipos de actividades a la base de datos */

    public void updateCertificacionesTipoActividad(CertificacionesTipoActividad certificacionesTipoActividad) {
        jdbcTemplate.update("UPDATE certificacionestipoactividad SET id_certificacion=? WHERE id_tipoActividad=?",
        		certificacionesTipoActividad.getId_certificacion(),certificacionesTipoActividad.getId_tipoActividad());
    }
	/* Borra las certificaciones de los tipos de actividades a la base de datos */

    public void deleteCertificacionesTipoActividad(String id) {
        jdbcTemplate.update("DELETE FROM certificacionestipoactividad WHERE id_tipoActividad=?", id);
    }
    
    
    /* Obtiene una certificación de los tipos de actividades de la base de datos */
	public CertificacionesTipoActividad getCertificacionesTipoActividad(String id) {
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM certificacionestipoactividad WHERE id_tipoActividad=?", new CertificacionesTipoActividadRowMapper(), id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

	
    /* Obtiene todas las certificaciones de los tipos de actividad. Devuelve una lista vacia si no encuentra ninguno */
    public List<CertificacionesTipoActividad> getCertificacionesTipoActividad() {
        try {
            return jdbcTemplate.query("SELECT * from certificacionestipoactividad",
                     new CertificacionesTipoActividadRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<CertificacionesTipoActividad>();
        }
    }
}
