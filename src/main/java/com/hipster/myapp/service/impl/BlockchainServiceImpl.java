package com.hipster.myapp.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.hipster.myapp.domain.Stamp;
import com.hipster.myapp.domain.StampVerify;
import com.hipster.myapp.service.BlockchainService;

@Service
public class BlockchainServiceImpl implements BlockchainService {
	private final Logger log = LoggerFactory.getLogger(BlockchainServiceImpl.class);
	
	public String hashDocumento(String documento) throws UnsupportedEncodingException {
		byte data[] = documento.getBytes("UTF8");
		return Hashing.sha256().hashBytes(data).toString();
	}
	
	public String altaBloque(String hash) throws ParseException, IOException {
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("https://tsaapi.bfa.ar/api/tsa/stamp/");
		httppost.addHeader("content-type", "application/json");
		StringEntity params = new StringEntity(new Gson().toJson(new Stamp(hash)));
		httppost.setEntity(params);
		HttpResponse response = httpclient.execute(httppost);
		return EntityUtils.toString(response.getEntity());
	}
	
	public String verificarBloque(String file_hash, String rd) throws ParseException, IOException {
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("https://tsaapi.bfa.ar/api/tsa/verify/");
		httppost.addHeader("content-type", "application/json");
		StringEntity params = new StringEntity(new Gson().toJson(new StampVerify(file_hash,rd)));
		httppost.setEntity(params);
		HttpResponse response = httpclient.execute(httppost);
		return EntityUtils.toString(response.getEntity());
	}


}
