package com.eagle.kyc.web.rest;

import com.eagle.kyc.Kyc5App;

import com.eagle.kyc.domain.ApplicationProspect;
import com.eagle.kyc.domain.PersonalInformation;
import com.eagle.kyc.domain.InvestmentPotential;
import com.eagle.kyc.domain.Nominee;
import com.eagle.kyc.domain.TradingInfo;
import com.eagle.kyc.domain.DepositoryInfo;
import com.eagle.kyc.domain.Address;
import com.eagle.kyc.domain.BankInformation;
import com.eagle.kyc.repository.ApplicationProspectRepository;
import com.eagle.kyc.service.ApplicationProspectService;
import com.eagle.kyc.service.dto.ApplicationProspectDTO;
import com.eagle.kyc.service.mapper.ApplicationProspectMapper;
import com.eagle.kyc.web.rest.errors.ExceptionTranslator;
import com.eagle.kyc.service.dto.ApplicationProspectCriteria;
import com.eagle.kyc.service.ApplicationProspectQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.eagle.kyc.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ApplicationProspectResource REST controller.
 *
 * @see ApplicationProspectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Kyc5App.class)
public class ApplicationProspectResourceIntTest {

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_CONFIRM_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_CONFIRM_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private ApplicationProspectRepository applicationProspectRepository;

    @Autowired
    private ApplicationProspectMapper applicationProspectMapper;
    
    @Autowired
    private ApplicationProspectService applicationProspectService;

    @Autowired
    private ApplicationProspectQueryService applicationProspectQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApplicationProspectMockMvc;

    private ApplicationProspect applicationProspect;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicationProspectResource applicationProspectResource = new ApplicationProspectResource(applicationProspectService, applicationProspectQueryService);
        this.restApplicationProspectMockMvc = MockMvcBuilders.standaloneSetup(applicationProspectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationProspect createEntity(EntityManager em) {
        ApplicationProspect applicationProspect = new ApplicationProspect()
            .fullName(DEFAULT_FULL_NAME)
            .mobileNo(DEFAULT_MOBILE_NO)
            .email(DEFAULT_EMAIL)
            .password(DEFAULT_PASSWORD)
            .confirmPassword(DEFAULT_CONFIRM_PASSWORD);
        return applicationProspect;
    }

    @Before
    public void initTest() {
        applicationProspect = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationProspect() throws Exception {
        int databaseSizeBeforeCreate = applicationProspectRepository.findAll().size();

        // Create the ApplicationProspect
        ApplicationProspectDTO applicationProspectDTO = applicationProspectMapper.toDto(applicationProspect);
        restApplicationProspectMockMvc.perform(post("/api/application-prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationProspectDTO)))
            .andExpect(status().isCreated());

        // Validate the ApplicationProspect in the database
        List<ApplicationProspect> applicationProspectList = applicationProspectRepository.findAll();
        assertThat(applicationProspectList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationProspect testApplicationProspect = applicationProspectList.get(applicationProspectList.size() - 1);
        assertThat(testApplicationProspect.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testApplicationProspect.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testApplicationProspect.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testApplicationProspect.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testApplicationProspect.getConfirmPassword()).isEqualTo(DEFAULT_CONFIRM_PASSWORD);
    }

    @Test
    @Transactional
    public void createApplicationProspectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationProspectRepository.findAll().size();

        // Create the ApplicationProspect with an existing ID
        applicationProspect.setId(1L);
        ApplicationProspectDTO applicationProspectDTO = applicationProspectMapper.toDto(applicationProspect);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationProspectMockMvc.perform(post("/api/application-prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationProspectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationProspect in the database
        List<ApplicationProspect> applicationProspectList = applicationProspectRepository.findAll();
        assertThat(applicationProspectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFullNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationProspectRepository.findAll().size();
        // set the field null
        applicationProspect.setFullName(null);

        // Create the ApplicationProspect, which fails.
        ApplicationProspectDTO applicationProspectDTO = applicationProspectMapper.toDto(applicationProspect);

        restApplicationProspectMockMvc.perform(post("/api/application-prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationProspectDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationProspect> applicationProspectList = applicationProspectRepository.findAll();
        assertThat(applicationProspectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMobileNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationProspectRepository.findAll().size();
        // set the field null
        applicationProspect.setMobileNo(null);

        // Create the ApplicationProspect, which fails.
        ApplicationProspectDTO applicationProspectDTO = applicationProspectMapper.toDto(applicationProspect);

        restApplicationProspectMockMvc.perform(post("/api/application-prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationProspectDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationProspect> applicationProspectList = applicationProspectRepository.findAll();
        assertThat(applicationProspectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationProspectRepository.findAll().size();
        // set the field null
        applicationProspect.setEmail(null);

        // Create the ApplicationProspect, which fails.
        ApplicationProspectDTO applicationProspectDTO = applicationProspectMapper.toDto(applicationProspect);

        restApplicationProspectMockMvc.perform(post("/api/application-prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationProspectDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationProspect> applicationProspectList = applicationProspectRepository.findAll();
        assertThat(applicationProspectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationProspectRepository.findAll().size();
        // set the field null
        applicationProspect.setPassword(null);

        // Create the ApplicationProspect, which fails.
        ApplicationProspectDTO applicationProspectDTO = applicationProspectMapper.toDto(applicationProspect);

        restApplicationProspectMockMvc.perform(post("/api/application-prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationProspectDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationProspect> applicationProspectList = applicationProspectRepository.findAll();
        assertThat(applicationProspectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConfirmPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationProspectRepository.findAll().size();
        // set the field null
        applicationProspect.setConfirmPassword(null);

        // Create the ApplicationProspect, which fails.
        ApplicationProspectDTO applicationProspectDTO = applicationProspectMapper.toDto(applicationProspect);

        restApplicationProspectMockMvc.perform(post("/api/application-prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationProspectDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationProspect> applicationProspectList = applicationProspectRepository.findAll();
        assertThat(applicationProspectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicationProspects() throws Exception {
        // Initialize the database
        applicationProspectRepository.saveAndFlush(applicationProspect);

        // Get all the applicationProspectList
        restApplicationProspectMockMvc.perform(get("/api/application-prospects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationProspect.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].confirmPassword").value(hasItem(DEFAULT_CONFIRM_PASSWORD.toString())));
    }
    
    @Test
    @Transactional
    public void getApplicationProspect() throws Exception {
        // Initialize the database
        applicationProspectRepository.saveAndFlush(applicationProspect);

        // Get the applicationProspect
        restApplicationProspectMockMvc.perform(get("/api/application-prospects/{id}", applicationProspect.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicationProspect.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.confirmPassword").value(DEFAULT_CONFIRM_PASSWORD.toString()));
    }

    @Test
    @Transactional
    public void getAllApplicationProspectsByFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationProspectRepository.saveAndFlush(applicationProspect);

        // Get all the applicationProspectList where fullName equals to DEFAULT_FULL_NAME
        defaultApplicationProspectShouldBeFound("fullName.equals=" + DEFAULT_FULL_NAME);

        // Get all the applicationProspectList where fullName equals to UPDATED_FULL_NAME
        defaultApplicationProspectShouldNotBeFound("fullName.equals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationProspectsByFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        applicationProspectRepository.saveAndFlush(applicationProspect);

        // Get all the applicationProspectList where fullName in DEFAULT_FULL_NAME or UPDATED_FULL_NAME
        defaultApplicationProspectShouldBeFound("fullName.in=" + DEFAULT_FULL_NAME + "," + UPDATED_FULL_NAME);

        // Get all the applicationProspectList where fullName equals to UPDATED_FULL_NAME
        defaultApplicationProspectShouldNotBeFound("fullName.in=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationProspectsByFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationProspectRepository.saveAndFlush(applicationProspect);

        // Get all the applicationProspectList where fullName is not null
        defaultApplicationProspectShouldBeFound("fullName.specified=true");

        // Get all the applicationProspectList where fullName is null
        defaultApplicationProspectShouldNotBeFound("fullName.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationProspectsByMobileNoIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationProspectRepository.saveAndFlush(applicationProspect);

        // Get all the applicationProspectList where mobileNo equals to DEFAULT_MOBILE_NO
        defaultApplicationProspectShouldBeFound("mobileNo.equals=" + DEFAULT_MOBILE_NO);

        // Get all the applicationProspectList where mobileNo equals to UPDATED_MOBILE_NO
        defaultApplicationProspectShouldNotBeFound("mobileNo.equals=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    public void getAllApplicationProspectsByMobileNoIsInShouldWork() throws Exception {
        // Initialize the database
        applicationProspectRepository.saveAndFlush(applicationProspect);

        // Get all the applicationProspectList where mobileNo in DEFAULT_MOBILE_NO or UPDATED_MOBILE_NO
        defaultApplicationProspectShouldBeFound("mobileNo.in=" + DEFAULT_MOBILE_NO + "," + UPDATED_MOBILE_NO);

        // Get all the applicationProspectList where mobileNo equals to UPDATED_MOBILE_NO
        defaultApplicationProspectShouldNotBeFound("mobileNo.in=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    public void getAllApplicationProspectsByMobileNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationProspectRepository.saveAndFlush(applicationProspect);

        // Get all the applicationProspectList where mobileNo is not null
        defaultApplicationProspectShouldBeFound("mobileNo.specified=true");

        // Get all the applicationProspectList where mobileNo is null
        defaultApplicationProspectShouldNotBeFound("mobileNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationProspectsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationProspectRepository.saveAndFlush(applicationProspect);

        // Get all the applicationProspectList where email equals to DEFAULT_EMAIL
        defaultApplicationProspectShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the applicationProspectList where email equals to UPDATED_EMAIL
        defaultApplicationProspectShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllApplicationProspectsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        applicationProspectRepository.saveAndFlush(applicationProspect);

        // Get all the applicationProspectList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultApplicationProspectShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the applicationProspectList where email equals to UPDATED_EMAIL
        defaultApplicationProspectShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllApplicationProspectsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationProspectRepository.saveAndFlush(applicationProspect);

        // Get all the applicationProspectList where email is not null
        defaultApplicationProspectShouldBeFound("email.specified=true");

        // Get all the applicationProspectList where email is null
        defaultApplicationProspectShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationProspectsByPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationProspectRepository.saveAndFlush(applicationProspect);

        // Get all the applicationProspectList where password equals to DEFAULT_PASSWORD
        defaultApplicationProspectShouldBeFound("password.equals=" + DEFAULT_PASSWORD);

        // Get all the applicationProspectList where password equals to UPDATED_PASSWORD
        defaultApplicationProspectShouldNotBeFound("password.equals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllApplicationProspectsByPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        applicationProspectRepository.saveAndFlush(applicationProspect);

        // Get all the applicationProspectList where password in DEFAULT_PASSWORD or UPDATED_PASSWORD
        defaultApplicationProspectShouldBeFound("password.in=" + DEFAULT_PASSWORD + "," + UPDATED_PASSWORD);

        // Get all the applicationProspectList where password equals to UPDATED_PASSWORD
        defaultApplicationProspectShouldNotBeFound("password.in=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllApplicationProspectsByPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationProspectRepository.saveAndFlush(applicationProspect);

        // Get all the applicationProspectList where password is not null
        defaultApplicationProspectShouldBeFound("password.specified=true");

        // Get all the applicationProspectList where password is null
        defaultApplicationProspectShouldNotBeFound("password.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationProspectsByConfirmPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationProspectRepository.saveAndFlush(applicationProspect);

        // Get all the applicationProspectList where confirmPassword equals to DEFAULT_CONFIRM_PASSWORD
        defaultApplicationProspectShouldBeFound("confirmPassword.equals=" + DEFAULT_CONFIRM_PASSWORD);

        // Get all the applicationProspectList where confirmPassword equals to UPDATED_CONFIRM_PASSWORD
        defaultApplicationProspectShouldNotBeFound("confirmPassword.equals=" + UPDATED_CONFIRM_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllApplicationProspectsByConfirmPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        applicationProspectRepository.saveAndFlush(applicationProspect);

        // Get all the applicationProspectList where confirmPassword in DEFAULT_CONFIRM_PASSWORD or UPDATED_CONFIRM_PASSWORD
        defaultApplicationProspectShouldBeFound("confirmPassword.in=" + DEFAULT_CONFIRM_PASSWORD + "," + UPDATED_CONFIRM_PASSWORD);

        // Get all the applicationProspectList where confirmPassword equals to UPDATED_CONFIRM_PASSWORD
        defaultApplicationProspectShouldNotBeFound("confirmPassword.in=" + UPDATED_CONFIRM_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllApplicationProspectsByConfirmPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationProspectRepository.saveAndFlush(applicationProspect);

        // Get all the applicationProspectList where confirmPassword is not null
        defaultApplicationProspectShouldBeFound("confirmPassword.specified=true");

        // Get all the applicationProspectList where confirmPassword is null
        defaultApplicationProspectShouldNotBeFound("confirmPassword.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationProspectsByPersonalInformationIsEqualToSomething() throws Exception {
        // Initialize the database
        PersonalInformation personalInformation = PersonalInformationResourceIntTest.createEntity(em);
        em.persist(personalInformation);
        em.flush();
        applicationProspect.setPersonalInformation(personalInformation);
        applicationProspectRepository.saveAndFlush(applicationProspect);
        Long personalInformationId = personalInformation.getId();

        // Get all the applicationProspectList where personalInformation equals to personalInformationId
        defaultApplicationProspectShouldBeFound("personalInformationId.equals=" + personalInformationId);

        // Get all the applicationProspectList where personalInformation equals to personalInformationId + 1
        defaultApplicationProspectShouldNotBeFound("personalInformationId.equals=" + (personalInformationId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicationProspectsByInvestmentPotentialIsEqualToSomething() throws Exception {
        // Initialize the database
        InvestmentPotential investmentPotential = InvestmentPotentialResourceIntTest.createEntity(em);
        em.persist(investmentPotential);
        em.flush();
        applicationProspect.setInvestmentPotential(investmentPotential);
        applicationProspectRepository.saveAndFlush(applicationProspect);
        Long investmentPotentialId = investmentPotential.getId();

        // Get all the applicationProspectList where investmentPotential equals to investmentPotentialId
        defaultApplicationProspectShouldBeFound("investmentPotentialId.equals=" + investmentPotentialId);

        // Get all the applicationProspectList where investmentPotential equals to investmentPotentialId + 1
        defaultApplicationProspectShouldNotBeFound("investmentPotentialId.equals=" + (investmentPotentialId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicationProspectsByNomineeIsEqualToSomething() throws Exception {
        // Initialize the database
        Nominee nominee = NomineeResourceIntTest.createEntity(em);
        em.persist(nominee);
        em.flush();
        applicationProspect.setNominee(nominee);
        applicationProspectRepository.saveAndFlush(applicationProspect);
        Long nomineeId = nominee.getId();

        // Get all the applicationProspectList where nominee equals to nomineeId
        defaultApplicationProspectShouldBeFound("nomineeId.equals=" + nomineeId);

        // Get all the applicationProspectList where nominee equals to nomineeId + 1
        defaultApplicationProspectShouldNotBeFound("nomineeId.equals=" + (nomineeId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicationProspectsByTradingInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        TradingInfo tradingInfo = TradingInfoResourceIntTest.createEntity(em);
        em.persist(tradingInfo);
        em.flush();
        applicationProspect.setTradingInfo(tradingInfo);
        applicationProspectRepository.saveAndFlush(applicationProspect);
        Long tradingInfoId = tradingInfo.getId();

        // Get all the applicationProspectList where tradingInfo equals to tradingInfoId
        defaultApplicationProspectShouldBeFound("tradingInfoId.equals=" + tradingInfoId);

        // Get all the applicationProspectList where tradingInfo equals to tradingInfoId + 1
        defaultApplicationProspectShouldNotBeFound("tradingInfoId.equals=" + (tradingInfoId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicationProspectsByDepositoryIsEqualToSomething() throws Exception {
        // Initialize the database
        DepositoryInfo depository = DepositoryInfoResourceIntTest.createEntity(em);
        em.persist(depository);
        em.flush();
        applicationProspect.setDepository(depository);
        applicationProspectRepository.saveAndFlush(applicationProspect);
        Long depositoryId = depository.getId();

        // Get all the applicationProspectList where depository equals to depositoryId
        defaultApplicationProspectShouldBeFound("depositoryId.equals=" + depositoryId);

        // Get all the applicationProspectList where depository equals to depositoryId + 1
        defaultApplicationProspectShouldNotBeFound("depositoryId.equals=" + (depositoryId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicationProspectsByAddressesIsEqualToSomething() throws Exception {
        // Initialize the database
        Address addresses = AddressResourceIntTest.createEntity(em);
        em.persist(addresses);
        em.flush();
        applicationProspect.addAddresses(addresses);
        applicationProspectRepository.saveAndFlush(applicationProspect);
        Long addressesId = addresses.getId();

        // Get all the applicationProspectList where addresses equals to addressesId
        defaultApplicationProspectShouldBeFound("addressesId.equals=" + addressesId);

        // Get all the applicationProspectList where addresses equals to addressesId + 1
        defaultApplicationProspectShouldNotBeFound("addressesId.equals=" + (addressesId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicationProspectsByBankInformationIsEqualToSomething() throws Exception {
        // Initialize the database
        BankInformation bankInformation = BankInformationResourceIntTest.createEntity(em);
        em.persist(bankInformation);
        em.flush();
        applicationProspect.addBankInformation(bankInformation);
        applicationProspectRepository.saveAndFlush(applicationProspect);
        Long bankInformationId = bankInformation.getId();

        // Get all the applicationProspectList where bankInformation equals to bankInformationId
        defaultApplicationProspectShouldBeFound("bankInformationId.equals=" + bankInformationId);

        // Get all the applicationProspectList where bankInformation equals to bankInformationId + 1
        defaultApplicationProspectShouldNotBeFound("bankInformationId.equals=" + (bankInformationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultApplicationProspectShouldBeFound(String filter) throws Exception {
        restApplicationProspectMockMvc.perform(get("/api/application-prospects?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationProspect.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].confirmPassword").value(hasItem(DEFAULT_CONFIRM_PASSWORD.toString())));

        // Check, that the count call also returns 1
        restApplicationProspectMockMvc.perform(get("/api/application-prospects/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultApplicationProspectShouldNotBeFound(String filter) throws Exception {
        restApplicationProspectMockMvc.perform(get("/api/application-prospects?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicationProspectMockMvc.perform(get("/api/application-prospects/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingApplicationProspect() throws Exception {
        // Get the applicationProspect
        restApplicationProspectMockMvc.perform(get("/api/application-prospects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationProspect() throws Exception {
        // Initialize the database
        applicationProspectRepository.saveAndFlush(applicationProspect);

        int databaseSizeBeforeUpdate = applicationProspectRepository.findAll().size();

        // Update the applicationProspect
        ApplicationProspect updatedApplicationProspect = applicationProspectRepository.findById(applicationProspect.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationProspect are not directly saved in db
        em.detach(updatedApplicationProspect);
        updatedApplicationProspect
            .fullName(UPDATED_FULL_NAME)
            .mobileNo(UPDATED_MOBILE_NO)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD)
            .confirmPassword(UPDATED_CONFIRM_PASSWORD);
        ApplicationProspectDTO applicationProspectDTO = applicationProspectMapper.toDto(updatedApplicationProspect);

        restApplicationProspectMockMvc.perform(put("/api/application-prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationProspectDTO)))
            .andExpect(status().isOk());

        // Validate the ApplicationProspect in the database
        List<ApplicationProspect> applicationProspectList = applicationProspectRepository.findAll();
        assertThat(applicationProspectList).hasSize(databaseSizeBeforeUpdate);
        ApplicationProspect testApplicationProspect = applicationProspectList.get(applicationProspectList.size() - 1);
        assertThat(testApplicationProspect.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testApplicationProspect.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testApplicationProspect.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testApplicationProspect.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testApplicationProspect.getConfirmPassword()).isEqualTo(UPDATED_CONFIRM_PASSWORD);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationProspect() throws Exception {
        int databaseSizeBeforeUpdate = applicationProspectRepository.findAll().size();

        // Create the ApplicationProspect
        ApplicationProspectDTO applicationProspectDTO = applicationProspectMapper.toDto(applicationProspect);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationProspectMockMvc.perform(put("/api/application-prospects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationProspectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationProspect in the database
        List<ApplicationProspect> applicationProspectList = applicationProspectRepository.findAll();
        assertThat(applicationProspectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicationProspect() throws Exception {
        // Initialize the database
        applicationProspectRepository.saveAndFlush(applicationProspect);

        int databaseSizeBeforeDelete = applicationProspectRepository.findAll().size();

        // Get the applicationProspect
        restApplicationProspectMockMvc.perform(delete("/api/application-prospects/{id}", applicationProspect.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ApplicationProspect> applicationProspectList = applicationProspectRepository.findAll();
        assertThat(applicationProspectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationProspect.class);
        ApplicationProspect applicationProspect1 = new ApplicationProspect();
        applicationProspect1.setId(1L);
        ApplicationProspect applicationProspect2 = new ApplicationProspect();
        applicationProspect2.setId(applicationProspect1.getId());
        assertThat(applicationProspect1).isEqualTo(applicationProspect2);
        applicationProspect2.setId(2L);
        assertThat(applicationProspect1).isNotEqualTo(applicationProspect2);
        applicationProspect1.setId(null);
        assertThat(applicationProspect1).isNotEqualTo(applicationProspect2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationProspectDTO.class);
        ApplicationProspectDTO applicationProspectDTO1 = new ApplicationProspectDTO();
        applicationProspectDTO1.setId(1L);
        ApplicationProspectDTO applicationProspectDTO2 = new ApplicationProspectDTO();
        assertThat(applicationProspectDTO1).isNotEqualTo(applicationProspectDTO2);
        applicationProspectDTO2.setId(applicationProspectDTO1.getId());
        assertThat(applicationProspectDTO1).isEqualTo(applicationProspectDTO2);
        applicationProspectDTO2.setId(2L);
        assertThat(applicationProspectDTO1).isNotEqualTo(applicationProspectDTO2);
        applicationProspectDTO1.setId(null);
        assertThat(applicationProspectDTO1).isNotEqualTo(applicationProspectDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(applicationProspectMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(applicationProspectMapper.fromId(null)).isNull();
    }
}
