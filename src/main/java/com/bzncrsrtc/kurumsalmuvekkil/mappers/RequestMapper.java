package com.bzncrsrtc.kurumsalmuvekkil.mappers;

import org.mapstruct.Mapper;

import com.bzncrsrtc.kurumsalmuvekkil.models.Client;
import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.models.Court;
import com.bzncrsrtc.kurumsalmuvekkil.models.File;
import com.bzncrsrtc.kurumsalmuvekkil.models.Lawyer;
import com.bzncrsrtc.kurumsalmuvekkil.models.Plan;
import com.bzncrsrtc.kurumsalmuvekkil.models.Subscription;
import com.bzncrsrtc.kurumsalmuvekkil.models.User;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateClientRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateCompanyRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateCourtRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateFileRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateLawyerRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreatePlanRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateSubscriptionRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateUserRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateClientRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateCompanyRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateCourtRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateFileRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateLawyerRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdatePlanRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateSubscriptionRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateUserRequest;

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
	
	User fromCreateUserRequestToUser(CreateUserRequest createUserRequest);
	User fromUpdateUserRequestToUser(UpdateUserRequest updateUserRequest);
	CreateUserRequest fromUserToCreateUserRequest(User user);
	UpdateUserRequest fromUserToUpdateUserRequest(User user);
	
	Client fromCreateClientRequestToClient(CreateClientRequest createClientRequest);
	Client fromUpdateClientRequestToClient(UpdateClientRequest updateClientRequest);
	CreateClientRequest fromClientToCreateClientRequest(Client client);
	UpdateClientRequest fromClientToUpdateClientRequest(Client client);
	
	Lawyer fromCreateLawyerRequestToLawyer(CreateLawyerRequest createLawyerRequest);
	Lawyer fromUpdateLawyerRequestToLawyer(UpdateLawyerRequest updateLawyerRequest);
	CreateLawyerRequest fromLawyerToCreateLawyerRequest(Lawyer lawyer);
	UpdateLawyerRequest fromLawyerToUpdateLawyerRequest(Lawyer lawyer);
	
	File fromCreateFileRequestToFile(CreateFileRequest createFileRequest);
	File fromUpdateFileRequestToFile(UpdateFileRequest updateFileRequest);
	CreateFileRequest fromFileToCreateFileRequest(File file);
	UpdateFileRequest fromFileToUpdateFileRequest(File file);
	
	Subscription fromCreateSubscriptionRequestToSubscription(CreateSubscriptionRequest createSubscriptionRequest);
	Subscription fromUpdateSubscriptionRequestToSubscription(UpdateSubscriptionRequest updateSubscriptionRequest);
	CreateSubscriptionRequest fromSubscriptionToCreateSubscriptionRequest(Subscription subscription);
	UpdateSubscriptionRequest fromSubscriptionToUpdateSubscriptionRequest(Subscription subscription);
	
}
