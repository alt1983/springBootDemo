package ru.netology.springbootdemo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;
    @Container
    private static final GenericContainer<?> myAppFirst = new GenericContainer<>("devapp:latest").withExposedPorts(8080);
    @Container
    private static final GenericContainer<?> myAppSecond = new GenericContainer<>("prodapp:latest").withExposedPorts(8081);


    @BeforeAll
    public static void setUp() {

    }

    @Test
    void contextLoads() {

        ResponseEntity<String> forEntityFirst = restTemplate.getForEntity("http://localhost:" + myAppFirst.getMappedPort(8080)+"/profile", String.class);
        Assertions.assertEquals(forEntityFirst.getBody(),"Current profile is dev");
        System.out.println(forEntityFirst.getBody());

        ResponseEntity<String> forEntitySecond = restTemplate.getForEntity("http://localhost:" + myAppSecond.getMappedPort(8081)+"/profile", String.class);
        Assertions.assertEquals(forEntitySecond.getBody(),"Current profile is production");
        System.out.println(forEntitySecond.getBody());

    }

}
