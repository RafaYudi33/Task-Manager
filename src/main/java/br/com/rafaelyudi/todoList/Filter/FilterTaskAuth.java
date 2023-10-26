package br.com.rafaelyudi.todoList.Filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.rafaelyudi.todoList.User.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter{


    @Autowired 
    private IUserRepository userRepository; 

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                var servletPath = request.getServletPath(); 
                


                if(servletPath.startsWith("/tasks/")){
                    var authorization = request.getHeader("Authorization"); 

                    

                    var auth_encoded = authorization.substring("Basic".length()).trim(); 
                    
                    byte[] authDecode = Base64.getDecoder().decode(auth_encoded);  
                    String authString = new String(authDecode);
                    String[] credentials = authString.split(":"); 
                    var username = credentials[0];
                    var password = credentials[1];
                    var userValidation = this.userRepository.findByUsername(username); 
        
                    //validar usuario

                    if(userValidation == null){
                        request.setAttribute("idUser", "Unauthorizedd");
                        filterChain.doFilter(request, response);
                    //validar a senha 
                    }else{
                        var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), userValidation.getPassword()); 
                        if(passwordVerify.verified){
                            // seto esse atributo aqui e recupero no meu controller
                            request.setAttribute("idUser",userValidation.getId());
                            filterChain.doFilter(request, response);
                        }else{
                            request.setAttribute("idUser", "Unauthorized");
                            filterChain.doFilter(request, response);
                        }
                    }
                }else{
                    filterChain.doFilter(request, response);
                }
    
            }
    
}
