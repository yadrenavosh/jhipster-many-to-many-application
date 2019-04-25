package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterMtomApplicationApp;

import io.github.jhipster.application.domain.InternalCode;
import io.github.jhipster.application.domain.ExternalCode;
import io.github.jhipster.application.repository.InternalCodeRepository;
import io.github.jhipster.application.service.InternalCodeService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.InternalCodeCriteria;
import io.github.jhipster.application.service.InternalCodeQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InternalCodeResource REST controller.
 *
 * @see InternalCodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterMtomApplicationApp.class)
public class InternalCodeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private InternalCodeRepository internalCodeRepository;

    @Mock
    private InternalCodeRepository internalCodeRepositoryMock;

    @Mock
    private InternalCodeService internalCodeServiceMock;

    @Autowired
    private InternalCodeService internalCodeService;

    @Autowired
    private InternalCodeQueryService internalCodeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restInternalCodeMockMvc;

    private InternalCode internalCode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InternalCodeResource internalCodeResource = new InternalCodeResource(internalCodeService, internalCodeQueryService);
        this.restInternalCodeMockMvc = MockMvcBuilders.standaloneSetup(internalCodeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InternalCode createEntity(EntityManager em) {
        InternalCode internalCode = new InternalCode()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return internalCode;
    }

    @Before
    public void initTest() {
        internalCode = createEntity(em);
    }

    @Test
    @Transactional
    public void createInternalCode() throws Exception {
        int databaseSizeBeforeCreate = internalCodeRepository.findAll().size();

        // Create the InternalCode
        restInternalCodeMockMvc.perform(post("/api/internal-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internalCode)))
            .andExpect(status().isCreated());

        // Validate the InternalCode in the database
        List<InternalCode> internalCodeList = internalCodeRepository.findAll();
        assertThat(internalCodeList).hasSize(databaseSizeBeforeCreate + 1);
        InternalCode testInternalCode = internalCodeList.get(internalCodeList.size() - 1);
        assertThat(testInternalCode.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testInternalCode.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createInternalCodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = internalCodeRepository.findAll().size();

        // Create the InternalCode with an existing ID
        internalCode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInternalCodeMockMvc.perform(post("/api/internal-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internalCode)))
            .andExpect(status().isBadRequest());

        // Validate the InternalCode in the database
        List<InternalCode> internalCodeList = internalCodeRepository.findAll();
        assertThat(internalCodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = internalCodeRepository.findAll().size();
        // set the field null
        internalCode.setCode(null);

        // Create the InternalCode, which fails.

        restInternalCodeMockMvc.perform(post("/api/internal-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internalCode)))
            .andExpect(status().isBadRequest());

        List<InternalCode> internalCodeList = internalCodeRepository.findAll();
        assertThat(internalCodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = internalCodeRepository.findAll().size();
        // set the field null
        internalCode.setDescription(null);

        // Create the InternalCode, which fails.

        restInternalCodeMockMvc.perform(post("/api/internal-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internalCode)))
            .andExpect(status().isBadRequest());

        List<InternalCode> internalCodeList = internalCodeRepository.findAll();
        assertThat(internalCodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInternalCodes() throws Exception {
        // Initialize the database
        internalCodeRepository.saveAndFlush(internalCode);

        // Get all the internalCodeList
        restInternalCodeMockMvc.perform(get("/api/internal-codes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(internalCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllInternalCodesWithEagerRelationshipsIsEnabled() throws Exception {
        InternalCodeResource internalCodeResource = new InternalCodeResource(internalCodeServiceMock, internalCodeQueryService);
        when(internalCodeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restInternalCodeMockMvc = MockMvcBuilders.standaloneSetup(internalCodeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restInternalCodeMockMvc.perform(get("/api/internal-codes?eagerload=true"))
        .andExpect(status().isOk());

        verify(internalCodeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllInternalCodesWithEagerRelationshipsIsNotEnabled() throws Exception {
        InternalCodeResource internalCodeResource = new InternalCodeResource(internalCodeServiceMock, internalCodeQueryService);
            when(internalCodeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restInternalCodeMockMvc = MockMvcBuilders.standaloneSetup(internalCodeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restInternalCodeMockMvc.perform(get("/api/internal-codes?eagerload=true"))
        .andExpect(status().isOk());

            verify(internalCodeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getInternalCode() throws Exception {
        // Initialize the database
        internalCodeRepository.saveAndFlush(internalCode);

        // Get the internalCode
        restInternalCodeMockMvc.perform(get("/api/internal-codes/{id}", internalCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(internalCode.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllInternalCodesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        internalCodeRepository.saveAndFlush(internalCode);

        // Get all the internalCodeList where code equals to DEFAULT_CODE
        defaultInternalCodeShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the internalCodeList where code equals to UPDATED_CODE
        defaultInternalCodeShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllInternalCodesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        internalCodeRepository.saveAndFlush(internalCode);

        // Get all the internalCodeList where code in DEFAULT_CODE or UPDATED_CODE
        defaultInternalCodeShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the internalCodeList where code equals to UPDATED_CODE
        defaultInternalCodeShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllInternalCodesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        internalCodeRepository.saveAndFlush(internalCode);

        // Get all the internalCodeList where code is not null
        defaultInternalCodeShouldBeFound("code.specified=true");

        // Get all the internalCodeList where code is null
        defaultInternalCodeShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllInternalCodesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        internalCodeRepository.saveAndFlush(internalCode);

        // Get all the internalCodeList where description equals to DEFAULT_DESCRIPTION
        defaultInternalCodeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the internalCodeList where description equals to UPDATED_DESCRIPTION
        defaultInternalCodeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllInternalCodesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        internalCodeRepository.saveAndFlush(internalCode);

        // Get all the internalCodeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultInternalCodeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the internalCodeList where description equals to UPDATED_DESCRIPTION
        defaultInternalCodeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllInternalCodesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        internalCodeRepository.saveAndFlush(internalCode);

        // Get all the internalCodeList where description is not null
        defaultInternalCodeShouldBeFound("description.specified=true");

        // Get all the internalCodeList where description is null
        defaultInternalCodeShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllInternalCodesByExternalIsEqualToSomething() throws Exception {
        // Initialize the database
        ExternalCode external = ExternalCodeResourceIntTest.createEntity(em);
        em.persist(external);
        em.flush();
        internalCode.addExternal(external);
        internalCodeRepository.saveAndFlush(internalCode);
        Long externalId = external.getId();

        // Get all the internalCodeList where external equals to externalId
        defaultInternalCodeShouldBeFound("externalId.equals=" + externalId);

        // Get all the internalCodeList where external equals to externalId + 1
        defaultInternalCodeShouldNotBeFound("externalId.equals=" + (externalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultInternalCodeShouldBeFound(String filter) throws Exception {
        restInternalCodeMockMvc.perform(get("/api/internal-codes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(internalCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restInternalCodeMockMvc.perform(get("/api/internal-codes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultInternalCodeShouldNotBeFound(String filter) throws Exception {
        restInternalCodeMockMvc.perform(get("/api/internal-codes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInternalCodeMockMvc.perform(get("/api/internal-codes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingInternalCode() throws Exception {
        // Get the internalCode
        restInternalCodeMockMvc.perform(get("/api/internal-codes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInternalCode() throws Exception {
        // Initialize the database
        internalCodeService.save(internalCode);

        int databaseSizeBeforeUpdate = internalCodeRepository.findAll().size();

        // Update the internalCode
        InternalCode updatedInternalCode = internalCodeRepository.findById(internalCode.getId()).get();
        // Disconnect from session so that the updates on updatedInternalCode are not directly saved in db
        em.detach(updatedInternalCode);
        updatedInternalCode
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);

        restInternalCodeMockMvc.perform(put("/api/internal-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInternalCode)))
            .andExpect(status().isOk());

        // Validate the InternalCode in the database
        List<InternalCode> internalCodeList = internalCodeRepository.findAll();
        assertThat(internalCodeList).hasSize(databaseSizeBeforeUpdate);
        InternalCode testInternalCode = internalCodeList.get(internalCodeList.size() - 1);
        assertThat(testInternalCode.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testInternalCode.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingInternalCode() throws Exception {
        int databaseSizeBeforeUpdate = internalCodeRepository.findAll().size();

        // Create the InternalCode

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInternalCodeMockMvc.perform(put("/api/internal-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internalCode)))
            .andExpect(status().isBadRequest());

        // Validate the InternalCode in the database
        List<InternalCode> internalCodeList = internalCodeRepository.findAll();
        assertThat(internalCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInternalCode() throws Exception {
        // Initialize the database
        internalCodeService.save(internalCode);

        int databaseSizeBeforeDelete = internalCodeRepository.findAll().size();

        // Delete the internalCode
        restInternalCodeMockMvc.perform(delete("/api/internal-codes/{id}", internalCode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InternalCode> internalCodeList = internalCodeRepository.findAll();
        assertThat(internalCodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InternalCode.class);
        InternalCode internalCode1 = new InternalCode();
        internalCode1.setId(1L);
        InternalCode internalCode2 = new InternalCode();
        internalCode2.setId(internalCode1.getId());
        assertThat(internalCode1).isEqualTo(internalCode2);
        internalCode2.setId(2L);
        assertThat(internalCode1).isNotEqualTo(internalCode2);
        internalCode1.setId(null);
        assertThat(internalCode1).isNotEqualTo(internalCode2);
    }
}
