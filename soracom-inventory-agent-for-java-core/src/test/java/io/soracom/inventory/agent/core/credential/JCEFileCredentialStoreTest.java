package io.soracom.inventory.agent.core.credential;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.File;

import org.eclipse.leshan.core.request.BindingMode;
import org.junit.Before;
import org.junit.Test;

public class JCEFileCredentialStoreTest {

	@Before
	public void init() {
		File file = new File(System.getProperty("user.home") + File.separator + ".soracom-inventory-jce");
		file.delete();
	}

	@Test
	public void testSaveAndLoadAndClear() {
		JCEFileCredentialStore store = new JCEFileCredentialStore();

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

	@Test
	public void testLoadOldCredential() {
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

		store = new JCEFileCredentialStore();
		Credentials loadCred = store.loadCredentials();
		assertThat(loadCred.getBindingMode(), is(cred.getBindingMode()));
		assertThat(loadCred.getLifetime(), is(cred.getLifetime()));
		assertThat(loadCred.isNotifyWhenDisable(), is(cred.isNotifyWhenDisable()));
		assertThat(loadCred.getPskId(), is(cred.getPskId()));
		assertThat(loadCred.getPskKey(), is(cred.getPskKey()));
		assertThat(loadCred.getServerURI(), is(cred.getServerURI()));
		assertThat(loadCred.getShortServerId(), is(cred.getShortServerId()));

		store.saveCredentials(loadCred);
		assertNotNull(store.loadCredentials());
		assertNull(new FileCredentialStore().loadCredentials());
		store.clearCredentials();
		assertNull(store.loadCredentials());
	}

	@Test
	public void testLoadOldCredentialWithInvalidKey() {
		System.setProperty(JCEFileCredentialStore.ENV_NAME_INVENTORY_CREDENTIAL_STORE_KEY, "hoge");
		JCEFileCredentialStore store = new JCEFileCredentialStore();

		Credentials cred = new Credentials();
		cred.setBindingMode(BindingMode.U);
		cred.setLifetime(100);
		cred.setNotifyWhenDisable(true);
		cred.setPskId("hoge".getBytes());
		cred.setPskKey("foo".getBytes());
		cred.setServerURI("coap://bar");
		cred.setShortServerId(9);
		store.saveCredentials(cred);

		try {
			System.setProperty(JCEFileCredentialStore.ENV_NAME_INVENTORY_CREDENTIAL_STORE_KEY, "bar");
			store = new JCEFileCredentialStore();
			Credentials loadCred = store.loadCredentials();
			fail();
		} catch (Exception e) {

		} finally {
			System.getProperties().remove(JCEFileCredentialStore.ENV_NAME_INVENTORY_CREDENTIAL_STORE_KEY);
		}
	}

}
