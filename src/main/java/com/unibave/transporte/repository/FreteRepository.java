package com.unibave.transporte.repository;

import com.unibave.transporte.entity.Entregas;
import com.unibave.transporte.entity.Frete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreteRepository extends JpaRepository<Frete, Integer> {
}
