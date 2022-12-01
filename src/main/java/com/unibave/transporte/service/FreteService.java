package com.unibave.transporte.service;

import com.unibave.transporte.entity.Frete;
import com.unibave.transporte.exception.RegistroNaoEncontradoException;
import com.unibave.transporte.repository.FreteRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class FreteService {

    final FreteRepository freteRepository;


    public FreteService(FreteRepository freteRepository) {
        this.freteRepository = freteRepository;
    }

    public Frete findById(Integer id) {
        Optional<Frete> registroEncontrado = freteRepository.findById(id);
        if (registroEncontrado.isPresent()) {
            return registroEncontrado.get();
        }
        throw new RegistroNaoEncontradoException("Não foi encontrado nenhuma tabela frete com id: "+id);
    }

    @Transactional
    public Frete save(Frete fretes) {
        return freteRepository.save(fretes);
    }

    @Transactional
    public Frete delete(Integer id) {
        Frete bandeiraSalva = freteRepository.findById(id).get();
        this.freteRepository.deleteById(id);
        return bandeiraSalva;
    }

    public Frete searchByDescrip(String description){
        Frete registroEncontrado = freteRepository.searchByDescrip(description);
        System.out.println(registroEncontrado);
        if (registroEncontrado != null) {
            return registroEncontrado;
        }
        throw new RegistroNaoEncontradoException("Não foi encontrada nenhuma tabela frete com a descrição: '" + description + "'");
    }
    private void checarExistenciaNaBaseDo(Integer idDaBandeira) {
        boolean isExiste = freteRepository.existsById(idDaBandeira);
        if (!isExiste) {
            throw new RegistroNaoEncontradoException("Não foi encontrada frete com o ID informado!");
        }
    }

}



