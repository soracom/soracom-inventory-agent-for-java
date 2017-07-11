package io.soracom.inventory.agent.core.credential;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.eclipse.leshan.core.request.BindingMode;
import org.junit.Test;

public class FileCredentialStoreTest {

	@Test
	public void testSaveAndLoadAndClear() {
		FileCredentialStore store = new FileCredentialStore();

		Credentials cred = new Credentials();
		cred.setBindingMode(BindingMode.U);
		cred.setLifetime(100);
		cred.setNotifyWhenDisable(true);
		cred.setPskId("hoge".getBytes());
		cred.setPskKey("foo".getBytes());
		cred.setServerURI("coap://bar");
		cred.setShortServerId(9);
		store.saveCredentials(cred);

		Credentials loadCred = store.loadCredentials();
		assertThat(loadCred.getBindingMode(), is(cred.getBindingMode()));
		assertThat(loadCred.getLifetime(), is(cred.getLifetime()));
		assertThat(loadCred.isNotifyWhenDisable(), is(cred.isNotifyWhenDisable()));
		assertThat(loadCred.getPskId(), is(cred.getPskId()));
		assertThat(loadCred.getPskKey(), is(cred.getPskKey()));
		assertThat(loadCred.getServerURI(), is(cred.getServerURI()));
		assertThat(loadCred.getShortServerId(), is(cred.getShortServerId()));

		store.clearCredentials();
		assertNull(store.loadCredentials());
	}
}
