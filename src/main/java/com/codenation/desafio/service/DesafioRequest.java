package com.codenation.desafio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.codenation.desafio.config.ApplicationProperties;

@Component
public class DesafioRequest {
	
	@Autowired
	private ApplicationProperties app;
	
	private RestTemplate template;

	public DesafioRequest() {
		template = new RestTemplate();
	}
	
	/**
	 * @param method
	 * @param uriVariables
	 * @return url do end point
	 */
	private String builEndPoint(String method, MultiValueMap<String, String> uriVariables) {
		
		UriComponents uri = UriComponentsBuilder.newInstance()
				.scheme("https")
				.host(app.getHost()+app.getEndpoint())
				.path(method)
				.queryParams(uriVariables)
				.build();	 
		return uri.toString();
	}

	/**
	 * @param method
	 * @param uriVariables
	 * @return retornará um json como exemplo:
	 * {
			"numero_casas": 10,
			"token":"token_do_usuario",
			"cifrado": "texto criptografado",
			"decifrado": "aqui vai o texto decifrado",
			"resumo_criptografico": "aqui vai o resumo"
		}
	 */
	public ResponseEntity<String> getApiCodeNation(String method, MultiValueMap<String, String> uriVariables) {

		ResponseEntity<String> response = null;
		
		try {
			response = template.getForEntity(builEndPoint(method, uriVariables), String.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	/**
	 * @param method
	 * @param requestEntity
	 * @param uriVariables
	 * @return retorna um json com minha pontuação exemplo: {"score":100}
	 */
	public ResponseEntity<String> postApiCodeNation(String method, 
			HttpEntity<MultiValueMap<String, Object>> requestEntity, MultiValueMap<String, String> uriVariables) {

		ResponseEntity<String> response = null;
		
		try {
			response = template.exchange(builEndPoint(method, uriVariables), HttpMethod.POST, requestEntity, String.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
}