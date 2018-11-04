package com.eagle.kyc.web.rest;

import com.eagle.kyc.Kyc5App;

import com.eagle.kyc.domain.IdentityVerification;
import com.eagle.kyc.repository.IdentityVerificationRepository;
import com.eagle.kyc.service.IdentityVerificationService;
import com.eagle.kyc.service.dto.IdentityVerificationDTO;
import com.eagle.kyc.service.mapper.IdentityVerificationMapper;
import com.eagle.kyc.web.rest.errors.ExceptionTranslator;
import com.eagle.kyc.service.dto.IdentityVerificationCriteria;
import com.eagle.kyc.service.IdentityVerificationQueryService;

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
 * Test class for the IdentityVerificationResource REST controller.
 *
 * @see IdentityVerificationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Kyc5App.class)
public class IdentityVerificationResourceIntTest {

    private static final String DEFAULT_ADHAAR_NO = "AAAAAAAAAAAA";
    private static final String UPDATED_ADHAAR_NO = "BBBBBBBBBBBB";

    private static final Boolean DEFAULT_AADHAR_NO_VERIFIED = false;
    private static final Boolean UPDATED_AADHAR_NO_VERIFIED = true;

    private static final String DEFAULT_PAN_NO = "AAAAAAAAAA";
    private static final String UPDATED_PAN_NO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PAN_NO_VERIFIED = false;
    private static final Boolean UPDATED_PAN_NO_VERIFIED = true;

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private IdentityVerificationRepository identityVerificationRepository;

    @Autowired
    private IdentityVerificationMapper identityVerificationMapper;
    
    @Autowired
    private IdentityVerificationService identityVerificationService;

    @Autowired
    private IdentityVerificationQueryService identityVerificationQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIdentityVerificationMockMvc;

    private IdentityVerification identityVerification;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IdentityVerificationResource identityVerificationResource = new IdentityVerificationResource(identityVerificationService, identityVerificationQueryService);
        this.restIdentityVerificationMockMvc = MockMvcBuilders.standaloneSetup(identityVerificationResource)
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
    public static IdentityVerification createEntity(EntityManager em) {
        IdentityVerification identityVerification = new IdentityVerification()
            .adhaarNo(DEFAULT_ADHAAR_NO)
            .aadharNoVerified(DEFAULT_AADHAR_NO_VERIFIED)
            .panNo(DEFAULT_PAN_NO)
            .panNoVerified(DEFAULT_PAN_NO_VERIFIED)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH);
        return identityVerification;
    }

    @Before
    public void initTest() {
        identityVerification = createEntity(em);
    }

    @Test
    @Transactional
    public void createIdentityVerification() throws Exception {
        int databaseSizeBeforeCreate = identityVerificationRepository.findAll().size();

        // Create the IdentityVerification
        IdentityVerificationDTO identityVerificationDTO = identityVerificationMapper.toDto(identityVerification);
        restIdentityVerificationMockMvc.perform(post("/api/identity-verifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identityVerificationDTO)))
            .andExpect(status().isCreated());

        // Validate the IdentityVerification in the database
        List<IdentityVerification> identityVerificationList = identityVerificationRepository.findAll();
        assertThat(identityVerificationList).hasSize(databaseSizeBeforeCreate + 1);
        IdentityVerification testIdentityVerification = identityVerificationList.get(identityVerificationList.size() - 1);
        assertThat(testIdentityVerification.getAdhaarNo()).isEqualTo(DEFAULT_ADHAAR_NO);
        assertThat(testIdentityVerification.isAadharNoVerified()).isEqualTo(DEFAULT_AADHAR_NO_VERIFIED);
        assertThat(testIdentityVerification.getPanNo()).isEqualTo(DEFAULT_PAN_NO);
        assertThat(testIdentityVerification.isPanNoVerified()).isEqualTo(DEFAULT_PAN_NO_VERIFIED);
        assertThat(testIdentityVerification.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void createIdentityVerificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = identityVerificationRepository.findAll().size();

        // Create the IdentityVerification with an existing ID
        identityVerification.setId(1L);
        IdentityVerificationDTO identityVerificationDTO = identityVerificationMapper.toDto(identityVerification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdentityVerificationMockMvc.perform(post("/api/identity-verifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identityVerificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IdentityVerification in the database
        List<IdentityVerification> identityVerificationList = identityVerificationRepository.findAll();
        assertThat(identityVerificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAdhaarNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = identityVerificationRepository.findAll().size();
        // set the field null
        identityVerification.setAdhaarNo(null);

        // Create the IdentityVerification, which fails.
        IdentityVerificationDTO identityVerificationDTO = identityVerificationMapper.toDto(identityVerification);

        restIdentityVerificationMockMvc.perform(post("/api/identity-verifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identityVerificationDTO)))
            .andExpect(status().isBadRequest());

        List<IdentityVerification> identityVerificationList = identityVerificationRepository.findAll();
        assertThat(identityVerificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPanNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = identityVerificationRepository.findAll().size();
        // set the field null
        identityVerification.setPanNo(null);

        // Create the IdentityVerification, which fails.
        IdentityVerificationDTO identityVerificationDTO = identityVerificationMapper.toDto(identityVerification);

        restIdentityVerificationMockMvc.perform(post("/api/identity-verifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identityVerificationDTO)))
            .andExpect(status().isBadRequest());

        List<IdentityVerification> identityVerificationList = identityVerificationRepository.findAll();
        assertThat(identityVerificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = identityVerificationRepository.findAll().size();
        // set the field null
        identityVerification.setDateOfBirth(null);

        // Create the IdentityVerification, which fails.
        IdentityVerificationDTO identityVerificationDTO = identityVerificationMapper.toDto(identityVerification);

        restIdentityVerificationMockMvc.perform(post("/api/identity-verifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identityVerificationDTO)))
            .andExpect(status().isBadRequest());

        List<IdentityVerification> identityVerificationList = identityVerificationRepository.findAll();
        assertThat(identityVerificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIdentityVerifications() throws Exception {
        // Initialize the database
        identityVerificationRepository.saveAndFlush(identityVerification);

        // Get all the identityVerificationList
        restIdentityVerificationMockMvc.perform(get("/api/identity-verifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(identityVerification.getId().intValue())))
            .andExpect(jsonPath("$.[*].adhaarNo").value(hasItem(DEFAULT_ADHAAR_NO.toString())))
            .andExpect(jsonPath("$.[*].aadharNoVerified").value(hasItem(DEFAULT_AADHAR_NO_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].panNo").value(hasItem(DEFAULT_PAN_NO.toString())))
            .andExpect(jsonPath("$.[*].panNoVerified").value(hasItem(DEFAULT_PAN_NO_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())));
    }
    
    @Test
    @Transactional
    public void getIdentityVerification() throws Exception {
        // Initialize the database
        identityVerificationRepository.saveAndFlush(identityVerification);

        // Get the identityVerification
        restIdentityVerificationMockMvc.perform(get("/api/identity-verifications/{id}", identityVerification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(identityVerification.getId().intValue()))
            .andExpect(jsonPath("$.adhaarNo").value(DEFAULT_ADHAAR_NO.toString()))
            .andExpect(jsonPath("$.aadharNoVerified").value(DEFAULT_AADHAR_NO_VERIFIED.booleanValue()))
            .andExpect(jsonPath("$.panNo").value(DEFAULT_PAN_NO.toString()))
            .andExpect(jsonPath("$.panNoVerified").value(DEFAULT_PAN_NO_VERIFIED.booleanValue()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()));
    }

    @Test
    @Transactional
    public void getAllIdentityVerificationsByAdhaarNoIsEqualToSomething() throws Exception {
        // Initialize the database
        identityVerificationRepository.saveAndFlush(identityVerification);

        // Get all the identityVerificationList where adhaarNo equals to DEFAULT_ADHAAR_NO
        defaultIdentityVerificationShouldBeFound("adhaarNo.equals=" + DEFAULT_ADHAAR_NO);

        // Get all the identityVerificationList where adhaarNo equals to UPDATED_ADHAAR_NO
        defaultIdentityVerificationShouldNotBeFound("adhaarNo.equals=" + UPDATED_ADHAAR_NO);
    }

    @Test
    @Transactional
    public void getAllIdentityVerificationsByAdhaarNoIsInShouldWork() throws Exception {
        // Initialize the database
        identityVerificationRepository.saveAndFlush(identityVerification);

        // Get all the identityVerificationList where adhaarNo in DEFAULT_ADHAAR_NO or UPDATED_ADHAAR_NO
        defaultIdentityVerificationShouldBeFound("adhaarNo.in=" + DEFAULT_ADHAAR_NO + "," + UPDATED_ADHAAR_NO);

        // Get all the identityVerificationList where adhaarNo equals to UPDATED_ADHAAR_NO
        defaultIdentityVerificationShouldNotBeFound("adhaarNo.in=" + UPDATED_ADHAAR_NO);
    }

    @Test
    @Transactional
    public void getAllIdentityVerificationsByAdhaarNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        identityVerificationRepository.saveAndFlush(identityVerification);

        // Get all the identityVerificationList where adhaarNo is not null
        defaultIdentityVerificationShouldBeFound("adhaarNo.specified=true");

        // Get all the identityVerificationList where adhaarNo is null
        defaultIdentityVerificationShouldNotBeFound("adhaarNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllIdentityVerificationsByAadharNoVerifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        identityVerificationRepository.saveAndFlush(identityVerification);

        // Get all the identityVerificationList where aadharNoVerified equals to DEFAULT_AADHAR_NO_VERIFIED
        defaultIdentityVerificationShouldBeFound("aadharNoVerified.equals=" + DEFAULT_AADHAR_NO_VERIFIED);

        // Get all the identityVerificationList where aadharNoVerified equals to UPDATED_AADHAR_NO_VERIFIED
        defaultIdentityVerificationShouldNotBeFound("aadharNoVerified.equals=" + UPDATED_AADHAR_NO_VERIFIED);
    }

    @Test
    @Transactional
    public void getAllIdentityVerificationsByAadharNoVerifiedIsInShouldWork() throws Exception {
        // Initialize the database
        identityVerificationRepository.saveAndFlush(identityVerification);

        // Get all the identityVerificationList where aadharNoVerified in DEFAULT_AADHAR_NO_VERIFIED or UPDATED_AADHAR_NO_VERIFIED
        defaultIdentityVerificationShouldBeFound("aadharNoVerified.in=" + DEFAULT_AADHAR_NO_VERIFIED + "," + UPDATED_AADHAR_NO_VERIFIED);

        // Get all the identityVerificationList where aadharNoVerified equals to UPDATED_AADHAR_NO_VERIFIED
        defaultIdentityVerificationShouldNotBeFound("aadharNoVerified.in=" + UPDATED_AADHAR_NO_VERIFIED);
    }

    @Test
    @Transactional
    public void getAllIdentityVerificationsByAadharNoVerifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        identityVerificationRepository.saveAndFlush(identityVerification);

        // Get all the identityVerificationList where aadharNoVerified is not null
        defaultIdentityVerificationShouldBeFound("aadharNoVerified.specified=true");

        // Get all the identityVerificationList where aadharNoVerified is null
        defaultIdentityVerificationShouldNotBeFound("aadharNoVerified.specified=false");
    }

    @Test
    @Transactional
    public void getAllIdentityVerificationsByPanNoIsEqualToSomething() throws Exception {
        // Initialize the database
        identityVerificationRepository.saveAndFlush(identityVerification);

        // Get all the identityVerificationList where panNo equals to DEFAULT_PAN_NO
        defaultIdentityVerificationShouldBeFound("panNo.equals=" + DEFAULT_PAN_NO);

        // Get all the identityVerificationList where panNo equals to UPDATED_PAN_NO
        defaultIdentityVerificationShouldNotBeFound("panNo.equals=" + UPDATED_PAN_NO);
    }

    @Test
    @Transactional
    public void getAllIdentityVerificationsByPanNoIsInShouldWork() throws Exception {
        // Initialize the database
        identityVerificationRepository.saveAndFlush(identityVerification);

        // Get all the identityVerificationList where panNo in DEFAULT_PAN_NO or UPDATED_PAN_NO
        defaultIdentityVerificationShouldBeFound("panNo.in=" + DEFAULT_PAN_NO + "," + UPDATED_PAN_NO);

        // Get all the identityVerificationList where panNo equals to UPDATED_PAN_NO
        defaultIdentityVerificationShouldNotBeFound("panNo.in=" + UPDATED_PAN_NO);
    }

    @Test
    @Transactional
    public void getAllIdentityVerificationsByPanNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        identityVerificationRepository.saveAndFlush(identityVerification);

        // Get all the identityVerificationList where panNo is not null
        defaultIdentityVerificationShouldBeFound("panNo.specified=true");

        // Get all the identityVerificationList where panNo is null
        defaultIdentityVerificationShouldNotBeFound("panNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllIdentityVerificationsByPanNoVerifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        identityVerificationRepository.saveAndFlush(identityVerification);

        // Get all the identityVerificationList where panNoVerified equals to DEFAULT_PAN_NO_VERIFIED
        defaultIdentityVerificationShouldBeFound("panNoVerified.equals=" + DEFAULT_PAN_NO_VERIFIED);

        // Get all the identityVerificationList where panNoVerified equals to UPDATED_PAN_NO_VERIFIED
        defaultIdentityVerificationShouldNotBeFound("panNoVerified.equals=" + UPDATED_PAN_NO_VERIFIED);
    }

    @Test
    @Transactional
    public void getAllIdentityVerificationsByPanNoVerifiedIsInShouldWork() throws Exception {
        // Initialize the database
        identityVerificationRepository.saveAndFlush(identityVerification);

        // Get all the identityVerificationList where panNoVerified in DEFAULT_PAN_NO_VERIFIED or UPDATED_PAN_NO_VERIFIED
        defaultIdentityVerificationShouldBeFound("panNoVerified.in=" + DEFAULT_PAN_NO_VERIFIED + "," + UPDATED_PAN_NO_VERIFIED);

        // Get all the identityVerificationList where panNoVerified equals to UPDATED_PAN_NO_VERIFIED
        defaultIdentityVerificationShouldNotBeFound("panNoVerified.in=" + UPDATED_PAN_NO_VERIFIED);
    }

    @Test
    @Transactional
    public void getAllIdentityVerificationsByPanNoVerifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        identityVerificationRepository.saveAndFlush(identityVerification);

        // Get all the identityVerificationList where panNoVerified is not null
        defaultIdentityVerificationShouldBeFound("panNoVerified.specified=true");

        // Get all the identityVerificationList where panNoVerified is null
        defaultIdentityVerificationShouldNotBeFound("panNoVerified.specified=false");
    }

    @Test
    @Transactional
    public void getAllIdentityVerificationsByDateOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        identityVerificationRepository.saveAndFlush(identityVerification);

        // Get all the identityVerificationList where dateOfBirth equals to DEFAULT_DATE_OF_BIRTH
        defaultIdentityVerificationShouldBeFound("dateOfBirth.equals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the identityVerificationList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultIdentityVerificationShouldNotBeFound("dateOfBirth.equals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllIdentityVerificationsByDateOfBirthIsInShouldWork() throws Exception {
        // Initialize the database
        identityVerificationRepository.saveAndFlush(identityVerification);

        // Get all the identityVerificationList where dateOfBirth in DEFAULT_DATE_OF_BIRTH or UPDATED_DATE_OF_BIRTH
        defaultIdentityVerificationShouldBeFound("dateOfBirth.in=" + DEFAULT_DATE_OF_BIRTH + "," + UPDATED_DATE_OF_BIRTH);

        // Get all the identityVerificationList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultIdentityVerificationShouldNotBeFound("dateOfBirth.in=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllIdentityVerificationsByDateOfBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        identityVerificationRepository.saveAndFlush(identityVerification);

        // Get all the identityVerificationList where dateOfBirth is not null
        defaultIdentityVerificationShouldBeFound("dateOfBirth.specified=true");

        // Get all the identityVerificationList where dateOfBirth is null
        defaultIdentityVerificationShouldNotBeFound("dateOfBirth.specified=false");
    }

    @Test
    @Transactional
    public void getAllIdentityVerificationsByDateOfBirthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        identityVerificationRepository.saveAndFlush(identityVerification);

        // Get all the identityVerificationList where dateOfBirth greater than or equals to DEFAULT_DATE_OF_BIRTH
        defaultIdentityVerificationShouldBeFound("dateOfBirth.greaterOrEqualThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the identityVerificationList where dateOfBirth greater than or equals to UPDATED_DATE_OF_BIRTH
        defaultIdentityVerificationShouldNotBeFound("dateOfBirth.greaterOrEqualThan=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllIdentityVerificationsByDateOfBirthIsLessThanSomething() throws Exception {
        // Initialize the database
        identityVerificationRepository.saveAndFlush(identityVerification);

        // Get all the identityVerificationList where dateOfBirth less than or equals to DEFAULT_DATE_OF_BIRTH
        defaultIdentityVerificationShouldNotBeFound("dateOfBirth.lessThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the identityVerificationList where dateOfBirth less than or equals to UPDATED_DATE_OF_BIRTH
        defaultIdentityVerificationShouldBeFound("dateOfBirth.lessThan=" + UPDATED_DATE_OF_BIRTH);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultIdentityVerificationShouldBeFound(String filter) throws Exception {
        restIdentityVerificationMockMvc.perform(get("/api/identity-verifications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(identityVerification.getId().intValue())))
            .andExpect(jsonPath("$.[*].adhaarNo").value(hasItem(DEFAULT_ADHAAR_NO.toString())))
            .andExpect(jsonPath("$.[*].aadharNoVerified").value(hasItem(DEFAULT_AADHAR_NO_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].panNo").value(hasItem(DEFAULT_PAN_NO.toString())))
            .andExpect(jsonPath("$.[*].panNoVerified").value(hasItem(DEFAULT_PAN_NO_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())));

        // Check, that the count call also returns 1
        restIdentityVerificationMockMvc.perform(get("/api/identity-verifications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultIdentityVerificationShouldNotBeFound(String filter) throws Exception {
        restIdentityVerificationMockMvc.perform(get("/api/identity-verifications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIdentityVerificationMockMvc.perform(get("/api/identity-verifications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingIdentityVerification() throws Exception {
        // Get the identityVerification
        restIdentityVerificationMockMvc.perform(get("/api/identity-verifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIdentityVerification() throws Exception {
        // Initialize the database
        identityVerificationRepository.saveAndFlush(identityVerification);

        int databaseSizeBeforeUpdate = identityVerificationRepository.findAll().size();

        // Update the identityVerification
        IdentityVerification updatedIdentityVerification = identityVerificationRepository.findById(identityVerification.getId()).get();
        // Disconnect from session so that the updates on updatedIdentityVerification are not directly saved in db
        em.detach(updatedIdentityVerification);
        updatedIdentityVerification
            .adhaarNo(UPDATED_ADHAAR_NO)
            .aadharNoVerified(UPDATED_AADHAR_NO_VERIFIED)
            .panNo(UPDATED_PAN_NO)
            .panNoVerified(UPDATED_PAN_NO_VERIFIED)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH);
        IdentityVerificationDTO identityVerificationDTO = identityVerificationMapper.toDto(updatedIdentityVerification);

        restIdentityVerificationMockMvc.perform(put("/api/identity-verifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identityVerificationDTO)))
            .andExpect(status().isOk());

        // Validate the IdentityVerification in the database
        List<IdentityVerification> identityVerificationList = identityVerificationRepository.findAll();
        assertThat(identityVerificationList).hasSize(databaseSizeBeforeUpdate);
        IdentityVerification testIdentityVerification = identityVerificationList.get(identityVerificationList.size() - 1);
        assertThat(testIdentityVerification.getAdhaarNo()).isEqualTo(UPDATED_ADHAAR_NO);
        assertThat(testIdentityVerification.isAadharNoVerified()).isEqualTo(UPDATED_AADHAR_NO_VERIFIED);
        assertThat(testIdentityVerification.getPanNo()).isEqualTo(UPDATED_PAN_NO);
        assertThat(testIdentityVerification.isPanNoVerified()).isEqualTo(UPDATED_PAN_NO_VERIFIED);
        assertThat(testIdentityVerification.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void updateNonExistingIdentityVerification() throws Exception {
        int databaseSizeBeforeUpdate = identityVerificationRepository.findAll().size();

        // Create the IdentityVerification
        IdentityVerificationDTO identityVerificationDTO = identityVerificationMapper.toDto(identityVerification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdentityVerificationMockMvc.perform(put("/api/identity-verifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identityVerificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IdentityVerification in the database
        List<IdentityVerification> identityVerificationList = identityVerificationRepository.findAll();
        assertThat(identityVerificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIdentityVerification() throws Exception {
        // Initialize the database
        identityVerificationRepository.saveAndFlush(identityVerification);

        int databaseSizeBeforeDelete = identityVerificationRepository.findAll().size();

        // Get the identityVerification
        restIdentityVerificationMockMvc.perform(delete("/api/identity-verifications/{id}", identityVerification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IdentityVerification> identityVerificationList = identityVerificationRepository.findAll();
        assertThat(identityVerificationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IdentityVerification.class);
        IdentityVerification identityVerification1 = new IdentityVerification();
        identityVerification1.setId(1L);
        IdentityVerification identityVerification2 = new IdentityVerification();
        identityVerification2.setId(identityVerification1.getId());
        assertThat(identityVerification1).isEqualTo(identityVerification2);
        identityVerification2.setId(2L);
        assertThat(identityVerification1).isNotEqualTo(identityVerification2);
        identityVerification1.setId(null);
        assertThat(identityVerification1).isNotEqualTo(identityVerification2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IdentityVerificationDTO.class);
        IdentityVerificationDTO identityVerificationDTO1 = new IdentityVerificationDTO();
        identityVerificationDTO1.setId(1L);
        IdentityVerificationDTO identityVerificationDTO2 = new IdentityVerificationDTO();
        assertThat(identityVerificationDTO1).isNotEqualTo(identityVerificationDTO2);
        identityVerificationDTO2.setId(identityVerificationDTO1.getId());
        assertThat(identityVerificationDTO1).isEqualTo(identityVerificationDTO2);
        identityVerificationDTO2.setId(2L);
        assertThat(identityVerificationDTO1).isNotEqualTo(identityVerificationDTO2);
        identityVerificationDTO1.setId(null);
        assertThat(identityVerificationDTO1).isNotEqualTo(identityVerificationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(identityVerificationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(identityVerificationMapper.fromId(null)).isNull();
    }
}
