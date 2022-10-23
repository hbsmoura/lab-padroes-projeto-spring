package one.digitalinnovation.gof.service;

import one.digitalinnovation.gof.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface que define o padrão <b>Strategy</b> no domínio de cliente. Com
 * isso, se necessário, podemos ter multiplas implementações dessa mesma
 * interface.
 * 
 * @author falvojr
 */
public interface ClienteService {

	Page<Cliente> listar(Pageable pageable);

	Cliente buscarPorId(Long id);

	Cliente inserir(Cliente cliente);

	Cliente atualizar(Cliente cliente);

	void deletar(Long id);

}
