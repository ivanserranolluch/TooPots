package es.uji.ei1027.toopots;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.dao.CertificacionDao;

@Configuration
public class TooPotsConfiguration {

	@Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
	
	@Bean
    public ActividadDao myActividadDao() {
        return new ActividadDao();
    }
	
	@Bean
    public CertificacionDao certificacionDao() {
        return new CertificacionDao();
    }
    
}
