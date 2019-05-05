package com.codenation.desafio.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "numero_casas", "token", "cifrado", "decifrado", "resumo_criptografico" })
public class DesafioBean {

	@JsonProperty("numero_casas")
	private Integer numeroCasas;
	@JsonProperty("token")
	private String token;
	@JsonProperty("cifrado")
	private String cifrado;
	@JsonProperty("decifrado")
	private String decifrado;
	@JsonProperty("resumo_criptografico")
	private String resumoCriptografico;

	@JsonProperty("numero_casas")
	public Integer getNumeroCasas() {
		return numeroCasas;
	}

	@JsonProperty("numero_casas")
	public void setNumeroCasas(Integer numeroCasas) {
		this.numeroCasas = numeroCasas;
	}

	@JsonProperty("token")
	public String getToken() {
		return token;
	}

	@JsonProperty("token")
	public void setToken(String token) {
		this.token = token;
	}

	@JsonProperty("cifrado")
	public String getCifrado() {
		return cifrado;
	}

	@JsonProperty("cifrado")
	public void setCifrado(String cifrado) {
		this.cifrado = cifrado;
	}

	@JsonProperty("decifrado")
	public String getDecifrado() {
		return decifrado;
	}

	@JsonProperty("decifrado")
	public void setDecifrado(String decifrado) {
		this.decifrado = decifrado;
	}

	@JsonProperty("resumo_criptografico")
	public String getResumoCriptografico() {
		return resumoCriptografico;
	}

	@JsonProperty("resumo_criptografico")
	public void setResumoCriptografico(String resumoCriptografico) {
		this.resumoCriptografico = resumoCriptografico;
	}

	@Override
	public String toString() {
		return "DesafioBean [numeroCasas=" + numeroCasas + ", token=" + token + ", cifrado=" + cifrado + ", decifrado="
				+ decifrado + ", resumoCriptografico=" + resumoCriptografico + "]";
	}
	
}