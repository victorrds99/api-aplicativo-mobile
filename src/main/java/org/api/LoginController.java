package org.api;

import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import java.util.List;
import java.util.Optional;

@Path("/auth")
public class LoginController {
 
    
	@POST
	@Transactional
	public String auth(Login loginRequest) {
		String token = null;
        
        try {
            List<Login> emailList= Login.autenticaEmail(loginRequest.getEmail());
            for(Login emailOb: emailList){
                if(emailOb.getEmail().equals(loginRequest.getEmail())){
                    
                    List<Login> senhaList= Login.autenticaSenha(loginRequest.getSenha());
                    for(Login senhaOb: senhaList){
                        if(senhaOb.getSenha().equals(loginRequest.getSenha())){
                        token = "ofh74g374f7f8nfhryg4y7gfyg6d6674bks";
                        return token;
                        }
                    }                    
                }
            }
    
            
           
        } catch (Exception e) {
            return "Execção";
        }
        
        
       
        
        return "Email ou Senha incorreto!!";
	}
	
    @Path("/cadastro")
	@POST
	@Transactional
	public Login cria(Login login) {
		login.persist();
		return login;
	}

	@PUT
	@Path("/{email}")
	@Transactional
	public String alterar(@PathParam("email") String email, Login request) {
		try {
			Login dto = Login.findByName(email);
			
		
			
			dto.setEmail(request.getEmail());
			dto.setSenha(request.getSenha());

			dto.persist();
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

			Optional<Login> jogadorOp = Login.findByIdOptional(id);
			
			jogadorOp.ifPresentOrElse(Login::delete, () -> {
				throw new NotFoundException();
			});
			return "Login excluido com sucesso!!";
		} catch (Exception e) {
			throw new NotFoundException(e);
		}
	}
}