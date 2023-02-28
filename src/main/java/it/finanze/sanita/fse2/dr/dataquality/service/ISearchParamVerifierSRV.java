package it.finanze.sanita.fse2.dr.dataquality.service;

public interface ISearchParamVerifierSRV {
	
	boolean isSearchParam(String resourceType, String path);
	
}
