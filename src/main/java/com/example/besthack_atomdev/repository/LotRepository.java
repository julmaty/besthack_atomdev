package com.example.besthack_atomdev.repository;

import com.example.besthack_atomdev.common.FuelType;
import com.example.besthack_atomdev.common.OilBase;
import com.example.besthack_atomdev.model.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LotRepository extends JpaRepository<Lot, Long> {

    @Query("SELECT l FROM Lot l " +
            "WHERE (:oilBaseCodes IS NULL OR l.oilBase IN :oilBaseCodes) " +
            "AND (:fuelTypes IS NULL OR l.fuelType IN :fuelTypes) " +
            "AND (l.availableBalance > 0 AND l.lotDate >= :validDate) " +
            "ORDER BY l.id DESC")
    Page<Lot> findFiltered(
            @Param("oilBaseCodes") List<OilBase> oilBaseCodes,
            @Param("fuelTypes") List<FuelType> fuelTypes,
            @Param("validDate") LocalDate validDate,
            Pageable pageable
    );
}
