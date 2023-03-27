package it.finanze.sanita.fse2.dr.dataquality.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import it.finanze.sanita.fse2.dr.dataquality.client.ISrvQueryClient;
import it.finanze.sanita.fse2.dr.dataquality.dto.SearchParamResourceDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.SearchParamsResponseDTO;
import it.finanze.sanita.fse2.dr.dataquality.service.ISearchParamVerifierSRV;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SearchParamVerifierSRV implements ISearchParamVerifierSRV {

	@Autowired
	private ISrvQueryClient srvQueryClient;
	
	private SearchParamsResponseDTO searchParameters;

	@Async
	@EventListener(ApplicationStartedEvent.class)
	void initialize() {
		try {
			searchParameters = srvQueryClient.getSearchParams();
		} catch(Exception ex) {
			log.error("Error while requesting searchParameters from Server FHIR: " , ex);
		}
	}
	
	@Override
	public boolean isSearchParam(String resourceType, String path) {
		SearchParamResourceDTO resource = getResource(resourceType);
		if (resource == null) return false;
		return hasSearchParam(resource, path);
	}

	private SearchParamResourceDTO getResource(String resourceType) {
		return getSearchParams()
				.getParams()
				.stream()
				.filter(resource -> resource.getResourceName().equals(resourceType))
				.findFirst()
				.orElse(null);	
	}

	private SearchParamsResponseDTO getSearchParams() {
		if (searchParameters == null) initializeSearchParams();
		return searchParameters;
	}

	private void initializeSearchParams() {
		searchParameters = srvQueryClient.getSearchParams();
	}

	private boolean hasSearchParam(SearchParamResourceDTO resource, String path) {
		return resource
				.getParameters()
				.stream()
				.anyMatch(param -> param.getPath().equals(path));
	}
	
}
