package com.ms.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;

@SpringBootApplication
public class OrdersMsApplication {

	public static void main(String[] args) {
		File truststore = new File("orders-ms/src/main/resources/emulator-truststore.p12");
		if (!truststore.exists()) {
			truststore = new File("src/main/resources/emulator-truststore.p12");
		}
		if (truststore.exists()) {
			System.setProperty("javax.net.ssl.trustStore", truststore.getAbsolutePath());
			System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
			System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");
		}
		SpringApplication.run(OrdersMsApplication.class, args);
	}

}
