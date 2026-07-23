package PousadaAPI.controller;

import PousadaAPI.config.URIConfig;
import PousadaAPI.domain.model.Funcionario;
import PousadaAPI.dto.request.CriarFuncionarioRequestDTO;
import PousadaAPI.dto.response.FuncionarioResponse;
import PousadaAPI.service.FuncionarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/funcionario")
@AllArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;
    private final URIConfig uriConfig;

    @PostMapping("/cadastro")
    public ResponseEntity<FuncionarioResponse> cadastrarFuncionario(
            @RequestBody @Valid CriarFuncionarioRequestDTO funcionarioRequestDTO) {
        FuncionarioResponse funcionario = funcionarioService.cadastrarFuncionario(funcionarioRequestDTO);
        URI config = uriConfig.criarUriLocation(funcionario);
        return ResponseEntity.created(config).body(funcionario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> deletarFuncionario(@PathVariable("id") String id) {
        FuncionarioResponse funcionario = funcionarioService.deletarFuncionario(id);
        return ResponseEntity.ok(funcionario);
    }

    @GetMapping("/funcionarioList")
    public ResponseEntity<List<Funcionario>> listarFuncionario(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email) {
        return ResponseEntity.ok(funcionarioService.listarFuncionario(nome, email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> atualizarDados(
            @PathVariable("id") String id,
            @RequestBody @Valid CriarFuncionarioRequestDTO funcionarioRequestDTO) {
        return ResponseEntity.ok(funcionarioService.atualizarDadosFuncionario(funcionarioRequestDTO, id));
    }
}