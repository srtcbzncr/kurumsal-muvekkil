package com.bzncrsrtc.kurumsalmuvekkil.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bzncrsrtc.kurumsalmuvekkil.models.Client;
import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.models.Court;
import com.bzncrsrtc.kurumsalmuvekkil.models.File;
import com.bzncrsrtc.kurumsalmuvekkil.models.Lawyer;
import com.bzncrsrtc.kurumsalmuvekkil.models.Plan;
import com.bzncrsrtc.kurumsalmuvekkil.models.Subscription;
import com.bzncrsrtc.kurumsalmuvekkil.models.Update;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateCompanyRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateCompanyRequest;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetClientResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetCompanyResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetCourtResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetFileResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetLawyerResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetPlanResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetSubscriptionResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetUpdateResponse;

@Mapper(componentModel = "spring")
public interface ResponseMapper {

	GetClientResponse getClientResponse(Client client);
	List<GetClientResponse> getClientListResponse(List<Client> clients);
	
	GetCompanyResponse getCompanyResponse(Company company);
	List<GetCompanyResponse> getCompanyListResponse(List<Company> companies);
	
	@Mapping(target="parentId", source="parent.id")
	@Mapping(target="parentName", source="parent.name")
	GetCourtResponse getCourtResponse(Court court);
	List<GetCourtResponse> getCourtListResponse(List<Court> courts);
	
	GetFileResponse getFileResponse(File file);
	List<GetFileResponse> getFileListResponse(List<File> files);
	
	GetLawyerResponse getLawyerResponse(Lawyer lawyer);
	List<GetLawyerResponse> getLawyerListResponse(List<Lawyer> lawyers);
	
	GetPlanResponse getPlanResponse(Plan plan);
	List<GetPlanResponse> getPlanListResponse(List<Plan> plans);
	
	GetSubscriptionResponse getSubscriptionResponse(Subscription subscription);
	List<GetSubscriptionResponse> getSubscriptionListResponse(List<Subscription> subscriptions);
	
	GetUpdateResponse getUpdateResponse(Update update);
	List<GetUpdateResponse> getUpdateListResponse(List<Update> updates);
	
}
