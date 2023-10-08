package com.celuveat.common;

import com.celuveat.common.dao.Dao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

@DataJpaTest(
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Dao.class),
        }
)
@Import({TestConfig.class, TestDataInserter.class})
@DisplayNameGeneration(ReplaceUnderscores.class)
public abstract class DaoTest {

    protected final TestData testData = new TestData();

    @Autowired
    protected TestDataInserter testDataInserter;

    @BeforeEach
    void setUp() {
        testDataInserter.insertData(prepareTestData());
    }

    protected abstract TestData prepareTestData();
}
