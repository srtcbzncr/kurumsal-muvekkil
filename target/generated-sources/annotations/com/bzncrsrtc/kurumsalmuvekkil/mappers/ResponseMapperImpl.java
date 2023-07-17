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
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetClientResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetCompanyResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetCourtResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetCourtWithoutParentResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetFileResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetFileWithoutCourtResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetLawyerResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetPlanResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetRoleResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetSubscriptionResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetSubscriptionWithoutPlanResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetUpdateResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetUserResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-17T14:10:52+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.33.0.v20230218-1114, environment: Java 17.0.6 (Eclipse Adoptium)"
)
*/
@Component
public class ResponseMapperImpl implements ResponseMapper {

    @Override
    public GetClientResponse getClientResponse(Client client) {
        if ( client == null ) {
            return null;
        }

        GetClientResponse getClientResponse = new GetClientResponse();

        getClientResponse.setFirstName( client.getFirstName() );
        getClientResponse.setId( client.getId() );
        getClientResponse.setIdentificationNumber( client.getIdentificationNumber() );
        getClientResponse.setLastName( client.getLastName() );
        getClientResponse.setPhone( client.getPhone() );

        return getClientResponse;
    }

    @Override
    public List<GetClientResponse> getClientListResponse(List<Client> clients) {
        if ( clients == null ) {
            return null;
        }

        List<GetClientResponse> list = new ArrayList<GetClientResponse>( clients.size() );
        for ( Client client : clients ) {
            list.add( getClientResponse( client ) );
        }

        return list;
    }

    @Override
    public GetCompanyResponse getCompanyResponse(Company company) {
        if ( company == null ) {
            return null;
        }

        GetCompanyResponse getCompanyResponse = new GetCompanyResponse();

        getCompanyResponse.setId( company.getId() );
        getCompanyResponse.setName( company.getName() );

        return getCompanyResponse;
    }

    @Override
    public List<GetCompanyResponse> getCompanyListResponse(List<Company> companies) {
        if ( companies == null ) {
            return null;
        }

        List<GetCompanyResponse> list = new ArrayList<GetCompanyResponse>( companies.size() );
        for ( Company company : companies ) {
            list.add( getCompanyResponse( company ) );
        }

        return list;
    }

    @Override
    public GetCourtResponse getCourtResponse(Court court) {
        if ( court == null ) {
            return null;
        }

        GetCourtResponse getCourtResponse = new GetCourtResponse();

        getCourtResponse.setActive( court.isActive() );
        getCourtResponse.setDeleted( court.isDeleted() );
        getCourtResponse.setId( court.getId() );
        getCourtResponse.setName( court.getName() );
        getCourtResponse.setParent( getCourtWithoutParentResponse( court.getParent() ) );

        return getCourtResponse;
    }

    @Override
    public List<GetCourtResponse> getCourtListResponse(List<Court> courts) {
        if ( courts == null ) {
            return null;
        }

        List<GetCourtResponse> list = new ArrayList<GetCourtResponse>( courts.size() );
        for ( Court court : courts ) {
            list.add( getCourtResponse( court ) );
        }

        return list;
    }

    @Override
    public GetCourtWithoutParentResponse getCourtWithoutParentResponse(Court court) {
        if ( court == null ) {
            return null;
        }

        GetCourtWithoutParentResponse getCourtWithoutParentResponse = new GetCourtWithoutParentResponse();

        getCourtWithoutParentResponse.setActive( court.isActive() );
        getCourtWithoutParentResponse.setDeleted( court.isDeleted() );
        getCourtWithoutParentResponse.setId( court.getId() );
        getCourtWithoutParentResponse.setName( court.getName() );

        return getCourtWithoutParentResponse;
    }

    @Override
    public List<GetCourtWithoutParentResponse> getCourtWithoutParentListResponse(List<Court> courts) {
        if ( courts == null ) {
            return null;
        }

        List<GetCourtWithoutParentResponse> list = new ArrayList<GetCourtWithoutParentResponse>( courts.size() );
        for ( Court court : courts ) {
            list.add( getCourtWithoutParentResponse( court ) );
        }

        return list;
    }

    @Override
    public GetFileResponse getFileResponse(File file) {
        if ( file == null ) {
            return null;
        }

        GetFileResponse getFileResponse = new GetFileResponse();

        getFileResponse.setCourt( getCourtResponse( file.getCourt() ) );
        getFileResponse.setCourtDetail( file.getCourtDetail() );
        getFileResponse.setDescription( file.getDescription() );
        getFileResponse.setId( file.getId() );
        getFileResponse.setTitle( file.getTitle() );

        return getFileResponse;
    }

    @Override
    public List<GetFileResponse> getFileListResponse(List<File> files) {
        if ( files == null ) {
            return null;
        }

        List<GetFileResponse> list = new ArrayList<GetFileResponse>( files.size() );
        for ( File file : files ) {
            list.add( getFileResponse( file ) );
        }

        return list;
    }

    @Override
    public GetFileWithoutCourtResponse getFileWithoutCourtResponse(File file) {
        if ( file == null ) {
            return null;
        }

        GetFileWithoutCourtResponse getFileWithoutCourtResponse = new GetFileWithoutCourtResponse();

        getFileWithoutCourtResponse.setCourtDetail( file.getCourtDetail() );
        getFileWithoutCourtResponse.setDescription( file.getDescription() );
        getFileWithoutCourtResponse.setId( file.getId() );
        getFileWithoutCourtResponse.setTitle( file.getTitle() );

        return getFileWithoutCourtResponse;
    }

    @Override
    public List<GetFileWithoutCourtResponse> getFileWithoutCourtListResponse(List<File> files) {
        if ( files == null ) {
            return null;
        }

        List<GetFileWithoutCourtResponse> list = new ArrayList<GetFileWithoutCourtResponse>( files.size() );
        for ( File file : files ) {
            list.add( getFileWithoutCourtResponse( file ) );
        }

        return list;
    }

    @Override
    public GetLawyerResponse getLawyerResponse(Lawyer lawyer) {
        if ( lawyer == null ) {
            return null;
        }

        GetLawyerResponse getLawyerResponse = new GetLawyerResponse();

        getLawyerResponse.setFirstName( lawyer.getFirstName() );
        getLawyerResponse.setId( lawyer.getId() );
        getLawyerResponse.setLastName( lawyer.getLastName() );
        getLawyerResponse.setPhone( lawyer.getPhone() );
        getLawyerResponse.setTitle( lawyer.getTitle() );

        return getLawyerResponse;
    }

    @Override
    public List<GetLawyerResponse> getLawyerListResponse(List<Lawyer> lawyers) {
        if ( lawyers == null ) {
            return null;
        }

        List<GetLawyerResponse> list = new ArrayList<GetLawyerResponse>( lawyers.size() );
        for ( Lawyer lawyer : lawyers ) {
            list.add( getLawyerResponse( lawyer ) );
        }

        return list;
    }

    @Override
    public GetPlanResponse getPlanResponse(Plan plan) {
        if ( plan == null ) {
            return null;
        }

        GetPlanResponse getPlanResponse = new GetPlanResponse();

        getPlanResponse.setAnnualPrice( plan.getAnnualPrice() );
        if ( plan.getClientQuota() != null ) {
            getPlanResponse.setClientQuota( plan.getClientQuota() );
        }
        getPlanResponse.setDescription( plan.getDescription() );
        if ( plan.getFileQuotaPerClient() != null ) {
            getPlanResponse.setFileQuotaPerClient( plan.getFileQuotaPerClient() );
        }
        getPlanResponse.setId( plan.getId() );
        if ( plan.getLawyerQuota() != null ) {
            getPlanResponse.setLawyerQuota( plan.getLawyerQuota() );
        }
        getPlanResponse.setMonthlyPrice( plan.getMonthlyPrice() );
        getPlanResponse.setName( plan.getName() );

        return getPlanResponse;
    }

    @Override
    public List<GetPlanResponse> getPlanListResponse(List<Plan> plans) {
        if ( plans == null ) {
            return null;
        }

        List<GetPlanResponse> list = new ArrayList<GetPlanResponse>( plans.size() );
        for ( Plan plan : plans ) {
            list.add( getPlanResponse( plan ) );
        }

        return list;
    }

    @Override
    public GetSubscriptionResponse getSubscriptionResponse(Subscription subscription) {
        if ( subscription == null ) {
            return null;
        }

        GetSubscriptionResponse getSubscriptionResponse = new GetSubscriptionResponse();

        getSubscriptionResponse.setAutoRenew( subscription.isAutoRenew() );
        getSubscriptionResponse.setCompany( getCompanyResponse( subscription.getCompany() ) );
        getSubscriptionResponse.setEndDate( subscription.getEndDate() );
        getSubscriptionResponse.setFee( subscription.getFee() );
        getSubscriptionResponse.setId( subscription.getId() );
        getSubscriptionResponse.setPlan( getPlanResponse( subscription.getPlan() ) );
        getSubscriptionResponse.setStartDate( subscription.getStartDate() );
        getSubscriptionResponse.setType( subscription.getType() );

        return getSubscriptionResponse;
    }

    @Override
    public List<GetSubscriptionResponse> getSubscriptionListResponse(List<Subscription> subscriptions) {
        if ( subscriptions == null ) {
            return null;
        }

        List<GetSubscriptionResponse> list = new ArrayList<GetSubscriptionResponse>( subscriptions.size() );
        for ( Subscription subscription : subscriptions ) {
            list.add( getSubscriptionResponse( subscription ) );
        }

        return list;
    }

    @Override
    public GetSubscriptionWithoutPlanResponse getSubscriptionWithoutPlanResponse(Subscription subscription) {
        if ( subscription == null ) {
            return null;
        }

        GetSubscriptionWithoutPlanResponse getSubscriptionWithoutPlanResponse = new GetSubscriptionWithoutPlanResponse();

        getSubscriptionWithoutPlanResponse.setAutoRenew( subscription.isAutoRenew() );
        getSubscriptionWithoutPlanResponse.setCompany( getCompanyResponse( subscription.getCompany() ) );
        getSubscriptionWithoutPlanResponse.setEndDate( subscription.getEndDate() );
        getSubscriptionWithoutPlanResponse.setFee( subscription.getFee() );
        getSubscriptionWithoutPlanResponse.setId( subscription.getId() );
        getSubscriptionWithoutPlanResponse.setStartDate( subscription.getStartDate() );
        getSubscriptionWithoutPlanResponse.setType( subscription.getType() );

        return getSubscriptionWithoutPlanResponse;
    }

    @Override
    public List<GetSubscriptionWithoutPlanResponse> getSubscriptionWithoutPlanListResponse(List<Subscription> subscriptions) {
        if ( subscriptions == null ) {
            return null;
        }

        List<GetSubscriptionWithoutPlanResponse> list = new ArrayList<GetSubscriptionWithoutPlanResponse>( subscriptions.size() );
        for ( Subscription subscription : subscriptions ) {
            list.add( getSubscriptionWithoutPlanResponse( subscription ) );
        }

        return list;
    }

    @Override
    public GetUpdateResponse getUpdateResponse(Update update) {
        if ( update == null ) {
            return null;
        }

        GetUpdateResponse getUpdateResponse = new GetUpdateResponse();

        getUpdateResponse.setContent( update.getContent() );
        getUpdateResponse.setFile( getFileResponse( update.getFile() ) );
        getUpdateResponse.setId( update.getId() );
        getUpdateResponse.setLawyer( getLawyerResponse( update.getLawyer() ) );
        getUpdateResponse.setState( update.getState() );

        return getUpdateResponse;
    }

    @Override
    public List<GetUpdateResponse> getUpdateListResponse(List<Update> updates) {
        if ( updates == null ) {
            return null;
        }

        List<GetUpdateResponse> list = new ArrayList<GetUpdateResponse>( updates.size() );
        for ( Update update : updates ) {
            list.add( getUpdateResponse( update ) );
        }

        return list;
    }

    @Override
    public GetUserResponse getUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        GetUserResponse getUserResponse = new GetUserResponse();

        getUserResponse.setEmail( user.getEmail() );
        getUserResponse.setId( user.getId() );
        getUserResponse.setRole( getRoleResponse( user.getRole() ) );
        getUserResponse.setUsername( user.getUsername() );

        return getUserResponse;
    }

    @Override
    public List<GetUserResponse> getUserListResponse(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<GetUserResponse> list = new ArrayList<GetUserResponse>( users.size() );
        for ( User user : users ) {
            list.add( getUserResponse( user ) );
        }

        return list;
    }

    @Override
    public GetRoleResponse getRoleResponse(Role role) {
        if ( role == null ) {
            return null;
        }

        GetRoleResponse getRoleResponse = new GetRoleResponse();

        getRoleResponse.setId( role.getId() );
        getRoleResponse.setName( role.getName() );

        return getRoleResponse;
    }

    @Override
    public List<GetRoleResponse> getRoleListResponse(List<Role> roles) {
        if ( roles == null ) {
            return null;
        }

        List<GetRoleResponse> list = new ArrayList<GetRoleResponse>( roles.size() );
        for ( Role role : roles ) {
            list.add( getRoleResponse( role ) );
        }

        return list;
    }
}
