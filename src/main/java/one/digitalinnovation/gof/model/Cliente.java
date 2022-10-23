package one.digitalinnovation.gof.model;

import one.digitalinnovation.gof.exceptions.FalhaNaInstanciacaoDoBuilderException;

import javax.persistence.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	@ManyToOne
	private Endereco endereco;

	public Cliente() {};

	public Cliente(Long id, String nome, Endereco endereco) {
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public static ClienteBuilder builder() {
		try {
			return ClienteBuilder.class.newInstance();
		} catch (ReflectiveOperationException e) {
			throw new FalhaNaInstanciacaoDoBuilderException(e, "Cliente");
		}
	}

	public static class ClienteBuilder {
		private Long idBuilder;
		private String nomeBuilder;
		private Endereco enderecoBuilder;

		public ClienteBuilder() {};

		public ClienteBuilder id(Long id) {
			this.idBuilder = id;
			return this;
		}

		public ClienteBuilder nome(String nome) {
			this.nomeBuilder = nome;
			return this;
		}

		public ClienteBuilder endereco(Endereco endereco) {
			this.enderecoBuilder = endereco;
			return this;
		}

		public Cliente build() {
			return new Cliente(this.idBuilder, this.nomeBuilder, this.enderecoBuilder);
		}
	}

}
