package com.eagle.kyc.web.rest;

import com.eagle.kyc.Kyc5App;

import com.eagle.kyc.domain.GroupCd;
import com.eagle.kyc.domain.TypeCd;
import com.eagle.kyc.repository.GroupCdRepository;
import com.eagle.kyc.service.GroupCdService;
import com.eagle.kyc.service.dto.GroupCdDTO;
import com.eagle.kyc.service.mapper.GroupCdMapper;
import com.eagle.kyc.web.rest.errors.ExceptionTranslator;
import com.eagle.kyc.service.dto.GroupCdCriteria;
import com.eagle.kyc.service.GroupCdQueryService;

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
 * Test class for the GroupCdResource REST controller.
 *
 * @see GroupCdResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Kyc5App.class)
public class GroupCdResourceIntTest {

    private static final String DEFAULT_GROUP_CD = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_CD = "BBBBBBBBBB";

    @Autowired
    private GroupCdRepository groupCdRepository;

    @Autowired
    private GroupCdMapper groupCdMapper;
    
    @Autowired
    private GroupCdService groupCdService;

    @Autowired
    private GroupCdQueryService groupCdQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGroupCdMockMvc;

    private GroupCd groupCd;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GroupCdResource groupCdResource = new GroupCdResource(groupCdService, groupCdQueryService);
        this.restGroupCdMockMvc = MockMvcBuilders.standaloneSetup(groupCdResource)
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
    public static GroupCd createEntity(EntityManager em) {
        GroupCd groupCd = new GroupCd()
            .groupCd(DEFAULT_GROUP_CD);
        return groupCd;
    }

    @Before
    public void initTest() {
        groupCd = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupCd() throws Exception {
        int databaseSizeBeforeCreate = groupCdRepository.findAll().size();

        // Create the GroupCd
        GroupCdDTO groupCdDTO = groupCdMapper.toDto(groupCd);
        restGroupCdMockMvc.perform(post("/api/group-cds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupCdDTO)))
            .andExpect(status().isCreated());

        // Validate the GroupCd in the database
        List<GroupCd> groupCdList = groupCdRepository.findAll();
        assertThat(groupCdList).hasSize(databaseSizeBeforeCreate + 1);
        GroupCd testGroupCd = groupCdList.get(groupCdList.size() - 1);
        assertThat(testGroupCd.getGroupCd()).isEqualTo(DEFAULT_GROUP_CD);
    }

    @Test
    @Transactional
    public void createGroupCdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupCdRepository.findAll().size();

        // Create the GroupCd with an existing ID
        groupCd.setId(1L);
        GroupCdDTO groupCdDTO = groupCdMapper.toDto(groupCd);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupCdMockMvc.perform(post("/api/group-cds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupCdDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupCd in the database
        List<GroupCd> groupCdList = groupCdRepository.findAll();
        assertThat(groupCdList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkGroupCdIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupCdRepository.findAll().size();
        // set the field null
        groupCd.setGroupCd(null);

        // Create the GroupCd, which fails.
        GroupCdDTO groupCdDTO = groupCdMapper.toDto(groupCd);

        restGroupCdMockMvc.perform(post("/api/group-cds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupCdDTO)))
            .andExpect(status().isBadRequest());

        List<GroupCd> groupCdList = groupCdRepository.findAll();
        assertThat(groupCdList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGroupCds() throws Exception {
        // Initialize the database
        groupCdRepository.saveAndFlush(groupCd);

        // Get all the groupCdList
        restGroupCdMockMvc.perform(get("/api/group-cds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupCd.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupCd").value(hasItem(DEFAULT_GROUP_CD.toString())));
    }
    
    @Test
    @Transactional
    public void getGroupCd() throws Exception {
        // Initialize the database
        groupCdRepository.saveAndFlush(groupCd);

        // Get the groupCd
        restGroupCdMockMvc.perform(get("/api/group-cds/{id}", groupCd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(groupCd.getId().intValue()))
            .andExpect(jsonPath("$.groupCd").value(DEFAULT_GROUP_CD.toString()));
    }

    @Test
    @Transactional
    public void getAllGroupCdsByGroupCdIsEqualToSomething() throws Exception {
        // Initialize the database
        groupCdRepository.saveAndFlush(groupCd);

        // Get all the groupCdList where groupCd equals to DEFAULT_GROUP_CD
        defaultGroupCdShouldBeFound("groupCd.equals=" + DEFAULT_GROUP_CD);

        // Get all the groupCdList where groupCd equals to UPDATED_GROUP_CD
        defaultGroupCdShouldNotBeFound("groupCd.equals=" + UPDATED_GROUP_CD);
    }

    @Test
    @Transactional
    public void getAllGroupCdsByGroupCdIsInShouldWork() throws Exception {
        // Initialize the database
        groupCdRepository.saveAndFlush(groupCd);

        // Get all the groupCdList where groupCd in DEFAULT_GROUP_CD or UPDATED_GROUP_CD
        defaultGroupCdShouldBeFound("groupCd.in=" + DEFAULT_GROUP_CD + "," + UPDATED_GROUP_CD);

        // Get all the groupCdList where groupCd equals to UPDATED_GROUP_CD
        defaultGroupCdShouldNotBeFound("groupCd.in=" + UPDATED_GROUP_CD);
    }

    @Test
    @Transactional
    public void getAllGroupCdsByGroupCdIsNullOrNotNull() throws Exception {
        // Initialize the database
        groupCdRepository.saveAndFlush(groupCd);

        // Get all the groupCdList where groupCd is not null
        defaultGroupCdShouldBeFound("groupCd.specified=true");

        // Get all the groupCdList where groupCd is null
        defaultGroupCdShouldNotBeFound("groupCd.specified=false");
    }

    @Test
    @Transactional
    public void getAllGroupCdsByTypeCdIsEqualToSomething() throws Exception {
        // Initialize the database
        TypeCd typeCd = TypeCdResourceIntTest.createEntity(em);
        em.persist(typeCd);
        em.flush();
        groupCd.addTypeCd(typeCd);
        groupCdRepository.saveAndFlush(groupCd);
        Long typeCdId = typeCd.getId();

        // Get all the groupCdList where typeCd equals to typeCdId
        defaultGroupCdShouldBeFound("typeCdId.equals=" + typeCdId);

        // Get all the groupCdList where typeCd equals to typeCdId + 1
        defaultGroupCdShouldNotBeFound("typeCdId.equals=" + (typeCdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultGroupCdShouldBeFound(String filter) throws Exception {
        restGroupCdMockMvc.perform(get("/api/group-cds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupCd.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupCd").value(hasItem(DEFAULT_GROUP_CD.toString())));

        // Check, that the count call also returns 1
        restGroupCdMockMvc.perform(get("/api/group-cds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultGroupCdShouldNotBeFound(String filter) throws Exception {
        restGroupCdMockMvc.perform(get("/api/group-cds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGroupCdMockMvc.perform(get("/api/group-cds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingGroupCd() throws Exception {
        // Get the groupCd
        restGroupCdMockMvc.perform(get("/api/group-cds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupCd() throws Exception {
        // Initialize the database
        groupCdRepository.saveAndFlush(groupCd);

        int databaseSizeBeforeUpdate = groupCdRepository.findAll().size();

        // Update the groupCd
        GroupCd updatedGroupCd = groupCdRepository.findById(groupCd.getId()).get();
        // Disconnect from session so that the updates on updatedGroupCd are not directly saved in db
        em.detach(updatedGroupCd);
        updatedGroupCd
            .groupCd(UPDATED_GROUP_CD);
        GroupCdDTO groupCdDTO = groupCdMapper.toDto(updatedGroupCd);

        restGroupCdMockMvc.perform(put("/api/group-cds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupCdDTO)))
            .andExpect(status().isOk());

        // Validate the GroupCd in the database
        List<GroupCd> groupCdList = groupCdRepository.findAll();
        assertThat(groupCdList).hasSize(databaseSizeBeforeUpdate);
        GroupCd testGroupCd = groupCdList.get(groupCdList.size() - 1);
        assertThat(testGroupCd.getGroupCd()).isEqualTo(UPDATED_GROUP_CD);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupCd() throws Exception {
        int databaseSizeBeforeUpdate = groupCdRepository.findAll().size();

        // Create the GroupCd
        GroupCdDTO groupCdDTO = groupCdMapper.toDto(groupCd);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupCdMockMvc.perform(put("/api/group-cds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupCdDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupCd in the database
        List<GroupCd> groupCdList = groupCdRepository.findAll();
        assertThat(groupCdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroupCd() throws Exception {
        // Initialize the database
        groupCdRepository.saveAndFlush(groupCd);

        int databaseSizeBeforeDelete = groupCdRepository.findAll().size();

        // Get the groupCd
        restGroupCdMockMvc.perform(delete("/api/group-cds/{id}", groupCd.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GroupCd> groupCdList = groupCdRepository.findAll();
        assertThat(groupCdList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupCd.class);
        GroupCd groupCd1 = new GroupCd();
        groupCd1.setId(1L);
        GroupCd groupCd2 = new GroupCd();
        groupCd2.setId(groupCd1.getId());
        assertThat(groupCd1).isEqualTo(groupCd2);
        groupCd2.setId(2L);
        assertThat(groupCd1).isNotEqualTo(groupCd2);
        groupCd1.setId(null);
        assertThat(groupCd1).isNotEqualTo(groupCd2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupCdDTO.class);
        GroupCdDTO groupCdDTO1 = new GroupCdDTO();
        groupCdDTO1.setId(1L);
        GroupCdDTO groupCdDTO2 = new GroupCdDTO();
        assertThat(groupCdDTO1).isNotEqualTo(groupCdDTO2);
        groupCdDTO2.setId(groupCdDTO1.getId());
        assertThat(groupCdDTO1).isEqualTo(groupCdDTO2);
        groupCdDTO2.setId(2L);
        assertThat(groupCdDTO1).isNotEqualTo(groupCdDTO2);
        groupCdDTO1.setId(null);
        assertThat(groupCdDTO1).isNotEqualTo(groupCdDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(groupCdMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(groupCdMapper.fromId(null)).isNull();
    }
}
