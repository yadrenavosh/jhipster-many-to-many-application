package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterMtomApplicationApp;

import io.github.jhipster.application.domain.ExternalCode;
import io.github.jhipster.application.domain.InternalCode;
import io.github.jhipster.application.repository.ExternalCodeRepository;
import io.github.jhipster.application.service.ExternalCodeService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.ExternalCodeCriteria;
import io.github.jhipster.application.service.ExternalCodeQueryService;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExternalCodeResource REST controller.
 *
 * @see ExternalCodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterMtomApplicationApp.class)
public class ExternalCodeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ExternalCodeRepository externalCodeRepository;

    @Autowired
    private ExternalCodeService externalCodeService;

    @Autowired
    private ExternalCodeQueryService externalCodeQueryService;

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

    private MockMvc restExternalCodeMockMvc;

    private ExternalCode externalCode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExternalCodeResource externalCodeResource = new ExternalCodeResource(externalCodeService, externalCodeQueryService);
        this.restExternalCodeMockMvc = MockMvcBuilders.standaloneSetup(externalCodeResource)
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
    public static ExternalCode createEntity(EntityManager em) {
        ExternalCode externalCode = new ExternalCode()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return externalCode;
    }

    @Before
    public void initTest() {
        externalCode = createEntity(em);
    }

    @Test
    @Transactional
    public void createExternalCode() throws Exception {
        int databaseSizeBeforeCreate = externalCodeRepository.findAll().size();

        // Create the ExternalCode
        restExternalCodeMockMvc.perform(post("/api/external-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(externalCode)))
            .andExpect(status().isCreated());

        // Validate the ExternalCode in the database
        List<ExternalCode> externalCodeList = externalCodeRepository.findAll();
        assertThat(externalCodeList).hasSize(databaseSizeBeforeCreate + 1);
        ExternalCode testExternalCode = externalCodeList.get(externalCodeList.size() - 1);
        assertThat(testExternalCode.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testExternalCode.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createExternalCodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = externalCodeRepository.findAll().size();

        // Create the ExternalCode with an existing ID
        externalCode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExternalCodeMockMvc.perform(post("/api/external-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(externalCode)))
            .andExpect(status().isBadRequest());

        // Validate the ExternalCode in the database
        List<ExternalCode> externalCodeList = externalCodeRepository.findAll();
        assertThat(externalCodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = externalCodeRepository.findAll().size();
        // set the field null
        externalCode.setCode(null);

        // Create the ExternalCode, which fails.

        restExternalCodeMockMvc.perform(post("/api/external-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(externalCode)))
            .andExpect(status().isBadRequest());

        List<ExternalCode> externalCodeList = externalCodeRepository.findAll();
        assertThat(externalCodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = externalCodeRepository.findAll().size();
        // set the field null
        externalCode.setDescription(null);

        // Create the ExternalCode, which fails.

        restExternalCodeMockMvc.perform(post("/api/external-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(externalCode)))
            .andExpect(status().isBadRequest());

        List<ExternalCode> externalCodeList = externalCodeRepository.findAll();
        assertThat(externalCodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExternalCodes() throws Exception {
        // Initialize the database
        externalCodeRepository.saveAndFlush(externalCode);

        // Get all the externalCodeList
        restExternalCodeMockMvc.perform(get("/api/external-codes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(externalCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getExternalCode() throws Exception {
        // Initialize the database
        externalCodeRepository.saveAndFlush(externalCode);

        // Get the externalCode
        restExternalCodeMockMvc.perform(get("/api/external-codes/{id}", externalCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(externalCode.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllExternalCodesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        externalCodeRepository.saveAndFlush(externalCode);

        // Get all the externalCodeList where code equals to DEFAULT_CODE
        defaultExternalCodeShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the externalCodeList where code equals to UPDATED_CODE
        defaultExternalCodeShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllExternalCodesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        externalCodeRepository.saveAndFlush(externalCode);

        // Get all the externalCodeList where code in DEFAULT_CODE or UPDATED_CODE
        defaultExternalCodeShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the externalCodeList where code equals to UPDATED_CODE
        defaultExternalCodeShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllExternalCodesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        externalCodeRepository.saveAndFlush(externalCode);

        // Get all the externalCodeList where code is not null
        defaultExternalCodeShouldBeFound("code.specified=true");

        // Get all the externalCodeList where code is null
        defaultExternalCodeShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllExternalCodesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        externalCodeRepository.saveAndFlush(externalCode);

        // Get all the externalCodeList where description equals to DEFAULT_DESCRIPTION
        defaultExternalCodeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the externalCodeList where description equals to UPDATED_DESCRIPTION
        defaultExternalCodeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllExternalCodesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        externalCodeRepository.saveAndFlush(externalCode);

        // Get all the externalCodeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultExternalCodeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the externalCodeList where description equals to UPDATED_DESCRIPTION
        defaultExternalCodeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllExternalCodesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        externalCodeRepository.saveAndFlush(externalCode);

        // Get all the externalCodeList where description is not null
        defaultExternalCodeShouldBeFound("description.specified=true");

        // Get all the externalCodeList where description is null
        defaultExternalCodeShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllExternalCodesByInternalIsEqualToSomething() throws Exception {
        // Initialize the database
        InternalCode internal = InternalCodeResourceIntTest.createEntity(em);
        em.persist(internal);
        em.flush();
        externalCode.addInternal(internal);
        externalCodeRepository.saveAndFlush(externalCode);
        Long internalId = internal.getId();

        // Get all the externalCodeList where internal equals to internalId
        defaultExternalCodeShouldBeFound("internalId.equals=" + internalId);

        // Get all the externalCodeList where internal equals to internalId + 1
        defaultExternalCodeShouldNotBeFound("internalId.equals=" + (internalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultExternalCodeShouldBeFound(String filter) throws Exception {
        restExternalCodeMockMvc.perform(get("/api/external-codes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(externalCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restExternalCodeMockMvc.perform(get("/api/external-codes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultExternalCodeShouldNotBeFound(String filter) throws Exception {
        restExternalCodeMockMvc.perform(get("/api/external-codes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restExternalCodeMockMvc.perform(get("/api/external-codes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingExternalCode() throws Exception {
        // Get the externalCode
        restExternalCodeMockMvc.perform(get("/api/external-codes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExternalCode() throws Exception {
        // Initialize the database
        externalCodeService.save(externalCode);

        int databaseSizeBeforeUpdate = externalCodeRepository.findAll().size();

        // Update the externalCode
        ExternalCode updatedExternalCode = externalCodeRepository.findById(externalCode.getId()).get();
        // Disconnect from session so that the updates on updatedExternalCode are not directly saved in db
        em.detach(updatedExternalCode);
        updatedExternalCode
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);

        restExternalCodeMockMvc.perform(put("/api/external-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExternalCode)))
            .andExpect(status().isOk());

        // Validate the ExternalCode in the database
        List<ExternalCode> externalCodeList = externalCodeRepository.findAll();
        assertThat(externalCodeList).hasSize(databaseSizeBeforeUpdate);
        ExternalCode testExternalCode = externalCodeList.get(externalCodeList.size() - 1);
        assertThat(testExternalCode.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testExternalCode.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingExternalCode() throws Exception {
        int databaseSizeBeforeUpdate = externalCodeRepository.findAll().size();

        // Create the ExternalCode

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExternalCodeMockMvc.perform(put("/api/external-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(externalCode)))
            .andExpect(status().isBadRequest());

        // Validate the ExternalCode in the database
        List<ExternalCode> externalCodeList = externalCodeRepository.findAll();
        assertThat(externalCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExternalCode() throws Exception {
        // Initialize the database
        externalCodeService.save(externalCode);

        int databaseSizeBeforeDelete = externalCodeRepository.findAll().size();

        // Delete the externalCode
        restExternalCodeMockMvc.perform(delete("/api/external-codes/{id}", externalCode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExternalCode> externalCodeList = externalCodeRepository.findAll();
        assertThat(externalCodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExternalCode.class);
        ExternalCode externalCode1 = new ExternalCode();
        externalCode1.setId(1L);
        ExternalCode externalCode2 = new ExternalCode();
        externalCode2.setId(externalCode1.getId());
        assertThat(externalCode1).isEqualTo(externalCode2);
        externalCode2.setId(2L);
        assertThat(externalCode1).isNotEqualTo(externalCode2);
        externalCode1.setId(null);
        assertThat(externalCode1).isNotEqualTo(externalCode2);
    }
}
