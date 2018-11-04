package com.eagle.kyc.web.rest;

import com.eagle.kyc.Kyc5App;

import com.eagle.kyc.domain.DepositoryInfo;
import com.eagle.kyc.domain.ApplicationProspect;
import com.eagle.kyc.repository.DepositoryInfoRepository;
import com.eagle.kyc.service.DepositoryInfoService;
import com.eagle.kyc.service.dto.DepositoryInfoDTO;
import com.eagle.kyc.service.mapper.DepositoryInfoMapper;
import com.eagle.kyc.web.rest.errors.ExceptionTranslator;
import com.eagle.kyc.service.dto.DepositoryInfoCriteria;
import com.eagle.kyc.service.DepositoryInfoQueryService;

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
 * Test class for the DepositoryInfoResource REST controller.
 *
 * @see DepositoryInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Kyc5App.class)
public class DepositoryInfoResourceIntTest {

    private static final Boolean DEFAULT_HAVE_ACCOUNT_WITH_OTHER_DP = false;
    private static final Boolean UPDATED_HAVE_ACCOUNT_WITH_OTHER_DP = true;

    private static final Boolean DEFAULT_HAVE_SMS_ENABLED = false;
    private static final Boolean UPDATED_HAVE_SMS_ENABLED = true;

    private static final String DEFAULT_STATEMENT_FREQUENCY = "AAAAAAAAAA";
    private static final String UPDATED_STATEMENT_FREQUENCY = "BBBBBBBBBB";

    private static final String DEFAULT_DP_SCHEME = "AAAAAAAAAA";
    private static final String UPDATED_DP_SCHEME = "BBBBBBBBBB";

    private static final String DEFAULT_DEPOSITORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEPOSITORY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BROKER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BROKER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_AS_PER_DEMAT = "AAAAAAAAAA";
    private static final String UPDATED_NAME_AS_PER_DEMAT = "BBBBBBBBBB";

    @Autowired
    private DepositoryInfoRepository depositoryInfoRepository;

    @Autowired
    private DepositoryInfoMapper depositoryInfoMapper;
    
    @Autowired
    private DepositoryInfoService depositoryInfoService;

    @Autowired
    private DepositoryInfoQueryService depositoryInfoQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDepositoryInfoMockMvc;

    private DepositoryInfo depositoryInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepositoryInfoResource depositoryInfoResource = new DepositoryInfoResource(depositoryInfoService, depositoryInfoQueryService);
        this.restDepositoryInfoMockMvc = MockMvcBuilders.standaloneSetup(depositoryInfoResource)
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
    public static DepositoryInfo createEntity(EntityManager em) {
        DepositoryInfo depositoryInfo = new DepositoryInfo()
            .haveAccountWithOtherDP(DEFAULT_HAVE_ACCOUNT_WITH_OTHER_DP)
            .haveSMSEnabled(DEFAULT_HAVE_SMS_ENABLED)
            .statementFrequency(DEFAULT_STATEMENT_FREQUENCY)
            .dpScheme(DEFAULT_DP_SCHEME)
            .depositoryName(DEFAULT_DEPOSITORY_NAME)
            .brokerName(DEFAULT_BROKER_NAME)
            .nameAsPerDemat(DEFAULT_NAME_AS_PER_DEMAT);
        return depositoryInfo;
    }

    @Before
    public void initTest() {
        depositoryInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepositoryInfo() throws Exception {
        int databaseSizeBeforeCreate = depositoryInfoRepository.findAll().size();

        // Create the DepositoryInfo
        DepositoryInfoDTO depositoryInfoDTO = depositoryInfoMapper.toDto(depositoryInfo);
        restDepositoryInfoMockMvc.perform(post("/api/depository-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositoryInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the DepositoryInfo in the database
        List<DepositoryInfo> depositoryInfoList = depositoryInfoRepository.findAll();
        assertThat(depositoryInfoList).hasSize(databaseSizeBeforeCreate + 1);
        DepositoryInfo testDepositoryInfo = depositoryInfoList.get(depositoryInfoList.size() - 1);
        assertThat(testDepositoryInfo.isHaveAccountWithOtherDP()).isEqualTo(DEFAULT_HAVE_ACCOUNT_WITH_OTHER_DP);
        assertThat(testDepositoryInfo.isHaveSMSEnabled()).isEqualTo(DEFAULT_HAVE_SMS_ENABLED);
        assertThat(testDepositoryInfo.getStatementFrequency()).isEqualTo(DEFAULT_STATEMENT_FREQUENCY);
        assertThat(testDepositoryInfo.getDpScheme()).isEqualTo(DEFAULT_DP_SCHEME);
        assertThat(testDepositoryInfo.getDepositoryName()).isEqualTo(DEFAULT_DEPOSITORY_NAME);
        assertThat(testDepositoryInfo.getBrokerName()).isEqualTo(DEFAULT_BROKER_NAME);
        assertThat(testDepositoryInfo.getNameAsPerDemat()).isEqualTo(DEFAULT_NAME_AS_PER_DEMAT);
    }

    @Test
    @Transactional
    public void createDepositoryInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = depositoryInfoRepository.findAll().size();

        // Create the DepositoryInfo with an existing ID
        depositoryInfo.setId(1L);
        DepositoryInfoDTO depositoryInfoDTO = depositoryInfoMapper.toDto(depositoryInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepositoryInfoMockMvc.perform(post("/api/depository-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositoryInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DepositoryInfo in the database
        List<DepositoryInfo> depositoryInfoList = depositoryInfoRepository.findAll();
        assertThat(depositoryInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkHaveAccountWithOtherDPIsRequired() throws Exception {
        int databaseSizeBeforeTest = depositoryInfoRepository.findAll().size();
        // set the field null
        depositoryInfo.setHaveAccountWithOtherDP(null);

        // Create the DepositoryInfo, which fails.
        DepositoryInfoDTO depositoryInfoDTO = depositoryInfoMapper.toDto(depositoryInfo);

        restDepositoryInfoMockMvc.perform(post("/api/depository-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositoryInfoDTO)))
            .andExpect(status().isBadRequest());

        List<DepositoryInfo> depositoryInfoList = depositoryInfoRepository.findAll();
        assertThat(depositoryInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHaveSMSEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = depositoryInfoRepository.findAll().size();
        // set the field null
        depositoryInfo.setHaveSMSEnabled(null);

        // Create the DepositoryInfo, which fails.
        DepositoryInfoDTO depositoryInfoDTO = depositoryInfoMapper.toDto(depositoryInfo);

        restDepositoryInfoMockMvc.perform(post("/api/depository-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositoryInfoDTO)))
            .andExpect(status().isBadRequest());

        List<DepositoryInfo> depositoryInfoList = depositoryInfoRepository.findAll();
        assertThat(depositoryInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatementFrequencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = depositoryInfoRepository.findAll().size();
        // set the field null
        depositoryInfo.setStatementFrequency(null);

        // Create the DepositoryInfo, which fails.
        DepositoryInfoDTO depositoryInfoDTO = depositoryInfoMapper.toDto(depositoryInfo);

        restDepositoryInfoMockMvc.perform(post("/api/depository-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositoryInfoDTO)))
            .andExpect(status().isBadRequest());

        List<DepositoryInfo> depositoryInfoList = depositoryInfoRepository.findAll();
        assertThat(depositoryInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDpSchemeIsRequired() throws Exception {
        int databaseSizeBeforeTest = depositoryInfoRepository.findAll().size();
        // set the field null
        depositoryInfo.setDpScheme(null);

        // Create the DepositoryInfo, which fails.
        DepositoryInfoDTO depositoryInfoDTO = depositoryInfoMapper.toDto(depositoryInfo);

        restDepositoryInfoMockMvc.perform(post("/api/depository-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositoryInfoDTO)))
            .andExpect(status().isBadRequest());

        List<DepositoryInfo> depositoryInfoList = depositoryInfoRepository.findAll();
        assertThat(depositoryInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDepositoryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = depositoryInfoRepository.findAll().size();
        // set the field null
        depositoryInfo.setDepositoryName(null);

        // Create the DepositoryInfo, which fails.
        DepositoryInfoDTO depositoryInfoDTO = depositoryInfoMapper.toDto(depositoryInfo);

        restDepositoryInfoMockMvc.perform(post("/api/depository-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositoryInfoDTO)))
            .andExpect(status().isBadRequest());

        List<DepositoryInfo> depositoryInfoList = depositoryInfoRepository.findAll();
        assertThat(depositoryInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBrokerNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = depositoryInfoRepository.findAll().size();
        // set the field null
        depositoryInfo.setBrokerName(null);

        // Create the DepositoryInfo, which fails.
        DepositoryInfoDTO depositoryInfoDTO = depositoryInfoMapper.toDto(depositoryInfo);

        restDepositoryInfoMockMvc.perform(post("/api/depository-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositoryInfoDTO)))
            .andExpect(status().isBadRequest());

        List<DepositoryInfo> depositoryInfoList = depositoryInfoRepository.findAll();
        assertThat(depositoryInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameAsPerDematIsRequired() throws Exception {
        int databaseSizeBeforeTest = depositoryInfoRepository.findAll().size();
        // set the field null
        depositoryInfo.setNameAsPerDemat(null);

        // Create the DepositoryInfo, which fails.
        DepositoryInfoDTO depositoryInfoDTO = depositoryInfoMapper.toDto(depositoryInfo);

        restDepositoryInfoMockMvc.perform(post("/api/depository-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositoryInfoDTO)))
            .andExpect(status().isBadRequest());

        List<DepositoryInfo> depositoryInfoList = depositoryInfoRepository.findAll();
        assertThat(depositoryInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDepositoryInfos() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        // Get all the depositoryInfoList
        restDepositoryInfoMockMvc.perform(get("/api/depository-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depositoryInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].haveAccountWithOtherDP").value(hasItem(DEFAULT_HAVE_ACCOUNT_WITH_OTHER_DP.booleanValue())))
            .andExpect(jsonPath("$.[*].haveSMSEnabled").value(hasItem(DEFAULT_HAVE_SMS_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].statementFrequency").value(hasItem(DEFAULT_STATEMENT_FREQUENCY.toString())))
            .andExpect(jsonPath("$.[*].dpScheme").value(hasItem(DEFAULT_DP_SCHEME.toString())))
            .andExpect(jsonPath("$.[*].depositoryName").value(hasItem(DEFAULT_DEPOSITORY_NAME.toString())))
            .andExpect(jsonPath("$.[*].brokerName").value(hasItem(DEFAULT_BROKER_NAME.toString())))
            .andExpect(jsonPath("$.[*].nameAsPerDemat").value(hasItem(DEFAULT_NAME_AS_PER_DEMAT.toString())));
    }
    
    @Test
    @Transactional
    public void getDepositoryInfo() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        // Get the depositoryInfo
        restDepositoryInfoMockMvc.perform(get("/api/depository-infos/{id}", depositoryInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(depositoryInfo.getId().intValue()))
            .andExpect(jsonPath("$.haveAccountWithOtherDP").value(DEFAULT_HAVE_ACCOUNT_WITH_OTHER_DP.booleanValue()))
            .andExpect(jsonPath("$.haveSMSEnabled").value(DEFAULT_HAVE_SMS_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.statementFrequency").value(DEFAULT_STATEMENT_FREQUENCY.toString()))
            .andExpect(jsonPath("$.dpScheme").value(DEFAULT_DP_SCHEME.toString()))
            .andExpect(jsonPath("$.depositoryName").value(DEFAULT_DEPOSITORY_NAME.toString()))
            .andExpect(jsonPath("$.brokerName").value(DEFAULT_BROKER_NAME.toString()))
            .andExpect(jsonPath("$.nameAsPerDemat").value(DEFAULT_NAME_AS_PER_DEMAT.toString()));
    }

    @Test
    @Transactional
    public void getAllDepositoryInfosByHaveAccountWithOtherDPIsEqualToSomething() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        // Get all the depositoryInfoList where haveAccountWithOtherDP equals to DEFAULT_HAVE_ACCOUNT_WITH_OTHER_DP
        defaultDepositoryInfoShouldBeFound("haveAccountWithOtherDP.equals=" + DEFAULT_HAVE_ACCOUNT_WITH_OTHER_DP);

        // Get all the depositoryInfoList where haveAccountWithOtherDP equals to UPDATED_HAVE_ACCOUNT_WITH_OTHER_DP
        defaultDepositoryInfoShouldNotBeFound("haveAccountWithOtherDP.equals=" + UPDATED_HAVE_ACCOUNT_WITH_OTHER_DP);
    }

    @Test
    @Transactional
    public void getAllDepositoryInfosByHaveAccountWithOtherDPIsInShouldWork() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        // Get all the depositoryInfoList where haveAccountWithOtherDP in DEFAULT_HAVE_ACCOUNT_WITH_OTHER_DP or UPDATED_HAVE_ACCOUNT_WITH_OTHER_DP
        defaultDepositoryInfoShouldBeFound("haveAccountWithOtherDP.in=" + DEFAULT_HAVE_ACCOUNT_WITH_OTHER_DP + "," + UPDATED_HAVE_ACCOUNT_WITH_OTHER_DP);

        // Get all the depositoryInfoList where haveAccountWithOtherDP equals to UPDATED_HAVE_ACCOUNT_WITH_OTHER_DP
        defaultDepositoryInfoShouldNotBeFound("haveAccountWithOtherDP.in=" + UPDATED_HAVE_ACCOUNT_WITH_OTHER_DP);
    }

    @Test
    @Transactional
    public void getAllDepositoryInfosByHaveAccountWithOtherDPIsNullOrNotNull() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        // Get all the depositoryInfoList where haveAccountWithOtherDP is not null
        defaultDepositoryInfoShouldBeFound("haveAccountWithOtherDP.specified=true");

        // Get all the depositoryInfoList where haveAccountWithOtherDP is null
        defaultDepositoryInfoShouldNotBeFound("haveAccountWithOtherDP.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepositoryInfosByHaveSMSEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        // Get all the depositoryInfoList where haveSMSEnabled equals to DEFAULT_HAVE_SMS_ENABLED
        defaultDepositoryInfoShouldBeFound("haveSMSEnabled.equals=" + DEFAULT_HAVE_SMS_ENABLED);

        // Get all the depositoryInfoList where haveSMSEnabled equals to UPDATED_HAVE_SMS_ENABLED
        defaultDepositoryInfoShouldNotBeFound("haveSMSEnabled.equals=" + UPDATED_HAVE_SMS_ENABLED);
    }

    @Test
    @Transactional
    public void getAllDepositoryInfosByHaveSMSEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        // Get all the depositoryInfoList where haveSMSEnabled in DEFAULT_HAVE_SMS_ENABLED or UPDATED_HAVE_SMS_ENABLED
        defaultDepositoryInfoShouldBeFound("haveSMSEnabled.in=" + DEFAULT_HAVE_SMS_ENABLED + "," + UPDATED_HAVE_SMS_ENABLED);

        // Get all the depositoryInfoList where haveSMSEnabled equals to UPDATED_HAVE_SMS_ENABLED
        defaultDepositoryInfoShouldNotBeFound("haveSMSEnabled.in=" + UPDATED_HAVE_SMS_ENABLED);
    }

    @Test
    @Transactional
    public void getAllDepositoryInfosByHaveSMSEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        // Get all the depositoryInfoList where haveSMSEnabled is not null
        defaultDepositoryInfoShouldBeFound("haveSMSEnabled.specified=true");

        // Get all the depositoryInfoList where haveSMSEnabled is null
        defaultDepositoryInfoShouldNotBeFound("haveSMSEnabled.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepositoryInfosByStatementFrequencyIsEqualToSomething() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        // Get all the depositoryInfoList where statementFrequency equals to DEFAULT_STATEMENT_FREQUENCY
        defaultDepositoryInfoShouldBeFound("statementFrequency.equals=" + DEFAULT_STATEMENT_FREQUENCY);

        // Get all the depositoryInfoList where statementFrequency equals to UPDATED_STATEMENT_FREQUENCY
        defaultDepositoryInfoShouldNotBeFound("statementFrequency.equals=" + UPDATED_STATEMENT_FREQUENCY);
    }

    @Test
    @Transactional
    public void getAllDepositoryInfosByStatementFrequencyIsInShouldWork() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        // Get all the depositoryInfoList where statementFrequency in DEFAULT_STATEMENT_FREQUENCY or UPDATED_STATEMENT_FREQUENCY
        defaultDepositoryInfoShouldBeFound("statementFrequency.in=" + DEFAULT_STATEMENT_FREQUENCY + "," + UPDATED_STATEMENT_FREQUENCY);

        // Get all the depositoryInfoList where statementFrequency equals to UPDATED_STATEMENT_FREQUENCY
        defaultDepositoryInfoShouldNotBeFound("statementFrequency.in=" + UPDATED_STATEMENT_FREQUENCY);
    }

    @Test
    @Transactional
    public void getAllDepositoryInfosByStatementFrequencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        // Get all the depositoryInfoList where statementFrequency is not null
        defaultDepositoryInfoShouldBeFound("statementFrequency.specified=true");

        // Get all the depositoryInfoList where statementFrequency is null
        defaultDepositoryInfoShouldNotBeFound("statementFrequency.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepositoryInfosByDpSchemeIsEqualToSomething() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        // Get all the depositoryInfoList where dpScheme equals to DEFAULT_DP_SCHEME
        defaultDepositoryInfoShouldBeFound("dpScheme.equals=" + DEFAULT_DP_SCHEME);

        // Get all the depositoryInfoList where dpScheme equals to UPDATED_DP_SCHEME
        defaultDepositoryInfoShouldNotBeFound("dpScheme.equals=" + UPDATED_DP_SCHEME);
    }

    @Test
    @Transactional
    public void getAllDepositoryInfosByDpSchemeIsInShouldWork() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        // Get all the depositoryInfoList where dpScheme in DEFAULT_DP_SCHEME or UPDATED_DP_SCHEME
        defaultDepositoryInfoShouldBeFound("dpScheme.in=" + DEFAULT_DP_SCHEME + "," + UPDATED_DP_SCHEME);

        // Get all the depositoryInfoList where dpScheme equals to UPDATED_DP_SCHEME
        defaultDepositoryInfoShouldNotBeFound("dpScheme.in=" + UPDATED_DP_SCHEME);
    }

    @Test
    @Transactional
    public void getAllDepositoryInfosByDpSchemeIsNullOrNotNull() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        // Get all the depositoryInfoList where dpScheme is not null
        defaultDepositoryInfoShouldBeFound("dpScheme.specified=true");

        // Get all the depositoryInfoList where dpScheme is null
        defaultDepositoryInfoShouldNotBeFound("dpScheme.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepositoryInfosByDepositoryNameIsEqualToSomething() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        // Get all the depositoryInfoList where depositoryName equals to DEFAULT_DEPOSITORY_NAME
        defaultDepositoryInfoShouldBeFound("depositoryName.equals=" + DEFAULT_DEPOSITORY_NAME);

        // Get all the depositoryInfoList where depositoryName equals to UPDATED_DEPOSITORY_NAME
        defaultDepositoryInfoShouldNotBeFound("depositoryName.equals=" + UPDATED_DEPOSITORY_NAME);
    }

    @Test
    @Transactional
    public void getAllDepositoryInfosByDepositoryNameIsInShouldWork() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        // Get all the depositoryInfoList where depositoryName in DEFAULT_DEPOSITORY_NAME or UPDATED_DEPOSITORY_NAME
        defaultDepositoryInfoShouldBeFound("depositoryName.in=" + DEFAULT_DEPOSITORY_NAME + "," + UPDATED_DEPOSITORY_NAME);

        // Get all the depositoryInfoList where depositoryName equals to UPDATED_DEPOSITORY_NAME
        defaultDepositoryInfoShouldNotBeFound("depositoryName.in=" + UPDATED_DEPOSITORY_NAME);
    }

    @Test
    @Transactional
    public void getAllDepositoryInfosByDepositoryNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        // Get all the depositoryInfoList where depositoryName is not null
        defaultDepositoryInfoShouldBeFound("depositoryName.specified=true");

        // Get all the depositoryInfoList where depositoryName is null
        defaultDepositoryInfoShouldNotBeFound("depositoryName.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepositoryInfosByBrokerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        // Get all the depositoryInfoList where brokerName equals to DEFAULT_BROKER_NAME
        defaultDepositoryInfoShouldBeFound("brokerName.equals=" + DEFAULT_BROKER_NAME);

        // Get all the depositoryInfoList where brokerName equals to UPDATED_BROKER_NAME
        defaultDepositoryInfoShouldNotBeFound("brokerName.equals=" + UPDATED_BROKER_NAME);
    }

    @Test
    @Transactional
    public void getAllDepositoryInfosByBrokerNameIsInShouldWork() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        // Get all the depositoryInfoList where brokerName in DEFAULT_BROKER_NAME or UPDATED_BROKER_NAME
        defaultDepositoryInfoShouldBeFound("brokerName.in=" + DEFAULT_BROKER_NAME + "," + UPDATED_BROKER_NAME);

        // Get all the depositoryInfoList where brokerName equals to UPDATED_BROKER_NAME
        defaultDepositoryInfoShouldNotBeFound("brokerName.in=" + UPDATED_BROKER_NAME);
    }

    @Test
    @Transactional
    public void getAllDepositoryInfosByBrokerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        // Get all the depositoryInfoList where brokerName is not null
        defaultDepositoryInfoShouldBeFound("brokerName.specified=true");

        // Get all the depositoryInfoList where brokerName is null
        defaultDepositoryInfoShouldNotBeFound("brokerName.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepositoryInfosByNameAsPerDematIsEqualToSomething() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        // Get all the depositoryInfoList where nameAsPerDemat equals to DEFAULT_NAME_AS_PER_DEMAT
        defaultDepositoryInfoShouldBeFound("nameAsPerDemat.equals=" + DEFAULT_NAME_AS_PER_DEMAT);

        // Get all the depositoryInfoList where nameAsPerDemat equals to UPDATED_NAME_AS_PER_DEMAT
        defaultDepositoryInfoShouldNotBeFound("nameAsPerDemat.equals=" + UPDATED_NAME_AS_PER_DEMAT);
    }

    @Test
    @Transactional
    public void getAllDepositoryInfosByNameAsPerDematIsInShouldWork() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        // Get all the depositoryInfoList where nameAsPerDemat in DEFAULT_NAME_AS_PER_DEMAT or UPDATED_NAME_AS_PER_DEMAT
        defaultDepositoryInfoShouldBeFound("nameAsPerDemat.in=" + DEFAULT_NAME_AS_PER_DEMAT + "," + UPDATED_NAME_AS_PER_DEMAT);

        // Get all the depositoryInfoList where nameAsPerDemat equals to UPDATED_NAME_AS_PER_DEMAT
        defaultDepositoryInfoShouldNotBeFound("nameAsPerDemat.in=" + UPDATED_NAME_AS_PER_DEMAT);
    }

    @Test
    @Transactional
    public void getAllDepositoryInfosByNameAsPerDematIsNullOrNotNull() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        // Get all the depositoryInfoList where nameAsPerDemat is not null
        defaultDepositoryInfoShouldBeFound("nameAsPerDemat.specified=true");

        // Get all the depositoryInfoList where nameAsPerDemat is null
        defaultDepositoryInfoShouldNotBeFound("nameAsPerDemat.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepositoryInfosByApplicationProspectIsEqualToSomething() throws Exception {
        // Initialize the database
        ApplicationProspect applicationProspect = ApplicationProspectResourceIntTest.createEntity(em);
        em.persist(applicationProspect);
        em.flush();
        depositoryInfo.setApplicationProspect(applicationProspect);
        applicationProspect.setDepository(depositoryInfo);
        depositoryInfoRepository.saveAndFlush(depositoryInfo);
        Long applicationProspectId = applicationProspect.getId();

        // Get all the depositoryInfoList where applicationProspect equals to applicationProspectId
        defaultDepositoryInfoShouldBeFound("applicationProspectId.equals=" + applicationProspectId);

        // Get all the depositoryInfoList where applicationProspect equals to applicationProspectId + 1
        defaultDepositoryInfoShouldNotBeFound("applicationProspectId.equals=" + (applicationProspectId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDepositoryInfoShouldBeFound(String filter) throws Exception {
        restDepositoryInfoMockMvc.perform(get("/api/depository-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depositoryInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].haveAccountWithOtherDP").value(hasItem(DEFAULT_HAVE_ACCOUNT_WITH_OTHER_DP.booleanValue())))
            .andExpect(jsonPath("$.[*].haveSMSEnabled").value(hasItem(DEFAULT_HAVE_SMS_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].statementFrequency").value(hasItem(DEFAULT_STATEMENT_FREQUENCY.toString())))
            .andExpect(jsonPath("$.[*].dpScheme").value(hasItem(DEFAULT_DP_SCHEME.toString())))
            .andExpect(jsonPath("$.[*].depositoryName").value(hasItem(DEFAULT_DEPOSITORY_NAME.toString())))
            .andExpect(jsonPath("$.[*].brokerName").value(hasItem(DEFAULT_BROKER_NAME.toString())))
            .andExpect(jsonPath("$.[*].nameAsPerDemat").value(hasItem(DEFAULT_NAME_AS_PER_DEMAT.toString())));

        // Check, that the count call also returns 1
        restDepositoryInfoMockMvc.perform(get("/api/depository-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDepositoryInfoShouldNotBeFound(String filter) throws Exception {
        restDepositoryInfoMockMvc.perform(get("/api/depository-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDepositoryInfoMockMvc.perform(get("/api/depository-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDepositoryInfo() throws Exception {
        // Get the depositoryInfo
        restDepositoryInfoMockMvc.perform(get("/api/depository-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepositoryInfo() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        int databaseSizeBeforeUpdate = depositoryInfoRepository.findAll().size();

        // Update the depositoryInfo
        DepositoryInfo updatedDepositoryInfo = depositoryInfoRepository.findById(depositoryInfo.getId()).get();
        // Disconnect from session so that the updates on updatedDepositoryInfo are not directly saved in db
        em.detach(updatedDepositoryInfo);
        updatedDepositoryInfo
            .haveAccountWithOtherDP(UPDATED_HAVE_ACCOUNT_WITH_OTHER_DP)
            .haveSMSEnabled(UPDATED_HAVE_SMS_ENABLED)
            .statementFrequency(UPDATED_STATEMENT_FREQUENCY)
            .dpScheme(UPDATED_DP_SCHEME)
            .depositoryName(UPDATED_DEPOSITORY_NAME)
            .brokerName(UPDATED_BROKER_NAME)
            .nameAsPerDemat(UPDATED_NAME_AS_PER_DEMAT);
        DepositoryInfoDTO depositoryInfoDTO = depositoryInfoMapper.toDto(updatedDepositoryInfo);

        restDepositoryInfoMockMvc.perform(put("/api/depository-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositoryInfoDTO)))
            .andExpect(status().isOk());

        // Validate the DepositoryInfo in the database
        List<DepositoryInfo> depositoryInfoList = depositoryInfoRepository.findAll();
        assertThat(depositoryInfoList).hasSize(databaseSizeBeforeUpdate);
        DepositoryInfo testDepositoryInfo = depositoryInfoList.get(depositoryInfoList.size() - 1);
        assertThat(testDepositoryInfo.isHaveAccountWithOtherDP()).isEqualTo(UPDATED_HAVE_ACCOUNT_WITH_OTHER_DP);
        assertThat(testDepositoryInfo.isHaveSMSEnabled()).isEqualTo(UPDATED_HAVE_SMS_ENABLED);
        assertThat(testDepositoryInfo.getStatementFrequency()).isEqualTo(UPDATED_STATEMENT_FREQUENCY);
        assertThat(testDepositoryInfo.getDpScheme()).isEqualTo(UPDATED_DP_SCHEME);
        assertThat(testDepositoryInfo.getDepositoryName()).isEqualTo(UPDATED_DEPOSITORY_NAME);
        assertThat(testDepositoryInfo.getBrokerName()).isEqualTo(UPDATED_BROKER_NAME);
        assertThat(testDepositoryInfo.getNameAsPerDemat()).isEqualTo(UPDATED_NAME_AS_PER_DEMAT);
    }

    @Test
    @Transactional
    public void updateNonExistingDepositoryInfo() throws Exception {
        int databaseSizeBeforeUpdate = depositoryInfoRepository.findAll().size();

        // Create the DepositoryInfo
        DepositoryInfoDTO depositoryInfoDTO = depositoryInfoMapper.toDto(depositoryInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepositoryInfoMockMvc.perform(put("/api/depository-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositoryInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DepositoryInfo in the database
        List<DepositoryInfo> depositoryInfoList = depositoryInfoRepository.findAll();
        assertThat(depositoryInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDepositoryInfo() throws Exception {
        // Initialize the database
        depositoryInfoRepository.saveAndFlush(depositoryInfo);

        int databaseSizeBeforeDelete = depositoryInfoRepository.findAll().size();

        // Get the depositoryInfo
        restDepositoryInfoMockMvc.perform(delete("/api/depository-infos/{id}", depositoryInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DepositoryInfo> depositoryInfoList = depositoryInfoRepository.findAll();
        assertThat(depositoryInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepositoryInfo.class);
        DepositoryInfo depositoryInfo1 = new DepositoryInfo();
        depositoryInfo1.setId(1L);
        DepositoryInfo depositoryInfo2 = new DepositoryInfo();
        depositoryInfo2.setId(depositoryInfo1.getId());
        assertThat(depositoryInfo1).isEqualTo(depositoryInfo2);
        depositoryInfo2.setId(2L);
        assertThat(depositoryInfo1).isNotEqualTo(depositoryInfo2);
        depositoryInfo1.setId(null);
        assertThat(depositoryInfo1).isNotEqualTo(depositoryInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepositoryInfoDTO.class);
        DepositoryInfoDTO depositoryInfoDTO1 = new DepositoryInfoDTO();
        depositoryInfoDTO1.setId(1L);
        DepositoryInfoDTO depositoryInfoDTO2 = new DepositoryInfoDTO();
        assertThat(depositoryInfoDTO1).isNotEqualTo(depositoryInfoDTO2);
        depositoryInfoDTO2.setId(depositoryInfoDTO1.getId());
        assertThat(depositoryInfoDTO1).isEqualTo(depositoryInfoDTO2);
        depositoryInfoDTO2.setId(2L);
        assertThat(depositoryInfoDTO1).isNotEqualTo(depositoryInfoDTO2);
        depositoryInfoDTO1.setId(null);
        assertThat(depositoryInfoDTO1).isNotEqualTo(depositoryInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(depositoryInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(depositoryInfoMapper.fromId(null)).isNull();
    }
}
