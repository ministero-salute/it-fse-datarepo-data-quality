package it.finanze.sanita.fse2.dr.dataquality.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.finanze.sanita.fse2.dr.dataquality.client.ISrvQueryClient;
import it.finanze.sanita.fse2.dr.dataquality.dto.SearchParamResourceDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.SearchParamsResponseDTO;
import it.finanze.sanita.fse2.dr.dataquality.service.ISearchParamVerifierSRV;

@Service
public class SearchParamVerifierSRV implements ISearchParamVerifierSRV {

	@Autowired
	private ISrvQueryClient srvQueryClient;
	
	@Override
	public boolean isSearchParam(String resourceType, String path) {
		SearchParamResourceDTO resource = getResource(resourceType);
		if (resource == null) return false;
		return hasSearchParam(resource, path);
	}

	private SearchParamResourceDTO getResource(String resourceType) {
		return getSearchParams()
				.getResources()
				.stream()
				.filter(resource -> resource.getResourceName().equals(resourceType))
				.findFirst()
				.orElse(null);	
	}

	private SearchParamsResponseDTO getSearchParams() {
		return srvQueryClient
				.getSearchParams();
	}

	private boolean hasSearchParam(SearchParamResourceDTO resource, String path) {
		return resource
				.getSearchParams()
				.stream()
				.anyMatch(param -> param.getPath().equals(path));
	}
	
}
