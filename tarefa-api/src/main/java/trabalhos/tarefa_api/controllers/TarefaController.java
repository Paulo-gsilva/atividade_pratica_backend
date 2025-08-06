package trabalhos.tarefa_api.controllers;

import trabalhos.tarefa_api.models.Tarefa;
import trabalhos.tarefa_api.repository.TarefaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    private final TarefaRepository repo;

    public TarefaController(TarefaRepository repo) {
        this.repo = repo;
    }

    // 1. Criar tarefa
    @PostMapping
    public Tarefa criar(@RequestBody Tarefa tarefa) {
        return repo.save(tarefa);
    }

    // 2. Listar todas
    @GetMapping
    public List<Tarefa> listar() {
        return repo.findAll();
    }

    // 3. Obter por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscar(@PathVariable Long id) {
        Optional<Tarefa> opt = repo.findById(id);
        return opt.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    // 4. Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @RequestBody Tarefa dados) {
        return repo.findById(id).map(t -> {
            t.setNome(dados.getNome());
            t.setDataEntrega(dados.getDataEntrega());
            t.setResponsavel(dados.getResponsavel());
            repo.save(t);
            return ResponseEntity.ok(t);
        }).orElse(ResponseEntity.notFound().build());
    }

    // 5. Remover
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        return repo.findById(id).map(t -> {
            repo.delete(t);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}