package br.com.techne.cadastro.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.techne.cadastro.model.Pessoa;
import br.com.techne.cadastro.service.PessoaService;

@Path("/cadastro/pessoa")
public class PessoaController {
	@EJB
	private PessoaService pessoaService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarPessoa() {
		try {
			List<Pessoa> arrayList = pessoaService.listar();

			return Response.ok(arrayList).build();//
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response inserirPessoa(Pessoa pessoa) {
		try {
			Pessoa pessoaSalva = pessoaService.salvar(pessoa);
			if (pessoaSalva != null) {
				return Response.ok(pessoa).build();
			} else {
				return Response.serverError().build();
			}
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}
	@Path("{id}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response editarPessoa(Pessoa pessoa, @PathParam("id") Long id) {
		try {
			Pessoa pessoaBD = pessoaService.show(id);
			if(pessoa!=null){
				pessoaBD.setCpf(pessoa.getCpf());
				pessoaBD.setIdade(pessoa.getIdade());
				pessoaBD.setNome(pessoa.getNome());
				Pessoa pessoaSalva = pessoaService.editar(pessoaBD);
				if (pessoaSalva != null) {
					return Response.ok(pessoa).build();
				} else {
					return Response.serverError().build();
				}
			}else{
				return Response.status(404).build();
			}	
		} catch (Exception e) {
			System.out.println(e);
			return Response.serverError().build();
		}
	}
	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response excluirPessoa(@PathParam("id") Long id) {
		try {
			Pessoa pessoa = pessoaService.show(id);
			if(pessoa!=null){
				pessoaService.excluir(pessoa);
				return Response.ok(pessoa).build();
			}else{
				return Response.status(404).build();
			}			
			
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response showPessoa(@PathParam("id") Long id) {
		try {
			Pessoa pessoa = pessoaService.show(id);
			if(pessoa!=null){
				return Response.ok(pessoa).build();
			}else{
				return Response.status(404).build();
			}		
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

}
