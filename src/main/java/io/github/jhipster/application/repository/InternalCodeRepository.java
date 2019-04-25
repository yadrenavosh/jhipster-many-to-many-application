package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.InternalCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the InternalCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InternalCodeRepository extends JpaRepository<InternalCode, Long>, JpaSpecificationExecutor<InternalCode> {

    @Query(value = "select distinct internal_code from InternalCode internal_code left join fetch internal_code.externals",
        countQuery = "select count(distinct internal_code) from InternalCode internal_code")
    Page<InternalCode> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct internal_code from InternalCode internal_code left join fetch internal_code.externals")
    List<InternalCode> findAllWithEagerRelationships();

    @Query("select internal_code from InternalCode internal_code left join fetch internal_code.externals where internal_code.id =:id")
    Optional<InternalCode> findOneWithEagerRelationships(@Param("id") Long id);

}
