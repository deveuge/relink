package com.deveuge.relink.kgs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deveuge.relink.kgs.model.entity.UsedKey;
import com.deveuge.relink.kgs.model.repository.KeyRepository;
import com.deveuge.relink.kgs.model.repository.UsedKeyRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class KeyController {

	@Autowired
	private KeyRepository keyRepository;

	@Autowired
	private UsedKeyRepository usedKeyRepository;

	/**
	 * Gets a key from the generated keys available in the database and moves it to
	 * the used keys table.
	 * 
	 * @return {@link String} key
	 */
	@GetMapping("/get")
	public String getKey(Authentication authentication) {
		String key = keyRepository.findOne().getKey();
		usedKeyRepository.insert(new UsedKey(key));
		keyRepository.deleteById(key);
		log.info("Key '{}' granted to '{}'", key, authentication.getName());
		return key;
	}

	@DeleteMapping("/delete")
	public void removeKeys(Authentication authentication, @RequestBody List<String> ids) {
		log.info("Used keys deletion order by '{}'. Hashes to delete: '{}'", authentication.getName(), ids.toString());
		usedKeyRepository.deleteAllById(ids);
	}
}
