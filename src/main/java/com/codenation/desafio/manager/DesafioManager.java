package com.codenation.desafio.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.codenation.desafio.bean.DesafioBean;
import com.codenation.desafio.config.ApplicationProperties;
import com.codenation.desafio.service.DesafioRequest;

@Component
public class DesafioManager {
	
	@Autowired
	private DesafioRequest request;
	
	@Autowired
	private ApplicationProperties app;
	
	/**
	 * @return valor do body da request do metodo generate-data
	 */
	public String getJsonFromCodeNation() {
		
		MultiValueMap<String, String> uriVariables = new LinkedMultiValueMap<>();
		uriVariables.add("token", app.getToken());
		
		ResponseEntity<String> response = request.getApiCodeNation("generate-data", uriVariables);
		System.out.println("Status Code GET: "+response.getStatusCodeValue());
		
		return response != null ? response.getBody() : null;
	}
	
	/**
	 * @return valor do body da request do metodo submit-solution
	 */
	public String postFileForCodeNation() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("answer", new FileSystemResource(app.getPath()+File.separator+app.getFileName()+"."+app.getFileExtension()));
		
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		
		MultiValueMap<String, String> uriVariables = new LinkedMultiValueMap<>();
		uriVariables.add("token", app.getToken());

		ResponseEntity<String> response = request.postApiCodeNation("submit-solution", requestEntity, uriVariables);
		System.out.println(response != null ? "Status Code POST: "+response.getStatusCodeValue() : "");
		
		return response != null ? response.getBody() : null;
	}
	
	/**
	 * @param json
	 */
	public void createFile(String json) {
		try {
			FileOutputStream output = new FileOutputStream(
					new File(app.getPath()+File.separator+app.getFileName()+"."+app.getFileExtension()));
			output.write(json.getBytes());
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return retorna o valor do arquivo criado
	 */
	public String readFile() {
		String result = "";
		try {
			File file = new File(app.getPath()+File.separator+app.getFileName()+"."+app.getFileExtension());
			byte[] fileBytes = new byte[(int)file.length()];
			FileInputStream input = new FileInputStream(file);
			input.read(fileBytes);
			input.close();
			result = new String(fileBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * @param bean
	 * @return retorna o atributo "cifrado" de um objeto do tipo DesafioBean decifrado
	 */
	public String dataDecrypt(DesafioBean bean) {
		int numCasas = bean.getNumeroCasas();
		String txtCifrado = bean.getCifrado().toLowerCase();
		String result = "";
		for (int i = 0; i < txtCifrado.length(); i++) {
			char c = txtCifrado.charAt(i);
			result += c != ' ' && c != '.' && !Character.isDigit(c) ? (char) (c-numCasas) : c;
		}
		return result;
	}
	
}