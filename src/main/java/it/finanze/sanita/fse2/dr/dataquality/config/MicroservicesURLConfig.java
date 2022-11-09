/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.config;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class MicroservicesURLConfig implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1978136469476074347L;
	
	@Value("${ms.url.srv-query-service}")
	private String querySrvHost;
	
}
