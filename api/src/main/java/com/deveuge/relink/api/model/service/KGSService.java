package com.deveuge.relink.api.model.service;

import java.util.List;

public interface KGSService {

	/**
	 * Obtains a new hash by querying the Key Generator Service
	 * 
	 * @return {@link String} New hash
	 */
	String getHash();

	void deleteHashes(List<String> ids);
}
