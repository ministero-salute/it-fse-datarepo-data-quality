/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.service;

public interface ISearchParamVerifierSRV {
	
	boolean isSearchParam(String resourceType, String path);

	void refresh() throws Exception;

	void tryToUpdateParamsIfNecessary();
	
}
