package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Lapin5App;
import com.mycompany.myapp.domain.Lapin;
import com.mycompany.myapp.repository.LapinRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LapinResource} REST controller.
 */
@SpringBootTest(classes = Lapin5App.class)
@AutoConfigureMockMvc
@WithMockUser
public class LapinResourceIT {

    private static final LocalDate DEFAULT_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_MODIFY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ZonedDateTime DEFAULT_DELETED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private LapinRepository lapinRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLapinMockMvc;

    private Lapin lapin;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lapin createEntity(EntityManager em) {
        Lapin lapin = new Lapin()
            .created(DEFAULT_CREATED)
            .modify(DEFAULT_MODIFY)
            .deleted(DEFAULT_DELETED);
        return lapin;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lapin createUpdatedEntity(EntityManager em) {
        Lapin lapin = new Lapin()
            .created(UPDATED_CREATED)
            .modify(UPDATED_MODIFY)
            .deleted(UPDATED_DELETED);
        return lapin;
    }

    @BeforeEach
    public void initTest() {
        lapin = createEntity(em);
    }

    @Test
    @Transactional
    public void createLapin() throws Exception {
        int databaseSizeBeforeCreate = lapinRepository.findAll().size();
        // Create the Lapin
        restLapinMockMvc.perform(post("/api/lapins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lapin)))
            .andExpect(status().isCreated());

        // Validate the Lapin in the database
        List<Lapin> lapinList = lapinRepository.findAll();
        assertThat(lapinList).hasSize(databaseSizeBeforeCreate + 1);
        Lapin testLapin = lapinList.get(lapinList.size() - 1);
        assertThat(testLapin.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testLapin.getModify()).isEqualTo(DEFAULT_MODIFY);
        assertThat(testLapin.getDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createLapinWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lapinRepository.findAll().size();

        // Create the Lapin with an existing ID
        lapin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLapinMockMvc.perform(post("/api/lapins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lapin)))
            .andExpect(status().isBadRequest());

        // Validate the Lapin in the database
        List<Lapin> lapinList = lapinRepository.findAll();
        assertThat(lapinList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLapins() throws Exception {
        // Initialize the database
        lapinRepository.saveAndFlush(lapin);

        // Get all the lapinList
        restLapinMockMvc.perform(get("/api/lapins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lapin.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].modify").value(hasItem(DEFAULT_MODIFY.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(sameInstant(DEFAULT_DELETED))));
    }
    
    @Test
    @Transactional
    public void getLapin() throws Exception {
        // Initialize the database
        lapinRepository.saveAndFlush(lapin);

        // Get the lapin
        restLapinMockMvc.perform(get("/api/lapins/{id}", lapin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lapin.getId().intValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.modify").value(DEFAULT_MODIFY.toString()))
            .andExpect(jsonPath("$.deleted").value(sameInstant(DEFAULT_DELETED)));
    }
    @Test
    @Transactional
    public void getNonExistingLapin() throws Exception {
        // Get the lapin
        restLapinMockMvc.perform(get("/api/lapins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLapin() throws Exception {
        // Initialize the database
        lapinRepository.saveAndFlush(lapin);

        int databaseSizeBeforeUpdate = lapinRepository.findAll().size();

        // Update the lapin
        Lapin updatedLapin = lapinRepository.findById(lapin.getId()).get();
        // Disconnect from session so that the updates on updatedLapin are not directly saved in db
        em.detach(updatedLapin);
        updatedLapin
            .created(UPDATED_CREATED)
            .modify(UPDATED_MODIFY)
            .deleted(UPDATED_DELETED);

        restLapinMockMvc.perform(put("/api/lapins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLapin)))
            .andExpect(status().isOk());

        // Validate the Lapin in the database
        List<Lapin> lapinList = lapinRepository.findAll();
        assertThat(lapinList).hasSize(databaseSizeBeforeUpdate);
        Lapin testLapin = lapinList.get(lapinList.size() - 1);
        assertThat(testLapin.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testLapin.getModify()).isEqualTo(UPDATED_MODIFY);
        assertThat(testLapin.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingLapin() throws Exception {
        int databaseSizeBeforeUpdate = lapinRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLapinMockMvc.perform(put("/api/lapins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lapin)))
            .andExpect(status().isBadRequest());

        // Validate the Lapin in the database
        List<Lapin> lapinList = lapinRepository.findAll();
        assertThat(lapinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLapin() throws Exception {
        // Initialize the database
        lapinRepository.saveAndFlush(lapin);

        int databaseSizeBeforeDelete = lapinRepository.findAll().size();

        // Delete the lapin
        restLapinMockMvc.perform(delete("/api/lapins/{id}", lapin.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lapin> lapinList = lapinRepository.findAll();
        assertThat(lapinList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
