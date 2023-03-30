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

	@Override
	public void refresh() {
		params = client.getSearchParams();
	}

	@Override
	public boolean isSearchParam(String type, String path) {
		return getResource(type).filter(res -> hasSearchParam(res, path)).isPresent();
	}

	private Optional<SearchParamResourceDTO> getResource(String resourceType) {
		return params
				.getParams()
				.stream()
				.filter(resource -> resource.getResourceName().equals(resourceType))
				.findFirst();
	}

	private boolean hasSearchParam(SearchParamResourceDTO resource, String path) {
		return resource
				.getParameters()
				.stream()
				.anyMatch(param -> param.getPath().equals(path));
	}
	
}
