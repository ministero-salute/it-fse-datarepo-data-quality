package it.finanze.sanita.fse2.dr.dataquality.service;

import it.finanze.sanita.fse2.dr.dataquality.dto.graph.EdgeDTO;

import java.util.List;

public interface IGraphSRV {

	List<EdgeDTO> traverseGraph(String jsonBundle);
	
}
