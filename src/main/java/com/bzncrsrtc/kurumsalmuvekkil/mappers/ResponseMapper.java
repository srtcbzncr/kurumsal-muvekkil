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
import com.bzncrsrtc.kurumsalmuvekkil.models.Role;
import com.bzncrsrtc.kurumsalmuvekkil.models.Subscription;
import com.bzncrsrtc.kurumsalmuvekkil.models.Update;
import com.bzncrsrtc.kurumsalmuvekkil.models.User;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ClientResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.CompanyResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.CourtDetailsResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.CourtResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.CourtWithoutParentResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.FileResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.FileWithoutCourtResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.LawyerResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.PlanResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.RoleResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.SubscriptionResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.SubscriptionWithoutPlanResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.UpdateResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.UserResponse;

@Mapper(componentModel = "spring")
public interface ResponseMapper {

	ClientResponse getClientResponse(Client client);
	List<ClientResponse> getClientListResponse(List<Client> clients);
	
	@Mapping(target = "lawyerCount", expression = "java(company.getLawyers() != null && company.getLawyers().size() > 0 ? company.getLawyers().stream().filter(c -> c.isActive() == true && c.isDeleted() == false).collect(java.util.stream.Collectors.toList()).size() : 0)")
	@Mapping(target = "plan", expression = "java(company.getSubscriptions() != null && company.getSubscriptions().size() > 0 ? company.getSubscriptions().get(0).getPlan().getName() : null )")
	CompanyResponse getCompanyResponse(Company company);
	List<CompanyResponse> getCompanyListResponse(List<Company> companies);
	
	@Mapping(target = "subCount", expression = "java(court.getSubs() != null && court.getSubs().size() > 0 ? court.getSubs().stream().filter(c -> c.isActive() == true && c.isDeleted() == false).collect(java.util.stream.Collectors.toList()).size() : 0)")
	CourtResponse getCourtResponse(Court court);
	List<CourtResponse> getCourtListResponse(List<Court> courts);
	@Mapping(target = "subCount", expression = "java(court.getSubs() != null && court.getSubs().size() > 0 ? court.getSubs().stream().filter(c -> c.isActive() == true && c.isDeleted() == false).collect(java.util.stream.Collectors.toList()).size() : 0)")
	CourtWithoutParentResponse getCourtWithoutParentResponse(Court court);
	List<CourtWithoutParentResponse> getCourtWithoutParentListResponse(List<Court> courts);
	CourtDetailsResponse getCourtDetailsResponse(Court court);
	List<CourtDetailsResponse> getCourtDetailsListResponse(List<Court> courts);
		
	FileResponse getFileResponse(File file);
	List<FileResponse> getFileListResponse(List<File> files);
	
	FileWithoutCourtResponse getFileWithoutCourtResponse(File file);
	List<FileWithoutCourtResponse> getFileWithoutCourtListResponse(List<File> files);
	
	LawyerResponse getLawyerResponse(Lawyer lawyer);
	List<LawyerResponse> getLawyerListResponse(List<Lawyer> lawyers);
	
	PlanResponse getPlanResponse(Plan plan);
	List<PlanResponse> getPlanListResponse(List<Plan> plans);
	
	SubscriptionResponse getSubscriptionResponse(Subscription subscription);
	List<SubscriptionResponse> getSubscriptionListResponse(List<Subscription> subscriptions);
	SubscriptionWithoutPlanResponse getSubscriptionWithoutPlanResponse(Subscription subscription);
	List<SubscriptionWithoutPlanResponse> getSubscriptionWithoutPlanListResponse(List<Subscription> subscriptions);
	
	UpdateResponse getUpdateResponse(Update update);
	List<UpdateResponse> getUpdateListResponse(List<Update> updates);
	
	UserResponse getUserResponse(User user);
	List<UserResponse> getUserListResponse(List<User> users);
	
	RoleResponse getRoleResponse(Role role);
	List<RoleResponse> getRoleListResponse(List<Role> roles);
	
}
