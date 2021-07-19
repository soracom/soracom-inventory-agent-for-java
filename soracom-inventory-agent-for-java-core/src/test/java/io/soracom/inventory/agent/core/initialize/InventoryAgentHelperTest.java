package io.soracom.inventory.agent.core.initialize;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InventoryAgentHelperTest {

	@Test
	public void testCalculateDefaultLifetimeSec() {
		assertEquals(60 + 247 + 30, InventoryAgentHelper.calculateDefaultLifetimeSec());
	}

	@Test
	public void calculateRecommendedLifetimeSec() {
		assertEquals(60 + 247 + 30, InventoryAgentHelper.calculateRecommendedLifetimeSec(0));
		assertEquals(60 + 247 + 30, InventoryAgentHelper.calculateRecommendedLifetimeSec(60));
		assertEquals(60 + 247 + 30 + 1, InventoryAgentHelper.calculateRecommendedLifetimeSec(60 + 247 + 30 + 1));
	}

}
