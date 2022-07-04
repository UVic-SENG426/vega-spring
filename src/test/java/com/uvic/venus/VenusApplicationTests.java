package com.uvic.venus;

import com.uvic.venus.controller.VaultController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VenusApplicationTests {

	@Autowired
	private VaultController controller;

	@Test
	void contextLoads() {
		assertNotNull(controller);
	}
}
