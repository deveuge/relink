package com.deveuge.relink.kgs;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import com.deveuge.relink.kgs.config.GeneratorConfiguration;
import com.deveuge.relink.kgs.model.entity.Key;
import com.deveuge.relink.kgs.model.repository.KeyRepository;
import com.deveuge.relink.kgs.model.repository.UsedKeyRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KeyGenerator {

	@Autowired
	private GeneratorConfiguration configuration;

	@Autowired
	private KeyRepository keyRepository;

	@Autowired
	private UsedKeyRepository usedKeyRepository;

	private static Random random = new Random();

	/**
	 * Generates a certain number of keys specified by configuration and adds them
	 * to the database. Checks that they are not already available or used.
	 */
	public void generateKeys() {
		log.info("Key generation process: {} keys will be generated", configuration.getQuantity());
		for (int i = 0; i < configuration.getQuantity(); i++) {
			String newKey = generateKey();
			if (isUsedKey(newKey)) {
				log.warn("New key '{}' has already been USED. This generated key will be ignored.", newKey);
				continue;
			}
			insertNewKey(newKey);
		}
	}

	/**
	 * Checks if the key passed by parameter has already been used.
	 * 
	 * @param newKey {@link String} The key to be checked
	 * @return true if it has been used, false otherwise
	 */
	private boolean isUsedKey(String newKey) {
		return usedKeyRepository.findById(newKey).isPresent();
	}

	/**
	 * Inserts a new key into the database. If already inserted, a warning message
	 * is generated.
	 * 
	 * @param newKey {@link String} The key to be inserted
	 */
	private void insertNewKey(String newKey) {
		try {
			keyRepository.insert(new Key(newKey));
		} catch (DuplicateKeyException ex) {
			log.warn("New key '{}' has already been GENERATED. This generated key will be ignored.", newKey);
		}
	}

	/**
	 * Generates a new key of a length defined by configuration.
	 * 
	 * @return {@link String} The new key
	 */
	private String generateKey() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < configuration.getLength(); i++) {
			builder.append(configuration.getAlphabet().charAt(random.nextInt(configuration.getAlphabet().length())));
		}
		return builder.toString();
	}
}
