/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 * 
 * Copyright (C) 2023 Ministero della Salute
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
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
	
	private SearchParamsResponseDTO params;

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
