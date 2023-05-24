package it.finanze.sanita.fse2.dr.dataquality.service.impl;

import it.finanze.sanita.fse2.dr.dataquality.client.ISrvQueryClient;
import it.finanze.sanita.fse2.dr.dataquality.dto.SearchParamResourceDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.SearchParamsResponseDTO;
import it.finanze.sanita.fse2.dr.dataquality.service.ISearchParamVerifierSRV;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class SearchParamVerifierSRV implements ISearchParamVerifierSRV {

	@Autowired
	private ISrvQueryClient client;
	
	private volatile SearchParamsResponseDTO params;

	public SearchParamVerifierSRV() {
		this.params = new SearchParamsResponseDTO();
	}

	@Override
	public void refresh() throws Exception {
		params = client.getSearchParams();
	}

	@Override
	public boolean isSearchParam(String type, String path) {
		return getResource(type).filter(res -> hasSearchParam(res, path)).isPresent();
	}

	public void tryToUpdateParamsIfNecessary() {
		// Check emptiness
		if(params.getParams().isEmpty()) {
			// Log emptines
			log.info("Search-params are empty, trying to reach FHIR server to retrieve theme...");
			try {
				paramsOrRefresh();
			} catch (Exception e) {
				throw new IllegalStateException(
					"Unable to refresh search-params from FHIR, cannot invoke isSearchParam()", e
				);
			}
			log.info("Search-params have been successfully retrieved");
		}
	}

	private synchronized void paramsOrRefresh() throws Exception {
		// Check emptiness
		if(params.getParams().isEmpty()) {
			// Try to refresh params
			refresh();
		}
	}

	private Optional<SearchParamResourceDTO> getResource(String resourceType) {
		return params
				.getParams()
				.stream()
				.filter(resource -> resource.getName().equals(resourceType))
				.findFirst();
	}

	private boolean hasSearchParam(SearchParamResourceDTO resource, String path) {
		return resource.getParameters().stream().anyMatch(p -> p.equals(path));
	}

	public SearchParamsResponseDTO getParams() {
		return params;
	}
}
