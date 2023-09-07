package com.bzncrsrtc.kurumsalmuvekkil.mappers;

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
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateClientRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateCompanyRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateCourtRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateFileRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateLawyerRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreatePlanRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateRoleRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateSubscriptionRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateUpdateRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateUserRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.ParentCourtRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateClientRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateCompanyRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateCourtRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateFileRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateLawyerRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdatePlanRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateRoleRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateSubscriptionRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateUpdateRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateUserRequest;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-07T19:55:18+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.33.0.v20230218-1114, environment: Java 17.0.6 (Eclipse Adoptium)"
)
*/
@Component
public class RequestMapperImpl implements RequestMapper {

    @Override
    public Company fromCreateCompanyRequestToCompany(CreateCompanyRequest createCompanyRequest) {
        if ( createCompanyRequest == null ) {
            return null;
        }

        Company company = new Company();

        company.setName( createCompanyRequest.getName() );

        return company;
    }

    @Override
    public Company fromUpdateCompanyRequestToCompany(UpdateCompanyRequest updateCompanyRequest) {
        if ( updateCompanyRequest == null ) {
            return null;
        }

        Company company = new Company();

        company.setId( updateCompanyRequest.getId() );
        company.setName( updateCompanyRequest.getName() );

        return company;
    }

    @Override
    public CreateCompanyRequest fromCompanyToCreateCompanyRequest(Company company) {
        if ( company == null ) {
            return null;
        }

        CreateCompanyRequest createCompanyRequest = new CreateCompanyRequest();

        createCompanyRequest.setName( company.getName() );

        return createCompanyRequest;
    }

    @Override
    public UpdateCompanyRequest fromCompanyToUpdateCompanyRequest(Company company) {
        if ( company == null ) {
            return null;
        }

        UpdateCompanyRequest updateCompanyRequest = new UpdateCompanyRequest();

        updateCompanyRequest.setId( company.getId() );
        updateCompanyRequest.setName( company.getName() );

        return updateCompanyRequest;
    }

    @Override
    public Plan fromCreatePlanRequestToPlan(CreatePlanRequest createPlanRequest) {
        if ( createPlanRequest == null ) {
            return null;
        }

        Plan plan = new Plan();

        plan.setAnnualPrice( createPlanRequest.getAnnualPrice() );
        plan.setClientQuota( createPlanRequest.getClientQuota() );
        plan.setDescription( createPlanRequest.getDescription() );
        plan.setFileQuotaPerClient( createPlanRequest.getFileQuotaPerClient() );
        plan.setLawyerQuota( createPlanRequest.getLawyerQuota() );
        plan.setMonthlyPrice( createPlanRequest.getMonthlyPrice() );
        plan.setName( createPlanRequest.getName() );

        return plan;
    }

    @Override
    public Plan fromUpdatePlanRequestToPlan(UpdatePlanRequest updatePlanRequest) {
        if ( updatePlanRequest == null ) {
            return null;
        }

        Plan plan = new Plan();

        plan.setAnnualPrice( updatePlanRequest.getAnnualPrice() );
        plan.setClientQuota( updatePlanRequest.getClientQuota() );
        plan.setDescription( updatePlanRequest.getDescription() );
        plan.setFileQuotaPerClient( updatePlanRequest.getFileQuotaPerClient() );
        plan.setId( updatePlanRequest.getId() );
        plan.setLawyerQuota( updatePlanRequest.getLawyerQuota() );
        plan.setMonthlyPrice( updatePlanRequest.getMonthlyPrice() );
        plan.setName( updatePlanRequest.getName() );

        return plan;
    }

    @Override
    public CreatePlanRequest fromPlanToCreatePlanRequest(Plan plan) {
        if ( plan == null ) {
            return null;
        }

        CreatePlanRequest createPlanRequest = new CreatePlanRequest();

        createPlanRequest.setAnnualPrice( plan.getAnnualPrice() );
        createPlanRequest.setClientQuota( plan.getClientQuota() );
        createPlanRequest.setDescription( plan.getDescription() );
        createPlanRequest.setFileQuotaPerClient( plan.getFileQuotaPerClient() );
        createPlanRequest.setLawyerQuota( plan.getLawyerQuota() );
        createPlanRequest.setMonthlyPrice( plan.getMonthlyPrice() );
        createPlanRequest.setName( plan.getName() );

        return createPlanRequest;
    }

    @Override
    public UpdatePlanRequest fromPlanToUpdatePlanRequest(Plan plan) {
        if ( plan == null ) {
            return null;
        }

        UpdatePlanRequest updatePlanRequest = new UpdatePlanRequest();

        updatePlanRequest.setAnnualPrice( plan.getAnnualPrice() );
        updatePlanRequest.setClientQuota( plan.getClientQuota() );
        updatePlanRequest.setDescription( plan.getDescription() );
        updatePlanRequest.setFileQuotaPerClient( plan.getFileQuotaPerClient() );
        updatePlanRequest.setId( plan.getId() );
        updatePlanRequest.setLawyerQuota( plan.getLawyerQuota() );
        updatePlanRequest.setMonthlyPrice( plan.getMonthlyPrice() );
        updatePlanRequest.setName( plan.getName() );

        return updatePlanRequest;
    }

    @Override
    public Court fromCreateCourtRequestToCourt(CreateCourtRequest createCourtRequest) {
        if ( createCourtRequest == null ) {
            return null;
        }

        Court court = new Court();

        court.setName( createCourtRequest.getName() );
        court.setParent( fromParentCourtRequestToCourt( createCourtRequest.getParent() ) );

        return court;
    }

    @Override
    public Court fromUpdateCourtRequestToCourt(UpdateCourtRequest updateCourtRequest) {
        if ( updateCourtRequest == null ) {
            return null;
        }

        Court court = new Court();

        court.setId( updateCourtRequest.getId() );
        court.setName( updateCourtRequest.getName() );
        court.setParent( fromParentCourtRequestToCourt( updateCourtRequest.getParent() ) );

        return court;
    }

    @Override
    public CreateCourtRequest fromCourtToCreateCourtRequest(Court court) {
        if ( court == null ) {
            return null;
        }

        CreateCourtRequest createCourtRequest = new CreateCourtRequest();

        createCourtRequest.setName( court.getName() );
        createCourtRequest.setParent( fromCourtToParentCourtRequest( court.getParent() ) );

        return createCourtRequest;
    }

    @Override
    public UpdateCourtRequest fromCourtToUpdateCourtRequest(Court court) {
        if ( court == null ) {
            return null;
        }

        UpdateCourtRequest updateCourtRequest = new UpdateCourtRequest();

        updateCourtRequest.setId( court.getId() );
        updateCourtRequest.setName( court.getName() );
        updateCourtRequest.setParent( fromCourtToParentCourtRequest( court.getParent() ) );

        return updateCourtRequest;
    }

    @Override
    public Court fromParentCourtRequestToCourt(ParentCourtRequest parentCourtRequest) {
        if ( parentCourtRequest == null ) {
            return null;
        }

        Court court = new Court();

        court.setId( parentCourtRequest.getId() );

        return court;
    }

    @Override
    public ParentCourtRequest fromCourtToParentCourtRequest(Court court) {
        if ( court == null ) {
            return null;
        }

        ParentCourtRequest parentCourtRequest = new ParentCourtRequest();

        parentCourtRequest.setId( court.getId() );

        return parentCourtRequest;
    }

    @Override
    public Role fromCreateRoleRequestToRole(CreateRoleRequest createRoleRequest) {
        if ( createRoleRequest == null ) {
            return null;
        }

        Role role = new Role();

        return role;
    }

    @Override
    public Role fromUpdateRoleRequestToRole(UpdateRoleRequest updateRoleRequest) {
        if ( updateRoleRequest == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( updateRoleRequest.getId() );
        role.setName( updateRoleRequest.getName() );

        return role;
    }

    @Override
    public CreateRoleRequest fromRoleToCreateRoleRequest(Role role) {
        if ( role == null ) {
            return null;
        }

        CreateRoleRequest createRoleRequest = new CreateRoleRequest();

        return createRoleRequest;
    }

    @Override
    public UpdateRoleRequest fromRoleToUpdateRoleRequest(Role role) {
        if ( role == null ) {
            return null;
        }

        UpdateRoleRequest updateRoleRequest = new UpdateRoleRequest();

        updateRoleRequest.setId( role.getId() );
        updateRoleRequest.setName( role.getName() );

        return updateRoleRequest;
    }

    @Override
    public User fromCreateUserRequestToUser(CreateUserRequest createUserRequest) {
        if ( createUserRequest == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( createUserRequest.getEmail() );
        user.setPassword( createUserRequest.getPassword() );
        user.setUsername( createUserRequest.getUsername() );

        return user;
    }

    @Override
    public User fromUpdateUserRequestToUser(UpdateUserRequest updateUserRequest) {
        if ( updateUserRequest == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( updateUserRequest.getEmail() );
        user.setId( updateUserRequest.getId() );
        user.setPassword( updateUserRequest.getPassword() );
        user.setUsername( updateUserRequest.getUsername() );

        return user;
    }

    @Override
    public CreateUserRequest fromUserToCreateUserRequest(User user) {
        if ( user == null ) {
            return null;
        }

        CreateUserRequest createUserRequest = new CreateUserRequest();

        createUserRequest.setEmail( user.getEmail() );
        createUserRequest.setPassword( user.getPassword() );
        createUserRequest.setUsername( user.getUsername() );

        return createUserRequest;
    }

    @Override
    public UpdateUserRequest fromUserToUpdateUserRequest(User user) {
        if ( user == null ) {
            return null;
        }

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();

        updateUserRequest.setEmail( user.getEmail() );
        updateUserRequest.setId( user.getId() );
        updateUserRequest.setPassword( user.getPassword() );
        updateUserRequest.setUsername( user.getUsername() );

        return updateUserRequest;
    }

    @Override
    public Client fromCreateClientRequestToClient(CreateClientRequest createClientRequest) {
        if ( createClientRequest == null ) {
            return null;
        }

        Client client = new Client();

        client.setFirstName( createClientRequest.getFirstName() );
        client.setIdentificationNumber( createClientRequest.getIdentificationNumber() );
        client.setLastName( createClientRequest.getLastName() );
        client.setPhone( createClientRequest.getPhone() );
        client.setUser( fromCreateUserRequestToUser( createClientRequest.getUser() ) );

        return client;
    }

    @Override
    public Client fromUpdateClientRequestToClient(UpdateClientRequest updateClientRequest) {
        if ( updateClientRequest == null ) {
            return null;
        }

        Client client = new Client();

        client.setFirstName( updateClientRequest.getFirstName() );
        client.setId( updateClientRequest.getId() );
        client.setIdentificationNumber( updateClientRequest.getIdentificationNumber() );
        client.setLastName( updateClientRequest.getLastName() );
        client.setPhone( updateClientRequest.getPhone() );
        client.setUser( fromUpdateUserRequestToUser( updateClientRequest.getUser() ) );

        return client;
    }

    @Override
    public CreateClientRequest fromClientToCreateClientRequest(Client client) {
        if ( client == null ) {
            return null;
        }

        CreateClientRequest createClientRequest = new CreateClientRequest();

        createClientRequest.setFirstName( client.getFirstName() );
        createClientRequest.setIdentificationNumber( client.getIdentificationNumber() );
        createClientRequest.setLastName( client.getLastName() );
        createClientRequest.setPhone( client.getPhone() );
        createClientRequest.setUser( fromUserToCreateUserRequest( client.getUser() ) );

        return createClientRequest;
    }

    @Override
    public UpdateClientRequest fromClientToUpdateClientRequest(Client client) {
        if ( client == null ) {
            return null;
        }

        UpdateClientRequest updateClientRequest = new UpdateClientRequest();

        updateClientRequest.setFirstName( client.getFirstName() );
        updateClientRequest.setId( client.getId() );
        updateClientRequest.setIdentificationNumber( client.getIdentificationNumber() );
        updateClientRequest.setLastName( client.getLastName() );
        updateClientRequest.setPhone( client.getPhone() );
        updateClientRequest.setUser( fromUserToUpdateUserRequest( client.getUser() ) );

        return updateClientRequest;
    }

    @Override
    public Lawyer fromCreateLawyerRequestToLawyer(CreateLawyerRequest createLawyerRequest) {
        if ( createLawyerRequest == null ) {
            return null;
        }

        Lawyer lawyer = new Lawyer();

        lawyer.setFirstName( createLawyerRequest.getFirstName() );
        lawyer.setIdentificationNumber( createLawyerRequest.getIdentificationNumber() );
        lawyer.setLastName( createLawyerRequest.getLastName() );
        lawyer.setPhone( createLawyerRequest.getPhone() );
        lawyer.setUser( fromCreateUserRequestToUser( createLawyerRequest.getUser() ) );

        return lawyer;
    }

    @Override
    public Lawyer fromUpdateLawyerRequestToLawyer(UpdateLawyerRequest updateLawyerRequest) {
        if ( updateLawyerRequest == null ) {
            return null;
        }

        Lawyer lawyer = new Lawyer();

        lawyer.setFirstName( updateLawyerRequest.getFirstName() );
        lawyer.setId( updateLawyerRequest.getId() );
        lawyer.setIdentificationNumber( updateLawyerRequest.getIdentificationNumber() );
        lawyer.setLastName( updateLawyerRequest.getLastName() );
        lawyer.setPhone( updateLawyerRequest.getPhone() );
        lawyer.setUser( fromUpdateUserRequestToUser( updateLawyerRequest.getUser() ) );

        return lawyer;
    }

    @Override
    public CreateLawyerRequest fromLawyerToCreateLawyerRequest(Lawyer lawyer) {
        if ( lawyer == null ) {
            return null;
        }

        CreateLawyerRequest createLawyerRequest = new CreateLawyerRequest();

        createLawyerRequest.setFirstName( lawyer.getFirstName() );
        createLawyerRequest.setIdentificationNumber( lawyer.getIdentificationNumber() );
        createLawyerRequest.setLastName( lawyer.getLastName() );
        createLawyerRequest.setPhone( lawyer.getPhone() );
        createLawyerRequest.setUser( fromUserToCreateUserRequest( lawyer.getUser() ) );

        return createLawyerRequest;
    }

    @Override
    public UpdateLawyerRequest fromLawyerToUpdateLawyerRequest(Lawyer lawyer) {
        if ( lawyer == null ) {
            return null;
        }

        UpdateLawyerRequest updateLawyerRequest = new UpdateLawyerRequest();

        updateLawyerRequest.setFirstName( lawyer.getFirstName() );
        updateLawyerRequest.setId( lawyer.getId() );
        updateLawyerRequest.setIdentificationNumber( lawyer.getIdentificationNumber() );
        updateLawyerRequest.setLastName( lawyer.getLastName() );
        updateLawyerRequest.setPhone( lawyer.getPhone() );
        updateLawyerRequest.setUser( fromUserToUpdateUserRequest( lawyer.getUser() ) );

        return updateLawyerRequest;
    }

    @Override
    public File fromCreateFileRequestToFile(CreateFileRequest createFileRequest) {
        if ( createFileRequest == null ) {
            return null;
        }

        File file = new File();

        file.setCourtDetail( createFileRequest.getCourtDetail() );
        file.setDescription( createFileRequest.getDescription() );
        file.setTitle( createFileRequest.getTitle() );

        return file;
    }

    @Override
    public File fromUpdateFileRequestToFile(UpdateFileRequest updateFileRequest) {
        if ( updateFileRequest == null ) {
            return null;
        }

        File file = new File();

        file.setCourtDetail( updateFileRequest.getCourtDetail() );
        file.setDescription( updateFileRequest.getDescription() );
        file.setId( updateFileRequest.getId() );
        file.setTitle( updateFileRequest.getTitle() );

        return file;
    }

    @Override
    public CreateFileRequest fromFileToCreateFileRequest(File file) {
        if ( file == null ) {
            return null;
        }

        CreateFileRequest createFileRequest = new CreateFileRequest();

        createFileRequest.setCourtDetail( file.getCourtDetail() );
        createFileRequest.setDescription( file.getDescription() );
        createFileRequest.setTitle( file.getTitle() );

        return createFileRequest;
    }

    @Override
    public UpdateFileRequest fromFileToUpdateFileRequest(File file) {
        if ( file == null ) {
            return null;
        }

        UpdateFileRequest updateFileRequest = new UpdateFileRequest();

        updateFileRequest.setCourtDetail( file.getCourtDetail() );
        updateFileRequest.setDescription( file.getDescription() );
        updateFileRequest.setId( file.getId() );
        updateFileRequest.setTitle( file.getTitle() );

        return updateFileRequest;
    }

    @Override
    public Subscription fromCreateSubscriptionRequestToSubscription(CreateSubscriptionRequest createSubscriptionRequest) {
        if ( createSubscriptionRequest == null ) {
            return null;
        }

        Subscription subscription = new Subscription();

        subscription.setAutoRenew( createSubscriptionRequest.isAutoRenew() );
        subscription.setEndDate( createSubscriptionRequest.getEndDate() );
        subscription.setFee( createSubscriptionRequest.getFee() );
        subscription.setStartDate( createSubscriptionRequest.getStartDate() );
        subscription.setType( createSubscriptionRequest.getType() );

        return subscription;
    }

    @Override
    public Subscription fromUpdateSubscriptionRequestToSubscription(UpdateSubscriptionRequest updateSubscriptionRequest) {
        if ( updateSubscriptionRequest == null ) {
            return null;
        }

        Subscription subscription = new Subscription();

        subscription.setAutoRenew( updateSubscriptionRequest.isAutoRenew() );
        subscription.setEndDate( updateSubscriptionRequest.getEndDate() );
        subscription.setFee( updateSubscriptionRequest.getFee() );
        subscription.setId( updateSubscriptionRequest.getId() );
        subscription.setStartDate( updateSubscriptionRequest.getStartDate() );
        subscription.setType( updateSubscriptionRequest.getType() );

        return subscription;
    }

    @Override
    public CreateSubscriptionRequest fromSubscriptionToCreateSubscriptionRequest(Subscription subscription) {
        if ( subscription == null ) {
            return null;
        }

        CreateSubscriptionRequest createSubscriptionRequest = new CreateSubscriptionRequest();

        createSubscriptionRequest.setAutoRenew( subscription.isAutoRenew() );
        createSubscriptionRequest.setEndDate( subscription.getEndDate() );
        createSubscriptionRequest.setFee( subscription.getFee() );
        createSubscriptionRequest.setStartDate( subscription.getStartDate() );
        createSubscriptionRequest.setType( subscription.getType() );

        return createSubscriptionRequest;
    }

    @Override
    public UpdateSubscriptionRequest fromSubscriptionToUpdateSubscriptionRequest(Subscription subscription) {
        if ( subscription == null ) {
            return null;
        }

        UpdateSubscriptionRequest updateSubscriptionRequest = new UpdateSubscriptionRequest();

        updateSubscriptionRequest.setAutoRenew( subscription.isAutoRenew() );
        updateSubscriptionRequest.setEndDate( subscription.getEndDate() );
        updateSubscriptionRequest.setFee( subscription.getFee() );
        updateSubscriptionRequest.setId( subscription.getId() );
        updateSubscriptionRequest.setStartDate( subscription.getStartDate() );
        updateSubscriptionRequest.setType( subscription.getType() );

        return updateSubscriptionRequest;
    }

    @Override
    public Update fromCreateUpdateRequestToUpdate(CreateUpdateRequest createUpdateRequest) {
        if ( createUpdateRequest == null ) {
            return null;
        }

        Update update = new Update();

        update.setContent( createUpdateRequest.getContent() );
        update.setState( createUpdateRequest.getState() );

        return update;
    }

    @Override
    public Update fromUpdateUpdateRequestToUpdate(UpdateUpdateRequest updateUpdateRequest) {
        if ( updateUpdateRequest == null ) {
            return null;
        }

        Update update = new Update();

        update.setContent( updateUpdateRequest.getContent() );
        update.setId( updateUpdateRequest.getId() );
        update.setState( updateUpdateRequest.getState() );

        return update;
    }

    @Override
    public CreateSubscriptionRequest fromUpdateToCreateUpdateRequest(Update update) {
        if ( update == null ) {
            return null;
        }

        CreateSubscriptionRequest createSubscriptionRequest = new CreateSubscriptionRequest();

        return createSubscriptionRequest;
    }

    @Override
    public UpdateSubscriptionRequest fromUpdateToUpdateUpdateRequest(Update update) {
        if ( update == null ) {
            return null;
        }

        UpdateSubscriptionRequest updateSubscriptionRequest = new UpdateSubscriptionRequest();

        updateSubscriptionRequest.setId( update.getId() );

        return updateSubscriptionRequest;
    }
}
