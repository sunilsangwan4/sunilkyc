package com.eagle.kyc.web.rest;

import com.eagle.kyc.Kyc5App;

import com.eagle.kyc.domain.PersonalInformation;
import com.eagle.kyc.domain.ApplicationProspect;
import com.eagle.kyc.repository.PersonalInformationRepository;
import com.eagle.kyc.service.PersonalInformationService;
import com.eagle.kyc.service.dto.PersonalInformationDTO;
import com.eagle.kyc.service.mapper.PersonalInformationMapper;
import com.eagle.kyc.web.rest.errors.ExceptionTranslator;
import com.eagle.kyc.service.dto.PersonalInformationCriteria;
import com.eagle.kyc.service.PersonalInformationQueryService;

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
 * Test class for the PersonalInformationResource REST controller.
 *
 * @see PersonalInformationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Kyc5App.class)
public class PersonalInformationResourceIntTest {

    private static final String DEFAULT_FATHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FATHER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MOTHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MOTHER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final String DEFAULT_MARITAL_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_MARITAL_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_INDIAN_TAX_PAYER = "AAAAAAAAAA";
    private static final String UPDATED_INDIAN_TAX_PAYER = "BBBBBBBBBB";

    private static final String DEFAULT_BIRTH_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_BIRTH_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_BIRTH_CITY = "AAAAAAAAAA";
    private static final String UPDATED_BIRTH_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_JURISDICTION_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_JURISDICTION_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_IDENTIFICATION_NO = "AAAAAAAAAA";
    private static final String UPDATED_TAX_IDENTIFICATION_NO = "BBBBBBBBBB";

    @Autowired
    private PersonalInformationRepository personalInformationRepository;

    @Autowired
    private PersonalInformationMapper personalInformationMapper;
    
    @Autowired
    private PersonalInformationService personalInformationService;

    @Autowired
    private PersonalInformationQueryService personalInformationQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonalInformationMockMvc;

    private PersonalInformation personalInformation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonalInformationResource personalInformationResource = new PersonalInformationResource(personalInformationService, personalInformationQueryService);
        this.restPersonalInformationMockMvc = MockMvcBuilders.standaloneSetup(personalInformationResource)
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
    public static PersonalInformation createEntity(EntityManager em) {
        PersonalInformation personalInformation = new PersonalInformation()
            .fatherName(DEFAULT_FATHER_NAME)
            .motherName(DEFAULT_MOTHER_NAME)
            .gender(DEFAULT_GENDER)
            .nationality(DEFAULT_NATIONALITY)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .indianTaxPayer(DEFAULT_INDIAN_TAX_PAYER)
            .birthCountry(DEFAULT_BIRTH_COUNTRY)
            .birthCity(DEFAULT_BIRTH_CITY)
            .jurisdictionCountry(DEFAULT_JURISDICTION_COUNTRY)
            .taxIdentificationNo(DEFAULT_TAX_IDENTIFICATION_NO);
        return personalInformation;
    }

    @Before
    public void initTest() {
        personalInformation = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonalInformation() throws Exception {
        int databaseSizeBeforeCreate = personalInformationRepository.findAll().size();

        // Create the PersonalInformation
        PersonalInformationDTO personalInformationDTO = personalInformationMapper.toDto(personalInformation);
        restPersonalInformationMockMvc.perform(post("/api/personal-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInformationDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonalInformation in the database
        List<PersonalInformation> personalInformationList = personalInformationRepository.findAll();
        assertThat(personalInformationList).hasSize(databaseSizeBeforeCreate + 1);
        PersonalInformation testPersonalInformation = personalInformationList.get(personalInformationList.size() - 1);
        assertThat(testPersonalInformation.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testPersonalInformation.getMotherName()).isEqualTo(DEFAULT_MOTHER_NAME);
        assertThat(testPersonalInformation.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPersonalInformation.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testPersonalInformation.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testPersonalInformation.getIndianTaxPayer()).isEqualTo(DEFAULT_INDIAN_TAX_PAYER);
        assertThat(testPersonalInformation.getBirthCountry()).isEqualTo(DEFAULT_BIRTH_COUNTRY);
        assertThat(testPersonalInformation.getBirthCity()).isEqualTo(DEFAULT_BIRTH_CITY);
        assertThat(testPersonalInformation.getJurisdictionCountry()).isEqualTo(DEFAULT_JURISDICTION_COUNTRY);
        assertThat(testPersonalInformation.getTaxIdentificationNo()).isEqualTo(DEFAULT_TAX_IDENTIFICATION_NO);
    }

    @Test
    @Transactional
    public void createPersonalInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personalInformationRepository.findAll().size();

        // Create the PersonalInformation with an existing ID
        personalInformation.setId(1L);
        PersonalInformationDTO personalInformationDTO = personalInformationMapper.toDto(personalInformation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonalInformationMockMvc.perform(post("/api/personal-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonalInformation in the database
        List<PersonalInformation> personalInformationList = personalInformationRepository.findAll();
        assertThat(personalInformationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFatherNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInformationRepository.findAll().size();
        // set the field null
        personalInformation.setFatherName(null);

        // Create the PersonalInformation, which fails.
        PersonalInformationDTO personalInformationDTO = personalInformationMapper.toDto(personalInformation);

        restPersonalInformationMockMvc.perform(post("/api/personal-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInformationDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalInformation> personalInformationList = personalInformationRepository.findAll();
        assertThat(personalInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMotherNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInformationRepository.findAll().size();
        // set the field null
        personalInformation.setMotherName(null);

        // Create the PersonalInformation, which fails.
        PersonalInformationDTO personalInformationDTO = personalInformationMapper.toDto(personalInformation);

        restPersonalInformationMockMvc.perform(post("/api/personal-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInformationDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalInformation> personalInformationList = personalInformationRepository.findAll();
        assertThat(personalInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInformationRepository.findAll().size();
        // set the field null
        personalInformation.setGender(null);

        // Create the PersonalInformation, which fails.
        PersonalInformationDTO personalInformationDTO = personalInformationMapper.toDto(personalInformation);

        restPersonalInformationMockMvc.perform(post("/api/personal-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInformationDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalInformation> personalInformationList = personalInformationRepository.findAll();
        assertThat(personalInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNationalityIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInformationRepository.findAll().size();
        // set the field null
        personalInformation.setNationality(null);

        // Create the PersonalInformation, which fails.
        PersonalInformationDTO personalInformationDTO = personalInformationMapper.toDto(personalInformation);

        restPersonalInformationMockMvc.perform(post("/api/personal-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInformationDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalInformation> personalInformationList = personalInformationRepository.findAll();
        assertThat(personalInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaritalStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInformationRepository.findAll().size();
        // set the field null
        personalInformation.setMaritalStatus(null);

        // Create the PersonalInformation, which fails.
        PersonalInformationDTO personalInformationDTO = personalInformationMapper.toDto(personalInformation);

        restPersonalInformationMockMvc.perform(post("/api/personal-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInformationDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalInformation> personalInformationList = personalInformationRepository.findAll();
        assertThat(personalInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIndianTaxPayerIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInformationRepository.findAll().size();
        // set the field null
        personalInformation.setIndianTaxPayer(null);

        // Create the PersonalInformation, which fails.
        PersonalInformationDTO personalInformationDTO = personalInformationMapper.toDto(personalInformation);

        restPersonalInformationMockMvc.perform(post("/api/personal-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInformationDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalInformation> personalInformationList = personalInformationRepository.findAll();
        assertThat(personalInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirthCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInformationRepository.findAll().size();
        // set the field null
        personalInformation.setBirthCountry(null);

        // Create the PersonalInformation, which fails.
        PersonalInformationDTO personalInformationDTO = personalInformationMapper.toDto(personalInformation);

        restPersonalInformationMockMvc.perform(post("/api/personal-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInformationDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalInformation> personalInformationList = personalInformationRepository.findAll();
        assertThat(personalInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirthCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInformationRepository.findAll().size();
        // set the field null
        personalInformation.setBirthCity(null);

        // Create the PersonalInformation, which fails.
        PersonalInformationDTO personalInformationDTO = personalInformationMapper.toDto(personalInformation);

        restPersonalInformationMockMvc.perform(post("/api/personal-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInformationDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalInformation> personalInformationList = personalInformationRepository.findAll();
        assertThat(personalInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJurisdictionCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInformationRepository.findAll().size();
        // set the field null
        personalInformation.setJurisdictionCountry(null);

        // Create the PersonalInformation, which fails.
        PersonalInformationDTO personalInformationDTO = personalInformationMapper.toDto(personalInformation);

        restPersonalInformationMockMvc.perform(post("/api/personal-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInformationDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalInformation> personalInformationList = personalInformationRepository.findAll();
        assertThat(personalInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxIdentificationNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInformationRepository.findAll().size();
        // set the field null
        personalInformation.setTaxIdentificationNo(null);

        // Create the PersonalInformation, which fails.
        PersonalInformationDTO personalInformationDTO = personalInformationMapper.toDto(personalInformation);

        restPersonalInformationMockMvc.perform(post("/api/personal-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInformationDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalInformation> personalInformationList = personalInformationRepository.findAll();
        assertThat(personalInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonalInformations() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList
        restPersonalInformationMockMvc.perform(get("/api/personal-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personalInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME.toString())))
            .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY.toString())))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].indianTaxPayer").value(hasItem(DEFAULT_INDIAN_TAX_PAYER.toString())))
            .andExpect(jsonPath("$.[*].birthCountry").value(hasItem(DEFAULT_BIRTH_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].birthCity").value(hasItem(DEFAULT_BIRTH_CITY.toString())))
            .andExpect(jsonPath("$.[*].jurisdictionCountry").value(hasItem(DEFAULT_JURISDICTION_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].taxIdentificationNo").value(hasItem(DEFAULT_TAX_IDENTIFICATION_NO.toString())));
    }
    
    @Test
    @Transactional
    public void getPersonalInformation() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get the personalInformation
        restPersonalInformationMockMvc.perform(get("/api/personal-informations/{id}", personalInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personalInformation.getId().intValue()))
            .andExpect(jsonPath("$.fatherName").value(DEFAULT_FATHER_NAME.toString()))
            .andExpect(jsonPath("$.motherName").value(DEFAULT_MOTHER_NAME.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY.toString()))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()))
            .andExpect(jsonPath("$.indianTaxPayer").value(DEFAULT_INDIAN_TAX_PAYER.toString()))
            .andExpect(jsonPath("$.birthCountry").value(DEFAULT_BIRTH_COUNTRY.toString()))
            .andExpect(jsonPath("$.birthCity").value(DEFAULT_BIRTH_CITY.toString()))
            .andExpect(jsonPath("$.jurisdictionCountry").value(DEFAULT_JURISDICTION_COUNTRY.toString()))
            .andExpect(jsonPath("$.taxIdentificationNo").value(DEFAULT_TAX_IDENTIFICATION_NO.toString()));
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByFatherNameIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where fatherName equals to DEFAULT_FATHER_NAME
        defaultPersonalInformationShouldBeFound("fatherName.equals=" + DEFAULT_FATHER_NAME);

        // Get all the personalInformationList where fatherName equals to UPDATED_FATHER_NAME
        defaultPersonalInformationShouldNotBeFound("fatherName.equals=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByFatherNameIsInShouldWork() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where fatherName in DEFAULT_FATHER_NAME or UPDATED_FATHER_NAME
        defaultPersonalInformationShouldBeFound("fatherName.in=" + DEFAULT_FATHER_NAME + "," + UPDATED_FATHER_NAME);

        // Get all the personalInformationList where fatherName equals to UPDATED_FATHER_NAME
        defaultPersonalInformationShouldNotBeFound("fatherName.in=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByFatherNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where fatherName is not null
        defaultPersonalInformationShouldBeFound("fatherName.specified=true");

        // Get all the personalInformationList where fatherName is null
        defaultPersonalInformationShouldNotBeFound("fatherName.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByMotherNameIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where motherName equals to DEFAULT_MOTHER_NAME
        defaultPersonalInformationShouldBeFound("motherName.equals=" + DEFAULT_MOTHER_NAME);

        // Get all the personalInformationList where motherName equals to UPDATED_MOTHER_NAME
        defaultPersonalInformationShouldNotBeFound("motherName.equals=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByMotherNameIsInShouldWork() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where motherName in DEFAULT_MOTHER_NAME or UPDATED_MOTHER_NAME
        defaultPersonalInformationShouldBeFound("motherName.in=" + DEFAULT_MOTHER_NAME + "," + UPDATED_MOTHER_NAME);

        // Get all the personalInformationList where motherName equals to UPDATED_MOTHER_NAME
        defaultPersonalInformationShouldNotBeFound("motherName.in=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByMotherNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where motherName is not null
        defaultPersonalInformationShouldBeFound("motherName.specified=true");

        // Get all the personalInformationList where motherName is null
        defaultPersonalInformationShouldNotBeFound("motherName.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where gender equals to DEFAULT_GENDER
        defaultPersonalInformationShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the personalInformationList where gender equals to UPDATED_GENDER
        defaultPersonalInformationShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultPersonalInformationShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the personalInformationList where gender equals to UPDATED_GENDER
        defaultPersonalInformationShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where gender is not null
        defaultPersonalInformationShouldBeFound("gender.specified=true");

        // Get all the personalInformationList where gender is null
        defaultPersonalInformationShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByNationalityIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where nationality equals to DEFAULT_NATIONALITY
        defaultPersonalInformationShouldBeFound("nationality.equals=" + DEFAULT_NATIONALITY);

        // Get all the personalInformationList where nationality equals to UPDATED_NATIONALITY
        defaultPersonalInformationShouldNotBeFound("nationality.equals=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByNationalityIsInShouldWork() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where nationality in DEFAULT_NATIONALITY or UPDATED_NATIONALITY
        defaultPersonalInformationShouldBeFound("nationality.in=" + DEFAULT_NATIONALITY + "," + UPDATED_NATIONALITY);

        // Get all the personalInformationList where nationality equals to UPDATED_NATIONALITY
        defaultPersonalInformationShouldNotBeFound("nationality.in=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByNationalityIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where nationality is not null
        defaultPersonalInformationShouldBeFound("nationality.specified=true");

        // Get all the personalInformationList where nationality is null
        defaultPersonalInformationShouldNotBeFound("nationality.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByMaritalStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where maritalStatus equals to DEFAULT_MARITAL_STATUS
        defaultPersonalInformationShouldBeFound("maritalStatus.equals=" + DEFAULT_MARITAL_STATUS);

        // Get all the personalInformationList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultPersonalInformationShouldNotBeFound("maritalStatus.equals=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByMaritalStatusIsInShouldWork() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where maritalStatus in DEFAULT_MARITAL_STATUS or UPDATED_MARITAL_STATUS
        defaultPersonalInformationShouldBeFound("maritalStatus.in=" + DEFAULT_MARITAL_STATUS + "," + UPDATED_MARITAL_STATUS);

        // Get all the personalInformationList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultPersonalInformationShouldNotBeFound("maritalStatus.in=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByMaritalStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where maritalStatus is not null
        defaultPersonalInformationShouldBeFound("maritalStatus.specified=true");

        // Get all the personalInformationList where maritalStatus is null
        defaultPersonalInformationShouldNotBeFound("maritalStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByIndianTaxPayerIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where indianTaxPayer equals to DEFAULT_INDIAN_TAX_PAYER
        defaultPersonalInformationShouldBeFound("indianTaxPayer.equals=" + DEFAULT_INDIAN_TAX_PAYER);

        // Get all the personalInformationList where indianTaxPayer equals to UPDATED_INDIAN_TAX_PAYER
        defaultPersonalInformationShouldNotBeFound("indianTaxPayer.equals=" + UPDATED_INDIAN_TAX_PAYER);
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByIndianTaxPayerIsInShouldWork() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where indianTaxPayer in DEFAULT_INDIAN_TAX_PAYER or UPDATED_INDIAN_TAX_PAYER
        defaultPersonalInformationShouldBeFound("indianTaxPayer.in=" + DEFAULT_INDIAN_TAX_PAYER + "," + UPDATED_INDIAN_TAX_PAYER);

        // Get all the personalInformationList where indianTaxPayer equals to UPDATED_INDIAN_TAX_PAYER
        defaultPersonalInformationShouldNotBeFound("indianTaxPayer.in=" + UPDATED_INDIAN_TAX_PAYER);
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByIndianTaxPayerIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where indianTaxPayer is not null
        defaultPersonalInformationShouldBeFound("indianTaxPayer.specified=true");

        // Get all the personalInformationList where indianTaxPayer is null
        defaultPersonalInformationShouldNotBeFound("indianTaxPayer.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByBirthCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where birthCountry equals to DEFAULT_BIRTH_COUNTRY
        defaultPersonalInformationShouldBeFound("birthCountry.equals=" + DEFAULT_BIRTH_COUNTRY);

        // Get all the personalInformationList where birthCountry equals to UPDATED_BIRTH_COUNTRY
        defaultPersonalInformationShouldNotBeFound("birthCountry.equals=" + UPDATED_BIRTH_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByBirthCountryIsInShouldWork() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where birthCountry in DEFAULT_BIRTH_COUNTRY or UPDATED_BIRTH_COUNTRY
        defaultPersonalInformationShouldBeFound("birthCountry.in=" + DEFAULT_BIRTH_COUNTRY + "," + UPDATED_BIRTH_COUNTRY);

        // Get all the personalInformationList where birthCountry equals to UPDATED_BIRTH_COUNTRY
        defaultPersonalInformationShouldNotBeFound("birthCountry.in=" + UPDATED_BIRTH_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByBirthCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where birthCountry is not null
        defaultPersonalInformationShouldBeFound("birthCountry.specified=true");

        // Get all the personalInformationList where birthCountry is null
        defaultPersonalInformationShouldNotBeFound("birthCountry.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByBirthCityIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where birthCity equals to DEFAULT_BIRTH_CITY
        defaultPersonalInformationShouldBeFound("birthCity.equals=" + DEFAULT_BIRTH_CITY);

        // Get all the personalInformationList where birthCity equals to UPDATED_BIRTH_CITY
        defaultPersonalInformationShouldNotBeFound("birthCity.equals=" + UPDATED_BIRTH_CITY);
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByBirthCityIsInShouldWork() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where birthCity in DEFAULT_BIRTH_CITY or UPDATED_BIRTH_CITY
        defaultPersonalInformationShouldBeFound("birthCity.in=" + DEFAULT_BIRTH_CITY + "," + UPDATED_BIRTH_CITY);

        // Get all the personalInformationList where birthCity equals to UPDATED_BIRTH_CITY
        defaultPersonalInformationShouldNotBeFound("birthCity.in=" + UPDATED_BIRTH_CITY);
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByBirthCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where birthCity is not null
        defaultPersonalInformationShouldBeFound("birthCity.specified=true");

        // Get all the personalInformationList where birthCity is null
        defaultPersonalInformationShouldNotBeFound("birthCity.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByJurisdictionCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where jurisdictionCountry equals to DEFAULT_JURISDICTION_COUNTRY
        defaultPersonalInformationShouldBeFound("jurisdictionCountry.equals=" + DEFAULT_JURISDICTION_COUNTRY);

        // Get all the personalInformationList where jurisdictionCountry equals to UPDATED_JURISDICTION_COUNTRY
        defaultPersonalInformationShouldNotBeFound("jurisdictionCountry.equals=" + UPDATED_JURISDICTION_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByJurisdictionCountryIsInShouldWork() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where jurisdictionCountry in DEFAULT_JURISDICTION_COUNTRY or UPDATED_JURISDICTION_COUNTRY
        defaultPersonalInformationShouldBeFound("jurisdictionCountry.in=" + DEFAULT_JURISDICTION_COUNTRY + "," + UPDATED_JURISDICTION_COUNTRY);

        // Get all the personalInformationList where jurisdictionCountry equals to UPDATED_JURISDICTION_COUNTRY
        defaultPersonalInformationShouldNotBeFound("jurisdictionCountry.in=" + UPDATED_JURISDICTION_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByJurisdictionCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where jurisdictionCountry is not null
        defaultPersonalInformationShouldBeFound("jurisdictionCountry.specified=true");

        // Get all the personalInformationList where jurisdictionCountry is null
        defaultPersonalInformationShouldNotBeFound("jurisdictionCountry.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByTaxIdentificationNoIsEqualToSomething() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where taxIdentificationNo equals to DEFAULT_TAX_IDENTIFICATION_NO
        defaultPersonalInformationShouldBeFound("taxIdentificationNo.equals=" + DEFAULT_TAX_IDENTIFICATION_NO);

        // Get all the personalInformationList where taxIdentificationNo equals to UPDATED_TAX_IDENTIFICATION_NO
        defaultPersonalInformationShouldNotBeFound("taxIdentificationNo.equals=" + UPDATED_TAX_IDENTIFICATION_NO);
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByTaxIdentificationNoIsInShouldWork() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where taxIdentificationNo in DEFAULT_TAX_IDENTIFICATION_NO or UPDATED_TAX_IDENTIFICATION_NO
        defaultPersonalInformationShouldBeFound("taxIdentificationNo.in=" + DEFAULT_TAX_IDENTIFICATION_NO + "," + UPDATED_TAX_IDENTIFICATION_NO);

        // Get all the personalInformationList where taxIdentificationNo equals to UPDATED_TAX_IDENTIFICATION_NO
        defaultPersonalInformationShouldNotBeFound("taxIdentificationNo.in=" + UPDATED_TAX_IDENTIFICATION_NO);
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByTaxIdentificationNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        // Get all the personalInformationList where taxIdentificationNo is not null
        defaultPersonalInformationShouldBeFound("taxIdentificationNo.specified=true");

        // Get all the personalInformationList where taxIdentificationNo is null
        defaultPersonalInformationShouldNotBeFound("taxIdentificationNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPersonalInformationsByApplicationProspectIsEqualToSomething() throws Exception {
        // Initialize the database
        ApplicationProspect applicationProspect = ApplicationProspectResourceIntTest.createEntity(em);
        em.persist(applicationProspect);
        em.flush();
        personalInformation.setApplicationProspect(applicationProspect);
        applicationProspect.setPersonalInformation(personalInformation);
        personalInformationRepository.saveAndFlush(personalInformation);
        Long applicationProspectId = applicationProspect.getId();

        // Get all the personalInformationList where applicationProspect equals to applicationProspectId
        defaultPersonalInformationShouldBeFound("applicationProspectId.equals=" + applicationProspectId);

        // Get all the personalInformationList where applicationProspect equals to applicationProspectId + 1
        defaultPersonalInformationShouldNotBeFound("applicationProspectId.equals=" + (applicationProspectId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPersonalInformationShouldBeFound(String filter) throws Exception {
        restPersonalInformationMockMvc.perform(get("/api/personal-informations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personalInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME.toString())))
            .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY.toString())))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].indianTaxPayer").value(hasItem(DEFAULT_INDIAN_TAX_PAYER.toString())))
            .andExpect(jsonPath("$.[*].birthCountry").value(hasItem(DEFAULT_BIRTH_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].birthCity").value(hasItem(DEFAULT_BIRTH_CITY.toString())))
            .andExpect(jsonPath("$.[*].jurisdictionCountry").value(hasItem(DEFAULT_JURISDICTION_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].taxIdentificationNo").value(hasItem(DEFAULT_TAX_IDENTIFICATION_NO.toString())));

        // Check, that the count call also returns 1
        restPersonalInformationMockMvc.perform(get("/api/personal-informations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPersonalInformationShouldNotBeFound(String filter) throws Exception {
        restPersonalInformationMockMvc.perform(get("/api/personal-informations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPersonalInformationMockMvc.perform(get("/api/personal-informations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPersonalInformation() throws Exception {
        // Get the personalInformation
        restPersonalInformationMockMvc.perform(get("/api/personal-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonalInformation() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        int databaseSizeBeforeUpdate = personalInformationRepository.findAll().size();

        // Update the personalInformation
        PersonalInformation updatedPersonalInformation = personalInformationRepository.findById(personalInformation.getId()).get();
        // Disconnect from session so that the updates on updatedPersonalInformation are not directly saved in db
        em.detach(updatedPersonalInformation);
        updatedPersonalInformation
            .fatherName(UPDATED_FATHER_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .gender(UPDATED_GENDER)
            .nationality(UPDATED_NATIONALITY)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .indianTaxPayer(UPDATED_INDIAN_TAX_PAYER)
            .birthCountry(UPDATED_BIRTH_COUNTRY)
            .birthCity(UPDATED_BIRTH_CITY)
            .jurisdictionCountry(UPDATED_JURISDICTION_COUNTRY)
            .taxIdentificationNo(UPDATED_TAX_IDENTIFICATION_NO);
        PersonalInformationDTO personalInformationDTO = personalInformationMapper.toDto(updatedPersonalInformation);

        restPersonalInformationMockMvc.perform(put("/api/personal-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInformationDTO)))
            .andExpect(status().isOk());

        // Validate the PersonalInformation in the database
        List<PersonalInformation> personalInformationList = personalInformationRepository.findAll();
        assertThat(personalInformationList).hasSize(databaseSizeBeforeUpdate);
        PersonalInformation testPersonalInformation = personalInformationList.get(personalInformationList.size() - 1);
        assertThat(testPersonalInformation.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testPersonalInformation.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testPersonalInformation.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPersonalInformation.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testPersonalInformation.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testPersonalInformation.getIndianTaxPayer()).isEqualTo(UPDATED_INDIAN_TAX_PAYER);
        assertThat(testPersonalInformation.getBirthCountry()).isEqualTo(UPDATED_BIRTH_COUNTRY);
        assertThat(testPersonalInformation.getBirthCity()).isEqualTo(UPDATED_BIRTH_CITY);
        assertThat(testPersonalInformation.getJurisdictionCountry()).isEqualTo(UPDATED_JURISDICTION_COUNTRY);
        assertThat(testPersonalInformation.getTaxIdentificationNo()).isEqualTo(UPDATED_TAX_IDENTIFICATION_NO);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonalInformation() throws Exception {
        int databaseSizeBeforeUpdate = personalInformationRepository.findAll().size();

        // Create the PersonalInformation
        PersonalInformationDTO personalInformationDTO = personalInformationMapper.toDto(personalInformation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonalInformationMockMvc.perform(put("/api/personal-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonalInformation in the database
        List<PersonalInformation> personalInformationList = personalInformationRepository.findAll();
        assertThat(personalInformationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonalInformation() throws Exception {
        // Initialize the database
        personalInformationRepository.saveAndFlush(personalInformation);

        int databaseSizeBeforeDelete = personalInformationRepository.findAll().size();

        // Get the personalInformation
        restPersonalInformationMockMvc.perform(delete("/api/personal-informations/{id}", personalInformation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonalInformation> personalInformationList = personalInformationRepository.findAll();
        assertThat(personalInformationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonalInformation.class);
        PersonalInformation personalInformation1 = new PersonalInformation();
        personalInformation1.setId(1L);
        PersonalInformation personalInformation2 = new PersonalInformation();
        personalInformation2.setId(personalInformation1.getId());
        assertThat(personalInformation1).isEqualTo(personalInformation2);
        personalInformation2.setId(2L);
        assertThat(personalInformation1).isNotEqualTo(personalInformation2);
        personalInformation1.setId(null);
        assertThat(personalInformation1).isNotEqualTo(personalInformation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonalInformationDTO.class);
        PersonalInformationDTO personalInformationDTO1 = new PersonalInformationDTO();
        personalInformationDTO1.setId(1L);
        PersonalInformationDTO personalInformationDTO2 = new PersonalInformationDTO();
        assertThat(personalInformationDTO1).isNotEqualTo(personalInformationDTO2);
        personalInformationDTO2.setId(personalInformationDTO1.getId());
        assertThat(personalInformationDTO1).isEqualTo(personalInformationDTO2);
        personalInformationDTO2.setId(2L);
        assertThat(personalInformationDTO1).isNotEqualTo(personalInformationDTO2);
        personalInformationDTO1.setId(null);
        assertThat(personalInformationDTO1).isNotEqualTo(personalInformationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(personalInformationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(personalInformationMapper.fromId(null)).isNull();
    }
}
