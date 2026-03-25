package com.projectD.multisimbox.repository;

import com.projectD.multisimbox.entity.SimCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SimCardRepository extends JpaRepository<SimCard, Long> {
    @Query(value = """
            SELECT
                sc.id,
                mo.name,
                sc.def_number,
                sc.iccid,
                ssd.code,
                COALESCE(ssd.description, '')
            FROM sim_card sc
            JOIN mobile_operator mo ON mo.id = sc.mo_id
            JOIN sim_status_dictionary ssd ON ssd.id = sc.status_id
            ORDER BY sc.id
            """, nativeQuery = true)
    List<Object[]> findAllForGridRows();

    @Query(value = """
            SELECT
                sc.id,
                mo.name,
                sc.def_number,
                sc.iccid,
                ssd.code,
                COALESCE(ssd.description, '')
            FROM sim_card sc
            JOIN mobile_operator mo ON mo.id = sc.mo_id
            JOIN sim_status_dictionary ssd ON ssd.id = sc.status_id
            WHERE sc.id = :id
            """, nativeQuery = true)
    Optional<Object[]> findGridRowById(@Param("id") Long id);

    @Query(value = """
            SELECT
                sc.id,
                mo.name,
                sc.def_number,
                sc.iccid,
                ssd.code,
                COALESCE(ssd.description, ''),
                sc.activation_date,
                sc.deactivation_date,
                o.obj_number,
                o.status,
                o.address,
                e.model
            FROM sim_card sc
            JOIN mobile_operator mo ON mo.id = sc.mo_id
            JOIN sim_status_dictionary ssd ON ssd.id = sc.status_id
            LEFT JOIN equipment e ON e.id = sc.equipment_id
            LEFT JOIN \"object\" o ON o.id = e.object_id
            WHERE sc.id = :id
            """, nativeQuery = true)
    List<Object[]> findDescriptionRow(@Param("id") Long id);
}
