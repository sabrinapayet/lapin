package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Lapin;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Lapin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LapinRepository extends JpaRepository<Lapin, Long> {
}
