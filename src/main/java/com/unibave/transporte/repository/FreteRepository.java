package com.unibave.transporte.repository;

import com.unibave.transporte.entity.Frete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreteRepository extends JpaRepository<Frete, Integer> {

    @Query("SELECT f "
            + "FROM Frete f "
            + "WHERE f.descShort like %:description% ")
    public List<Frete> searchByDescrip(String description);

    public boolean existsBydescShort(String description);

}
