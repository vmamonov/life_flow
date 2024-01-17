package dev.mamonov.lifeflow.modules.google.calendar.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Set;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;

public class LocalFileStorage implements DataStore<StoredCredential> {

	@Override
	public DataStoreFactory getDataStoreFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsKey(String key) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(StoredCredential value) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<String> keySet() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<StoredCredential> values() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoredCredential get(String key) throws IOException {
		StoredCredential sc = new StoredCredential();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(LocalFileStorage.class.getResourceAsStream("Credential)).txt")))) {
	        sc.setAccessToken(br.readLine());
	    }
		
		System.out.println("New access token wurde bekomen: " + sc.getAccessToken());
        return sc;
	}

	@Override
	public DataStore<StoredCredential> set(String key, StoredCredential value) throws IOException {
		System.out.println("New access token wurde erhalten: " + value.getAccessToken());
		BufferedWriter writer = new BufferedWriter(new FileWriter("Credential)).txt"));
		writer.write(value.getAccessToken());
	    writer.close();
        
        return this;
	}

	@Override
	public DataStore<StoredCredential> clear() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataStore<StoredCredential> delete(String key) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
