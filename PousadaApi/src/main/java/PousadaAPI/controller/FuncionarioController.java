package PousadaAPI.controller;

import PousadaAPI.config.URIConfig;
import PousadaAPI.domain.model.Funcionario;
import PousadaAPI.dto.request.CriarFuncionarioRequestDTO;
import PousadaAPI.service.FuncionarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/funcionario")
@RestController
@AllArgsConstructor
public class FuncionarioController {
    private final FuncionarioService funcionarioService;
    private final URIConfig uriConfig;

    @PostMapping("/cadastro")
    public ResponseEntity<Funcionario> cadastrarFuncionario(@RequestBody @Valid CriarFuncionarioRequestDTO funcionarioRequestDTO) {
        Funcionario funcionario = funcionarioService.cadastrarFuncionario(funcionarioRequestDTO);
        var config = uriConfig.criarUriLocation(funcionario);
        return ResponseEntity.created(config).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletarFuncionario(@PathVariable("id") String id) {
        Funcionario funcionario = funcionarioService.deletarFuncionario(id);
        return ResponseEntity.ok(funcionario);
    }

    @GetMapping("/funcionarioList")
    public ResponseEntity<List<Funcionario>> listarFuncionario(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email) {
        return ResponseEntity.ok(funcionarioService.listarFuncionario(nome, email));
    }

    @PutMapping("{id}")
    public ResponseEntity<Funcionario> atualizarDados
            (@PathVariable ("id") String id,
             @RequestBody CriarFuncionarioRequestDTO funcionarioRequestDTO) {
        return ResponseEntity.ok(funcionarioService.atualizarDadosFuncionario(funcionarioRequestDTO, id));
    }
}
