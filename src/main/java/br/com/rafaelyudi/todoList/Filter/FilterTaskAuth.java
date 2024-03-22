package br.com.rafaelyudi.todoList.Filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import br.com.rafaelyudi.todoList.Errors.CustomResponseError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
                if(servletPath.startsWith("/tasks/")||((servletPath.startsWith("/users/v1/")) && (!"POST".equalsIgnoreCase(request.getMethod())))){

                    response.setHeader("Access-Control-Allow-Origin", "*");
                    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                    response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
                    response.setHeader("Access-Control-Allow-Credentials", "true");

                    try {

                        var authorization = request.getHeader("Authorization");
                        var auth_encoded = authorization.substring("Basic".length()).trim();

                        byte[] authDecode = Base64.getDecoder().decode(auth_encoded);
                        String authString = new String(authDecode);
                        String[] credentials = authString.split(":");
                        var username = credentials[0];
                        var password = credentials[1];
                        var userValidation = this.userRepository.findByUsername(username);

                        if (userValidation == null) {
                            request.setAttribute("idUser", "Unauthorized");
                            filterChain.doFilter(request, response);
                        } else {
                            var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), userValidation.getPassword());
                            if (passwordVerify.verified) {
                                request.setAttribute("idUser", userValidation.getId());
                                filterChain.doFilter(request, response);
                            } else {
                                request.setAttribute("idUser", "Unauthorized");
                                filterChain.doFilter(request, response);
                            }
                        }
                    }catch (NullPointerException e){
                        ObjectMapper objMapper = new ObjectMapper();
                        objMapper.registerModule(new JavaTimeModule().addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME)));
                        String content = objMapper.writeValueAsString(
                                new CustomResponseError("O cabeçalho deve ser do tipo: Basic Auth", LocalDateTime.now(), request.getRequestURI())
                        );
                        response.setStatus(401);
                        response.setContentType("application/json");
                        response.getWriter().write(content);
                    }

                }else{
                    filterChain.doFilter(request, response);
                }
            }
}
