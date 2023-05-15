package com.bzncrsrtc.kurumsalmuvekkil.mappers;

import com.bzncrsrtc.kurumsalmuvekkil.models.Company;
import com.bzncrsrtc.kurumsalmuvekkil.responses.AllCompaniesResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-15T23:08:27+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.33.0.v20230218-1114, environment: Java 17.0.6 (Eclipse Adoptium)"
)
*/
@Component
public class CompanyMapperImpl implements CompanyMapper {

    @Override
    public List<AllCompaniesResponse> getAllCompaniesResponse(List<Company> companies) {
        if ( companies == null ) {
            return null;
        }

        List<AllCompaniesResponse> list = new ArrayList<AllCompaniesResponse>( companies.size() );
        for ( Company company : companies ) {
            list.add( companyToAllCompaniesResponse( company ) );
        }

        return list;
    }

    protected AllCompaniesResponse companyToAllCompaniesResponse(Company company) {
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
}
