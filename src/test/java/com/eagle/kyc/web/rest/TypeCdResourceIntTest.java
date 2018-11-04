package com.eagle.kyc.web.rest;

import com.eagle.kyc.Kyc5App;

import com.eagle.kyc.domain.TypeCd;
import com.eagle.kyc.domain.GroupCd;
import com.eagle.kyc.repository.TypeCdRepository;
import com.eagle.kyc.service.TypeCdService;
import com.eagle.kyc.service.dto.TypeCdDTO;
import com.eagle.kyc.service.mapper.TypeCdMapper;
import com.eagle.kyc.web.rest.errors.ExceptionTranslator;
import com.eagle.kyc.service.dto.TypeCdCriteria;
import com.eagle.kyc.service.TypeCdQueryService;

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
 * Test class for the TypeCdResource REST controller.
 *
 * @see TypeCdResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Kyc5App.class)
public class TypeCdResourceIntTest {

    private static final String DEFAULT_TYPE_CD = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_CD = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TypeCdRepository typeCdRepository;

    @Autowired
    private TypeCdMapper typeCdMapper;
    
    @Autowired
    private TypeCdService typeCdService;

    @Autowired
    private TypeCdQueryService typeCdQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeCdMockMvc;

    private TypeCd typeCd;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeCdResource typeCdResource = new TypeCdResource(typeCdService, typeCdQueryService);
        this.restTypeCdMockMvc = MockMvcBuilders.standaloneSetup(typeCdResource)
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
    public static TypeCd createEntity(EntityManager em) {
        TypeCd typeCd = new TypeCd()
            .typeCd(DEFAULT_TYPE_CD)
            .typeDescription(DEFAULT_TYPE_DESCRIPTION);
        return typeCd;
    }

    @Before
    public void initTest() {
        typeCd = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeCd() throws Exception {
        int databaseSizeBeforeCreate = typeCdRepository.findAll().size();

        // Create the TypeCd
        TypeCdDTO typeCdDTO = typeCdMapper.toDto(typeCd);
        restTypeCdMockMvc.perform(post("/api/type-cds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCdDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeCd in the database
        List<TypeCd> typeCdList = typeCdRepository.findAll();
        assertThat(typeCdList).hasSize(databaseSizeBeforeCreate + 1);
        TypeCd testTypeCd = typeCdList.get(typeCdList.size() - 1);
        assertThat(testTypeCd.getTypeCd()).isEqualTo(DEFAULT_TYPE_CD);
        assertThat(testTypeCd.getTypeDescription()).isEqualTo(DEFAULT_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTypeCdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeCdRepository.findAll().size();

        // Create the TypeCd with an existing ID
        typeCd.setId(1L);
        TypeCdDTO typeCdDTO = typeCdMapper.toDto(typeCd);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeCdMockMvc.perform(post("/api/type-cds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCdDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeCd in the database
        List<TypeCd> typeCdList = typeCdRepository.findAll();
        assertThat(typeCdList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeCdIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeCdRepository.findAll().size();
        // set the field null
        typeCd.setTypeCd(null);

        // Create the TypeCd, which fails.
        TypeCdDTO typeCdDTO = typeCdMapper.toDto(typeCd);

        restTypeCdMockMvc.perform(post("/api/type-cds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCdDTO)))
            .andExpect(status().isBadRequest());

        List<TypeCd> typeCdList = typeCdRepository.findAll();
        assertThat(typeCdList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeCdRepository.findAll().size();
        // set the field null
        typeCd.setTypeDescription(null);

        // Create the TypeCd, which fails.
        TypeCdDTO typeCdDTO = typeCdMapper.toDto(typeCd);

        restTypeCdMockMvc.perform(post("/api/type-cds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCdDTO)))
            .andExpect(status().isBadRequest());

        List<TypeCd> typeCdList = typeCdRepository.findAll();
        assertThat(typeCdList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeCds() throws Exception {
        // Initialize the database
        typeCdRepository.saveAndFlush(typeCd);

        // Get all the typeCdList
        restTypeCdMockMvc.perform(get("/api/type-cds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeCd.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeCd").value(hasItem(DEFAULT_TYPE_CD.toString())))
            .andExpect(jsonPath("$.[*].typeDescription").value(hasItem(DEFAULT_TYPE_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getTypeCd() throws Exception {
        // Initialize the database
        typeCdRepository.saveAndFlush(typeCd);

        // Get the typeCd
        restTypeCdMockMvc.perform(get("/api/type-cds/{id}", typeCd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeCd.getId().intValue()))
            .andExpect(jsonPath("$.typeCd").value(DEFAULT_TYPE_CD.toString()))
            .andExpect(jsonPath("$.typeDescription").value(DEFAULT_TYPE_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllTypeCdsByTypeCdIsEqualToSomething() throws Exception {
        // Initialize the database
        typeCdRepository.saveAndFlush(typeCd);

        // Get all the typeCdList where typeCd equals to DEFAULT_TYPE_CD
        defaultTypeCdShouldBeFound("typeCd.equals=" + DEFAULT_TYPE_CD);

        // Get all the typeCdList where typeCd equals to UPDATED_TYPE_CD
        defaultTypeCdShouldNotBeFound("typeCd.equals=" + UPDATED_TYPE_CD);
    }

    @Test
    @Transactional
    public void getAllTypeCdsByTypeCdIsInShouldWork() throws Exception {
        // Initialize the database
        typeCdRepository.saveAndFlush(typeCd);

        // Get all the typeCdList where typeCd in DEFAULT_TYPE_CD or UPDATED_TYPE_CD
        defaultTypeCdShouldBeFound("typeCd.in=" + DEFAULT_TYPE_CD + "," + UPDATED_TYPE_CD);

        // Get all the typeCdList where typeCd equals to UPDATED_TYPE_CD
        defaultTypeCdShouldNotBeFound("typeCd.in=" + UPDATED_TYPE_CD);
    }

    @Test
    @Transactional
    public void getAllTypeCdsByTypeCdIsNullOrNotNull() throws Exception {
        // Initialize the database
        typeCdRepository.saveAndFlush(typeCd);

        // Get all the typeCdList where typeCd is not null
        defaultTypeCdShouldBeFound("typeCd.specified=true");

        // Get all the typeCdList where typeCd is null
        defaultTypeCdShouldNotBeFound("typeCd.specified=false");
    }

    @Test
    @Transactional
    public void getAllTypeCdsByTypeDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        typeCdRepository.saveAndFlush(typeCd);

        // Get all the typeCdList where typeDescription equals to DEFAULT_TYPE_DESCRIPTION
        defaultTypeCdShouldBeFound("typeDescription.equals=" + DEFAULT_TYPE_DESCRIPTION);

        // Get all the typeCdList where typeDescription equals to UPDATED_TYPE_DESCRIPTION
        defaultTypeCdShouldNotBeFound("typeDescription.equals=" + UPDATED_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTypeCdsByTypeDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        typeCdRepository.saveAndFlush(typeCd);

        // Get all the typeCdList where typeDescription in DEFAULT_TYPE_DESCRIPTION or UPDATED_TYPE_DESCRIPTION
        defaultTypeCdShouldBeFound("typeDescription.in=" + DEFAULT_TYPE_DESCRIPTION + "," + UPDATED_TYPE_DESCRIPTION);

        // Get all the typeCdList where typeDescription equals to UPDATED_TYPE_DESCRIPTION
        defaultTypeCdShouldNotBeFound("typeDescription.in=" + UPDATED_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTypeCdsByTypeDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        typeCdRepository.saveAndFlush(typeCd);

        // Get all the typeCdList where typeDescription is not null
        defaultTypeCdShouldBeFound("typeDescription.specified=true");

        // Get all the typeCdList where typeDescription is null
        defaultTypeCdShouldNotBeFound("typeDescription.specified=false");
    }

    @Test
    @Transactional
    public void getAllTypeCdsByGroupCdIsEqualToSomething() throws Exception {
        // Initialize the database
        GroupCd groupCd = GroupCdResourceIntTest.createEntity(em);
        em.persist(groupCd);
        em.flush();
        typeCd.setGroupCd(groupCd);
        typeCdRepository.saveAndFlush(typeCd);
        Long groupCdId = groupCd.getId();

        // Get all the typeCdList where groupCd equals to groupCdId
        defaultTypeCdShouldBeFound("groupCdId.equals=" + groupCdId);

        // Get all the typeCdList where groupCd equals to groupCdId + 1
        defaultTypeCdShouldNotBeFound("groupCdId.equals=" + (groupCdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTypeCdShouldBeFound(String filter) throws Exception {
        restTypeCdMockMvc.perform(get("/api/type-cds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeCd.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeCd").value(hasItem(DEFAULT_TYPE_CD.toString())))
            .andExpect(jsonPath("$.[*].typeDescription").value(hasItem(DEFAULT_TYPE_DESCRIPTION.toString())));

        // Check, that the count call also returns 1
        restTypeCdMockMvc.perform(get("/api/type-cds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTypeCdShouldNotBeFound(String filter) throws Exception {
        restTypeCdMockMvc.perform(get("/api/type-cds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTypeCdMockMvc.perform(get("/api/type-cds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTypeCd() throws Exception {
        // Get the typeCd
        restTypeCdMockMvc.perform(get("/api/type-cds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeCd() throws Exception {
        // Initialize the database
        typeCdRepository.saveAndFlush(typeCd);

        int databaseSizeBeforeUpdate = typeCdRepository.findAll().size();

        // Update the typeCd
        TypeCd updatedTypeCd = typeCdRepository.findById(typeCd.getId()).get();
        // Disconnect from session so that the updates on updatedTypeCd are not directly saved in db
        em.detach(updatedTypeCd);
        updatedTypeCd
            .typeCd(UPDATED_TYPE_CD)
            .typeDescription(UPDATED_TYPE_DESCRIPTION);
        TypeCdDTO typeCdDTO = typeCdMapper.toDto(updatedTypeCd);

        restTypeCdMockMvc.perform(put("/api/type-cds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCdDTO)))
            .andExpect(status().isOk());

        // Validate the TypeCd in the database
        List<TypeCd> typeCdList = typeCdRepository.findAll();
        assertThat(typeCdList).hasSize(databaseSizeBeforeUpdate);
        TypeCd testTypeCd = typeCdList.get(typeCdList.size() - 1);
        assertThat(testTypeCd.getTypeCd()).isEqualTo(UPDATED_TYPE_CD);
        assertThat(testTypeCd.getTypeDescription()).isEqualTo(UPDATED_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeCd() throws Exception {
        int databaseSizeBeforeUpdate = typeCdRepository.findAll().size();

        // Create the TypeCd
        TypeCdDTO typeCdDTO = typeCdMapper.toDto(typeCd);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeCdMockMvc.perform(put("/api/type-cds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCdDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeCd in the database
        List<TypeCd> typeCdList = typeCdRepository.findAll();
        assertThat(typeCdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeCd() throws Exception {
        // Initialize the database
        typeCdRepository.saveAndFlush(typeCd);

        int databaseSizeBeforeDelete = typeCdRepository.findAll().size();

        // Get the typeCd
        restTypeCdMockMvc.perform(delete("/api/type-cds/{id}", typeCd.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeCd> typeCdList = typeCdRepository.findAll();
        assertThat(typeCdList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeCd.class);
        TypeCd typeCd1 = new TypeCd();
        typeCd1.setId(1L);
        TypeCd typeCd2 = new TypeCd();
        typeCd2.setId(typeCd1.getId());
        assertThat(typeCd1).isEqualTo(typeCd2);
        typeCd2.setId(2L);
        assertThat(typeCd1).isNotEqualTo(typeCd2);
        typeCd1.setId(null);
        assertThat(typeCd1).isNotEqualTo(typeCd2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeCdDTO.class);
        TypeCdDTO typeCdDTO1 = new TypeCdDTO();
        typeCdDTO1.setId(1L);
        TypeCdDTO typeCdDTO2 = new TypeCdDTO();
        assertThat(typeCdDTO1).isNotEqualTo(typeCdDTO2);
        typeCdDTO2.setId(typeCdDTO1.getId());
        assertThat(typeCdDTO1).isEqualTo(typeCdDTO2);
        typeCdDTO2.setId(2L);
        assertThat(typeCdDTO1).isNotEqualTo(typeCdDTO2);
        typeCdDTO1.setId(null);
        assertThat(typeCdDTO1).isNotEqualTo(typeCdDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(typeCdMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(typeCdMapper.fromId(null)).isNull();
    }
}
