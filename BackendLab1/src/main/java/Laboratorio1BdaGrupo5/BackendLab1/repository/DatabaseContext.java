package Laboratorio1BdaGrupo5.BackendLab1.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sql2o.Sql2o;

@Configuration
public class DatabaseContext {
    @Value("${DB_URL}")
    private String dbUrl;

    @Value("${USER}")
    private String dbUser;

    @Value("${PASSWORD}")
    private String dbPass;

    @Bean
    public Sql2o sql2o() {
        return new Sql2o(dbUrl, dbUser, dbPass);
    }
}
