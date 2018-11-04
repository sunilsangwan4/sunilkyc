package com.eagle.kyc.web.rest;

import com.eagle.kyc.Kyc5App;

import com.eagle.kyc.domain.EmailVerification;
import com.eagle.kyc.repository.EmailVerificationRepository;
import com.eagle.kyc.service.EmailVerificationService;
import com.eagle.kyc.service.dto.EmailVerificationDTO;
import com.eagle.kyc.service.mapper.EmailVerificationMapper;
import com.eagle.kyc.web.rest.errors.ExceptionTranslator;
import com.eagle.kyc.service.dto.EmailVerificationCriteria;
import com.eagle.kyc.service.EmailVerificationQueryService;

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
 * Test class for the EmailVerificationResource REST controller.
 *
 * @see EmailVerificationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Kyc5App.class)
public class EmailVerificationResourceIntTest {

    private static final String DEFAULT_EMAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TOKEN = "AAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    @Autowired
    private EmailVerificationMapper emailVerificationMapper;
    
    @Autowired
    private EmailVerificationService emailVerificationService;

    @Autowired
    private EmailVerificationQueryService emailVerificationQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmailVerificationMockMvc;

    private EmailVerification emailVerification;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmailVerificationResource emailVerificationResource = new EmailVerificationResource(emailVerificationService, emailVerificationQueryService);
        this.restEmailVerificationMockMvc = MockMvcBuilders.standaloneSetup(emailVerificationResource)
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
    public static EmailVerification createEntity(EntityManager em) {
        EmailVerification emailVerification = new EmailVerification()
            .emailId(DEFAULT_EMAIL_ID)
            .token(DEFAULT_TOKEN)
            .status(DEFAULT_STATUS);
        return emailVerification;
    }

    @Before
    public void initTest() {
        emailVerification = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmailVerification() throws Exception {
        int databaseSizeBeforeCreate = emailVerificationRepository.findAll().size();

        // Create the EmailVerification
        EmailVerificationDTO emailVerificationDTO = emailVerificationMapper.toDto(emailVerification);
        restEmailVerificationMockMvc.perform(post("/api/email-verifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailVerificationDTO)))
            .andExpect(status().isCreated());

        // Validate the EmailVerification in the database
        List<EmailVerification> emailVerificationList = emailVerificationRepository.findAll();
        assertThat(emailVerificationList).hasSize(databaseSizeBeforeCreate + 1);
        EmailVerification testEmailVerification = emailVerificationList.get(emailVerificationList.size() - 1);
        assertThat(testEmailVerification.getEmailId()).isEqualTo(DEFAULT_EMAIL_ID);
        assertThat(testEmailVerification.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testEmailVerification.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createEmailVerificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emailVerificationRepository.findAll().size();

        // Create the EmailVerification with an existing ID
        emailVerification.setId(1L);
        EmailVerificationDTO emailVerificationDTO = emailVerificationMapper.toDto(emailVerification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailVerificationMockMvc.perform(post("/api/email-verifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailVerificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailVerification in the database
        List<EmailVerification> emailVerificationList = emailVerificationRepository.findAll();
        assertThat(emailVerificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEmailIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailVerificationRepository.findAll().size();
        // set the field null
        emailVerification.setEmailId(null);

        // Create the EmailVerification, which fails.
        EmailVerificationDTO emailVerificationDTO = emailVerificationMapper.toDto(emailVerification);

        restEmailVerificationMockMvc.perform(post("/api/email-verifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailVerificationDTO)))
            .andExpect(status().isBadRequest());

        List<EmailVerification> emailVerificationList = emailVerificationRepository.findAll();
        assertThat(emailVerificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmailVerifications() throws Exception {
        // Initialize the database
        emailVerificationRepository.saveAndFlush(emailVerification);

        // Get all the emailVerificationList
        restEmailVerificationMockMvc.perform(get("/api/email-verifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailVerification.getId().intValue())))
            .andExpect(jsonPath("$.[*].emailId").value(hasItem(DEFAULT_EMAIL_ID.toString())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getEmailVerification() throws Exception {
        // Initialize the database
        emailVerificationRepository.saveAndFlush(emailVerification);

        // Get the emailVerification
        restEmailVerificationMockMvc.perform(get("/api/email-verifications/{id}", emailVerification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emailVerification.getId().intValue()))
            .andExpect(jsonPath("$.emailId").value(DEFAULT_EMAIL_ID.toString()))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getAllEmailVerificationsByEmailIdIsEqualToSomething() throws Exception {
        // Initialize the database
        emailVerificationRepository.saveAndFlush(emailVerification);

        // Get all the emailVerificationList where emailId equals to DEFAULT_EMAIL_ID
        defaultEmailVerificationShouldBeFound("emailId.equals=" + DEFAULT_EMAIL_ID);

        // Get all the emailVerificationList where emailId equals to UPDATED_EMAIL_ID
        defaultEmailVerificationShouldNotBeFound("emailId.equals=" + UPDATED_EMAIL_ID);
    }

    @Test
    @Transactional
    public void getAllEmailVerificationsByEmailIdIsInShouldWork() throws Exception {
        // Initialize the database
        emailVerificationRepository.saveAndFlush(emailVerification);

        // Get all the emailVerificationList where emailId in DEFAULT_EMAIL_ID or UPDATED_EMAIL_ID
        defaultEmailVerificationShouldBeFound("emailId.in=" + DEFAULT_EMAIL_ID + "," + UPDATED_EMAIL_ID);

        // Get all the emailVerificationList where emailId equals to UPDATED_EMAIL_ID
        defaultEmailVerificationShouldNotBeFound("emailId.in=" + UPDATED_EMAIL_ID);
    }

    @Test
    @Transactional
    public void getAllEmailVerificationsByEmailIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailVerificationRepository.saveAndFlush(emailVerification);

        // Get all the emailVerificationList where emailId is not null
        defaultEmailVerificationShouldBeFound("emailId.specified=true");

        // Get all the emailVerificationList where emailId is null
        defaultEmailVerificationShouldNotBeFound("emailId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmailVerificationsByTokenIsEqualToSomething() throws Exception {
        // Initialize the database
        emailVerificationRepository.saveAndFlush(emailVerification);

        // Get all the emailVerificationList where token equals to DEFAULT_TOKEN
        defaultEmailVerificationShouldBeFound("token.equals=" + DEFAULT_TOKEN);

        // Get all the emailVerificationList where token equals to UPDATED_TOKEN
        defaultEmailVerificationShouldNotBeFound("token.equals=" + UPDATED_TOKEN);
    }

    @Test
    @Transactional
    public void getAllEmailVerificationsByTokenIsInShouldWork() throws Exception {
        // Initialize the database
        emailVerificationRepository.saveAndFlush(emailVerification);

        // Get all the emailVerificationList where token in DEFAULT_TOKEN or UPDATED_TOKEN
        defaultEmailVerificationShouldBeFound("token.in=" + DEFAULT_TOKEN + "," + UPDATED_TOKEN);

        // Get all the emailVerificationList where token equals to UPDATED_TOKEN
        defaultEmailVerificationShouldNotBeFound("token.in=" + UPDATED_TOKEN);
    }

    @Test
    @Transactional
    public void getAllEmailVerificationsByTokenIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailVerificationRepository.saveAndFlush(emailVerification);

        // Get all the emailVerificationList where token is not null
        defaultEmailVerificationShouldBeFound("token.specified=true");

        // Get all the emailVerificationList where token is null
        defaultEmailVerificationShouldNotBeFound("token.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmailVerificationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        emailVerificationRepository.saveAndFlush(emailVerification);

        // Get all the emailVerificationList where status equals to DEFAULT_STATUS
        defaultEmailVerificationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the emailVerificationList where status equals to UPDATED_STATUS
        defaultEmailVerificationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmailVerificationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        emailVerificationRepository.saveAndFlush(emailVerification);

        // Get all the emailVerificationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultEmailVerificationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the emailVerificationList where status equals to UPDATED_STATUS
        defaultEmailVerificationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmailVerificationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailVerificationRepository.saveAndFlush(emailVerification);

        // Get all the emailVerificationList where status is not null
        defaultEmailVerificationShouldBeFound("status.specified=true");

        // Get all the emailVerificationList where status is null
        defaultEmailVerificationShouldNotBeFound("status.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultEmailVerificationShouldBeFound(String filter) throws Exception {
        restEmailVerificationMockMvc.perform(get("/api/email-verifications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailVerification.getId().intValue())))
            .andExpect(jsonPath("$.[*].emailId").value(hasItem(DEFAULT_EMAIL_ID.toString())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restEmailVerificationMockMvc.perform(get("/api/email-verifications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultEmailVerificationShouldNotBeFound(String filter) throws Exception {
        restEmailVerificationMockMvc.perform(get("/api/email-verifications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmailVerificationMockMvc.perform(get("/api/email-verifications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEmailVerification() throws Exception {
        // Get the emailVerification
        restEmailVerificationMockMvc.perform(get("/api/email-verifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmailVerification() throws Exception {
        // Initialize the database
        emailVerificationRepository.saveAndFlush(emailVerification);

        int databaseSizeBeforeUpdate = emailVerificationRepository.findAll().size();

        // Update the emailVerification
        EmailVerification updatedEmailVerification = emailVerificationRepository.findById(emailVerification.getId()).get();
        // Disconnect from session so that the updates on updatedEmailVerification are not directly saved in db
        em.detach(updatedEmailVerification);
        updatedEmailVerification
            .emailId(UPDATED_EMAIL_ID)
            .token(UPDATED_TOKEN)
            .status(UPDATED_STATUS);
        EmailVerificationDTO emailVerificationDTO = emailVerificationMapper.toDto(updatedEmailVerification);

        restEmailVerificationMockMvc.perform(put("/api/email-verifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailVerificationDTO)))
            .andExpect(status().isOk());

        // Validate the EmailVerification in the database
        List<EmailVerification> emailVerificationList = emailVerificationRepository.findAll();
        assertThat(emailVerificationList).hasSize(databaseSizeBeforeUpdate);
        EmailVerification testEmailVerification = emailVerificationList.get(emailVerificationList.size() - 1);
        assertThat(testEmailVerification.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testEmailVerification.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testEmailVerification.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingEmailVerification() throws Exception {
        int databaseSizeBeforeUpdate = emailVerificationRepository.findAll().size();

        // Create the EmailVerification
        EmailVerificationDTO emailVerificationDTO = emailVerificationMapper.toDto(emailVerification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailVerificationMockMvc.perform(put("/api/email-verifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailVerificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailVerification in the database
        List<EmailVerification> emailVerificationList = emailVerificationRepository.findAll();
        assertThat(emailVerificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmailVerification() throws Exception {
        // Initialize the database
        emailVerificationRepository.saveAndFlush(emailVerification);

        int databaseSizeBeforeDelete = emailVerificationRepository.findAll().size();

        // Get the emailVerification
        restEmailVerificationMockMvc.perform(delete("/api/email-verifications/{id}", emailVerification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmailVerification> emailVerificationList = emailVerificationRepository.findAll();
        assertThat(emailVerificationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailVerification.class);
        EmailVerification emailVerification1 = new EmailVerification();
        emailVerification1.setId(1L);
        EmailVerification emailVerification2 = new EmailVerification();
        emailVerification2.setId(emailVerification1.getId());
        assertThat(emailVerification1).isEqualTo(emailVerification2);
        emailVerification2.setId(2L);
        assertThat(emailVerification1).isNotEqualTo(emailVerification2);
        emailVerification1.setId(null);
        assertThat(emailVerification1).isNotEqualTo(emailVerification2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailVerificationDTO.class);
        EmailVerificationDTO emailVerificationDTO1 = new EmailVerificationDTO();
        emailVerificationDTO1.setId(1L);
        EmailVerificationDTO emailVerificationDTO2 = new EmailVerificationDTO();
        assertThat(emailVerificationDTO1).isNotEqualTo(emailVerificationDTO2);
        emailVerificationDTO2.setId(emailVerificationDTO1.getId());
        assertThat(emailVerificationDTO1).isEqualTo(emailVerificationDTO2);
        emailVerificationDTO2.setId(2L);
        assertThat(emailVerificationDTO1).isNotEqualTo(emailVerificationDTO2);
        emailVerificationDTO1.setId(null);
        assertThat(emailVerificationDTO1).isNotEqualTo(emailVerificationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(emailVerificationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(emailVerificationMapper.fromId(null)).isNull();
    }
}
