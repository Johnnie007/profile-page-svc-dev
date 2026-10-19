package com.profile.svc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"spring.data.redis.host=localhost",
		"spring.data.redis.port=6379",
		"spring.data.redis.password=test",
		"spring.data.redis.ssl.enabled=false"
})
class ServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
