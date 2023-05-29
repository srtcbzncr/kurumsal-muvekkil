package com.bzncrsrtc.kurumsalmuvekkil.mappers;

import com.bzncrsrtc.kurumsalmuvekkil.models.Client;
import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.models.Court;
import com.bzncrsrtc.kurumsalmuvekkil.models.File;
import com.bzncrsrtc.kurumsalmuvekkil.models.Lawyer;
import com.bzncrsrtc.kurumsalmuvekkil.models.Plan;
import com.bzncrsrtc.kurumsalmuvekkil.models.Subscription;
import com.bzncrsrtc.kurumsalmuvekkil.models.Update;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetClientResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetCompanyResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetCourtResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetFileResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetLawyerResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetPlanResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetSubscriptionResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetUpdateResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-29T15:17:15+0300",
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

        getClientResponse.setEmail( client.getEmail() );
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

        getCourtResponse.setId( court.getId() );
        getCourtResponse.setName( court.getName() );

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
    public GetLawyerResponse getLawyerResponse(Lawyer lawyer) {
        if ( lawyer == null ) {
            return null;
        }

        GetLawyerResponse getLawyerResponse = new GetLawyerResponse();

        getLawyerResponse.setEmail( lawyer.getEmail() );
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
}
