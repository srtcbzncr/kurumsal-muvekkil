package com.bzncrsrtc.kurumsalmuvekkil.mappers;

import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateCompanyRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateCompanyRequest;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-18T15:48:27+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.33.0.v20230218-1114, environment: Java 17.0.6 (Eclipse Adoptium)"
)
*/
@Component
public class RequestMapperImpl implements RequestMapper {

    @Override
    public Company fromCreateCompanyRequest(CreateCompanyRequest createCompanyRequest) {
        if ( createCompanyRequest == null ) {
            return null;
        }

        Company company = new Company();

        company.setName( createCompanyRequest.getName() );

        return company;
    }

    @Override
    public Company fromUpdateCompanyRequest(UpdateCompanyRequest updateCompanyRequest) {
        if ( updateCompanyRequest == null ) {
            return null;
        }

        Company company = new Company();

        company.setId( updateCompanyRequest.getId() );
        company.setName( updateCompanyRequest.getName() );

        return company;
    }
}
