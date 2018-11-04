package com.eagle.kyc.web.rest;

import com.eagle.kyc.Kyc5App;

import com.eagle.kyc.domain.Nominee;
import com.eagle.kyc.domain.Address;
import com.eagle.kyc.domain.ApplicationProspect;
import com.eagle.kyc.repository.NomineeRepository;
import com.eagle.kyc.service.NomineeService;
import com.eagle.kyc.service.dto.NomineeDTO;
import com.eagle.kyc.service.mapper.NomineeMapper;
import com.eagle.kyc.web.rest.errors.ExceptionTranslator;
import com.eagle.kyc.service.dto.NomineeCriteria;
import com.eagle.kyc.service.NomineeQueryService;

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
 * Test class for the NomineeResource REST controller.
 *
 * @see NomineeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Kyc5App.class)
public class NomineeResourceIntTest {

    private static final String DEFAULT_NOMINEE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NOMINEE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RELATION_SHIP = "AAAAAAAAAA";
    private static final String UPDATED_RELATION_SHIP = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GUARDIAN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GUARDIAN_NAME = "BBBBBBBBBB";

    @Autowired
    private NomineeRepository nomineeRepository;

    @Autowired
    private NomineeMapper nomineeMapper;
    
    @Autowired
    private NomineeService nomineeService;

    @Autowired
    private NomineeQueryService nomineeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNomineeMockMvc;

    private Nominee nominee;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NomineeResource nomineeResource = new NomineeResource(nomineeService, nomineeQueryService);
        this.restNomineeMockMvc = MockMvcBuilders.standaloneSetup(nomineeResource)
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
    public static Nominee createEntity(EntityManager em) {
        Nominee nominee = new Nominee()
            .nomineeName(DEFAULT_NOMINEE_NAME)
            .relationShip(DEFAULT_RELATION_SHIP)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .guardianName(DEFAULT_GUARDIAN_NAME);
        return nominee;
    }

    @Before
    public void initTest() {
        nominee = createEntity(em);
    }

    @Test
    @Transactional
    public void createNominee() throws Exception {
        int databaseSizeBeforeCreate = nomineeRepository.findAll().size();

        // Create the Nominee
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);
        restNomineeMockMvc.perform(post("/api/nominees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nomineeDTO)))
            .andExpect(status().isCreated());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeCreate + 1);
        Nominee testNominee = nomineeList.get(nomineeList.size() - 1);
        assertThat(testNominee.getNomineeName()).isEqualTo(DEFAULT_NOMINEE_NAME);
        assertThat(testNominee.getRelationShip()).isEqualTo(DEFAULT_RELATION_SHIP);
        assertThat(testNominee.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testNominee.getGuardianName()).isEqualTo(DEFAULT_GUARDIAN_NAME);
    }

    @Test
    @Transactional
    public void createNomineeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nomineeRepository.findAll().size();

        // Create the Nominee with an existing ID
        nominee.setId(1L);
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNomineeMockMvc.perform(post("/api/nominees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nomineeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomineeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = nomineeRepository.findAll().size();
        // set the field null
        nominee.setNomineeName(null);

        // Create the Nominee, which fails.
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);

        restNomineeMockMvc.perform(post("/api/nominees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nomineeDTO)))
            .andExpect(status().isBadRequest());

        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRelationShipIsRequired() throws Exception {
        int databaseSizeBeforeTest = nomineeRepository.findAll().size();
        // set the field null
        nominee.setRelationShip(null);

        // Create the Nominee, which fails.
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);

        restNomineeMockMvc.perform(post("/api/nominees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nomineeDTO)))
            .andExpect(status().isBadRequest());

        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = nomineeRepository.findAll().size();
        // set the field null
        nominee.setDateOfBirth(null);

        // Create the Nominee, which fails.
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);

        restNomineeMockMvc.perform(post("/api/nominees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nomineeDTO)))
            .andExpect(status().isBadRequest());

        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGuardianNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = nomineeRepository.findAll().size();
        // set the field null
        nominee.setGuardianName(null);

        // Create the Nominee, which fails.
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);

        restNomineeMockMvc.perform(post("/api/nominees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nomineeDTO)))
            .andExpect(status().isBadRequest());

        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNominees() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList
        restNomineeMockMvc.perform(get("/api/nominees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nominee.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomineeName").value(hasItem(DEFAULT_NOMINEE_NAME.toString())))
            .andExpect(jsonPath("$.[*].relationShip").value(hasItem(DEFAULT_RELATION_SHIP.toString())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].guardianName").value(hasItem(DEFAULT_GUARDIAN_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getNominee() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get the nominee
        restNomineeMockMvc.perform(get("/api/nominees/{id}", nominee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nominee.getId().intValue()))
            .andExpect(jsonPath("$.nomineeName").value(DEFAULT_NOMINEE_NAME.toString()))
            .andExpect(jsonPath("$.relationShip").value(DEFAULT_RELATION_SHIP.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.guardianName").value(DEFAULT_GUARDIAN_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllNomineesByNomineeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where nomineeName equals to DEFAULT_NOMINEE_NAME
        defaultNomineeShouldBeFound("nomineeName.equals=" + DEFAULT_NOMINEE_NAME);

        // Get all the nomineeList where nomineeName equals to UPDATED_NOMINEE_NAME
        defaultNomineeShouldNotBeFound("nomineeName.equals=" + UPDATED_NOMINEE_NAME);
    }

    @Test
    @Transactional
    public void getAllNomineesByNomineeNameIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where nomineeName in DEFAULT_NOMINEE_NAME or UPDATED_NOMINEE_NAME
        defaultNomineeShouldBeFound("nomineeName.in=" + DEFAULT_NOMINEE_NAME + "," + UPDATED_NOMINEE_NAME);

        // Get all the nomineeList where nomineeName equals to UPDATED_NOMINEE_NAME
        defaultNomineeShouldNotBeFound("nomineeName.in=" + UPDATED_NOMINEE_NAME);
    }

    @Test
    @Transactional
    public void getAllNomineesByNomineeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where nomineeName is not null
        defaultNomineeShouldBeFound("nomineeName.specified=true");

        // Get all the nomineeList where nomineeName is null
        defaultNomineeShouldNotBeFound("nomineeName.specified=false");
    }

    @Test
    @Transactional
    public void getAllNomineesByRelationShipIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where relationShip equals to DEFAULT_RELATION_SHIP
        defaultNomineeShouldBeFound("relationShip.equals=" + DEFAULT_RELATION_SHIP);

        // Get all the nomineeList where relationShip equals to UPDATED_RELATION_SHIP
        defaultNomineeShouldNotBeFound("relationShip.equals=" + UPDATED_RELATION_SHIP);
    }

    @Test
    @Transactional
    public void getAllNomineesByRelationShipIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where relationShip in DEFAULT_RELATION_SHIP or UPDATED_RELATION_SHIP
        defaultNomineeShouldBeFound("relationShip.in=" + DEFAULT_RELATION_SHIP + "," + UPDATED_RELATION_SHIP);

        // Get all the nomineeList where relationShip equals to UPDATED_RELATION_SHIP
        defaultNomineeShouldNotBeFound("relationShip.in=" + UPDATED_RELATION_SHIP);
    }

    @Test
    @Transactional
    public void getAllNomineesByRelationShipIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where relationShip is not null
        defaultNomineeShouldBeFound("relationShip.specified=true");

        // Get all the nomineeList where relationShip is null
        defaultNomineeShouldNotBeFound("relationShip.specified=false");
    }

    @Test
    @Transactional
    public void getAllNomineesByDateOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where dateOfBirth equals to DEFAULT_DATE_OF_BIRTH
        defaultNomineeShouldBeFound("dateOfBirth.equals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the nomineeList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultNomineeShouldNotBeFound("dateOfBirth.equals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllNomineesByDateOfBirthIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where dateOfBirth in DEFAULT_DATE_OF_BIRTH or UPDATED_DATE_OF_BIRTH
        defaultNomineeShouldBeFound("dateOfBirth.in=" + DEFAULT_DATE_OF_BIRTH + "," + UPDATED_DATE_OF_BIRTH);

        // Get all the nomineeList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultNomineeShouldNotBeFound("dateOfBirth.in=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllNomineesByDateOfBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where dateOfBirth is not null
        defaultNomineeShouldBeFound("dateOfBirth.specified=true");

        // Get all the nomineeList where dateOfBirth is null
        defaultNomineeShouldNotBeFound("dateOfBirth.specified=false");
    }

    @Test
    @Transactional
    public void getAllNomineesByDateOfBirthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where dateOfBirth greater than or equals to DEFAULT_DATE_OF_BIRTH
        defaultNomineeShouldBeFound("dateOfBirth.greaterOrEqualThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the nomineeList where dateOfBirth greater than or equals to UPDATED_DATE_OF_BIRTH
        defaultNomineeShouldNotBeFound("dateOfBirth.greaterOrEqualThan=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    public void getAllNomineesByDateOfBirthIsLessThanSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where dateOfBirth less than or equals to DEFAULT_DATE_OF_BIRTH
        defaultNomineeShouldNotBeFound("dateOfBirth.lessThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the nomineeList where dateOfBirth less than or equals to UPDATED_DATE_OF_BIRTH
        defaultNomineeShouldBeFound("dateOfBirth.lessThan=" + UPDATED_DATE_OF_BIRTH);
    }


    @Test
    @Transactional
    public void getAllNomineesByGuardianNameIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where guardianName equals to DEFAULT_GUARDIAN_NAME
        defaultNomineeShouldBeFound("guardianName.equals=" + DEFAULT_GUARDIAN_NAME);

        // Get all the nomineeList where guardianName equals to UPDATED_GUARDIAN_NAME
        defaultNomineeShouldNotBeFound("guardianName.equals=" + UPDATED_GUARDIAN_NAME);
    }

    @Test
    @Transactional
    public void getAllNomineesByGuardianNameIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where guardianName in DEFAULT_GUARDIAN_NAME or UPDATED_GUARDIAN_NAME
        defaultNomineeShouldBeFound("guardianName.in=" + DEFAULT_GUARDIAN_NAME + "," + UPDATED_GUARDIAN_NAME);

        // Get all the nomineeList where guardianName equals to UPDATED_GUARDIAN_NAME
        defaultNomineeShouldNotBeFound("guardianName.in=" + UPDATED_GUARDIAN_NAME);
    }

    @Test
    @Transactional
    public void getAllNomineesByGuardianNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where guardianName is not null
        defaultNomineeShouldBeFound("guardianName.specified=true");

        // Get all the nomineeList where guardianName is null
        defaultNomineeShouldNotBeFound("guardianName.specified=false");
    }

    @Test
    @Transactional
    public void getAllNomineesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        Address address = AddressResourceIntTest.createEntity(em);
        em.persist(address);
        em.flush();
        nominee.addAddress(address);
        nomineeRepository.saveAndFlush(nominee);
        Long addressId = address.getId();

        // Get all the nomineeList where address equals to addressId
        defaultNomineeShouldBeFound("addressId.equals=" + addressId);

        // Get all the nomineeList where address equals to addressId + 1
        defaultNomineeShouldNotBeFound("addressId.equals=" + (addressId + 1));
    }


    @Test
    @Transactional
    public void getAllNomineesByApplicationProspectIsEqualToSomething() throws Exception {
        // Initialize the database
        ApplicationProspect applicationProspect = ApplicationProspectResourceIntTest.createEntity(em);
        em.persist(applicationProspect);
        em.flush();
        nominee.setApplicationProspect(applicationProspect);
        applicationProspect.setNominee(nominee);
        nomineeRepository.saveAndFlush(nominee);
        Long applicationProspectId = applicationProspect.getId();

        // Get all the nomineeList where applicationProspect equals to applicationProspectId
        defaultNomineeShouldBeFound("applicationProspectId.equals=" + applicationProspectId);

        // Get all the nomineeList where applicationProspect equals to applicationProspectId + 1
        defaultNomineeShouldNotBeFound("applicationProspectId.equals=" + (applicationProspectId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultNomineeShouldBeFound(String filter) throws Exception {
        restNomineeMockMvc.perform(get("/api/nominees?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nominee.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomineeName").value(hasItem(DEFAULT_NOMINEE_NAME.toString())))
            .andExpect(jsonPath("$.[*].relationShip").value(hasItem(DEFAULT_RELATION_SHIP.toString())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].guardianName").value(hasItem(DEFAULT_GUARDIAN_NAME.toString())));

        // Check, that the count call also returns 1
        restNomineeMockMvc.perform(get("/api/nominees/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultNomineeShouldNotBeFound(String filter) throws Exception {
        restNomineeMockMvc.perform(get("/api/nominees?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNomineeMockMvc.perform(get("/api/nominees/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingNominee() throws Exception {
        // Get the nominee
        restNomineeMockMvc.perform(get("/api/nominees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNominee() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        int databaseSizeBeforeUpdate = nomineeRepository.findAll().size();

        // Update the nominee
        Nominee updatedNominee = nomineeRepository.findById(nominee.getId()).get();
        // Disconnect from session so that the updates on updatedNominee are not directly saved in db
        em.detach(updatedNominee);
        updatedNominee
            .nomineeName(UPDATED_NOMINEE_NAME)
            .relationShip(UPDATED_RELATION_SHIP)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .guardianName(UPDATED_GUARDIAN_NAME);
        NomineeDTO nomineeDTO = nomineeMapper.toDto(updatedNominee);

        restNomineeMockMvc.perform(put("/api/nominees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nomineeDTO)))
            .andExpect(status().isOk());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeUpdate);
        Nominee testNominee = nomineeList.get(nomineeList.size() - 1);
        assertThat(testNominee.getNomineeName()).isEqualTo(UPDATED_NOMINEE_NAME);
        assertThat(testNominee.getRelationShip()).isEqualTo(UPDATED_RELATION_SHIP);
        assertThat(testNominee.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testNominee.getGuardianName()).isEqualTo(UPDATED_GUARDIAN_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingNominee() throws Exception {
        int databaseSizeBeforeUpdate = nomineeRepository.findAll().size();

        // Create the Nominee
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNomineeMockMvc.perform(put("/api/nominees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nomineeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNominee() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        int databaseSizeBeforeDelete = nomineeRepository.findAll().size();

        // Get the nominee
        restNomineeMockMvc.perform(delete("/api/nominees/{id}", nominee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nominee.class);
        Nominee nominee1 = new Nominee();
        nominee1.setId(1L);
        Nominee nominee2 = new Nominee();
        nominee2.setId(nominee1.getId());
        assertThat(nominee1).isEqualTo(nominee2);
        nominee2.setId(2L);
        assertThat(nominee1).isNotEqualTo(nominee2);
        nominee1.setId(null);
        assertThat(nominee1).isNotEqualTo(nominee2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NomineeDTO.class);
        NomineeDTO nomineeDTO1 = new NomineeDTO();
        nomineeDTO1.setId(1L);
        NomineeDTO nomineeDTO2 = new NomineeDTO();
        assertThat(nomineeDTO1).isNotEqualTo(nomineeDTO2);
        nomineeDTO2.setId(nomineeDTO1.getId());
        assertThat(nomineeDTO1).isEqualTo(nomineeDTO2);
        nomineeDTO2.setId(2L);
        assertThat(nomineeDTO1).isNotEqualTo(nomineeDTO2);
        nomineeDTO1.setId(null);
        assertThat(nomineeDTO1).isNotEqualTo(nomineeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(nomineeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(nomineeMapper.fromId(null)).isNull();
    }
}
