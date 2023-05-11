package com.citylist.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response implements Serializable{
	
	private Object data;
	private String responseCode;
	private String responseMessage;
	private String status;

}
