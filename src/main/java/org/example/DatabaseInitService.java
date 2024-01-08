package org.example;

import org.flywaydb.core.Flyway;


public class DatabaseInitService {
    public static void main(String[] args){

        Flyway flyway = Flyway
                .configure()
                .dataSource("jdbc:h2:./test", "sa", null)
                .load();

        flyway.migrate();

    }
}
