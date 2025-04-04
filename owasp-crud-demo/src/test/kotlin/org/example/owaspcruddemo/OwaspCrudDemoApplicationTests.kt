package org.example.owaspcruddemo

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("redis")
class OwaspCrudDemoApplicationTests {

    @Test
    fun contextLoads() {
    }

}
