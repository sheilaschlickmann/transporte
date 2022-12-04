package com.unibave.transporte.repository;

import com.unibave.transporte.entity.Entregador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntregadorRepository extends JpaRepository<Entregador, Integer> {

    @Query("select b from Entregador b where b.id = :id")
    Entregador buscarPor(Integer id);

    @Query("select b from Entregador b order by b.nome")
    List<Entregador> listarTodas();
}
