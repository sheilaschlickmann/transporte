package com.unibave.transporte.repository;

import com.unibave.transporte.entity.Frete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FreteRepository extends JpaRepository<Frete, Integer> {

    @Query("SELECT f "
            + "FROM Frete f "
            + "WHERE f.desc_short like :description ")
    public Frete searchByDescrip(String description);

}
