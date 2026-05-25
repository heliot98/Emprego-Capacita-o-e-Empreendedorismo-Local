package com.portal.emprego.service;

import com.portal.emprego.exception.ResourceNotFoundException;//importando exception
import com.portal.emprego.model.Vaga;
import com.portal.emprego.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VagaService {

    @Autowired
    private VagaRepository vagaRepository;

    public List<Vaga> listarTodas() {
        return vagaRepository.findAll();
    }

    public Vaga buscarPorId(Long id) {
        return vagaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Vaga com o ID " + id + " não foi encontrada."));
    }


    public Vaga salvar(Vaga vaga) {
        // Aqui você pode colocar validações no futuro (ex: validar se a empresa existe)
        return vagaRepository.save(vaga);
    }

    public void deletar(Long id) {
        vagaRepository.deleteById(id);
    }
}
