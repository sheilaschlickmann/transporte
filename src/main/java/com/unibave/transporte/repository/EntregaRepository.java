package com.unibave.transporte.repository;

import com.unibave.transporte.entity.Entregas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntregaRepository extends JpaRepository<Entregas, Integer> {

}
