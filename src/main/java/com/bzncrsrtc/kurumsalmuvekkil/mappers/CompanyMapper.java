package com.bzncrsrtc.kurumsalmuvekkil.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.models.File;
import com.bzncrsrtc.kurumsalmuvekkil.models.Lawyer;
import com.bzncrsrtc.kurumsalmuvekkil.models.Plan;
import com.bzncrsrtc.kurumsalmuvekkil.models.Subscription;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateCompanyRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateCompanyRequest;
import com.bzncrsrtc.kurumsalmuvekkil.responses.AllCompaniesResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.CreateCompanyResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetCompanyResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetFilesOfCompanyResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetLawyersOfCompanyResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetPlanInSubscriptionResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetSubscriptionOfCompanyResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.UpdateCompanyResponse;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
	
	AllCompaniesResponse getAllCompaniesResponseFromCompanyModel(Company company);
	List<AllCompaniesResponse> getAllCompaniesResponseFromCompanyModel(List<Company> companies);
	CreateCompanyResponse getCreateCompanyResponseFromCompanyModel(Company company);
	Company getCompanyModelFromCreateCompanyRequest(CreateCompanyRequest createCompanyRequest);
	Company getCompanyModelFromUpdateCompanyRequest(UpdateCompanyRequest updateCompanyRequest);
	UpdateCompanyResponse getUpdateCompanyResponseFromCompanyModel(Company company);
	GetCompanyResponse getCompanyResponseFromCompanyModel(Company company);
	GetSubscriptionOfCompanyResponse getSubscriptionOfCompanyResponseFromSubscriptionModel(Subscription subscription);
	GetPlanInSubscriptionResponse getPlanInSubscriptionResponseFromPlanModel(Plan plan);
	GetLawyersOfCompanyResponse getLawyersOfCompanyResponseFromLawyerModel(Lawyer lawyer);
	List<GetLawyersOfCompanyResponse> getLawyersOfCompanyResponseFromLawyersList(List<Lawyer> lawyers);
	GetFilesOfCompanyResponse getFilesOfCompanyResponseFromFileModel(File file);
	List<GetFilesOfCompanyResponse> getFilesOfCompanyResponseFromFilesList(List<File> files);
}
