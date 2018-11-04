package com.eagle.kyc.web.rest;

import com.eagle.kyc.Kyc5App;

import com.eagle.kyc.domain.TokenVerification;
import com.eagle.kyc.repository.TokenVerificationRepository;
import com.eagle.kyc.service.TokenVerificationService;
import com.eagle.kyc.service.dto.TokenVerificationDTO;
import com.eagle.kyc.service.mapper.TokenVerificationMapper;
import com.eagle.kyc.web.rest.errors.ExceptionTranslator;
import com.eagle.kyc.service.dto.TokenVerificationCriteria;
import com.eagle.kyc.service.TokenVerificationQueryService;

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
 * Test class for the TokenVerificationResource REST controller.
 *
 * @see TokenVerificationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Kyc5App.class)
public class TokenVerificationResourceIntTest {

    private static final String DEFAULT_MOBILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_TOKEN = "AAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private TokenVerificationRepository tokenVerificationRepository;

    @Autowired
    private TokenVerificationMapper tokenVerificationMapper;
    
    @Autowired
    private TokenVerificationService tokenVerificationService;

    @Autowired
    private TokenVerificationQueryService tokenVerificationQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTokenVerificationMockMvc;

    private TokenVerification tokenVerification;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TokenVerificationResource tokenVerificationResource = new TokenVerificationResource(tokenVerificationService, tokenVerificationQueryService);
        this.restTokenVerificationMockMvc = MockMvcBuilders.standaloneSetup(tokenVerificationResource)
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
    public static TokenVerification createEntity(EntityManager em) {
        TokenVerification tokenVerification = new TokenVerification()
            .mobileNo(DEFAULT_MOBILE_NO)
            .token(DEFAULT_TOKEN)
            .status(DEFAULT_STATUS);
        return tokenVerification;
    }

    @Before
    public void initTest() {
        tokenVerification = createEntity(em);
    }

    @Test
    @Transactional
    public void createTokenVerification() throws Exception {
        int databaseSizeBeforeCreate = tokenVerificationRepository.findAll().size();

        // Create the TokenVerification
        TokenVerificationDTO tokenVerificationDTO = tokenVerificationMapper.toDto(tokenVerification);
        restTokenVerificationMockMvc.perform(post("/api/token-verifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tokenVerificationDTO)))
            .andExpect(status().isCreated());

        // Validate the TokenVerification in the database
        List<TokenVerification> tokenVerificationList = tokenVerificationRepository.findAll();
        assertThat(tokenVerificationList).hasSize(databaseSizeBeforeCreate + 1);
        TokenVerification testTokenVerification = tokenVerificationList.get(tokenVerificationList.size() - 1);
        assertThat(testTokenVerification.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testTokenVerification.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testTokenVerification.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createTokenVerificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tokenVerificationRepository.findAll().size();

        // Create the TokenVerification with an existing ID
        tokenVerification.setId(1L);
        TokenVerificationDTO tokenVerificationDTO = tokenVerificationMapper.toDto(tokenVerification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTokenVerificationMockMvc.perform(post("/api/token-verifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tokenVerificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TokenVerification in the database
        List<TokenVerification> tokenVerificationList = tokenVerificationRepository.findAll();
        assertThat(tokenVerificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMobileNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tokenVerificationRepository.findAll().size();
        // set the field null
        tokenVerification.setMobileNo(null);

        // Create the TokenVerification, which fails.
        TokenVerificationDTO tokenVerificationDTO = tokenVerificationMapper.toDto(tokenVerification);

        restTokenVerificationMockMvc.perform(post("/api/token-verifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tokenVerificationDTO)))
            .andExpect(status().isBadRequest());

        List<TokenVerification> tokenVerificationList = tokenVerificationRepository.findAll();
        assertThat(tokenVerificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTokenVerifications() throws Exception {
        // Initialize the database
        tokenVerificationRepository.saveAndFlush(tokenVerification);

        // Get all the tokenVerificationList
        restTokenVerificationMockMvc.perform(get("/api/token-verifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tokenVerification.getId().intValue())))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO.toString())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getTokenVerification() throws Exception {
        // Initialize the database
        tokenVerificationRepository.saveAndFlush(tokenVerification);

        // Get the tokenVerification
        restTokenVerificationMockMvc.perform(get("/api/token-verifications/{id}", tokenVerification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tokenVerification.getId().intValue()))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO.toString()))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getAllTokenVerificationsByMobileNoIsEqualToSomething() throws Exception {
        // Initialize the database
        tokenVerificationRepository.saveAndFlush(tokenVerification);

        // Get all the tokenVerificationList where mobileNo equals to DEFAULT_MOBILE_NO
        defaultTokenVerificationShouldBeFound("mobileNo.equals=" + DEFAULT_MOBILE_NO);

        // Get all the tokenVerificationList where mobileNo equals to UPDATED_MOBILE_NO
        defaultTokenVerificationShouldNotBeFound("mobileNo.equals=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    public void getAllTokenVerificationsByMobileNoIsInShouldWork() throws Exception {
        // Initialize the database
        tokenVerificationRepository.saveAndFlush(tokenVerification);

        // Get all the tokenVerificationList where mobileNo in DEFAULT_MOBILE_NO or UPDATED_MOBILE_NO
        defaultTokenVerificationShouldBeFound("mobileNo.in=" + DEFAULT_MOBILE_NO + "," + UPDATED_MOBILE_NO);

        // Get all the tokenVerificationList where mobileNo equals to UPDATED_MOBILE_NO
        defaultTokenVerificationShouldNotBeFound("mobileNo.in=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    public void getAllTokenVerificationsByMobileNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tokenVerificationRepository.saveAndFlush(tokenVerification);

        // Get all the tokenVerificationList where mobileNo is not null
        defaultTokenVerificationShouldBeFound("mobileNo.specified=true");

        // Get all the tokenVerificationList where mobileNo is null
        defaultTokenVerificationShouldNotBeFound("mobileNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllTokenVerificationsByTokenIsEqualToSomething() throws Exception {
        // Initialize the database
        tokenVerificationRepository.saveAndFlush(tokenVerification);

        // Get all the tokenVerificationList where token equals to DEFAULT_TOKEN
        defaultTokenVerificationShouldBeFound("token.equals=" + DEFAULT_TOKEN);

        // Get all the tokenVerificationList where token equals to UPDATED_TOKEN
        defaultTokenVerificationShouldNotBeFound("token.equals=" + UPDATED_TOKEN);
    }

    @Test
    @Transactional
    public void getAllTokenVerificationsByTokenIsInShouldWork() throws Exception {
        // Initialize the database
        tokenVerificationRepository.saveAndFlush(tokenVerification);

        // Get all the tokenVerificationList where token in DEFAULT_TOKEN or UPDATED_TOKEN
        defaultTokenVerificationShouldBeFound("token.in=" + DEFAULT_TOKEN + "," + UPDATED_TOKEN);

        // Get all the tokenVerificationList where token equals to UPDATED_TOKEN
        defaultTokenVerificationShouldNotBeFound("token.in=" + UPDATED_TOKEN);
    }

    @Test
    @Transactional
    public void getAllTokenVerificationsByTokenIsNullOrNotNull() throws Exception {
        // Initialize the database
        tokenVerificationRepository.saveAndFlush(tokenVerification);

        // Get all the tokenVerificationList where token is not null
        defaultTokenVerificationShouldBeFound("token.specified=true");

        // Get all the tokenVerificationList where token is null
        defaultTokenVerificationShouldNotBeFound("token.specified=false");
    }

    @Test
    @Transactional
    public void getAllTokenVerificationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        tokenVerificationRepository.saveAndFlush(tokenVerification);

        // Get all the tokenVerificationList where status equals to DEFAULT_STATUS
        defaultTokenVerificationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the tokenVerificationList where status equals to UPDATED_STATUS
        defaultTokenVerificationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllTokenVerificationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        tokenVerificationRepository.saveAndFlush(tokenVerification);

        // Get all the tokenVerificationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultTokenVerificationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the tokenVerificationList where status equals to UPDATED_STATUS
        defaultTokenVerificationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllTokenVerificationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        tokenVerificationRepository.saveAndFlush(tokenVerification);

        // Get all the tokenVerificationList where status is not null
        defaultTokenVerificationShouldBeFound("status.specified=true");

        // Get all the tokenVerificationList where status is null
        defaultTokenVerificationShouldNotBeFound("status.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTokenVerificationShouldBeFound(String filter) throws Exception {
        restTokenVerificationMockMvc.perform(get("/api/token-verifications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tokenVerification.getId().intValue())))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO.toString())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restTokenVerificationMockMvc.perform(get("/api/token-verifications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTokenVerificationShouldNotBeFound(String filter) throws Exception {
        restTokenVerificationMockMvc.perform(get("/api/token-verifications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTokenVerificationMockMvc.perform(get("/api/token-verifications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTokenVerification() throws Exception {
        // Get the tokenVerification
        restTokenVerificationMockMvc.perform(get("/api/token-verifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTokenVerification() throws Exception {
        // Initialize the database
        tokenVerificationRepository.saveAndFlush(tokenVerification);

        int databaseSizeBeforeUpdate = tokenVerificationRepository.findAll().size();

        // Update the tokenVerification
        TokenVerification updatedTokenVerification = tokenVerificationRepository.findById(tokenVerification.getId()).get();
        // Disconnect from session so that the updates on updatedTokenVerification are not directly saved in db
        em.detach(updatedTokenVerification);
        updatedTokenVerification
            .mobileNo(UPDATED_MOBILE_NO)
            .token(UPDATED_TOKEN)
            .status(UPDATED_STATUS);
        TokenVerificationDTO tokenVerificationDTO = tokenVerificationMapper.toDto(updatedTokenVerification);

        restTokenVerificationMockMvc.perform(put("/api/token-verifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tokenVerificationDTO)))
            .andExpect(status().isOk());

        // Validate the TokenVerification in the database
        List<TokenVerification> tokenVerificationList = tokenVerificationRepository.findAll();
        assertThat(tokenVerificationList).hasSize(databaseSizeBeforeUpdate);
        TokenVerification testTokenVerification = tokenVerificationList.get(tokenVerificationList.size() - 1);
        assertThat(testTokenVerification.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testTokenVerification.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testTokenVerification.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingTokenVerification() throws Exception {
        int databaseSizeBeforeUpdate = tokenVerificationRepository.findAll().size();

        // Create the TokenVerification
        TokenVerificationDTO tokenVerificationDTO = tokenVerificationMapper.toDto(tokenVerification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTokenVerificationMockMvc.perform(put("/api/token-verifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tokenVerificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TokenVerification in the database
        List<TokenVerification> tokenVerificationList = tokenVerificationRepository.findAll();
        assertThat(tokenVerificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTokenVerification() throws Exception {
        // Initialize the database
        tokenVerificationRepository.saveAndFlush(tokenVerification);

        int databaseSizeBeforeDelete = tokenVerificationRepository.findAll().size();

        // Get the tokenVerification
        restTokenVerificationMockMvc.perform(delete("/api/token-verifications/{id}", tokenVerification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TokenVerification> tokenVerificationList = tokenVerificationRepository.findAll();
        assertThat(tokenVerificationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TokenVerification.class);
        TokenVerification tokenVerification1 = new TokenVerification();
        tokenVerification1.setId(1L);
        TokenVerification tokenVerification2 = new TokenVerification();
        tokenVerification2.setId(tokenVerification1.getId());
        assertThat(tokenVerification1).isEqualTo(tokenVerification2);
        tokenVerification2.setId(2L);
        assertThat(tokenVerification1).isNotEqualTo(tokenVerification2);
        tokenVerification1.setId(null);
        assertThat(tokenVerification1).isNotEqualTo(tokenVerification2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TokenVerificationDTO.class);
        TokenVerificationDTO tokenVerificationDTO1 = new TokenVerificationDTO();
        tokenVerificationDTO1.setId(1L);
        TokenVerificationDTO tokenVerificationDTO2 = new TokenVerificationDTO();
        assertThat(tokenVerificationDTO1).isNotEqualTo(tokenVerificationDTO2);
        tokenVerificationDTO2.setId(tokenVerificationDTO1.getId());
        assertThat(tokenVerificationDTO1).isEqualTo(tokenVerificationDTO2);
        tokenVerificationDTO2.setId(2L);
        assertThat(tokenVerificationDTO1).isNotEqualTo(tokenVerificationDTO2);
        tokenVerificationDTO1.setId(null);
        assertThat(tokenVerificationDTO1).isNotEqualTo(tokenVerificationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tokenVerificationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tokenVerificationMapper.fromId(null)).isNull();
    }
}
