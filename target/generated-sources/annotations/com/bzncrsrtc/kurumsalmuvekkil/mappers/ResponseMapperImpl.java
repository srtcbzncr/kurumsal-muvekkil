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
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-13T17:13:34+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.33.0.v20230218-1114, environment: Java 17.0.6 (Eclipse Adoptium)"
)
*/
@Component
public class ResponseMapperImpl implements ResponseMapper {

    @Override
    public ClientResponse getClientResponse(Client client) {
        if ( client == null ) {
            return null;
        }

        ClientResponse clientResponse = new ClientResponse();

        clientResponse.setFirstName( client.getFirstName() );
        clientResponse.setId( client.getId() );
        clientResponse.setIdentificationNumber( client.getIdentificationNumber() );
        clientResponse.setLastName( client.getLastName() );
        clientResponse.setPhone( client.getPhone() );

        return clientResponse;
    }

    @Override
    public List<ClientResponse> getClientListResponse(List<Client> clients) {
        if ( clients == null ) {
            return null;
        }

        List<ClientResponse> list = new ArrayList<ClientResponse>( clients.size() );
        for ( Client client : clients ) {
            list.add( getClientResponse( client ) );
        }

        return list;
    }

    @Override
    public CompanyResponse getCompanyResponse(Company company) {
        if ( company == null ) {
            return null;
        }

        CompanyResponse companyResponse = new CompanyResponse();

        companyResponse.setActive( company.isActive() );
        companyResponse.setDeleted( company.isDeleted() );
        companyResponse.setId( company.getId() );
        companyResponse.setName( company.getName() );

        companyResponse.setLawyerCount( company.getLawyers() != null && company.getLawyers().size() > 0 ? company.getLawyers().stream().filter(c -> c.isActive() == true && c.isDeleted() == false).collect(java.util.stream.Collectors.toList()).size() : 0 );
        companyResponse.setPlan( company.getSubscriptions() != null && company.getSubscriptions().size() > 0 ? company.getSubscriptions().get(0).getPlan().getName() : null );

        return companyResponse;
    }

    @Override
    public List<CompanyResponse> getCompanyListResponse(List<Company> companies) {
        if ( companies == null ) {
            return null;
        }

        List<CompanyResponse> list = new ArrayList<CompanyResponse>( companies.size() );
        for ( Company company : companies ) {
            list.add( getCompanyResponse( company ) );
        }

        return list;
    }

    @Override
    public CourtResponse getCourtResponse(Court court) {
        if ( court == null ) {
            return null;
        }

        CourtResponse courtResponse = new CourtResponse();

        courtResponse.setActive( court.isActive() );
        courtResponse.setDeleted( court.isDeleted() );
        courtResponse.setId( court.getId() );
        courtResponse.setName( court.getName() );
        courtResponse.setParent( getCourtWithoutParentResponse( court.getParent() ) );

        courtResponse.setSubCount( court.getSubs() != null && court.getSubs().size() > 0 ? court.getSubs().stream().filter(c -> c.isActive() == true && c.isDeleted() == false).collect(java.util.stream.Collectors.toList()).size() : 0 );

        return courtResponse;
    }

    @Override
    public List<CourtResponse> getCourtListResponse(List<Court> courts) {
        if ( courts == null ) {
            return null;
        }

        List<CourtResponse> list = new ArrayList<CourtResponse>( courts.size() );
        for ( Court court : courts ) {
            list.add( getCourtResponse( court ) );
        }

        return list;
    }

    @Override
    public CourtWithoutParentResponse getCourtWithoutParentResponse(Court court) {
        if ( court == null ) {
            return null;
        }

        CourtWithoutParentResponse courtWithoutParentResponse = new CourtWithoutParentResponse();

        courtWithoutParentResponse.setActive( court.isActive() );
        courtWithoutParentResponse.setDeleted( court.isDeleted() );
        courtWithoutParentResponse.setId( court.getId() );
        courtWithoutParentResponse.setName( court.getName() );

        courtWithoutParentResponse.setSubCount( court.getSubs() != null && court.getSubs().size() > 0 ? court.getSubs().stream().filter(c -> c.isActive() == true && c.isDeleted() == false).collect(java.util.stream.Collectors.toList()).size() : 0 );

        return courtWithoutParentResponse;
    }

    @Override
    public List<CourtWithoutParentResponse> getCourtWithoutParentListResponse(List<Court> courts) {
        if ( courts == null ) {
            return null;
        }

        List<CourtWithoutParentResponse> list = new ArrayList<CourtWithoutParentResponse>( courts.size() );
        for ( Court court : courts ) {
            list.add( getCourtWithoutParentResponse( court ) );
        }

        return list;
    }

    @Override
    public CourtDetailsResponse getCourtDetailsResponse(Court court) {
        if ( court == null ) {
            return null;
        }

        CourtDetailsResponse courtDetailsResponse = new CourtDetailsResponse();

        courtDetailsResponse.setActive( court.isActive() );
        courtDetailsResponse.setDeleted( court.isDeleted() );
        courtDetailsResponse.setId( court.getId() );
        courtDetailsResponse.setName( court.getName() );
        courtDetailsResponse.setParent( getCourtWithoutParentResponse( court.getParent() ) );
        courtDetailsResponse.setSubs( getCourtWithoutParentListResponse( court.getSubs() ) );

        return courtDetailsResponse;
    }

    @Override
    public List<CourtDetailsResponse> getCourtDetailsListResponse(List<Court> courts) {
        if ( courts == null ) {
            return null;
        }

        List<CourtDetailsResponse> list = new ArrayList<CourtDetailsResponse>( courts.size() );
        for ( Court court : courts ) {
            list.add( getCourtDetailsResponse( court ) );
        }

        return list;
    }

    @Override
    public FileResponse getFileResponse(File file) {
        if ( file == null ) {
            return null;
        }

        FileResponse fileResponse = new FileResponse();

        fileResponse.setCourt( getCourtResponse( file.getCourt() ) );
        fileResponse.setCourtDetail( file.getCourtDetail() );
        fileResponse.setDescription( file.getDescription() );
        fileResponse.setId( file.getId() );
        fileResponse.setTitle( file.getTitle() );

        return fileResponse;
    }

    @Override
    public List<FileResponse> getFileListResponse(List<File> files) {
        if ( files == null ) {
            return null;
        }

        List<FileResponse> list = new ArrayList<FileResponse>( files.size() );
        for ( File file : files ) {
            list.add( getFileResponse( file ) );
        }

        return list;
    }

    @Override
    public FileWithoutCourtResponse getFileWithoutCourtResponse(File file) {
        if ( file == null ) {
            return null;
        }

        FileWithoutCourtResponse fileWithoutCourtResponse = new FileWithoutCourtResponse();

        fileWithoutCourtResponse.setCourtDetail( file.getCourtDetail() );
        fileWithoutCourtResponse.setDescription( file.getDescription() );
        fileWithoutCourtResponse.setId( file.getId() );
        fileWithoutCourtResponse.setTitle( file.getTitle() );

        return fileWithoutCourtResponse;
    }

    @Override
    public List<FileWithoutCourtResponse> getFileWithoutCourtListResponse(List<File> files) {
        if ( files == null ) {
            return null;
        }

        List<FileWithoutCourtResponse> list = new ArrayList<FileWithoutCourtResponse>( files.size() );
        for ( File file : files ) {
            list.add( getFileWithoutCourtResponse( file ) );
        }

        return list;
    }

    @Override
    public LawyerResponse getLawyerResponse(Lawyer lawyer) {
        if ( lawyer == null ) {
            return null;
        }

        LawyerResponse lawyerResponse = new LawyerResponse();

        lawyerResponse.setCompanyName( lawyerCompanyName( lawyer ) );
        lawyerResponse.setEmail( lawyerUserEmail( lawyer ) );
        lawyerResponse.setActive( lawyer.isActive() );
        lawyerResponse.setDeleted( lawyer.isDeleted() );
        lawyerResponse.setFirstName( lawyer.getFirstName() );
        lawyerResponse.setId( lawyer.getId() );
        lawyerResponse.setLastName( lawyer.getLastName() );
        lawyerResponse.setPhone( lawyer.getPhone() );

        return lawyerResponse;
    }

    @Override
    public List<LawyerResponse> getLawyerListResponse(List<Lawyer> lawyers) {
        if ( lawyers == null ) {
            return null;
        }

        List<LawyerResponse> list = new ArrayList<LawyerResponse>( lawyers.size() );
        for ( Lawyer lawyer : lawyers ) {
            list.add( getLawyerResponse( lawyer ) );
        }

        return list;
    }

    @Override
    public PlanResponse getPlanResponse(Plan plan) {
        if ( plan == null ) {
            return null;
        }

        PlanResponse planResponse = new PlanResponse();

        planResponse.setActive( plan.isActive() );
        planResponse.setAnnualPrice( plan.getAnnualPrice() );
        if ( plan.getClientQuota() != null ) {
            planResponse.setClientQuota( plan.getClientQuota() );
        }
        planResponse.setDeleted( plan.isDeleted() );
        planResponse.setDescription( plan.getDescription() );
        if ( plan.getFileQuotaPerClient() != null ) {
            planResponse.setFileQuotaPerClient( plan.getFileQuotaPerClient() );
        }
        planResponse.setId( plan.getId() );
        if ( plan.getLawyerQuota() != null ) {
            planResponse.setLawyerQuota( plan.getLawyerQuota() );
        }
        planResponse.setMonthlyPrice( plan.getMonthlyPrice() );
        planResponse.setName( plan.getName() );

        return planResponse;
    }

    @Override
    public List<PlanResponse> getPlanListResponse(List<Plan> plans) {
        if ( plans == null ) {
            return null;
        }

        List<PlanResponse> list = new ArrayList<PlanResponse>( plans.size() );
        for ( Plan plan : plans ) {
            list.add( getPlanResponse( plan ) );
        }

        return list;
    }

    @Override
    public SubscriptionResponse getSubscriptionResponse(Subscription subscription) {
        if ( subscription == null ) {
            return null;
        }

        SubscriptionResponse subscriptionResponse = new SubscriptionResponse();

        subscriptionResponse.setAutoRenew( subscription.isAutoRenew() );
        subscriptionResponse.setCompany( getCompanyResponse( subscription.getCompany() ) );
        subscriptionResponse.setEndDate( subscription.getEndDate() );
        subscriptionResponse.setFee( subscription.getFee() );
        subscriptionResponse.setId( subscription.getId() );
        subscriptionResponse.setPlan( getPlanResponse( subscription.getPlan() ) );
        subscriptionResponse.setStartDate( subscription.getStartDate() );
        subscriptionResponse.setType( subscription.getType() );

        return subscriptionResponse;
    }

    @Override
    public List<SubscriptionResponse> getSubscriptionListResponse(List<Subscription> subscriptions) {
        if ( subscriptions == null ) {
            return null;
        }

        List<SubscriptionResponse> list = new ArrayList<SubscriptionResponse>( subscriptions.size() );
        for ( Subscription subscription : subscriptions ) {
            list.add( getSubscriptionResponse( subscription ) );
        }

        return list;
    }

    @Override
    public SubscriptionWithoutPlanResponse getSubscriptionWithoutPlanResponse(Subscription subscription) {
        if ( subscription == null ) {
            return null;
        }

        SubscriptionWithoutPlanResponse subscriptionWithoutPlanResponse = new SubscriptionWithoutPlanResponse();

        subscriptionWithoutPlanResponse.setAutoRenew( subscription.isAutoRenew() );
        subscriptionWithoutPlanResponse.setCompany( getCompanyResponse( subscription.getCompany() ) );
        subscriptionWithoutPlanResponse.setEndDate( subscription.getEndDate() );
        subscriptionWithoutPlanResponse.setFee( subscription.getFee() );
        subscriptionWithoutPlanResponse.setId( subscription.getId() );
        subscriptionWithoutPlanResponse.setStartDate( subscription.getStartDate() );
        subscriptionWithoutPlanResponse.setType( subscription.getType() );

        return subscriptionWithoutPlanResponse;
    }

    @Override
    public List<SubscriptionWithoutPlanResponse> getSubscriptionWithoutPlanListResponse(List<Subscription> subscriptions) {
        if ( subscriptions == null ) {
            return null;
        }

        List<SubscriptionWithoutPlanResponse> list = new ArrayList<SubscriptionWithoutPlanResponse>( subscriptions.size() );
        for ( Subscription subscription : subscriptions ) {
            list.add( getSubscriptionWithoutPlanResponse( subscription ) );
        }

        return list;
    }

    @Override
    public UpdateResponse getUpdateResponse(Update update) {
        if ( update == null ) {
            return null;
        }

        UpdateResponse updateResponse = new UpdateResponse();

        updateResponse.setContent( update.getContent() );
        updateResponse.setFile( getFileResponse( update.getFile() ) );
        updateResponse.setId( update.getId() );
        updateResponse.setLawyer( getLawyerResponse( update.getLawyer() ) );
        updateResponse.setState( update.getState() );

        return updateResponse;
    }

    @Override
    public List<UpdateResponse> getUpdateListResponse(List<Update> updates) {
        if ( updates == null ) {
            return null;
        }

        List<UpdateResponse> list = new ArrayList<UpdateResponse>( updates.size() );
        for ( Update update : updates ) {
            list.add( getUpdateResponse( update ) );
        }

        return list;
    }

    @Override
    public UserResponse getUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setActive( user.isActive() );
        userResponse.setDeleted( user.isDeleted() );
        userResponse.setEmail( user.getEmail() );
        userResponse.setId( user.getId() );
        userResponse.setLocked( user.isLocked() );
        userResponse.setNew( user.isNew() );
        userResponse.setRole( getRoleResponse( user.getRole() ) );
        userResponse.setUsername( user.getUsername() );

        return userResponse;
    }

    @Override
    public List<UserResponse> getUserListResponse(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserResponse> list = new ArrayList<UserResponse>( users.size() );
        for ( User user : users ) {
            list.add( getUserResponse( user ) );
        }

        return list;
    }

    @Override
    public RoleResponse getRoleResponse(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleResponse roleResponse = new RoleResponse();

        roleResponse.setId( role.getId() );
        roleResponse.setName( role.getName() );

        return roleResponse;
    }

    @Override
    public List<RoleResponse> getRoleListResponse(List<Role> roles) {
        if ( roles == null ) {
            return null;
        }

        List<RoleResponse> list = new ArrayList<RoleResponse>( roles.size() );
        for ( Role role : roles ) {
            list.add( getRoleResponse( role ) );
        }

        return list;
    }

    private String lawyerCompanyName(Lawyer lawyer) {
        if ( lawyer == null ) {
            return null;
        }
        Company company = lawyer.getCompany();
        if ( company == null ) {
            return null;
        }
        String name = company.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String lawyerUserEmail(Lawyer lawyer) {
        if ( lawyer == null ) {
            return null;
        }
        User user = lawyer.getUser();
        if ( user == null ) {
            return null;
        }
        String email = user.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }
}
