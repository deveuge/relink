package com.deveuge.relink.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class OnRenderDeploy {
	
	@Value("${relink.api.url}")
	private String apiURL;
	
	@Value("${relink.onrender.deploy}")
	private boolean onrenderDeploy;

	@PostConstruct
	private void init() {
		if(onrenderDeploy) {
			try {
				launchHttpToApi();
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void launchHttpToApi() throws IOException, URISyntaxException {
		System.out.println("> Connecting to API to wake up the instance");
		URL url = new URI(apiURL).toURL();
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		
		 BufferedReader entrada = new BufferedReader(new InputStreamReader(con.getInputStream()));
         String linea;
         while ((linea = entrada.readLine()) != null) {
            System.out.println(linea);
         }
         entrada.close();
		con.disconnect();
	}
}
