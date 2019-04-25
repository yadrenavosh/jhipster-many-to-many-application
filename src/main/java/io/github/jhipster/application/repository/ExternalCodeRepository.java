package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ExternalCode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ExternalCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExternalCodeRepository extends JpaRepository<ExternalCode, Long>, JpaSpecificationExecutor<ExternalCode> {

}
