package com.eagle.kyc.web.rest;

import com.eagle.kyc.Kyc5App;

import com.eagle.kyc.domain.BankInformation;
import com.eagle.kyc.domain.ApplicationProspect;
import com.eagle.kyc.repository.BankInformationRepository;
import com.eagle.kyc.service.BankInformationService;
import com.eagle.kyc.service.dto.BankInformationDTO;
import com.eagle.kyc.service.mapper.BankInformationMapper;
import com.eagle.kyc.web.rest.errors.ExceptionTranslator;
import com.eagle.kyc.service.dto.BankInformationCriteria;
import com.eagle.kyc.service.BankInformationQueryService;

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
 * Test class for the BankInformationResource REST controller.
 *
 * @see BankInformationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Kyc5App.class)
public class BankInformationResourceIntTest {

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IFSC_CODE = "AAAAAAAAAAA";
    private static final String UPDATED_IFSC_CODE = "BBBBBBBBBBB";

    private static final String DEFAULT_MICR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MICR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_HOLDER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_HOLDER_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BANK_ACCOUNT_COMMON = false;
    private static final Boolean UPDATED_BANK_ACCOUNT_COMMON = true;

    private static final String DEFAULT_SEGEMENT_TYPE_CD = "AAAAAAAAAA";
    private static final String UPDATED_SEGEMENT_TYPE_CD = "BBBBBBBBBB";

    @Autowired
    private BankInformationRepository bankInformationRepository;

    @Autowired
    private BankInformationMapper bankInformationMapper;
    
    @Autowired
    private BankInformationService bankInformationService;

    @Autowired
    private BankInformationQueryService bankInformationQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBankInformationMockMvc;

    private BankInformation bankInformation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BankInformationResource bankInformationResource = new BankInformationResource(bankInformationService, bankInformationQueryService);
        this.restBankInformationMockMvc = MockMvcBuilders.standaloneSetup(bankInformationResource)
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
    public static BankInformation createEntity(EntityManager em) {
        BankInformation bankInformation = new BankInformation()
            .bankName(DEFAULT_BANK_NAME)
            .ifscCode(DEFAULT_IFSC_CODE)
            .micrCode(DEFAULT_MICR_CODE)
            .branchName(DEFAULT_BRANCH_NAME)
            .accountType(DEFAULT_ACCOUNT_TYPE)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .accountHolderName(DEFAULT_ACCOUNT_HOLDER_NAME)
            .bankAccountCommon(DEFAULT_BANK_ACCOUNT_COMMON)
            .segementTypeCd(DEFAULT_SEGEMENT_TYPE_CD);
        return bankInformation;
    }

    @Before
    public void initTest() {
        bankInformation = createEntity(em);
    }

    @Test
    @Transactional
    public void createBankInformation() throws Exception {
        int databaseSizeBeforeCreate = bankInformationRepository.findAll().size();

        // Create the BankInformation
        BankInformationDTO bankInformationDTO = bankInformationMapper.toDto(bankInformation);
        restBankInformationMockMvc.perform(post("/api/bank-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankInformationDTO)))
            .andExpect(status().isCreated());

        // Validate the BankInformation in the database
        List<BankInformation> bankInformationList = bankInformationRepository.findAll();
        assertThat(bankInformationList).hasSize(databaseSizeBeforeCreate + 1);
        BankInformation testBankInformation = bankInformationList.get(bankInformationList.size() - 1);
        assertThat(testBankInformation.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testBankInformation.getIfscCode()).isEqualTo(DEFAULT_IFSC_CODE);
        assertThat(testBankInformation.getMicrCode()).isEqualTo(DEFAULT_MICR_CODE);
        assertThat(testBankInformation.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testBankInformation.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testBankInformation.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testBankInformation.getAccountHolderName()).isEqualTo(DEFAULT_ACCOUNT_HOLDER_NAME);
        assertThat(testBankInformation.isBankAccountCommon()).isEqualTo(DEFAULT_BANK_ACCOUNT_COMMON);
        assertThat(testBankInformation.getSegementTypeCd()).isEqualTo(DEFAULT_SEGEMENT_TYPE_CD);
    }

    @Test
    @Transactional
    public void createBankInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bankInformationRepository.findAll().size();

        // Create the BankInformation with an existing ID
        bankInformation.setId(1L);
        BankInformationDTO bankInformationDTO = bankInformationMapper.toDto(bankInformation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankInformationMockMvc.perform(post("/api/bank-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BankInformation in the database
        List<BankInformation> bankInformationList = bankInformationRepository.findAll();
        assertThat(bankInformationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBankNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankInformationRepository.findAll().size();
        // set the field null
        bankInformation.setBankName(null);

        // Create the BankInformation, which fails.
        BankInformationDTO bankInformationDTO = bankInformationMapper.toDto(bankInformation);

        restBankInformationMockMvc.perform(post("/api/bank-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankInformationDTO)))
            .andExpect(status().isBadRequest());

        List<BankInformation> bankInformationList = bankInformationRepository.findAll();
        assertThat(bankInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIfscCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankInformationRepository.findAll().size();
        // set the field null
        bankInformation.setIfscCode(null);

        // Create the BankInformation, which fails.
        BankInformationDTO bankInformationDTO = bankInformationMapper.toDto(bankInformation);

        restBankInformationMockMvc.perform(post("/api/bank-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankInformationDTO)))
            .andExpect(status().isBadRequest());

        List<BankInformation> bankInformationList = bankInformationRepository.findAll();
        assertThat(bankInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMicrCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankInformationRepository.findAll().size();
        // set the field null
        bankInformation.setMicrCode(null);

        // Create the BankInformation, which fails.
        BankInformationDTO bankInformationDTO = bankInformationMapper.toDto(bankInformation);

        restBankInformationMockMvc.perform(post("/api/bank-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankInformationDTO)))
            .andExpect(status().isBadRequest());

        List<BankInformation> bankInformationList = bankInformationRepository.findAll();
        assertThat(bankInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBranchNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankInformationRepository.findAll().size();
        // set the field null
        bankInformation.setBranchName(null);

        // Create the BankInformation, which fails.
        BankInformationDTO bankInformationDTO = bankInformationMapper.toDto(bankInformation);

        restBankInformationMockMvc.perform(post("/api/bank-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankInformationDTO)))
            .andExpect(status().isBadRequest());

        List<BankInformation> bankInformationList = bankInformationRepository.findAll();
        assertThat(bankInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankInformationRepository.findAll().size();
        // set the field null
        bankInformation.setAccountType(null);

        // Create the BankInformation, which fails.
        BankInformationDTO bankInformationDTO = bankInformationMapper.toDto(bankInformation);

        restBankInformationMockMvc.perform(post("/api/bank-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankInformationDTO)))
            .andExpect(status().isBadRequest());

        List<BankInformation> bankInformationList = bankInformationRepository.findAll();
        assertThat(bankInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankInformationRepository.findAll().size();
        // set the field null
        bankInformation.setAccountNumber(null);

        // Create the BankInformation, which fails.
        BankInformationDTO bankInformationDTO = bankInformationMapper.toDto(bankInformation);

        restBankInformationMockMvc.perform(post("/api/bank-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankInformationDTO)))
            .andExpect(status().isBadRequest());

        List<BankInformation> bankInformationList = bankInformationRepository.findAll();
        assertThat(bankInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountHolderNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankInformationRepository.findAll().size();
        // set the field null
        bankInformation.setAccountHolderName(null);

        // Create the BankInformation, which fails.
        BankInformationDTO bankInformationDTO = bankInformationMapper.toDto(bankInformation);

        restBankInformationMockMvc.perform(post("/api/bank-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankInformationDTO)))
            .andExpect(status().isBadRequest());

        List<BankInformation> bankInformationList = bankInformationRepository.findAll();
        assertThat(bankInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBankInformations() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList
        restBankInformationMockMvc.perform(get("/api/bank-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME.toString())))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE.toString())))
            .andExpect(jsonPath("$.[*].micrCode").value(hasItem(DEFAULT_MICR_CODE.toString())))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME.toString())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].accountHolderName").value(hasItem(DEFAULT_ACCOUNT_HOLDER_NAME.toString())))
            .andExpect(jsonPath("$.[*].bankAccountCommon").value(hasItem(DEFAULT_BANK_ACCOUNT_COMMON.booleanValue())))
            .andExpect(jsonPath("$.[*].segementTypeCd").value(hasItem(DEFAULT_SEGEMENT_TYPE_CD.toString())));
    }
    
    @Test
    @Transactional
    public void getBankInformation() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get the bankInformation
        restBankInformationMockMvc.perform(get("/api/bank-informations/{id}", bankInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bankInformation.getId().intValue()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME.toString()))
            .andExpect(jsonPath("$.ifscCode").value(DEFAULT_IFSC_CODE.toString()))
            .andExpect(jsonPath("$.micrCode").value(DEFAULT_MICR_CODE.toString()))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME.toString()))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.accountHolderName").value(DEFAULT_ACCOUNT_HOLDER_NAME.toString()))
            .andExpect(jsonPath("$.bankAccountCommon").value(DEFAULT_BANK_ACCOUNT_COMMON.booleanValue()))
            .andExpect(jsonPath("$.segementTypeCd").value(DEFAULT_SEGEMENT_TYPE_CD.toString()));
    }

    @Test
    @Transactional
    public void getAllBankInformationsByBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where bankName equals to DEFAULT_BANK_NAME
        defaultBankInformationShouldBeFound("bankName.equals=" + DEFAULT_BANK_NAME);

        // Get all the bankInformationList where bankName equals to UPDATED_BANK_NAME
        defaultBankInformationShouldNotBeFound("bankName.equals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllBankInformationsByBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where bankName in DEFAULT_BANK_NAME or UPDATED_BANK_NAME
        defaultBankInformationShouldBeFound("bankName.in=" + DEFAULT_BANK_NAME + "," + UPDATED_BANK_NAME);

        // Get all the bankInformationList where bankName equals to UPDATED_BANK_NAME
        defaultBankInformationShouldNotBeFound("bankName.in=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllBankInformationsByBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where bankName is not null
        defaultBankInformationShouldBeFound("bankName.specified=true");

        // Get all the bankInformationList where bankName is null
        defaultBankInformationShouldNotBeFound("bankName.specified=false");
    }

    @Test
    @Transactional
    public void getAllBankInformationsByIfscCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where ifscCode equals to DEFAULT_IFSC_CODE
        defaultBankInformationShouldBeFound("ifscCode.equals=" + DEFAULT_IFSC_CODE);

        // Get all the bankInformationList where ifscCode equals to UPDATED_IFSC_CODE
        defaultBankInformationShouldNotBeFound("ifscCode.equals=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    public void getAllBankInformationsByIfscCodeIsInShouldWork() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where ifscCode in DEFAULT_IFSC_CODE or UPDATED_IFSC_CODE
        defaultBankInformationShouldBeFound("ifscCode.in=" + DEFAULT_IFSC_CODE + "," + UPDATED_IFSC_CODE);

        // Get all the bankInformationList where ifscCode equals to UPDATED_IFSC_CODE
        defaultBankInformationShouldNotBeFound("ifscCode.in=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    public void getAllBankInformationsByIfscCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where ifscCode is not null
        defaultBankInformationShouldBeFound("ifscCode.specified=true");

        // Get all the bankInformationList where ifscCode is null
        defaultBankInformationShouldNotBeFound("ifscCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllBankInformationsByMicrCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where micrCode equals to DEFAULT_MICR_CODE
        defaultBankInformationShouldBeFound("micrCode.equals=" + DEFAULT_MICR_CODE);

        // Get all the bankInformationList where micrCode equals to UPDATED_MICR_CODE
        defaultBankInformationShouldNotBeFound("micrCode.equals=" + UPDATED_MICR_CODE);
    }

    @Test
    @Transactional
    public void getAllBankInformationsByMicrCodeIsInShouldWork() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where micrCode in DEFAULT_MICR_CODE or UPDATED_MICR_CODE
        defaultBankInformationShouldBeFound("micrCode.in=" + DEFAULT_MICR_CODE + "," + UPDATED_MICR_CODE);

        // Get all the bankInformationList where micrCode equals to UPDATED_MICR_CODE
        defaultBankInformationShouldNotBeFound("micrCode.in=" + UPDATED_MICR_CODE);
    }

    @Test
    @Transactional
    public void getAllBankInformationsByMicrCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where micrCode is not null
        defaultBankInformationShouldBeFound("micrCode.specified=true");

        // Get all the bankInformationList where micrCode is null
        defaultBankInformationShouldNotBeFound("micrCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllBankInformationsByBranchNameIsEqualToSomething() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where branchName equals to DEFAULT_BRANCH_NAME
        defaultBankInformationShouldBeFound("branchName.equals=" + DEFAULT_BRANCH_NAME);

        // Get all the bankInformationList where branchName equals to UPDATED_BRANCH_NAME
        defaultBankInformationShouldNotBeFound("branchName.equals=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    public void getAllBankInformationsByBranchNameIsInShouldWork() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where branchName in DEFAULT_BRANCH_NAME or UPDATED_BRANCH_NAME
        defaultBankInformationShouldBeFound("branchName.in=" + DEFAULT_BRANCH_NAME + "," + UPDATED_BRANCH_NAME);

        // Get all the bankInformationList where branchName equals to UPDATED_BRANCH_NAME
        defaultBankInformationShouldNotBeFound("branchName.in=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    public void getAllBankInformationsByBranchNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where branchName is not null
        defaultBankInformationShouldBeFound("branchName.specified=true");

        // Get all the bankInformationList where branchName is null
        defaultBankInformationShouldNotBeFound("branchName.specified=false");
    }

    @Test
    @Transactional
    public void getAllBankInformationsByAccountTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where accountType equals to DEFAULT_ACCOUNT_TYPE
        defaultBankInformationShouldBeFound("accountType.equals=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the bankInformationList where accountType equals to UPDATED_ACCOUNT_TYPE
        defaultBankInformationShouldNotBeFound("accountType.equals=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    public void getAllBankInformationsByAccountTypeIsInShouldWork() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where accountType in DEFAULT_ACCOUNT_TYPE or UPDATED_ACCOUNT_TYPE
        defaultBankInformationShouldBeFound("accountType.in=" + DEFAULT_ACCOUNT_TYPE + "," + UPDATED_ACCOUNT_TYPE);

        // Get all the bankInformationList where accountType equals to UPDATED_ACCOUNT_TYPE
        defaultBankInformationShouldNotBeFound("accountType.in=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    public void getAllBankInformationsByAccountTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where accountType is not null
        defaultBankInformationShouldBeFound("accountType.specified=true");

        // Get all the bankInformationList where accountType is null
        defaultBankInformationShouldNotBeFound("accountType.specified=false");
    }

    @Test
    @Transactional
    public void getAllBankInformationsByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where accountNumber equals to DEFAULT_ACCOUNT_NUMBER
        defaultBankInformationShouldBeFound("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the bankInformationList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultBankInformationShouldNotBeFound("accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllBankInformationsByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where accountNumber in DEFAULT_ACCOUNT_NUMBER or UPDATED_ACCOUNT_NUMBER
        defaultBankInformationShouldBeFound("accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER);

        // Get all the bankInformationList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultBankInformationShouldNotBeFound("accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllBankInformationsByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where accountNumber is not null
        defaultBankInformationShouldBeFound("accountNumber.specified=true");

        // Get all the bankInformationList where accountNumber is null
        defaultBankInformationShouldNotBeFound("accountNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllBankInformationsByAccountHolderNameIsEqualToSomething() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where accountHolderName equals to DEFAULT_ACCOUNT_HOLDER_NAME
        defaultBankInformationShouldBeFound("accountHolderName.equals=" + DEFAULT_ACCOUNT_HOLDER_NAME);

        // Get all the bankInformationList where accountHolderName equals to UPDATED_ACCOUNT_HOLDER_NAME
        defaultBankInformationShouldNotBeFound("accountHolderName.equals=" + UPDATED_ACCOUNT_HOLDER_NAME);
    }

    @Test
    @Transactional
    public void getAllBankInformationsByAccountHolderNameIsInShouldWork() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where accountHolderName in DEFAULT_ACCOUNT_HOLDER_NAME or UPDATED_ACCOUNT_HOLDER_NAME
        defaultBankInformationShouldBeFound("accountHolderName.in=" + DEFAULT_ACCOUNT_HOLDER_NAME + "," + UPDATED_ACCOUNT_HOLDER_NAME);

        // Get all the bankInformationList where accountHolderName equals to UPDATED_ACCOUNT_HOLDER_NAME
        defaultBankInformationShouldNotBeFound("accountHolderName.in=" + UPDATED_ACCOUNT_HOLDER_NAME);
    }

    @Test
    @Transactional
    public void getAllBankInformationsByAccountHolderNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where accountHolderName is not null
        defaultBankInformationShouldBeFound("accountHolderName.specified=true");

        // Get all the bankInformationList where accountHolderName is null
        defaultBankInformationShouldNotBeFound("accountHolderName.specified=false");
    }

    @Test
    @Transactional
    public void getAllBankInformationsByBankAccountCommonIsEqualToSomething() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where bankAccountCommon equals to DEFAULT_BANK_ACCOUNT_COMMON
        defaultBankInformationShouldBeFound("bankAccountCommon.equals=" + DEFAULT_BANK_ACCOUNT_COMMON);

        // Get all the bankInformationList where bankAccountCommon equals to UPDATED_BANK_ACCOUNT_COMMON
        defaultBankInformationShouldNotBeFound("bankAccountCommon.equals=" + UPDATED_BANK_ACCOUNT_COMMON);
    }

    @Test
    @Transactional
    public void getAllBankInformationsByBankAccountCommonIsInShouldWork() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where bankAccountCommon in DEFAULT_BANK_ACCOUNT_COMMON or UPDATED_BANK_ACCOUNT_COMMON
        defaultBankInformationShouldBeFound("bankAccountCommon.in=" + DEFAULT_BANK_ACCOUNT_COMMON + "," + UPDATED_BANK_ACCOUNT_COMMON);

        // Get all the bankInformationList where bankAccountCommon equals to UPDATED_BANK_ACCOUNT_COMMON
        defaultBankInformationShouldNotBeFound("bankAccountCommon.in=" + UPDATED_BANK_ACCOUNT_COMMON);
    }

    @Test
    @Transactional
    public void getAllBankInformationsByBankAccountCommonIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where bankAccountCommon is not null
        defaultBankInformationShouldBeFound("bankAccountCommon.specified=true");

        // Get all the bankInformationList where bankAccountCommon is null
        defaultBankInformationShouldNotBeFound("bankAccountCommon.specified=false");
    }

    @Test
    @Transactional
    public void getAllBankInformationsBySegementTypeCdIsEqualToSomething() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where segementTypeCd equals to DEFAULT_SEGEMENT_TYPE_CD
        defaultBankInformationShouldBeFound("segementTypeCd.equals=" + DEFAULT_SEGEMENT_TYPE_CD);

        // Get all the bankInformationList where segementTypeCd equals to UPDATED_SEGEMENT_TYPE_CD
        defaultBankInformationShouldNotBeFound("segementTypeCd.equals=" + UPDATED_SEGEMENT_TYPE_CD);
    }

    @Test
    @Transactional
    public void getAllBankInformationsBySegementTypeCdIsInShouldWork() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where segementTypeCd in DEFAULT_SEGEMENT_TYPE_CD or UPDATED_SEGEMENT_TYPE_CD
        defaultBankInformationShouldBeFound("segementTypeCd.in=" + DEFAULT_SEGEMENT_TYPE_CD + "," + UPDATED_SEGEMENT_TYPE_CD);

        // Get all the bankInformationList where segementTypeCd equals to UPDATED_SEGEMENT_TYPE_CD
        defaultBankInformationShouldNotBeFound("segementTypeCd.in=" + UPDATED_SEGEMENT_TYPE_CD);
    }

    @Test
    @Transactional
    public void getAllBankInformationsBySegementTypeCdIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        // Get all the bankInformationList where segementTypeCd is not null
        defaultBankInformationShouldBeFound("segementTypeCd.specified=true");

        // Get all the bankInformationList where segementTypeCd is null
        defaultBankInformationShouldNotBeFound("segementTypeCd.specified=false");
    }

    @Test
    @Transactional
    public void getAllBankInformationsByApplicationProspectIsEqualToSomething() throws Exception {
        // Initialize the database
        ApplicationProspect applicationProspect = ApplicationProspectResourceIntTest.createEntity(em);
        em.persist(applicationProspect);
        em.flush();
        bankInformation.setApplicationProspect(applicationProspect);
        bankInformationRepository.saveAndFlush(bankInformation);
        Long applicationProspectId = applicationProspect.getId();

        // Get all the bankInformationList where applicationProspect equals to applicationProspectId
        defaultBankInformationShouldBeFound("applicationProspectId.equals=" + applicationProspectId);

        // Get all the bankInformationList where applicationProspect equals to applicationProspectId + 1
        defaultBankInformationShouldNotBeFound("applicationProspectId.equals=" + (applicationProspectId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultBankInformationShouldBeFound(String filter) throws Exception {
        restBankInformationMockMvc.perform(get("/api/bank-informations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME.toString())))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE.toString())))
            .andExpect(jsonPath("$.[*].micrCode").value(hasItem(DEFAULT_MICR_CODE.toString())))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME.toString())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].accountHolderName").value(hasItem(DEFAULT_ACCOUNT_HOLDER_NAME.toString())))
            .andExpect(jsonPath("$.[*].bankAccountCommon").value(hasItem(DEFAULT_BANK_ACCOUNT_COMMON.booleanValue())))
            .andExpect(jsonPath("$.[*].segementTypeCd").value(hasItem(DEFAULT_SEGEMENT_TYPE_CD.toString())));

        // Check, that the count call also returns 1
        restBankInformationMockMvc.perform(get("/api/bank-informations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultBankInformationShouldNotBeFound(String filter) throws Exception {
        restBankInformationMockMvc.perform(get("/api/bank-informations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBankInformationMockMvc.perform(get("/api/bank-informations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBankInformation() throws Exception {
        // Get the bankInformation
        restBankInformationMockMvc.perform(get("/api/bank-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBankInformation() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        int databaseSizeBeforeUpdate = bankInformationRepository.findAll().size();

        // Update the bankInformation
        BankInformation updatedBankInformation = bankInformationRepository.findById(bankInformation.getId()).get();
        // Disconnect from session so that the updates on updatedBankInformation are not directly saved in db
        em.detach(updatedBankInformation);
        updatedBankInformation
            .bankName(UPDATED_BANK_NAME)
            .ifscCode(UPDATED_IFSC_CODE)
            .micrCode(UPDATED_MICR_CODE)
            .branchName(UPDATED_BRANCH_NAME)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountHolderName(UPDATED_ACCOUNT_HOLDER_NAME)
            .bankAccountCommon(UPDATED_BANK_ACCOUNT_COMMON)
            .segementTypeCd(UPDATED_SEGEMENT_TYPE_CD);
        BankInformationDTO bankInformationDTO = bankInformationMapper.toDto(updatedBankInformation);

        restBankInformationMockMvc.perform(put("/api/bank-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankInformationDTO)))
            .andExpect(status().isOk());

        // Validate the BankInformation in the database
        List<BankInformation> bankInformationList = bankInformationRepository.findAll();
        assertThat(bankInformationList).hasSize(databaseSizeBeforeUpdate);
        BankInformation testBankInformation = bankInformationList.get(bankInformationList.size() - 1);
        assertThat(testBankInformation.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testBankInformation.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testBankInformation.getMicrCode()).isEqualTo(UPDATED_MICR_CODE);
        assertThat(testBankInformation.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testBankInformation.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testBankInformation.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testBankInformation.getAccountHolderName()).isEqualTo(UPDATED_ACCOUNT_HOLDER_NAME);
        assertThat(testBankInformation.isBankAccountCommon()).isEqualTo(UPDATED_BANK_ACCOUNT_COMMON);
        assertThat(testBankInformation.getSegementTypeCd()).isEqualTo(UPDATED_SEGEMENT_TYPE_CD);
    }

    @Test
    @Transactional
    public void updateNonExistingBankInformation() throws Exception {
        int databaseSizeBeforeUpdate = bankInformationRepository.findAll().size();

        // Create the BankInformation
        BankInformationDTO bankInformationDTO = bankInformationMapper.toDto(bankInformation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankInformationMockMvc.perform(put("/api/bank-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BankInformation in the database
        List<BankInformation> bankInformationList = bankInformationRepository.findAll();
        assertThat(bankInformationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBankInformation() throws Exception {
        // Initialize the database
        bankInformationRepository.saveAndFlush(bankInformation);

        int databaseSizeBeforeDelete = bankInformationRepository.findAll().size();

        // Get the bankInformation
        restBankInformationMockMvc.perform(delete("/api/bank-informations/{id}", bankInformation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BankInformation> bankInformationList = bankInformationRepository.findAll();
        assertThat(bankInformationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankInformation.class);
        BankInformation bankInformation1 = new BankInformation();
        bankInformation1.setId(1L);
        BankInformation bankInformation2 = new BankInformation();
        bankInformation2.setId(bankInformation1.getId());
        assertThat(bankInformation1).isEqualTo(bankInformation2);
        bankInformation2.setId(2L);
        assertThat(bankInformation1).isNotEqualTo(bankInformation2);
        bankInformation1.setId(null);
        assertThat(bankInformation1).isNotEqualTo(bankInformation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankInformationDTO.class);
        BankInformationDTO bankInformationDTO1 = new BankInformationDTO();
        bankInformationDTO1.setId(1L);
        BankInformationDTO bankInformationDTO2 = new BankInformationDTO();
        assertThat(bankInformationDTO1).isNotEqualTo(bankInformationDTO2);
        bankInformationDTO2.setId(bankInformationDTO1.getId());
        assertThat(bankInformationDTO1).isEqualTo(bankInformationDTO2);
        bankInformationDTO2.setId(2L);
        assertThat(bankInformationDTO1).isNotEqualTo(bankInformationDTO2);
        bankInformationDTO1.setId(null);
        assertThat(bankInformationDTO1).isNotEqualTo(bankInformationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bankInformationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bankInformationMapper.fromId(null)).isNull();
    }
}
