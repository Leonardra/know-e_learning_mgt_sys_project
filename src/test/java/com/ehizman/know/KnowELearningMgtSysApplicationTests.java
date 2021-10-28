package com.ehizman.know;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class KnowELearningMgtSysApplicationTests {
    DataSource dataSource;

    @Autowired
    public KnowELearningMgtSysApplicationTests(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @BeforeEach
    void setUp() {

    }

    @Test
    void testConnection(){
        assertThat(dataSource).isNotNull();
        try{
            Connection connection = dataSource.getConnection();
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("learning_mgt_system");
            log.info("connection --> {}", connection.getCatalog());
        }
        catch (SQLException ex){
            log.info("exception occurred --> {}", ex.getMessage());
        }
    }
}
