package com.eagle.kyc.web.rest;

import com.eagle.kyc.Kyc5App;

import com.eagle.kyc.domain.TradingInfo;
import com.eagle.kyc.domain.ApplicationProspect;
import com.eagle.kyc.repository.TradingInfoRepository;
import com.eagle.kyc.service.TradingInfoService;
import com.eagle.kyc.service.dto.TradingInfoDTO;
import com.eagle.kyc.service.mapper.TradingInfoMapper;
import com.eagle.kyc.web.rest.errors.ExceptionTranslator;
import com.eagle.kyc.service.dto.TradingInfoCriteria;
import com.eagle.kyc.service.TradingInfoQueryService;

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
 * Test class for the TradingInfoResource REST controller.
 *
 * @see TradingInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Kyc5App.class)
public class TradingInfoResourceIntTest {

    private static final String DEFAULT_SEGMENT_CD = "AAAAAAAAAA";
    private static final String UPDATED_SEGMENT_CD = "BBBBBBBBBB";

    private static final String DEFAULT_PLAN_CD_EQUITY = "AAAAAAAAAA";
    private static final String UPDATED_PLAN_CD_EQUITY = "BBBBBBBBBB";

    private static final String DEFAULT_PLAN_CD_COMMODITY = "AAAAAAAAAA";
    private static final String UPDATED_PLAN_CD_COMMODITY = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRACT_NOTE_MODE = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_NOTE_MODE = "BBBBBBBBBB";

    private static final String DEFAULT_TRADING_MODE = "AAAAAAAAAA";
    private static final String UPDATED_TRADING_MODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INTERESTED_IN_MOBILE_TRADEING = false;
    private static final Boolean UPDATED_INTERESTED_IN_MOBILE_TRADEING = true;

    private static final String DEFAULT_ACCOUNT_AUTH_FREQUENCY = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_AUTH_FREQUENCY = "BBBBBBBBBB";

    private static final Integer DEFAULT_EXPERIENCE_YEAR = 1;
    private static final Integer UPDATED_EXPERIENCE_YEAR = 2;

    private static final Integer DEFAULT_EXPERIENCE_MONTH = 1;
    private static final Integer UPDATED_EXPERIENCE_MONTH = 2;

    @Autowired
    private TradingInfoRepository tradingInfoRepository;

    @Autowired
    private TradingInfoMapper tradingInfoMapper;
    
    @Autowired
    private TradingInfoService tradingInfoService;

    @Autowired
    private TradingInfoQueryService tradingInfoQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTradingInfoMockMvc;

    private TradingInfo tradingInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TradingInfoResource tradingInfoResource = new TradingInfoResource(tradingInfoService, tradingInfoQueryService);
        this.restTradingInfoMockMvc = MockMvcBuilders.standaloneSetup(tradingInfoResource)
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
    public static TradingInfo createEntity(EntityManager em) {
        TradingInfo tradingInfo = new TradingInfo()
            .segmentCd(DEFAULT_SEGMENT_CD)
            .planCdEquity(DEFAULT_PLAN_CD_EQUITY)
            .planCdCommodity(DEFAULT_PLAN_CD_COMMODITY)
            .contractNoteMode(DEFAULT_CONTRACT_NOTE_MODE)
            .tradingMode(DEFAULT_TRADING_MODE)
            .interestedInMobileTradeing(DEFAULT_INTERESTED_IN_MOBILE_TRADEING)
            .accountAuthFrequency(DEFAULT_ACCOUNT_AUTH_FREQUENCY)
            .experienceYear(DEFAULT_EXPERIENCE_YEAR)
            .experienceMonth(DEFAULT_EXPERIENCE_MONTH);
        return tradingInfo;
    }

    @Before
    public void initTest() {
        tradingInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTradingInfo() throws Exception {
        int databaseSizeBeforeCreate = tradingInfoRepository.findAll().size();

        // Create the TradingInfo
        TradingInfoDTO tradingInfoDTO = tradingInfoMapper.toDto(tradingInfo);
        restTradingInfoMockMvc.perform(post("/api/trading-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tradingInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the TradingInfo in the database
        List<TradingInfo> tradingInfoList = tradingInfoRepository.findAll();
        assertThat(tradingInfoList).hasSize(databaseSizeBeforeCreate + 1);
        TradingInfo testTradingInfo = tradingInfoList.get(tradingInfoList.size() - 1);
        assertThat(testTradingInfo.getSegmentCd()).isEqualTo(DEFAULT_SEGMENT_CD);
        assertThat(testTradingInfo.getPlanCdEquity()).isEqualTo(DEFAULT_PLAN_CD_EQUITY);
        assertThat(testTradingInfo.getPlanCdCommodity()).isEqualTo(DEFAULT_PLAN_CD_COMMODITY);
        assertThat(testTradingInfo.getContractNoteMode()).isEqualTo(DEFAULT_CONTRACT_NOTE_MODE);
        assertThat(testTradingInfo.getTradingMode()).isEqualTo(DEFAULT_TRADING_MODE);
        assertThat(testTradingInfo.isInterestedInMobileTradeing()).isEqualTo(DEFAULT_INTERESTED_IN_MOBILE_TRADEING);
        assertThat(testTradingInfo.getAccountAuthFrequency()).isEqualTo(DEFAULT_ACCOUNT_AUTH_FREQUENCY);
        assertThat(testTradingInfo.getExperienceYear()).isEqualTo(DEFAULT_EXPERIENCE_YEAR);
        assertThat(testTradingInfo.getExperienceMonth()).isEqualTo(DEFAULT_EXPERIENCE_MONTH);
    }

    @Test
    @Transactional
    public void createTradingInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tradingInfoRepository.findAll().size();

        // Create the TradingInfo with an existing ID
        tradingInfo.setId(1L);
        TradingInfoDTO tradingInfoDTO = tradingInfoMapper.toDto(tradingInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTradingInfoMockMvc.perform(post("/api/trading-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tradingInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TradingInfo in the database
        List<TradingInfo> tradingInfoList = tradingInfoRepository.findAll();
        assertThat(tradingInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSegmentCdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradingInfoRepository.findAll().size();
        // set the field null
        tradingInfo.setSegmentCd(null);

        // Create the TradingInfo, which fails.
        TradingInfoDTO tradingInfoDTO = tradingInfoMapper.toDto(tradingInfo);

        restTradingInfoMockMvc.perform(post("/api/trading-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tradingInfoDTO)))
            .andExpect(status().isBadRequest());

        List<TradingInfo> tradingInfoList = tradingInfoRepository.findAll();
        assertThat(tradingInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlanCdEquityIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradingInfoRepository.findAll().size();
        // set the field null
        tradingInfo.setPlanCdEquity(null);

        // Create the TradingInfo, which fails.
        TradingInfoDTO tradingInfoDTO = tradingInfoMapper.toDto(tradingInfo);

        restTradingInfoMockMvc.perform(post("/api/trading-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tradingInfoDTO)))
            .andExpect(status().isBadRequest());

        List<TradingInfo> tradingInfoList = tradingInfoRepository.findAll();
        assertThat(tradingInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlanCdCommodityIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradingInfoRepository.findAll().size();
        // set the field null
        tradingInfo.setPlanCdCommodity(null);

        // Create the TradingInfo, which fails.
        TradingInfoDTO tradingInfoDTO = tradingInfoMapper.toDto(tradingInfo);

        restTradingInfoMockMvc.perform(post("/api/trading-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tradingInfoDTO)))
            .andExpect(status().isBadRequest());

        List<TradingInfo> tradingInfoList = tradingInfoRepository.findAll();
        assertThat(tradingInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContractNoteModeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradingInfoRepository.findAll().size();
        // set the field null
        tradingInfo.setContractNoteMode(null);

        // Create the TradingInfo, which fails.
        TradingInfoDTO tradingInfoDTO = tradingInfoMapper.toDto(tradingInfo);

        restTradingInfoMockMvc.perform(post("/api/trading-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tradingInfoDTO)))
            .andExpect(status().isBadRequest());

        List<TradingInfo> tradingInfoList = tradingInfoRepository.findAll();
        assertThat(tradingInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTradingModeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradingInfoRepository.findAll().size();
        // set the field null
        tradingInfo.setTradingMode(null);

        // Create the TradingInfo, which fails.
        TradingInfoDTO tradingInfoDTO = tradingInfoMapper.toDto(tradingInfo);

        restTradingInfoMockMvc.perform(post("/api/trading-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tradingInfoDTO)))
            .andExpect(status().isBadRequest());

        List<TradingInfo> tradingInfoList = tradingInfoRepository.findAll();
        assertThat(tradingInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInterestedInMobileTradeingIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradingInfoRepository.findAll().size();
        // set the field null
        tradingInfo.setInterestedInMobileTradeing(null);

        // Create the TradingInfo, which fails.
        TradingInfoDTO tradingInfoDTO = tradingInfoMapper.toDto(tradingInfo);

        restTradingInfoMockMvc.perform(post("/api/trading-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tradingInfoDTO)))
            .andExpect(status().isBadRequest());

        List<TradingInfo> tradingInfoList = tradingInfoRepository.findAll();
        assertThat(tradingInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountAuthFrequencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradingInfoRepository.findAll().size();
        // set the field null
        tradingInfo.setAccountAuthFrequency(null);

        // Create the TradingInfo, which fails.
        TradingInfoDTO tradingInfoDTO = tradingInfoMapper.toDto(tradingInfo);

        restTradingInfoMockMvc.perform(post("/api/trading-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tradingInfoDTO)))
            .andExpect(status().isBadRequest());

        List<TradingInfo> tradingInfoList = tradingInfoRepository.findAll();
        assertThat(tradingInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExperienceYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradingInfoRepository.findAll().size();
        // set the field null
        tradingInfo.setExperienceYear(null);

        // Create the TradingInfo, which fails.
        TradingInfoDTO tradingInfoDTO = tradingInfoMapper.toDto(tradingInfo);

        restTradingInfoMockMvc.perform(post("/api/trading-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tradingInfoDTO)))
            .andExpect(status().isBadRequest());

        List<TradingInfo> tradingInfoList = tradingInfoRepository.findAll();
        assertThat(tradingInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExperienceMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradingInfoRepository.findAll().size();
        // set the field null
        tradingInfo.setExperienceMonth(null);

        // Create the TradingInfo, which fails.
        TradingInfoDTO tradingInfoDTO = tradingInfoMapper.toDto(tradingInfo);

        restTradingInfoMockMvc.perform(post("/api/trading-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tradingInfoDTO)))
            .andExpect(status().isBadRequest());

        List<TradingInfo> tradingInfoList = tradingInfoRepository.findAll();
        assertThat(tradingInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTradingInfos() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList
        restTradingInfoMockMvc.perform(get("/api/trading-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tradingInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].segmentCd").value(hasItem(DEFAULT_SEGMENT_CD.toString())))
            .andExpect(jsonPath("$.[*].planCdEquity").value(hasItem(DEFAULT_PLAN_CD_EQUITY.toString())))
            .andExpect(jsonPath("$.[*].planCdCommodity").value(hasItem(DEFAULT_PLAN_CD_COMMODITY.toString())))
            .andExpect(jsonPath("$.[*].contractNoteMode").value(hasItem(DEFAULT_CONTRACT_NOTE_MODE.toString())))
            .andExpect(jsonPath("$.[*].tradingMode").value(hasItem(DEFAULT_TRADING_MODE.toString())))
            .andExpect(jsonPath("$.[*].interestedInMobileTradeing").value(hasItem(DEFAULT_INTERESTED_IN_MOBILE_TRADEING.booleanValue())))
            .andExpect(jsonPath("$.[*].accountAuthFrequency").value(hasItem(DEFAULT_ACCOUNT_AUTH_FREQUENCY.toString())))
            .andExpect(jsonPath("$.[*].experienceYear").value(hasItem(DEFAULT_EXPERIENCE_YEAR)))
            .andExpect(jsonPath("$.[*].experienceMonth").value(hasItem(DEFAULT_EXPERIENCE_MONTH)));
    }
    
    @Test
    @Transactional
    public void getTradingInfo() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get the tradingInfo
        restTradingInfoMockMvc.perform(get("/api/trading-infos/{id}", tradingInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tradingInfo.getId().intValue()))
            .andExpect(jsonPath("$.segmentCd").value(DEFAULT_SEGMENT_CD.toString()))
            .andExpect(jsonPath("$.planCdEquity").value(DEFAULT_PLAN_CD_EQUITY.toString()))
            .andExpect(jsonPath("$.planCdCommodity").value(DEFAULT_PLAN_CD_COMMODITY.toString()))
            .andExpect(jsonPath("$.contractNoteMode").value(DEFAULT_CONTRACT_NOTE_MODE.toString()))
            .andExpect(jsonPath("$.tradingMode").value(DEFAULT_TRADING_MODE.toString()))
            .andExpect(jsonPath("$.interestedInMobileTradeing").value(DEFAULT_INTERESTED_IN_MOBILE_TRADEING.booleanValue()))
            .andExpect(jsonPath("$.accountAuthFrequency").value(DEFAULT_ACCOUNT_AUTH_FREQUENCY.toString()))
            .andExpect(jsonPath("$.experienceYear").value(DEFAULT_EXPERIENCE_YEAR))
            .andExpect(jsonPath("$.experienceMonth").value(DEFAULT_EXPERIENCE_MONTH));
    }

    @Test
    @Transactional
    public void getAllTradingInfosBySegmentCdIsEqualToSomething() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where segmentCd equals to DEFAULT_SEGMENT_CD
        defaultTradingInfoShouldBeFound("segmentCd.equals=" + DEFAULT_SEGMENT_CD);

        // Get all the tradingInfoList where segmentCd equals to UPDATED_SEGMENT_CD
        defaultTradingInfoShouldNotBeFound("segmentCd.equals=" + UPDATED_SEGMENT_CD);
    }

    @Test
    @Transactional
    public void getAllTradingInfosBySegmentCdIsInShouldWork() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where segmentCd in DEFAULT_SEGMENT_CD or UPDATED_SEGMENT_CD
        defaultTradingInfoShouldBeFound("segmentCd.in=" + DEFAULT_SEGMENT_CD + "," + UPDATED_SEGMENT_CD);

        // Get all the tradingInfoList where segmentCd equals to UPDATED_SEGMENT_CD
        defaultTradingInfoShouldNotBeFound("segmentCd.in=" + UPDATED_SEGMENT_CD);
    }

    @Test
    @Transactional
    public void getAllTradingInfosBySegmentCdIsNullOrNotNull() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where segmentCd is not null
        defaultTradingInfoShouldBeFound("segmentCd.specified=true");

        // Get all the tradingInfoList where segmentCd is null
        defaultTradingInfoShouldNotBeFound("segmentCd.specified=false");
    }

    @Test
    @Transactional
    public void getAllTradingInfosByPlanCdEquityIsEqualToSomething() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where planCdEquity equals to DEFAULT_PLAN_CD_EQUITY
        defaultTradingInfoShouldBeFound("planCdEquity.equals=" + DEFAULT_PLAN_CD_EQUITY);

        // Get all the tradingInfoList where planCdEquity equals to UPDATED_PLAN_CD_EQUITY
        defaultTradingInfoShouldNotBeFound("planCdEquity.equals=" + UPDATED_PLAN_CD_EQUITY);
    }

    @Test
    @Transactional
    public void getAllTradingInfosByPlanCdEquityIsInShouldWork() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where planCdEquity in DEFAULT_PLAN_CD_EQUITY or UPDATED_PLAN_CD_EQUITY
        defaultTradingInfoShouldBeFound("planCdEquity.in=" + DEFAULT_PLAN_CD_EQUITY + "," + UPDATED_PLAN_CD_EQUITY);

        // Get all the tradingInfoList where planCdEquity equals to UPDATED_PLAN_CD_EQUITY
        defaultTradingInfoShouldNotBeFound("planCdEquity.in=" + UPDATED_PLAN_CD_EQUITY);
    }

    @Test
    @Transactional
    public void getAllTradingInfosByPlanCdEquityIsNullOrNotNull() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where planCdEquity is not null
        defaultTradingInfoShouldBeFound("planCdEquity.specified=true");

        // Get all the tradingInfoList where planCdEquity is null
        defaultTradingInfoShouldNotBeFound("planCdEquity.specified=false");
    }

    @Test
    @Transactional
    public void getAllTradingInfosByPlanCdCommodityIsEqualToSomething() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where planCdCommodity equals to DEFAULT_PLAN_CD_COMMODITY
        defaultTradingInfoShouldBeFound("planCdCommodity.equals=" + DEFAULT_PLAN_CD_COMMODITY);

        // Get all the tradingInfoList where planCdCommodity equals to UPDATED_PLAN_CD_COMMODITY
        defaultTradingInfoShouldNotBeFound("planCdCommodity.equals=" + UPDATED_PLAN_CD_COMMODITY);
    }

    @Test
    @Transactional
    public void getAllTradingInfosByPlanCdCommodityIsInShouldWork() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where planCdCommodity in DEFAULT_PLAN_CD_COMMODITY or UPDATED_PLAN_CD_COMMODITY
        defaultTradingInfoShouldBeFound("planCdCommodity.in=" + DEFAULT_PLAN_CD_COMMODITY + "," + UPDATED_PLAN_CD_COMMODITY);

        // Get all the tradingInfoList where planCdCommodity equals to UPDATED_PLAN_CD_COMMODITY
        defaultTradingInfoShouldNotBeFound("planCdCommodity.in=" + UPDATED_PLAN_CD_COMMODITY);
    }

    @Test
    @Transactional
    public void getAllTradingInfosByPlanCdCommodityIsNullOrNotNull() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where planCdCommodity is not null
        defaultTradingInfoShouldBeFound("planCdCommodity.specified=true");

        // Get all the tradingInfoList where planCdCommodity is null
        defaultTradingInfoShouldNotBeFound("planCdCommodity.specified=false");
    }

    @Test
    @Transactional
    public void getAllTradingInfosByContractNoteModeIsEqualToSomething() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where contractNoteMode equals to DEFAULT_CONTRACT_NOTE_MODE
        defaultTradingInfoShouldBeFound("contractNoteMode.equals=" + DEFAULT_CONTRACT_NOTE_MODE);

        // Get all the tradingInfoList where contractNoteMode equals to UPDATED_CONTRACT_NOTE_MODE
        defaultTradingInfoShouldNotBeFound("contractNoteMode.equals=" + UPDATED_CONTRACT_NOTE_MODE);
    }

    @Test
    @Transactional
    public void getAllTradingInfosByContractNoteModeIsInShouldWork() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where contractNoteMode in DEFAULT_CONTRACT_NOTE_MODE or UPDATED_CONTRACT_NOTE_MODE
        defaultTradingInfoShouldBeFound("contractNoteMode.in=" + DEFAULT_CONTRACT_NOTE_MODE + "," + UPDATED_CONTRACT_NOTE_MODE);

        // Get all the tradingInfoList where contractNoteMode equals to UPDATED_CONTRACT_NOTE_MODE
        defaultTradingInfoShouldNotBeFound("contractNoteMode.in=" + UPDATED_CONTRACT_NOTE_MODE);
    }

    @Test
    @Transactional
    public void getAllTradingInfosByContractNoteModeIsNullOrNotNull() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where contractNoteMode is not null
        defaultTradingInfoShouldBeFound("contractNoteMode.specified=true");

        // Get all the tradingInfoList where contractNoteMode is null
        defaultTradingInfoShouldNotBeFound("contractNoteMode.specified=false");
    }

    @Test
    @Transactional
    public void getAllTradingInfosByTradingModeIsEqualToSomething() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where tradingMode equals to DEFAULT_TRADING_MODE
        defaultTradingInfoShouldBeFound("tradingMode.equals=" + DEFAULT_TRADING_MODE);

        // Get all the tradingInfoList where tradingMode equals to UPDATED_TRADING_MODE
        defaultTradingInfoShouldNotBeFound("tradingMode.equals=" + UPDATED_TRADING_MODE);
    }

    @Test
    @Transactional
    public void getAllTradingInfosByTradingModeIsInShouldWork() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where tradingMode in DEFAULT_TRADING_MODE or UPDATED_TRADING_MODE
        defaultTradingInfoShouldBeFound("tradingMode.in=" + DEFAULT_TRADING_MODE + "," + UPDATED_TRADING_MODE);

        // Get all the tradingInfoList where tradingMode equals to UPDATED_TRADING_MODE
        defaultTradingInfoShouldNotBeFound("tradingMode.in=" + UPDATED_TRADING_MODE);
    }

    @Test
    @Transactional
    public void getAllTradingInfosByTradingModeIsNullOrNotNull() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where tradingMode is not null
        defaultTradingInfoShouldBeFound("tradingMode.specified=true");

        // Get all the tradingInfoList where tradingMode is null
        defaultTradingInfoShouldNotBeFound("tradingMode.specified=false");
    }

    @Test
    @Transactional
    public void getAllTradingInfosByInterestedInMobileTradeingIsEqualToSomething() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where interestedInMobileTradeing equals to DEFAULT_INTERESTED_IN_MOBILE_TRADEING
        defaultTradingInfoShouldBeFound("interestedInMobileTradeing.equals=" + DEFAULT_INTERESTED_IN_MOBILE_TRADEING);

        // Get all the tradingInfoList where interestedInMobileTradeing equals to UPDATED_INTERESTED_IN_MOBILE_TRADEING
        defaultTradingInfoShouldNotBeFound("interestedInMobileTradeing.equals=" + UPDATED_INTERESTED_IN_MOBILE_TRADEING);
    }

    @Test
    @Transactional
    public void getAllTradingInfosByInterestedInMobileTradeingIsInShouldWork() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where interestedInMobileTradeing in DEFAULT_INTERESTED_IN_MOBILE_TRADEING or UPDATED_INTERESTED_IN_MOBILE_TRADEING
        defaultTradingInfoShouldBeFound("interestedInMobileTradeing.in=" + DEFAULT_INTERESTED_IN_MOBILE_TRADEING + "," + UPDATED_INTERESTED_IN_MOBILE_TRADEING);

        // Get all the tradingInfoList where interestedInMobileTradeing equals to UPDATED_INTERESTED_IN_MOBILE_TRADEING
        defaultTradingInfoShouldNotBeFound("interestedInMobileTradeing.in=" + UPDATED_INTERESTED_IN_MOBILE_TRADEING);
    }

    @Test
    @Transactional
    public void getAllTradingInfosByInterestedInMobileTradeingIsNullOrNotNull() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where interestedInMobileTradeing is not null
        defaultTradingInfoShouldBeFound("interestedInMobileTradeing.specified=true");

        // Get all the tradingInfoList where interestedInMobileTradeing is null
        defaultTradingInfoShouldNotBeFound("interestedInMobileTradeing.specified=false");
    }

    @Test
    @Transactional
    public void getAllTradingInfosByAccountAuthFrequencyIsEqualToSomething() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where accountAuthFrequency equals to DEFAULT_ACCOUNT_AUTH_FREQUENCY
        defaultTradingInfoShouldBeFound("accountAuthFrequency.equals=" + DEFAULT_ACCOUNT_AUTH_FREQUENCY);

        // Get all the tradingInfoList where accountAuthFrequency equals to UPDATED_ACCOUNT_AUTH_FREQUENCY
        defaultTradingInfoShouldNotBeFound("accountAuthFrequency.equals=" + UPDATED_ACCOUNT_AUTH_FREQUENCY);
    }

    @Test
    @Transactional
    public void getAllTradingInfosByAccountAuthFrequencyIsInShouldWork() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where accountAuthFrequency in DEFAULT_ACCOUNT_AUTH_FREQUENCY or UPDATED_ACCOUNT_AUTH_FREQUENCY
        defaultTradingInfoShouldBeFound("accountAuthFrequency.in=" + DEFAULT_ACCOUNT_AUTH_FREQUENCY + "," + UPDATED_ACCOUNT_AUTH_FREQUENCY);

        // Get all the tradingInfoList where accountAuthFrequency equals to UPDATED_ACCOUNT_AUTH_FREQUENCY
        defaultTradingInfoShouldNotBeFound("accountAuthFrequency.in=" + UPDATED_ACCOUNT_AUTH_FREQUENCY);
    }

    @Test
    @Transactional
    public void getAllTradingInfosByAccountAuthFrequencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where accountAuthFrequency is not null
        defaultTradingInfoShouldBeFound("accountAuthFrequency.specified=true");

        // Get all the tradingInfoList where accountAuthFrequency is null
        defaultTradingInfoShouldNotBeFound("accountAuthFrequency.specified=false");
    }

    @Test
    @Transactional
    public void getAllTradingInfosByExperienceYearIsEqualToSomething() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where experienceYear equals to DEFAULT_EXPERIENCE_YEAR
        defaultTradingInfoShouldBeFound("experienceYear.equals=" + DEFAULT_EXPERIENCE_YEAR);

        // Get all the tradingInfoList where experienceYear equals to UPDATED_EXPERIENCE_YEAR
        defaultTradingInfoShouldNotBeFound("experienceYear.equals=" + UPDATED_EXPERIENCE_YEAR);
    }

    @Test
    @Transactional
    public void getAllTradingInfosByExperienceYearIsInShouldWork() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where experienceYear in DEFAULT_EXPERIENCE_YEAR or UPDATED_EXPERIENCE_YEAR
        defaultTradingInfoShouldBeFound("experienceYear.in=" + DEFAULT_EXPERIENCE_YEAR + "," + UPDATED_EXPERIENCE_YEAR);

        // Get all the tradingInfoList where experienceYear equals to UPDATED_EXPERIENCE_YEAR
        defaultTradingInfoShouldNotBeFound("experienceYear.in=" + UPDATED_EXPERIENCE_YEAR);
    }

    @Test
    @Transactional
    public void getAllTradingInfosByExperienceYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where experienceYear is not null
        defaultTradingInfoShouldBeFound("experienceYear.specified=true");

        // Get all the tradingInfoList where experienceYear is null
        defaultTradingInfoShouldNotBeFound("experienceYear.specified=false");
    }

    @Test
    @Transactional
    public void getAllTradingInfosByExperienceYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where experienceYear greater than or equals to DEFAULT_EXPERIENCE_YEAR
        defaultTradingInfoShouldBeFound("experienceYear.greaterOrEqualThan=" + DEFAULT_EXPERIENCE_YEAR);

        // Get all the tradingInfoList where experienceYear greater than or equals to UPDATED_EXPERIENCE_YEAR
        defaultTradingInfoShouldNotBeFound("experienceYear.greaterOrEqualThan=" + UPDATED_EXPERIENCE_YEAR);
    }

    @Test
    @Transactional
    public void getAllTradingInfosByExperienceYearIsLessThanSomething() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where experienceYear less than or equals to DEFAULT_EXPERIENCE_YEAR
        defaultTradingInfoShouldNotBeFound("experienceYear.lessThan=" + DEFAULT_EXPERIENCE_YEAR);

        // Get all the tradingInfoList where experienceYear less than or equals to UPDATED_EXPERIENCE_YEAR
        defaultTradingInfoShouldBeFound("experienceYear.lessThan=" + UPDATED_EXPERIENCE_YEAR);
    }


    @Test
    @Transactional
    public void getAllTradingInfosByExperienceMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where experienceMonth equals to DEFAULT_EXPERIENCE_MONTH
        defaultTradingInfoShouldBeFound("experienceMonth.equals=" + DEFAULT_EXPERIENCE_MONTH);

        // Get all the tradingInfoList where experienceMonth equals to UPDATED_EXPERIENCE_MONTH
        defaultTradingInfoShouldNotBeFound("experienceMonth.equals=" + UPDATED_EXPERIENCE_MONTH);
    }

    @Test
    @Transactional
    public void getAllTradingInfosByExperienceMonthIsInShouldWork() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where experienceMonth in DEFAULT_EXPERIENCE_MONTH or UPDATED_EXPERIENCE_MONTH
        defaultTradingInfoShouldBeFound("experienceMonth.in=" + DEFAULT_EXPERIENCE_MONTH + "," + UPDATED_EXPERIENCE_MONTH);

        // Get all the tradingInfoList where experienceMonth equals to UPDATED_EXPERIENCE_MONTH
        defaultTradingInfoShouldNotBeFound("experienceMonth.in=" + UPDATED_EXPERIENCE_MONTH);
    }

    @Test
    @Transactional
    public void getAllTradingInfosByExperienceMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where experienceMonth is not null
        defaultTradingInfoShouldBeFound("experienceMonth.specified=true");

        // Get all the tradingInfoList where experienceMonth is null
        defaultTradingInfoShouldNotBeFound("experienceMonth.specified=false");
    }

    @Test
    @Transactional
    public void getAllTradingInfosByExperienceMonthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where experienceMonth greater than or equals to DEFAULT_EXPERIENCE_MONTH
        defaultTradingInfoShouldBeFound("experienceMonth.greaterOrEqualThan=" + DEFAULT_EXPERIENCE_MONTH);

        // Get all the tradingInfoList where experienceMonth greater than or equals to UPDATED_EXPERIENCE_MONTH
        defaultTradingInfoShouldNotBeFound("experienceMonth.greaterOrEqualThan=" + UPDATED_EXPERIENCE_MONTH);
    }

    @Test
    @Transactional
    public void getAllTradingInfosByExperienceMonthIsLessThanSomething() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        // Get all the tradingInfoList where experienceMonth less than or equals to DEFAULT_EXPERIENCE_MONTH
        defaultTradingInfoShouldNotBeFound("experienceMonth.lessThan=" + DEFAULT_EXPERIENCE_MONTH);

        // Get all the tradingInfoList where experienceMonth less than or equals to UPDATED_EXPERIENCE_MONTH
        defaultTradingInfoShouldBeFound("experienceMonth.lessThan=" + UPDATED_EXPERIENCE_MONTH);
    }


    @Test
    @Transactional
    public void getAllTradingInfosByApplicationProspectIsEqualToSomething() throws Exception {
        // Initialize the database
        ApplicationProspect applicationProspect = ApplicationProspectResourceIntTest.createEntity(em);
        em.persist(applicationProspect);
        em.flush();
        tradingInfo.setApplicationProspect(applicationProspect);
        applicationProspect.setTradingInfo(tradingInfo);
        tradingInfoRepository.saveAndFlush(tradingInfo);
        Long applicationProspectId = applicationProspect.getId();

        // Get all the tradingInfoList where applicationProspect equals to applicationProspectId
        defaultTradingInfoShouldBeFound("applicationProspectId.equals=" + applicationProspectId);

        // Get all the tradingInfoList where applicationProspect equals to applicationProspectId + 1
        defaultTradingInfoShouldNotBeFound("applicationProspectId.equals=" + (applicationProspectId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTradingInfoShouldBeFound(String filter) throws Exception {
        restTradingInfoMockMvc.perform(get("/api/trading-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tradingInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].segmentCd").value(hasItem(DEFAULT_SEGMENT_CD.toString())))
            .andExpect(jsonPath("$.[*].planCdEquity").value(hasItem(DEFAULT_PLAN_CD_EQUITY.toString())))
            .andExpect(jsonPath("$.[*].planCdCommodity").value(hasItem(DEFAULT_PLAN_CD_COMMODITY.toString())))
            .andExpect(jsonPath("$.[*].contractNoteMode").value(hasItem(DEFAULT_CONTRACT_NOTE_MODE.toString())))
            .andExpect(jsonPath("$.[*].tradingMode").value(hasItem(DEFAULT_TRADING_MODE.toString())))
            .andExpect(jsonPath("$.[*].interestedInMobileTradeing").value(hasItem(DEFAULT_INTERESTED_IN_MOBILE_TRADEING.booleanValue())))
            .andExpect(jsonPath("$.[*].accountAuthFrequency").value(hasItem(DEFAULT_ACCOUNT_AUTH_FREQUENCY.toString())))
            .andExpect(jsonPath("$.[*].experienceYear").value(hasItem(DEFAULT_EXPERIENCE_YEAR)))
            .andExpect(jsonPath("$.[*].experienceMonth").value(hasItem(DEFAULT_EXPERIENCE_MONTH)));

        // Check, that the count call also returns 1
        restTradingInfoMockMvc.perform(get("/api/trading-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTradingInfoShouldNotBeFound(String filter) throws Exception {
        restTradingInfoMockMvc.perform(get("/api/trading-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTradingInfoMockMvc.perform(get("/api/trading-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTradingInfo() throws Exception {
        // Get the tradingInfo
        restTradingInfoMockMvc.perform(get("/api/trading-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTradingInfo() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        int databaseSizeBeforeUpdate = tradingInfoRepository.findAll().size();

        // Update the tradingInfo
        TradingInfo updatedTradingInfo = tradingInfoRepository.findById(tradingInfo.getId()).get();
        // Disconnect from session so that the updates on updatedTradingInfo are not directly saved in db
        em.detach(updatedTradingInfo);
        updatedTradingInfo
            .segmentCd(UPDATED_SEGMENT_CD)
            .planCdEquity(UPDATED_PLAN_CD_EQUITY)
            .planCdCommodity(UPDATED_PLAN_CD_COMMODITY)
            .contractNoteMode(UPDATED_CONTRACT_NOTE_MODE)
            .tradingMode(UPDATED_TRADING_MODE)
            .interestedInMobileTradeing(UPDATED_INTERESTED_IN_MOBILE_TRADEING)
            .accountAuthFrequency(UPDATED_ACCOUNT_AUTH_FREQUENCY)
            .experienceYear(UPDATED_EXPERIENCE_YEAR)
            .experienceMonth(UPDATED_EXPERIENCE_MONTH);
        TradingInfoDTO tradingInfoDTO = tradingInfoMapper.toDto(updatedTradingInfo);

        restTradingInfoMockMvc.perform(put("/api/trading-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tradingInfoDTO)))
            .andExpect(status().isOk());

        // Validate the TradingInfo in the database
        List<TradingInfo> tradingInfoList = tradingInfoRepository.findAll();
        assertThat(tradingInfoList).hasSize(databaseSizeBeforeUpdate);
        TradingInfo testTradingInfo = tradingInfoList.get(tradingInfoList.size() - 1);
        assertThat(testTradingInfo.getSegmentCd()).isEqualTo(UPDATED_SEGMENT_CD);
        assertThat(testTradingInfo.getPlanCdEquity()).isEqualTo(UPDATED_PLAN_CD_EQUITY);
        assertThat(testTradingInfo.getPlanCdCommodity()).isEqualTo(UPDATED_PLAN_CD_COMMODITY);
        assertThat(testTradingInfo.getContractNoteMode()).isEqualTo(UPDATED_CONTRACT_NOTE_MODE);
        assertThat(testTradingInfo.getTradingMode()).isEqualTo(UPDATED_TRADING_MODE);
        assertThat(testTradingInfo.isInterestedInMobileTradeing()).isEqualTo(UPDATED_INTERESTED_IN_MOBILE_TRADEING);
        assertThat(testTradingInfo.getAccountAuthFrequency()).isEqualTo(UPDATED_ACCOUNT_AUTH_FREQUENCY);
        assertThat(testTradingInfo.getExperienceYear()).isEqualTo(UPDATED_EXPERIENCE_YEAR);
        assertThat(testTradingInfo.getExperienceMonth()).isEqualTo(UPDATED_EXPERIENCE_MONTH);
    }

    @Test
    @Transactional
    public void updateNonExistingTradingInfo() throws Exception {
        int databaseSizeBeforeUpdate = tradingInfoRepository.findAll().size();

        // Create the TradingInfo
        TradingInfoDTO tradingInfoDTO = tradingInfoMapper.toDto(tradingInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTradingInfoMockMvc.perform(put("/api/trading-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tradingInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TradingInfo in the database
        List<TradingInfo> tradingInfoList = tradingInfoRepository.findAll();
        assertThat(tradingInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTradingInfo() throws Exception {
        // Initialize the database
        tradingInfoRepository.saveAndFlush(tradingInfo);

        int databaseSizeBeforeDelete = tradingInfoRepository.findAll().size();

        // Get the tradingInfo
        restTradingInfoMockMvc.perform(delete("/api/trading-infos/{id}", tradingInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TradingInfo> tradingInfoList = tradingInfoRepository.findAll();
        assertThat(tradingInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TradingInfo.class);
        TradingInfo tradingInfo1 = new TradingInfo();
        tradingInfo1.setId(1L);
        TradingInfo tradingInfo2 = new TradingInfo();
        tradingInfo2.setId(tradingInfo1.getId());
        assertThat(tradingInfo1).isEqualTo(tradingInfo2);
        tradingInfo2.setId(2L);
        assertThat(tradingInfo1).isNotEqualTo(tradingInfo2);
        tradingInfo1.setId(null);
        assertThat(tradingInfo1).isNotEqualTo(tradingInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TradingInfoDTO.class);
        TradingInfoDTO tradingInfoDTO1 = new TradingInfoDTO();
        tradingInfoDTO1.setId(1L);
        TradingInfoDTO tradingInfoDTO2 = new TradingInfoDTO();
        assertThat(tradingInfoDTO1).isNotEqualTo(tradingInfoDTO2);
        tradingInfoDTO2.setId(tradingInfoDTO1.getId());
        assertThat(tradingInfoDTO1).isEqualTo(tradingInfoDTO2);
        tradingInfoDTO2.setId(2L);
        assertThat(tradingInfoDTO1).isNotEqualTo(tradingInfoDTO2);
        tradingInfoDTO1.setId(null);
        assertThat(tradingInfoDTO1).isNotEqualTo(tradingInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tradingInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tradingInfoMapper.fromId(null)).isNull();
    }
}
