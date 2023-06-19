/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.service;

import java.util.List;

public interface IGraphSRV {

	List<String> traverseGraph(String jsonBundle);
	
}
