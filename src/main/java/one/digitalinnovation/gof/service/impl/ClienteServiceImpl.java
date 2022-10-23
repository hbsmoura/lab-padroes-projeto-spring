package one.digitalinnovation.gof.service.impl;

import one.digitalinnovation.gof.exceptions.ClienteNaoEncontradoException;
import one.digitalinnovation.gof.model.Cliente;
import one.digitalinnovation.gof.model.ClienteRepository;
import one.digitalinnovation.gof.model.Endereco;
import one.digitalinnovation.gof.model.EnderecoRepository;
import one.digitalinnovation.gof.service.ClienteService;
import one.digitalinnovation.gof.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementação da <b>Strategy</b> {@link ClienteService}, a qual pode ser
 * injetada pelo Spring (via {@link Autowired}). Com isso, como essa classe é um
 * {@link Service}, ela será tratada como um <b>Singleton</b>.
 * 
 * @author falvojr
 */
@Service
public class ClienteServiceImpl implements ClienteService {

	// Singleton: Injetar os componentes do Spring com @Autowired.
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ViaCepService viaCepService;
	
	// Strategy: Implementar os métodos definidos na interface.
	// Facade: Abstrair integrações com subsistemas, provendo uma interface simples.

	@Override
	public Page<Cliente> listar(Pageable pageable) {
		// Buscar todos os Clientes.
		return clienteRepository.findAll(pageable);
	}

	@Override
	public Cliente buscarPorId(Long id) {
		// Buscar Cliente por ID.
		return clienteRepository.findById(id).orElseThrow(ClienteNaoEncontradoException::new);
	}

	@Override
	public Cliente inserir(Cliente cliente) {
		cliente.setId(null);
		return salvarClienteComCep(cliente);
	}

	@Override
	public Cliente atualizar(Cliente cliente) {
		// Buscar Cliente por ID, caso exista:
		clienteRepository.findById(cliente.getId()).orElseThrow(ClienteNaoEncontradoException::new);
		return salvarClienteComCep(cliente);
	}

	@Override
	public void deletar(Long id) {
		// Deletar Cliente por ID.
		clienteRepository.findById(id).orElseThrow(ClienteNaoEncontradoException::new);
		clienteRepository.deleteById(id);
	}

	private Cliente salvarClienteComCep(Cliente cliente) {
		// Verificar se o Endereco do Cliente já existe (pelo CEP).
		String cep = cliente.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			// Caso não exista, integrar com o ViaCEP e persistir o retorno.
			Endereco novoEndereco = viaCepService.consultarCep(cep);
			enderecoRepository.save(novoEndereco);
			return novoEndereco;
		});
		Cliente novoCliente = Cliente.builder()
				.id(cliente.getId())
				.nome(cliente.getNome())
				.endereco(endereco)
				.build();
		// Inserir Cliente, vinculando o Endereco (novo ou existente).
		return clienteRepository.save(novoCliente);
	}

}
