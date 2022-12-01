package org.api;

import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.config.inject.ConfigProperty;


import java.util.List;
import java.util.Optional;

@Path("/jogadores")
public class JogadorController {
	Login login = new Login();

	@ConfigProperty(name = "token")
	String token;

	@GET
    @Path("/posicao/{posicao}")
    public List<Jogador> buscarPorCategoria(String posicao) {
        return Jogador.findByPosicao(posicao);
    }

	@Path("/lista")
	@GET
	public List<Jogador> lista(@HeaderParam("token") String token) {
		
		if(token.equals("ofh74g374f7f8nfhryg4y7gfyg6d6674bks")){
			return Jogador.listAll();
		}else {
			throw new BadRequestException();
			
			
		}
	}

	@POST
	@Transactional
	public Jogador cria(Jogador jogador) {
		jogador.persist();
		return jogador;
	}

	@PUT
	@Path("/{id}")
	@Transactional
	public String alterar(@PathParam("id") int id, Jogador dto) {
		try {
			Optional<Jogador> jogadorOp = Jogador.findByIdOptional(id);
			if (jogadorOp.isEmpty()) {
				throw new NotFoundException();
			}
			Jogador jogador = jogadorOp.get();

			jogador.setNome_jogador(dto.getNome_jogador());
			jogador.setPosicao(dto.getPosicao());
			;

			jogador.persist();
			return "Alteração feita com sucesso!";
		} catch (Exception e) {
			throw new NotFoundException(e);
		}
	}

	@DELETE
	@Path("/{id}")
	@Transactional
	public String exclui(@PathParam("id") int id) {
		try {

			Optional<Jogador> jogadorOp = Jogador.findByIdOptional(id);
			
			jogadorOp.ifPresentOrElse(Jogador::delete, () -> {
				throw new NotFoundException();
			});
			return "Jogador excluido com sucesso!!";
		} catch (Exception e) {
			throw new NotFoundException(e);
		}
	}
}