package com.blackwhitemap.blackwhitemap_back;

import com.blackwhitemap.blackwhitemap_back.support.testcontainers.PostgreSQLTestContainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(PostgreSQLTestContainersConfig.class)
class BlackwhitemapBackApplicationTests {

    @Test
    void contextLoads() {
    }

}
