package com.bzncrsrtc.kurumsalmuvekkil.mappers;

import org.mapstruct.Mapper;

import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateCompanyRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateCompanyRequest;

@Mapper(componentModel = "spring")
public interface RequestMapper {

	Company fromCreateCompanyRequestToCompany(CreateCompanyRequest createCompanyRequest);
	Company fromUpdateCompanyRequestToCompany(UpdateCompanyRequest updateCompanyRequest);
	CreateCompanyRequest fromCompanyToCreateCompanyRequest(Company company);
	UpdateCompanyRequest fromCompanyToUpdateCompanyRequest(Company company);
}
