package com.bzncrsrtc.kurumsalmuvekkil.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.models.Court;
import com.bzncrsrtc.kurumsalmuvekkil.models.Plan;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateCompanyRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateCourtRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreatePlanRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateCompanyRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateCourtRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdatePlanRequest;

@Mapper(componentModel = "spring")
public interface RequestMapper {

	Company fromCreateCompanyRequestToCompany(CreateCompanyRequest createCompanyRequest);
	Company fromUpdateCompanyRequestToCompany(UpdateCompanyRequest updateCompanyRequest);
	CreateCompanyRequest fromCompanyToCreateCompanyRequest(Company company);
	UpdateCompanyRequest fromCompanyToUpdateCompanyRequest(Company company);
	
	Plan fromCreatePlanRequestToPlan(CreatePlanRequest createPlanRequest);
	Plan fromUpdatePlanRequestToPlan(UpdatePlanRequest updatePlanRequest);
	CreatePlanRequest fromPlanToCreatePlanRequest(Plan plan);
	UpdatePlanRequest fromPlanToUpdatePlanRequest(Plan plan);
	
	Court fromCreateCourtRequestToCourt(CreateCourtRequest createCourtRequest);
	Court fromUpdateCourtRequestToCourt(UpdateCourtRequest updateCourtRequest);
	CreateCourtRequest fromCourtToCreateCourtRequest(Court court);
	UpdateCourtRequest fromCourtToUpdateCourtRequest(Court court);
	
	
}
