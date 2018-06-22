package io.soracom.inventory.agent.core.bootstrap.krypton;

public enum KryptonApiEndpointUrl {

	// TODO set correct url
	GLOBAL_COVERAGE("https://key-manager-g-soracom.dyn.soracom.com/v1/keys",
			"https://jp-dev-active.api.soracom.io/v1/trusted_entities/soracom/inventory/bootstrap");

	private String keyAgreementUrl;
	private String keyDistributionUrl;

	private KryptonApiEndpointUrl(String keyAgreementUrl, String keyDistributionUrl) {
		this.keyAgreementUrl = keyAgreementUrl;
		this.keyDistributionUrl = keyDistributionUrl;
	}

	public String getKeyAgreementUrl() {
		return keyAgreementUrl;
	}

	public String getKeyDistributionUrl() {
		return keyDistributionUrl;
	}
}
