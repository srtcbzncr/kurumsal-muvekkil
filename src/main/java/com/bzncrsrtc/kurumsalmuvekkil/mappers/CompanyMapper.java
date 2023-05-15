package com.bzncrsrtc.kurumsalmuvekkil.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.responses.AllCompaniesResponse;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

	List<AllCompaniesResponse> getAllCompaniesResponse(List<Company> companies);
	
}
