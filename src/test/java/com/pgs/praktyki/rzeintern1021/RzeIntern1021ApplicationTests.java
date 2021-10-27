package com.pgs.praktyki.rzeintern1021;

import com.pgs.praktyki.rzeintern1021.aws.configuration.AwsTestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = AwsTestConfiguration.class)
class RzeIntern1021ApplicationTests {

    @Test
    void contextLoads() {
    }

}
