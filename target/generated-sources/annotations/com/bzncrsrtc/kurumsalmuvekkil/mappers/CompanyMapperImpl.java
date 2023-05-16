package com.bzncrsrtc.kurumsalmuvekkil.mappers;

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
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-16T17:04:02+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.33.0.v20230218-1114, environment: Java 17.0.6 (Eclipse Adoptium)"
)
*/
@Component
public class CompanyMapperImpl implements CompanyMapper {

    @Override
    public AllCompaniesResponse getAllCompaniesResponseFromCompanyModel(Company company) {
        if ( company == null ) {
            return null;
        }

        AllCompaniesResponse allCompaniesResponse = new AllCompaniesResponse();

        allCompaniesResponse.setActive( company.isActive() );
        allCompaniesResponse.setDeleted( company.isDeleted() );
        allCompaniesResponse.setId( company.getId() );
        allCompaniesResponse.setName( company.getName() );

        return allCompaniesResponse;
    }

    @Override
    public List<AllCompaniesResponse> getAllCompaniesResponseFromCompanyModel(List<Company> companies) {
        if ( companies == null ) {
            return null;
        }

        List<AllCompaniesResponse> list = new ArrayList<AllCompaniesResponse>( companies.size() );
        for ( Company company : companies ) {
            list.add( getAllCompaniesResponseFromCompanyModel( company ) );
        }

        return list;
    }

    @Override
    public CreateCompanyResponse getCreateCompanyResponseFromCompanyModel(Company company) {
        if ( company == null ) {
            return null;
        }

        CreateCompanyResponse createCompanyResponse = new CreateCompanyResponse();

        createCompanyResponse.setActive( company.isActive() );
        createCompanyResponse.setDeleted( company.isDeleted() );
        createCompanyResponse.setId( company.getId() );
        createCompanyResponse.setName( company.getName() );

        return createCompanyResponse;
    }

    @Override
    public Company getCompanyModelFromCreateCompanyRequest(CreateCompanyRequest createCompanyRequest) {
        if ( createCompanyRequest == null ) {
            return null;
        }

        Company company = new Company();

        company.setName( createCompanyRequest.getName() );

        return company;
    }

    @Override
    public Company getCompanyModelFromUpdateCompanyRequest(UpdateCompanyRequest updateCompanyRequest) {
        if ( updateCompanyRequest == null ) {
            return null;
        }

        Company company = new Company();

        company.setId( updateCompanyRequest.getId() );
        company.setName( updateCompanyRequest.getName() );

        return company;
    }

    @Override
    public UpdateCompanyResponse getUpdateCompanyResponseFromCompanyModel(Company company) {
        if ( company == null ) {
            return null;
        }

        UpdateCompanyResponse updateCompanyResponse = new UpdateCompanyResponse();

        updateCompanyResponse.setId( company.getId() );
        updateCompanyResponse.setName( company.getName() );

        return updateCompanyResponse;
    }

    @Override
    public GetCompanyResponse getCompanyResponseFromCompanyModel(Company company) {
        if ( company == null ) {
            return null;
        }

        GetCompanyResponse getCompanyResponse = new GetCompanyResponse();

        getCompanyResponse.setId( company.getId() );
        getCompanyResponse.setName( company.getName() );

        return getCompanyResponse;
    }

    @Override
    public GetSubscriptionOfCompanyResponse getSubscriptionOfCompanyResponseFromSubscriptionModel(Subscription subscription) {
        if ( subscription == null ) {
            return null;
        }

        GetSubscriptionOfCompanyResponse getSubscriptionOfCompanyResponse = new GetSubscriptionOfCompanyResponse();

        getSubscriptionOfCompanyResponse.setAutoRenew( subscription.isAutoRenew() );
        getSubscriptionOfCompanyResponse.setCompany( getCompanyResponseFromCompanyModel( subscription.getCompany() ) );
        getSubscriptionOfCompanyResponse.setEndDate( subscription.getEndDate() );
        getSubscriptionOfCompanyResponse.setFee( subscription.getFee() );
        getSubscriptionOfCompanyResponse.setId( subscription.getId() );
        getSubscriptionOfCompanyResponse.setPlan( getPlanInSubscriptionResponseFromPlanModel( subscription.getPlan() ) );
        getSubscriptionOfCompanyResponse.setStartDate( subscription.getStartDate() );
        getSubscriptionOfCompanyResponse.setType( subscription.getType() );

        return getSubscriptionOfCompanyResponse;
    }

    @Override
    public GetPlanInSubscriptionResponse getPlanInSubscriptionResponseFromPlanModel(Plan plan) {
        if ( plan == null ) {
            return null;
        }

        GetPlanInSubscriptionResponse getPlanInSubscriptionResponse = new GetPlanInSubscriptionResponse();

        return getPlanInSubscriptionResponse;
    }

    @Override
    public GetLawyersOfCompanyResponse getLawyersOfCompanyResponseFromLawyerModel(Lawyer lawyer) {
        if ( lawyer == null ) {
            return null;
        }

        GetLawyersOfCompanyResponse getLawyersOfCompanyResponse = new GetLawyersOfCompanyResponse();

        return getLawyersOfCompanyResponse;
    }

    @Override
    public List<GetLawyersOfCompanyResponse> getLawyersOfCompanyResponseFromLawyersList(List<Lawyer> lawyers) {
        if ( lawyers == null ) {
            return null;
        }

        List<GetLawyersOfCompanyResponse> list = new ArrayList<GetLawyersOfCompanyResponse>( lawyers.size() );
        for ( Lawyer lawyer : lawyers ) {
            list.add( getLawyersOfCompanyResponseFromLawyerModel( lawyer ) );
        }

        return list;
    }

    @Override
    public GetFilesOfCompanyResponse getFilesOfCompanyResponseFromFileModel(File file) {
        if ( file == null ) {
            return null;
        }

        GetFilesOfCompanyResponse getFilesOfCompanyResponse = new GetFilesOfCompanyResponse();

        return getFilesOfCompanyResponse;
    }

    @Override
    public List<GetFilesOfCompanyResponse> getFilesOfCompanyResponseFromFilesList(List<File> files) {
        if ( files == null ) {
            return null;
        }

        List<GetFilesOfCompanyResponse> list = new ArrayList<GetFilesOfCompanyResponse>( files.size() );
        for ( File file : files ) {
            list.add( getFilesOfCompanyResponseFromFileModel( file ) );
        }

        return list;
    }
}
