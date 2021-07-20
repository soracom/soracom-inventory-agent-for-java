package io.soracom.inventory.agent.core.initialize;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InventoryAgentHelperTest {

	@Test
	public void calculateRecommendedLifetimeSec() {
		assertEquals(247 + 30, InventoryAgentHelper.calculateRecommendedLifetimeSec(0));
		assertEquals(247 + 30, InventoryAgentHelper.calculateRecommendedLifetimeSec(60));
		assertEquals(247 + 30 + 1, InventoryAgentHelper.calculateRecommendedLifetimeSec(247 + 30 + 1));
	}

}
