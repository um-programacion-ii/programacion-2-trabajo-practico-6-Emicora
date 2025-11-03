package org.example.businessservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("mysql")
class MysqlProfileContextTests {
    @Test
    void contextLoads_mysql_profile_without_datasource() {

    }
}

@SpringBootTest
@ActiveProfiles("postgres")
class PostgresProfileContextTests {
    @Test
    void contextLoads_postgres_profile_without_datasource() {

    }
}
