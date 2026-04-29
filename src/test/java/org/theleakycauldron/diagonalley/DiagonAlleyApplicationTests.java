package org.theleakycauldron.diagonalley;

import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.theleakycauldron.diagonalley.productservice.repositories.DiagonAlleyElasticProductRepository;

@SpringBootTest
class DiagonAlleyApplicationTests {

	@MockitoBean
	private KafkaTemplate<String, String> kafkaTemplate;

	@MockitoBean
	private DiagonAlleyElasticProductRepository diagonAlleyElasticProductRepository;

	@Test
	void contextLoads() {
	}

}
