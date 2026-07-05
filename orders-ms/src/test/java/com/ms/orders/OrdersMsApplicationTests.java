package com.ms.orders;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.File;

@SpringBootTest
class OrdersMsApplicationTests {

	static {
		File truststore = new File("orders-ms/src/main/resources/emulator-truststore.p12");
		if (!truststore.exists()) {
			truststore = new File("src/main/resources/emulator-truststore.p12");
		}
		if (truststore.exists()) {
			System.setProperty("javax.net.ssl.trustStore", truststore.getAbsolutePath());
			System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
			System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");
		}
	}

	@Test
	void contextLoads() {
	}

}
