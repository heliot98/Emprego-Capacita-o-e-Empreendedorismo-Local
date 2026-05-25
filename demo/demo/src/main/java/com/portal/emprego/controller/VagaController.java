package com.portal.emprego.controller;

import com.portal.emprego.model.Vaga;
import com.portal.emprego.service.VagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vagas")
public class VagaController {

    @Autowired
    private VagaService vagaService;

    // Rota para listar todas as vagas (GET /vagas)
    @GetMapping
    public List<Vaga> listar() {
        return vagaService.listarTodas();
    }

    // ALTERADO: Agora busca a vaga direto. Se não existir, a exception cuida do erro!
    @GetMapping("/{id}")
    public ResponseEntity<Vaga> buscar(@PathVariable Long id) {
        Vaga vaga = vagaService.buscarPorId(id);
        return ResponseEntity.ok(vaga);
    }

    // Rota para cadastrar uma nova vaga (POST /vagas)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vaga adicionar(@RequestBody Vaga vaga) {
        return vagaService.salvar(vaga);
    }
}
