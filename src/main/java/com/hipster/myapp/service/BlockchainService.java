package com.hipster.myapp.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.ParseException;

public interface BlockchainService {

	
	public String hashDocumento(String documento) throws UnsupportedEncodingException;
	
	public String altaBloque(String hash) throws UnsupportedEncodingException, ParseException, IOException;
	
	public String verificarBloque(String file_hash, String rd) throws ParseException, IOException;
}
