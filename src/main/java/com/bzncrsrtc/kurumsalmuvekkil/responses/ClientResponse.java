package com.bzncrsrtc.kurumsalmuvekkil.responses;

import java.util.UUID;

import lombok.Data;

@Data
public class ClientResponse {

	private UUID id;
	private String identificationNumber;
	private String firstName;
	private String lastName;
	private String phone;
	
}
