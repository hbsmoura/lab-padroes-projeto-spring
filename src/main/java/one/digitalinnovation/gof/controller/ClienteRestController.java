package one.digitalinnovation.gof.controller;

import one.digitalinnovation.gof.model.Cliente;
import one.digitalinnovation.gof.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Esse {@link RestController} representa nossa <b>Facade</b>, pois abstrai toda
 * a complexidade de integrações (Banco de Dados H2 e API do ViaCEP) em uma
 * interface simples e coesa (API REST).
 * 
 * @author falvojr
 */
@RestController
@RequestMapping("clientes")
public class ClienteRestController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<Cliente> listar(Pageable pageable) {
		return clienteService.listar(pageable);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Cliente buscarPorId(@PathVariable Long id) {
		return clienteService.buscarPorId(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public Cliente inserir(@RequestBody Cliente cliente) {
		return clienteService.inserir(cliente);
	}

	@PutMapping("/{id}")
	public Cliente atualizar(@RequestBody Cliente cliente) {
		return clienteService.atualizar(cliente);
	}

	@DeleteMapping("/{id}")
	public void deletar(@PathVariable Long id) {
		clienteService.deletar(id);
	}
}
