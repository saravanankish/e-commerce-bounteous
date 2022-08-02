package com.saravanank.ecommerce.resourceserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestResponse {

	private String error;
	private String message;
	private int status;
	private String timestamp;


}
