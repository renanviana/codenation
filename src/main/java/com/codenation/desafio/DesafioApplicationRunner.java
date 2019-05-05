package com.codenation.desafio;

import java.io.File;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.codenation.desafio.bean.DesafioBean;
import com.codenation.desafio.config.ApplicationProperties;
import com.codenation.desafio.manager.DesafioManager;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DesafioApplicationRunner implements CommandLineRunner{

	@Autowired
	private DesafioManager manager;
	
	@Autowired
	private ApplicationProperties app;

	@Autowired
	private ObjectMapper mapper;

	public void run(String... args) throws Exception {
		
		String json = manager.getJsonFromCodeNation();
		
		if (json != null) {

			manager.createFile(json);
			
			DesafioBean bean = mapper.readValue(
					new File(app.getPath()+File.separator+app.getFileName()+"."+app.getFileExtension()), 
					DesafioBean.class);
			
			System.out.println(bean.toString());
			
			String txtDecifrado = manager.dataDecrypt(bean);
			System.out.println(txtDecifrado);
			bean.setDecifrado(txtDecifrado);
//			bean.setDecifrado(manager.dataDecrypt(bean));
			
			String sha1 = DigestUtils.sha1Hex(txtDecifrado);
			System.out.println(sha1);
			bean.setResumoCriptografico(sha1);
//			bean.setResumoCriptografico(DigestUtils.sha1Hex(manager.dataDecrypt(bean)));

			System.out.println(bean.toString());
			
			json = mapper.writeValueAsString(bean);
			manager.createFile(json);
			
			String retornoPost = manager.postFileForCodeNation();
			System.out.println(retornoPost);
		}
	}

}
