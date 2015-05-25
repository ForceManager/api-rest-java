/*******************************************************************************
Copyright (c) 2015, Tritium Software S.L.
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the Tritium Software S.L., nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*******************************************************************************/

package net.forcemanager.ws.sample.factories;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import net.forcemanager.ws.sample.models.GenericModel;
import net.forcemanager.ws.sample.utility.Constants;
import net.forcemanager.ws.sample.utility.FMEntity;
import net.forcemanager.ws.sample.utility.HashUtils;
import net.forcemanager.ws.sample.utility.PropUtils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class RequestFactory {

	public static <T> T CreateReadRequest(FMEntity fmEntity, Class<T> classType)
			throws RestClientException {
		return CreateReadRequest(fmEntity, classType, null);
	}

	public static <T> T CreateReadRequest(FMEntity fmEntity,
			Class<T> classType, String query) throws RestClientException {

		// To do request, you can use Spring Resttemplate
		RestTemplate restTemplate = new RestTemplate();

		URI url;
		if (query != null) {
			url = UriComponentsBuilder.fromUriString(Constants.API_URL)
					.path(fmEntity.toString())
					.queryParam(Constants.PARAMETER_ADVANCED_SEARCH, query)
					.build().toUri();
		} else {
			url = UriComponentsBuilder.fromUriString(Constants.API_URL)
					.path(fmEntity.toString()).build().toUri();
		}

		// get headers
		HttpEntity<?> entity = getHttpEntity(query);

		try {
			// Do GET request to get a List of "Company"
			HttpEntity<T> response = restTemplate.exchange(url, HttpMethod.GET,
					entity, classType);
			return response.getBody();

		} catch (HttpClientErrorException e) {
			if (Constants.NOT_FOUND == e.getStatusCode().value()) {
				System.out.println("Company was not found");
			}
			return null;
		}
	}

	public static <T> boolean InsertEntity(FMEntity fmEntity,
			Class<T> classType, GenericModel object) {

		// To do request, you can use Spring Resttemplate
		RestTemplate restTemplate = new RestTemplate();

		// Post rquest to insert element
		URI url = UriComponentsBuilder.fromUriString(Constants.API_URL)
				.path(fmEntity.toString()).build().toUri();

		// get headers
		HttpEntity<?> entity = getHttpEntity(object);

		try {
			restTemplate.exchange(url.toString(), HttpMethod.POST, entity,
					classType, object);
			return true;
		} catch (HttpClientErrorException e) {
			return false;
		}
	}

	public static <T> boolean UpdateEntity(FMEntity fmEntity,
			GenericModel object) throws RestClientException {

		// To do request, you can use Spring Resttemplate
		RestTemplate restTemplate = new RestTemplate();

		// Post request to insert element
		URI url = UriComponentsBuilder.fromUriString(Constants.API_URL)
				.path(fmEntity.toString() + "/" + object.getId()).build()
				.toUri();

		// get headers
		HttpEntity<?> entity = getHttpEntity(object);

		try {
			restTemplate.exchange(url.toString(), HttpMethod.PUT, entity,
					String.class, object);
			return true;
		} catch (HttpClientErrorException e) {
			if (Constants.NOT_UPDATED == e.getStatusCode().value()) {
				System.out
						.println("The Company was not updated, probably doesn’t exist on the FM System.");
			}
			return false;
		}
	}

	public static <T> boolean DeleteEntity(FMEntity fmEntity,
			GenericModel object) {

		// To do request, you can use Spring Resttemplate
		RestTemplate restTemplate = new RestTemplate();

		// Post rquest to insert element
		URI url = UriComponentsBuilder.fromUriString(Constants.API_URL)
				.path(fmEntity.toString() + "/" + object.getId()).build()
				.toUri();

		// get headers
		HttpEntity<?> entity = getHttpEntity();

		try {
			restTemplate.exchange(url.toString(), HttpMethod.DELETE, entity,
					String.class);
			return true;
		} catch (HttpClientErrorException e) {
			if (Constants.NOT_DELETED == e.getStatusCode().value()) {
				System.out
						.println("The Company was not deleted, probably it was already deleted on the FM System.");
			}
			return false;
		}
	}

	private static HttpEntity<?> getHttpEntity() {
		return getHttpEntity(null);
	}

	private static HttpEntity<?> getHttpEntity(Object object) {
		// get ApiURL, Public Key and Private Key from "fm-prop.properties" file
		// by PropUtils class
		String publicKey = PropUtils.getInstance().getPublicKey();
		String privateKey = PropUtils.getInstance().getPrivateKey();

		Long currentTimestamp = System.currentTimeMillis() / 1000L;

		// generate signature by utility HashUtils.createMessageHash
		String signature = HashUtils.createMessageHash(publicKey,
				currentTimestamp, privateKey);

		// Set request Headers (ContentType, Accept, Public Key, Timestamp,
		// Signature
		HttpHeaders headers = new HttpHeaders();

		List<MediaType> acceptMediaType = new ArrayList<MediaType>();
		acceptMediaType.add(MediaType.APPLICATION_JSON);

		headers.setAccept(acceptMediaType);

		headers.setContentType(MediaType.APPLICATION_JSON);

		headers.set(Constants.FM_PUBLIC_KEY, publicKey);
		headers.set(Constants.FM_UNIX_TIMESTAMP,
				String.valueOf(currentTimestamp));
		headers.set(Constants.FM_SIGNATURE, signature);

		return new HttpEntity<Object>(object, headers);
	}
}