package com.eagle.kyc.web.rest;

import com.eagle.kyc.Kyc5App;

import com.eagle.kyc.domain.InvestmentPotential;
import com.eagle.kyc.domain.ApplicationProspect;
import com.eagle.kyc.repository.InvestmentPotentialRepository;
import com.eagle.kyc.service.InvestmentPotentialService;
import com.eagle.kyc.service.dto.InvestmentPotentialDTO;
import com.eagle.kyc.service.mapper.InvestmentPotentialMapper;
import com.eagle.kyc.web.rest.errors.ExceptionTranslator;
import com.eagle.kyc.service.dto.InvestmentPotentialCriteria;
import com.eagle.kyc.service.InvestmentPotentialQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.eagle.kyc.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InvestmentPotentialResource REST controller.
 *
 * @see InvestmentPotentialResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Kyc5App.class)
public class InvestmentPotentialResourceIntTest {

    private static final String DEFAULT_EDUCATION_QUALIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_EDUCATION_QUALIFICATION = "BBBBBBBBBB";

    private static final String DEFAULT_OCCUPATION = "AAAAAAAAAA";
    private static final String UPDATED_OCCUPATION = "BBBBBBBBBB";

    private static final Float DEFAULT_ANNUAL_INCOME = 1F;
    private static final Float UPDATED_ANNUAL_INCOME = 2F;

    private static final Double DEFAULT_NET_WORTH = 1D;
    private static final Double UPDATED_NET_WORTH = 2D;

    private static final LocalDate DEFAULT_NETWORTH_DECLARATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NETWORTH_DECLARATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PEP_RELATIVE = "AAAAAAAAAA";
    private static final String UPDATED_PEP_RELATIVE = "BBBBBBBBBB";

    private static final String DEFAULT_PMLA_RISK_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_PMLA_RISK_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_PMLA_RISK_CATEGORY_REASON = "AAAAAAAAAA";
    private static final String UPDATED_PMLA_RISK_CATEGORY_REASON = "BBBBBBBBBB";

    @Autowired
    private InvestmentPotentialRepository investmentPotentialRepository;

    @Autowired
    private InvestmentPotentialMapper investmentPotentialMapper;
    
    @Autowired
    private InvestmentPotentialService investmentPotentialService;

    @Autowired
    private InvestmentPotentialQueryService investmentPotentialQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInvestmentPotentialMockMvc;

    private InvestmentPotential investmentPotential;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvestmentPotentialResource investmentPotentialResource = new InvestmentPotentialResource(investmentPotentialService, investmentPotentialQueryService);
        this.restInvestmentPotentialMockMvc = MockMvcBuilders.standaloneSetup(investmentPotentialResource)
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
    public static InvestmentPotential createEntity(EntityManager em) {
        InvestmentPotential investmentPotential = new InvestmentPotential()
            .educationQualification(DEFAULT_EDUCATION_QUALIFICATION)
            .occupation(DEFAULT_OCCUPATION)
            .annualIncome(DEFAULT_ANNUAL_INCOME)
            .netWorth(DEFAULT_NET_WORTH)
            .networthDeclarationDate(DEFAULT_NETWORTH_DECLARATION_DATE)
            .pepRelative(DEFAULT_PEP_RELATIVE)
            .pmlaRiskCategory(DEFAULT_PMLA_RISK_CATEGORY)
            .pmlaRiskCategoryReason(DEFAULT_PMLA_RISK_CATEGORY_REASON);
        return investmentPotential;
    }

    @Before
    public void initTest() {
        investmentPotential = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvestmentPotential() throws Exception {
        int databaseSizeBeforeCreate = investmentPotentialRepository.findAll().size();

        // Create the InvestmentPotential
        InvestmentPotentialDTO investmentPotentialDTO = investmentPotentialMapper.toDto(investmentPotential);
        restInvestmentPotentialMockMvc.perform(post("/api/investment-potentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investmentPotentialDTO)))
            .andExpect(status().isCreated());

        // Validate the InvestmentPotential in the database
        List<InvestmentPotential> investmentPotentialList = investmentPotentialRepository.findAll();
        assertThat(investmentPotentialList).hasSize(databaseSizeBeforeCreate + 1);
        InvestmentPotential testInvestmentPotential = investmentPotentialList.get(investmentPotentialList.size() - 1);
        assertThat(testInvestmentPotential.getEducationQualification()).isEqualTo(DEFAULT_EDUCATION_QUALIFICATION);
        assertThat(testInvestmentPotential.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testInvestmentPotential.getAnnualIncome()).isEqualTo(DEFAULT_ANNUAL_INCOME);
        assertThat(testInvestmentPotential.getNetWorth()).isEqualTo(DEFAULT_NET_WORTH);
        assertThat(testInvestmentPotential.getNetworthDeclarationDate()).isEqualTo(DEFAULT_NETWORTH_DECLARATION_DATE);
        assertThat(testInvestmentPotential.getPepRelative()).isEqualTo(DEFAULT_PEP_RELATIVE);
        assertThat(testInvestmentPotential.getPmlaRiskCategory()).isEqualTo(DEFAULT_PMLA_RISK_CATEGORY);
        assertThat(testInvestmentPotential.getPmlaRiskCategoryReason()).isEqualTo(DEFAULT_PMLA_RISK_CATEGORY_REASON);
    }

    @Test
    @Transactional
    public void createInvestmentPotentialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = investmentPotentialRepository.findAll().size();

        // Create the InvestmentPotential with an existing ID
        investmentPotential.setId(1L);
        InvestmentPotentialDTO investmentPotentialDTO = investmentPotentialMapper.toDto(investmentPotential);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvestmentPotentialMockMvc.perform(post("/api/investment-potentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investmentPotentialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InvestmentPotential in the database
        List<InvestmentPotential> investmentPotentialList = investmentPotentialRepository.findAll();
        assertThat(investmentPotentialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEducationQualificationIsRequired() throws Exception {
        int databaseSizeBeforeTest = investmentPotentialRepository.findAll().size();
        // set the field null
        investmentPotential.setEducationQualification(null);

        // Create the InvestmentPotential, which fails.
        InvestmentPotentialDTO investmentPotentialDTO = investmentPotentialMapper.toDto(investmentPotential);

        restInvestmentPotentialMockMvc.perform(post("/api/investment-potentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investmentPotentialDTO)))
            .andExpect(status().isBadRequest());

        List<InvestmentPotential> investmentPotentialList = investmentPotentialRepository.findAll();
        assertThat(investmentPotentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOccupationIsRequired() throws Exception {
        int databaseSizeBeforeTest = investmentPotentialRepository.findAll().size();
        // set the field null
        investmentPotential.setOccupation(null);

        // Create the InvestmentPotential, which fails.
        InvestmentPotentialDTO investmentPotentialDTO = investmentPotentialMapper.toDto(investmentPotential);

        restInvestmentPotentialMockMvc.perform(post("/api/investment-potentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investmentPotentialDTO)))
            .andExpect(status().isBadRequest());

        List<InvestmentPotential> investmentPotentialList = investmentPotentialRepository.findAll();
        assertThat(investmentPotentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNetWorthIsRequired() throws Exception {
        int databaseSizeBeforeTest = investmentPotentialRepository.findAll().size();
        // set the field null
        investmentPotential.setNetWorth(null);

        // Create the InvestmentPotential, which fails.
        InvestmentPotentialDTO investmentPotentialDTO = investmentPotentialMapper.toDto(investmentPotential);

        restInvestmentPotentialMockMvc.perform(post("/api/investment-potentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investmentPotentialDTO)))
            .andExpect(status().isBadRequest());

        List<InvestmentPotential> investmentPotentialList = investmentPotentialRepository.findAll();
        assertThat(investmentPotentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNetworthDeclarationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = investmentPotentialRepository.findAll().size();
        // set the field null
        investmentPotential.setNetworthDeclarationDate(null);

        // Create the InvestmentPotential, which fails.
        InvestmentPotentialDTO investmentPotentialDTO = investmentPotentialMapper.toDto(investmentPotential);

        restInvestmentPotentialMockMvc.perform(post("/api/investment-potentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investmentPotentialDTO)))
            .andExpect(status().isBadRequest());

        List<InvestmentPotential> investmentPotentialList = investmentPotentialRepository.findAll();
        assertThat(investmentPotentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPepRelativeIsRequired() throws Exception {
        int databaseSizeBeforeTest = investmentPotentialRepository.findAll().size();
        // set the field null
        investmentPotential.setPepRelative(null);

        // Create the InvestmentPotential, which fails.
        InvestmentPotentialDTO investmentPotentialDTO = investmentPotentialMapper.toDto(investmentPotential);

        restInvestmentPotentialMockMvc.perform(post("/api/investment-potentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investmentPotentialDTO)))
            .andExpect(status().isBadRequest());

        List<InvestmentPotential> investmentPotentialList = investmentPotentialRepository.findAll();
        assertThat(investmentPotentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPmlaRiskCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = investmentPotentialRepository.findAll().size();
        // set the field null
        investmentPotential.setPmlaRiskCategory(null);

        // Create the InvestmentPotential, which fails.
        InvestmentPotentialDTO investmentPotentialDTO = investmentPotentialMapper.toDto(investmentPotential);

        restInvestmentPotentialMockMvc.perform(post("/api/investment-potentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investmentPotentialDTO)))
            .andExpect(status().isBadRequest());

        List<InvestmentPotential> investmentPotentialList = investmentPotentialRepository.findAll();
        assertThat(investmentPotentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPmlaRiskCategoryReasonIsRequired() throws Exception {
        int databaseSizeBeforeTest = investmentPotentialRepository.findAll().size();
        // set the field null
        investmentPotential.setPmlaRiskCategoryReason(null);

        // Create the InvestmentPotential, which fails.
        InvestmentPotentialDTO investmentPotentialDTO = investmentPotentialMapper.toDto(investmentPotential);

        restInvestmentPotentialMockMvc.perform(post("/api/investment-potentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investmentPotentialDTO)))
            .andExpect(status().isBadRequest());

        List<InvestmentPotential> investmentPotentialList = investmentPotentialRepository.findAll();
        assertThat(investmentPotentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentials() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList
        restInvestmentPotentialMockMvc.perform(get("/api/investment-potentials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(investmentPotential.getId().intValue())))
            .andExpect(jsonPath("$.[*].educationQualification").value(hasItem(DEFAULT_EDUCATION_QUALIFICATION.toString())))
            .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION.toString())))
            .andExpect(jsonPath("$.[*].annualIncome").value(hasItem(DEFAULT_ANNUAL_INCOME.doubleValue())))
            .andExpect(jsonPath("$.[*].netWorth").value(hasItem(DEFAULT_NET_WORTH.doubleValue())))
            .andExpect(jsonPath("$.[*].networthDeclarationDate").value(hasItem(DEFAULT_NETWORTH_DECLARATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].pepRelative").value(hasItem(DEFAULT_PEP_RELATIVE.toString())))
            .andExpect(jsonPath("$.[*].pmlaRiskCategory").value(hasItem(DEFAULT_PMLA_RISK_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].pmlaRiskCategoryReason").value(hasItem(DEFAULT_PMLA_RISK_CATEGORY_REASON.toString())));
    }
    
    @Test
    @Transactional
    public void getInvestmentPotential() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get the investmentPotential
        restInvestmentPotentialMockMvc.perform(get("/api/investment-potentials/{id}", investmentPotential.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(investmentPotential.getId().intValue()))
            .andExpect(jsonPath("$.educationQualification").value(DEFAULT_EDUCATION_QUALIFICATION.toString()))
            .andExpect(jsonPath("$.occupation").value(DEFAULT_OCCUPATION.toString()))
            .andExpect(jsonPath("$.annualIncome").value(DEFAULT_ANNUAL_INCOME.doubleValue()))
            .andExpect(jsonPath("$.netWorth").value(DEFAULT_NET_WORTH.doubleValue()))
            .andExpect(jsonPath("$.networthDeclarationDate").value(DEFAULT_NETWORTH_DECLARATION_DATE.toString()))
            .andExpect(jsonPath("$.pepRelative").value(DEFAULT_PEP_RELATIVE.toString()))
            .andExpect(jsonPath("$.pmlaRiskCategory").value(DEFAULT_PMLA_RISK_CATEGORY.toString()))
            .andExpect(jsonPath("$.pmlaRiskCategoryReason").value(DEFAULT_PMLA_RISK_CATEGORY_REASON.toString()));
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByEducationQualificationIsEqualToSomething() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where educationQualification equals to DEFAULT_EDUCATION_QUALIFICATION
        defaultInvestmentPotentialShouldBeFound("educationQualification.equals=" + DEFAULT_EDUCATION_QUALIFICATION);

        // Get all the investmentPotentialList where educationQualification equals to UPDATED_EDUCATION_QUALIFICATION
        defaultInvestmentPotentialShouldNotBeFound("educationQualification.equals=" + UPDATED_EDUCATION_QUALIFICATION);
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByEducationQualificationIsInShouldWork() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where educationQualification in DEFAULT_EDUCATION_QUALIFICATION or UPDATED_EDUCATION_QUALIFICATION
        defaultInvestmentPotentialShouldBeFound("educationQualification.in=" + DEFAULT_EDUCATION_QUALIFICATION + "," + UPDATED_EDUCATION_QUALIFICATION);

        // Get all the investmentPotentialList where educationQualification equals to UPDATED_EDUCATION_QUALIFICATION
        defaultInvestmentPotentialShouldNotBeFound("educationQualification.in=" + UPDATED_EDUCATION_QUALIFICATION);
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByEducationQualificationIsNullOrNotNull() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where educationQualification is not null
        defaultInvestmentPotentialShouldBeFound("educationQualification.specified=true");

        // Get all the investmentPotentialList where educationQualification is null
        defaultInvestmentPotentialShouldNotBeFound("educationQualification.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByOccupationIsEqualToSomething() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where occupation equals to DEFAULT_OCCUPATION
        defaultInvestmentPotentialShouldBeFound("occupation.equals=" + DEFAULT_OCCUPATION);

        // Get all the investmentPotentialList where occupation equals to UPDATED_OCCUPATION
        defaultInvestmentPotentialShouldNotBeFound("occupation.equals=" + UPDATED_OCCUPATION);
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByOccupationIsInShouldWork() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where occupation in DEFAULT_OCCUPATION or UPDATED_OCCUPATION
        defaultInvestmentPotentialShouldBeFound("occupation.in=" + DEFAULT_OCCUPATION + "," + UPDATED_OCCUPATION);

        // Get all the investmentPotentialList where occupation equals to UPDATED_OCCUPATION
        defaultInvestmentPotentialShouldNotBeFound("occupation.in=" + UPDATED_OCCUPATION);
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByOccupationIsNullOrNotNull() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where occupation is not null
        defaultInvestmentPotentialShouldBeFound("occupation.specified=true");

        // Get all the investmentPotentialList where occupation is null
        defaultInvestmentPotentialShouldNotBeFound("occupation.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByAnnualIncomeIsEqualToSomething() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where annualIncome equals to DEFAULT_ANNUAL_INCOME
        defaultInvestmentPotentialShouldBeFound("annualIncome.equals=" + DEFAULT_ANNUAL_INCOME);

        // Get all the investmentPotentialList where annualIncome equals to UPDATED_ANNUAL_INCOME
        defaultInvestmentPotentialShouldNotBeFound("annualIncome.equals=" + UPDATED_ANNUAL_INCOME);
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByAnnualIncomeIsInShouldWork() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where annualIncome in DEFAULT_ANNUAL_INCOME or UPDATED_ANNUAL_INCOME
        defaultInvestmentPotentialShouldBeFound("annualIncome.in=" + DEFAULT_ANNUAL_INCOME + "," + UPDATED_ANNUAL_INCOME);

        // Get all the investmentPotentialList where annualIncome equals to UPDATED_ANNUAL_INCOME
        defaultInvestmentPotentialShouldNotBeFound("annualIncome.in=" + UPDATED_ANNUAL_INCOME);
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByAnnualIncomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where annualIncome is not null
        defaultInvestmentPotentialShouldBeFound("annualIncome.specified=true");

        // Get all the investmentPotentialList where annualIncome is null
        defaultInvestmentPotentialShouldNotBeFound("annualIncome.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByNetWorthIsEqualToSomething() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where netWorth equals to DEFAULT_NET_WORTH
        defaultInvestmentPotentialShouldBeFound("netWorth.equals=" + DEFAULT_NET_WORTH);

        // Get all the investmentPotentialList where netWorth equals to UPDATED_NET_WORTH
        defaultInvestmentPotentialShouldNotBeFound("netWorth.equals=" + UPDATED_NET_WORTH);
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByNetWorthIsInShouldWork() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where netWorth in DEFAULT_NET_WORTH or UPDATED_NET_WORTH
        defaultInvestmentPotentialShouldBeFound("netWorth.in=" + DEFAULT_NET_WORTH + "," + UPDATED_NET_WORTH);

        // Get all the investmentPotentialList where netWorth equals to UPDATED_NET_WORTH
        defaultInvestmentPotentialShouldNotBeFound("netWorth.in=" + UPDATED_NET_WORTH);
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByNetWorthIsNullOrNotNull() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where netWorth is not null
        defaultInvestmentPotentialShouldBeFound("netWorth.specified=true");

        // Get all the investmentPotentialList where netWorth is null
        defaultInvestmentPotentialShouldNotBeFound("netWorth.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByNetworthDeclarationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where networthDeclarationDate equals to DEFAULT_NETWORTH_DECLARATION_DATE
        defaultInvestmentPotentialShouldBeFound("networthDeclarationDate.equals=" + DEFAULT_NETWORTH_DECLARATION_DATE);

        // Get all the investmentPotentialList where networthDeclarationDate equals to UPDATED_NETWORTH_DECLARATION_DATE
        defaultInvestmentPotentialShouldNotBeFound("networthDeclarationDate.equals=" + UPDATED_NETWORTH_DECLARATION_DATE);
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByNetworthDeclarationDateIsInShouldWork() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where networthDeclarationDate in DEFAULT_NETWORTH_DECLARATION_DATE or UPDATED_NETWORTH_DECLARATION_DATE
        defaultInvestmentPotentialShouldBeFound("networthDeclarationDate.in=" + DEFAULT_NETWORTH_DECLARATION_DATE + "," + UPDATED_NETWORTH_DECLARATION_DATE);

        // Get all the investmentPotentialList where networthDeclarationDate equals to UPDATED_NETWORTH_DECLARATION_DATE
        defaultInvestmentPotentialShouldNotBeFound("networthDeclarationDate.in=" + UPDATED_NETWORTH_DECLARATION_DATE);
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByNetworthDeclarationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where networthDeclarationDate is not null
        defaultInvestmentPotentialShouldBeFound("networthDeclarationDate.specified=true");

        // Get all the investmentPotentialList where networthDeclarationDate is null
        defaultInvestmentPotentialShouldNotBeFound("networthDeclarationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByNetworthDeclarationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where networthDeclarationDate greater than or equals to DEFAULT_NETWORTH_DECLARATION_DATE
        defaultInvestmentPotentialShouldBeFound("networthDeclarationDate.greaterOrEqualThan=" + DEFAULT_NETWORTH_DECLARATION_DATE);

        // Get all the investmentPotentialList where networthDeclarationDate greater than or equals to UPDATED_NETWORTH_DECLARATION_DATE
        defaultInvestmentPotentialShouldNotBeFound("networthDeclarationDate.greaterOrEqualThan=" + UPDATED_NETWORTH_DECLARATION_DATE);
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByNetworthDeclarationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where networthDeclarationDate less than or equals to DEFAULT_NETWORTH_DECLARATION_DATE
        defaultInvestmentPotentialShouldNotBeFound("networthDeclarationDate.lessThan=" + DEFAULT_NETWORTH_DECLARATION_DATE);

        // Get all the investmentPotentialList where networthDeclarationDate less than or equals to UPDATED_NETWORTH_DECLARATION_DATE
        defaultInvestmentPotentialShouldBeFound("networthDeclarationDate.lessThan=" + UPDATED_NETWORTH_DECLARATION_DATE);
    }


    @Test
    @Transactional
    public void getAllInvestmentPotentialsByPepRelativeIsEqualToSomething() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where pepRelative equals to DEFAULT_PEP_RELATIVE
        defaultInvestmentPotentialShouldBeFound("pepRelative.equals=" + DEFAULT_PEP_RELATIVE);

        // Get all the investmentPotentialList where pepRelative equals to UPDATED_PEP_RELATIVE
        defaultInvestmentPotentialShouldNotBeFound("pepRelative.equals=" + UPDATED_PEP_RELATIVE);
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByPepRelativeIsInShouldWork() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where pepRelative in DEFAULT_PEP_RELATIVE or UPDATED_PEP_RELATIVE
        defaultInvestmentPotentialShouldBeFound("pepRelative.in=" + DEFAULT_PEP_RELATIVE + "," + UPDATED_PEP_RELATIVE);

        // Get all the investmentPotentialList where pepRelative equals to UPDATED_PEP_RELATIVE
        defaultInvestmentPotentialShouldNotBeFound("pepRelative.in=" + UPDATED_PEP_RELATIVE);
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByPepRelativeIsNullOrNotNull() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where pepRelative is not null
        defaultInvestmentPotentialShouldBeFound("pepRelative.specified=true");

        // Get all the investmentPotentialList where pepRelative is null
        defaultInvestmentPotentialShouldNotBeFound("pepRelative.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByPmlaRiskCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where pmlaRiskCategory equals to DEFAULT_PMLA_RISK_CATEGORY
        defaultInvestmentPotentialShouldBeFound("pmlaRiskCategory.equals=" + DEFAULT_PMLA_RISK_CATEGORY);

        // Get all the investmentPotentialList where pmlaRiskCategory equals to UPDATED_PMLA_RISK_CATEGORY
        defaultInvestmentPotentialShouldNotBeFound("pmlaRiskCategory.equals=" + UPDATED_PMLA_RISK_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByPmlaRiskCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where pmlaRiskCategory in DEFAULT_PMLA_RISK_CATEGORY or UPDATED_PMLA_RISK_CATEGORY
        defaultInvestmentPotentialShouldBeFound("pmlaRiskCategory.in=" + DEFAULT_PMLA_RISK_CATEGORY + "," + UPDATED_PMLA_RISK_CATEGORY);

        // Get all the investmentPotentialList where pmlaRiskCategory equals to UPDATED_PMLA_RISK_CATEGORY
        defaultInvestmentPotentialShouldNotBeFound("pmlaRiskCategory.in=" + UPDATED_PMLA_RISK_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByPmlaRiskCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where pmlaRiskCategory is not null
        defaultInvestmentPotentialShouldBeFound("pmlaRiskCategory.specified=true");

        // Get all the investmentPotentialList where pmlaRiskCategory is null
        defaultInvestmentPotentialShouldNotBeFound("pmlaRiskCategory.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByPmlaRiskCategoryReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where pmlaRiskCategoryReason equals to DEFAULT_PMLA_RISK_CATEGORY_REASON
        defaultInvestmentPotentialShouldBeFound("pmlaRiskCategoryReason.equals=" + DEFAULT_PMLA_RISK_CATEGORY_REASON);

        // Get all the investmentPotentialList where pmlaRiskCategoryReason equals to UPDATED_PMLA_RISK_CATEGORY_REASON
        defaultInvestmentPotentialShouldNotBeFound("pmlaRiskCategoryReason.equals=" + UPDATED_PMLA_RISK_CATEGORY_REASON);
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByPmlaRiskCategoryReasonIsInShouldWork() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where pmlaRiskCategoryReason in DEFAULT_PMLA_RISK_CATEGORY_REASON or UPDATED_PMLA_RISK_CATEGORY_REASON
        defaultInvestmentPotentialShouldBeFound("pmlaRiskCategoryReason.in=" + DEFAULT_PMLA_RISK_CATEGORY_REASON + "," + UPDATED_PMLA_RISK_CATEGORY_REASON);

        // Get all the investmentPotentialList where pmlaRiskCategoryReason equals to UPDATED_PMLA_RISK_CATEGORY_REASON
        defaultInvestmentPotentialShouldNotBeFound("pmlaRiskCategoryReason.in=" + UPDATED_PMLA_RISK_CATEGORY_REASON);
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByPmlaRiskCategoryReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        // Get all the investmentPotentialList where pmlaRiskCategoryReason is not null
        defaultInvestmentPotentialShouldBeFound("pmlaRiskCategoryReason.specified=true");

        // Get all the investmentPotentialList where pmlaRiskCategoryReason is null
        defaultInvestmentPotentialShouldNotBeFound("pmlaRiskCategoryReason.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvestmentPotentialsByApplicationProspectIsEqualToSomething() throws Exception {
        // Initialize the database
        ApplicationProspect applicationProspect = ApplicationProspectResourceIntTest.createEntity(em);
        em.persist(applicationProspect);
        em.flush();
        investmentPotential.setApplicationProspect(applicationProspect);
        applicationProspect.setInvestmentPotential(investmentPotential);
        investmentPotentialRepository.saveAndFlush(investmentPotential);
        Long applicationProspectId = applicationProspect.getId();

        // Get all the investmentPotentialList where applicationProspect equals to applicationProspectId
        defaultInvestmentPotentialShouldBeFound("applicationProspectId.equals=" + applicationProspectId);

        // Get all the investmentPotentialList where applicationProspect equals to applicationProspectId + 1
        defaultInvestmentPotentialShouldNotBeFound("applicationProspectId.equals=" + (applicationProspectId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultInvestmentPotentialShouldBeFound(String filter) throws Exception {
        restInvestmentPotentialMockMvc.perform(get("/api/investment-potentials?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(investmentPotential.getId().intValue())))
            .andExpect(jsonPath("$.[*].educationQualification").value(hasItem(DEFAULT_EDUCATION_QUALIFICATION.toString())))
            .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION.toString())))
            .andExpect(jsonPath("$.[*].annualIncome").value(hasItem(DEFAULT_ANNUAL_INCOME.doubleValue())))
            .andExpect(jsonPath("$.[*].netWorth").value(hasItem(DEFAULT_NET_WORTH.doubleValue())))
            .andExpect(jsonPath("$.[*].networthDeclarationDate").value(hasItem(DEFAULT_NETWORTH_DECLARATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].pepRelative").value(hasItem(DEFAULT_PEP_RELATIVE.toString())))
            .andExpect(jsonPath("$.[*].pmlaRiskCategory").value(hasItem(DEFAULT_PMLA_RISK_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].pmlaRiskCategoryReason").value(hasItem(DEFAULT_PMLA_RISK_CATEGORY_REASON.toString())));

        // Check, that the count call also returns 1
        restInvestmentPotentialMockMvc.perform(get("/api/investment-potentials/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultInvestmentPotentialShouldNotBeFound(String filter) throws Exception {
        restInvestmentPotentialMockMvc.perform(get("/api/investment-potentials?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInvestmentPotentialMockMvc.perform(get("/api/investment-potentials/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingInvestmentPotential() throws Exception {
        // Get the investmentPotential
        restInvestmentPotentialMockMvc.perform(get("/api/investment-potentials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvestmentPotential() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        int databaseSizeBeforeUpdate = investmentPotentialRepository.findAll().size();

        // Update the investmentPotential
        InvestmentPotential updatedInvestmentPotential = investmentPotentialRepository.findById(investmentPotential.getId()).get();
        // Disconnect from session so that the updates on updatedInvestmentPotential are not directly saved in db
        em.detach(updatedInvestmentPotential);
        updatedInvestmentPotential
            .educationQualification(UPDATED_EDUCATION_QUALIFICATION)
            .occupation(UPDATED_OCCUPATION)
            .annualIncome(UPDATED_ANNUAL_INCOME)
            .netWorth(UPDATED_NET_WORTH)
            .networthDeclarationDate(UPDATED_NETWORTH_DECLARATION_DATE)
            .pepRelative(UPDATED_PEP_RELATIVE)
            .pmlaRiskCategory(UPDATED_PMLA_RISK_CATEGORY)
            .pmlaRiskCategoryReason(UPDATED_PMLA_RISK_CATEGORY_REASON);
        InvestmentPotentialDTO investmentPotentialDTO = investmentPotentialMapper.toDto(updatedInvestmentPotential);

        restInvestmentPotentialMockMvc.perform(put("/api/investment-potentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investmentPotentialDTO)))
            .andExpect(status().isOk());

        // Validate the InvestmentPotential in the database
        List<InvestmentPotential> investmentPotentialList = investmentPotentialRepository.findAll();
        assertThat(investmentPotentialList).hasSize(databaseSizeBeforeUpdate);
        InvestmentPotential testInvestmentPotential = investmentPotentialList.get(investmentPotentialList.size() - 1);
        assertThat(testInvestmentPotential.getEducationQualification()).isEqualTo(UPDATED_EDUCATION_QUALIFICATION);
        assertThat(testInvestmentPotential.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testInvestmentPotential.getAnnualIncome()).isEqualTo(UPDATED_ANNUAL_INCOME);
        assertThat(testInvestmentPotential.getNetWorth()).isEqualTo(UPDATED_NET_WORTH);
        assertThat(testInvestmentPotential.getNetworthDeclarationDate()).isEqualTo(UPDATED_NETWORTH_DECLARATION_DATE);
        assertThat(testInvestmentPotential.getPepRelative()).isEqualTo(UPDATED_PEP_RELATIVE);
        assertThat(testInvestmentPotential.getPmlaRiskCategory()).isEqualTo(UPDATED_PMLA_RISK_CATEGORY);
        assertThat(testInvestmentPotential.getPmlaRiskCategoryReason()).isEqualTo(UPDATED_PMLA_RISK_CATEGORY_REASON);
    }

    @Test
    @Transactional
    public void updateNonExistingInvestmentPotential() throws Exception {
        int databaseSizeBeforeUpdate = investmentPotentialRepository.findAll().size();

        // Create the InvestmentPotential
        InvestmentPotentialDTO investmentPotentialDTO = investmentPotentialMapper.toDto(investmentPotential);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvestmentPotentialMockMvc.perform(put("/api/investment-potentials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investmentPotentialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InvestmentPotential in the database
        List<InvestmentPotential> investmentPotentialList = investmentPotentialRepository.findAll();
        assertThat(investmentPotentialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvestmentPotential() throws Exception {
        // Initialize the database
        investmentPotentialRepository.saveAndFlush(investmentPotential);

        int databaseSizeBeforeDelete = investmentPotentialRepository.findAll().size();

        // Get the investmentPotential
        restInvestmentPotentialMockMvc.perform(delete("/api/investment-potentials/{id}", investmentPotential.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InvestmentPotential> investmentPotentialList = investmentPotentialRepository.findAll();
        assertThat(investmentPotentialList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvestmentPotential.class);
        InvestmentPotential investmentPotential1 = new InvestmentPotential();
        investmentPotential1.setId(1L);
        InvestmentPotential investmentPotential2 = new InvestmentPotential();
        investmentPotential2.setId(investmentPotential1.getId());
        assertThat(investmentPotential1).isEqualTo(investmentPotential2);
        investmentPotential2.setId(2L);
        assertThat(investmentPotential1).isNotEqualTo(investmentPotential2);
        investmentPotential1.setId(null);
        assertThat(investmentPotential1).isNotEqualTo(investmentPotential2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvestmentPotentialDTO.class);
        InvestmentPotentialDTO investmentPotentialDTO1 = new InvestmentPotentialDTO();
        investmentPotentialDTO1.setId(1L);
        InvestmentPotentialDTO investmentPotentialDTO2 = new InvestmentPotentialDTO();
        assertThat(investmentPotentialDTO1).isNotEqualTo(investmentPotentialDTO2);
        investmentPotentialDTO2.setId(investmentPotentialDTO1.getId());
        assertThat(investmentPotentialDTO1).isEqualTo(investmentPotentialDTO2);
        investmentPotentialDTO2.setId(2L);
        assertThat(investmentPotentialDTO1).isNotEqualTo(investmentPotentialDTO2);
        investmentPotentialDTO1.setId(null);
        assertThat(investmentPotentialDTO1).isNotEqualTo(investmentPotentialDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(investmentPotentialMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(investmentPotentialMapper.fromId(null)).isNull();
    }
}
