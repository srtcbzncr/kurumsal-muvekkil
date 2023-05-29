package com.bzncrsrtc.kurumsalmuvekkil.mappers;

import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.models.Plan;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateCompanyRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreatePlanRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateCompanyRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdatePlanRequest;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-29T14:00:18+0300",
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

        return plan;
    }

    @Override
    public CreatePlanRequest fromPlanToCreatePlanRequest(Plan plan) {
        if ( plan == null ) {
            return null;
        }

        CreatePlanRequest createPlanRequest = new CreatePlanRequest();

        createPlanRequest.setAnnualPrice( plan.getAnnualPrice() );
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

        return updatePlanRequest;
    }
}
