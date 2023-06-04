package com.carrentalbackend.repository;

import com.carrentalbackend.model.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {

    @Transactional
    @Modifying
    @Query("SELECT id FROM Office")
    List<Long> getAllOfficeIds();

}
