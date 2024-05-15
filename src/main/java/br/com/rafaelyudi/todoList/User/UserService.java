package br.com.rafaelyudi.todoList.User;


import br.com.rafaelyudi.todoList.Errors.ForbiddenException;
import br.com.rafaelyudi.todoList.Errors.UserAlreadyExistsException;
import br.com.rafaelyudi.todoList.Mapper.ModelMapperConverter;
import br.com.rafaelyudi.todoList.Security.TokenService;
import br.com.rafaelyudi.todoList.Task.TaskController;
import br.com.rafaelyudi.todoList.Utils.Utils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserService {

     @Autowired
     private IUserRepository userRepository;

     @Autowired 
     private Utils utils;

     @Autowired
     private AuthenticationManager authenticationManager;

     @Autowired
     PasswordEncoder passwordEncoder;

     @Autowired
     TokenService tokenService;

     public UserDTO userCreate(UserDTO data) {
          var verifyUserAlreadyExists = this.userRepository.findByUsername(data.getUsername());

          if (verifyUserAlreadyExists != null) {
               throw new UserAlreadyExistsException("Esse nome de usuário ja existe!");
          }
          var passEncode = this.passwordEncoder.encode(data.getPassword());
          var userModel = ModelMapperConverter.parseObject(data, UserModel.class);
          userModel.setPassword(passEncode);
          var userDto = ModelMapperConverter.parseObject(this.userRepository.save(userModel), UserDTO.class);
          userDto.add(linkTo(methodOn(TaskController.class).create(null)).withRel("Criar sua primeira tarefa").withType("POST"));
          
          return userDto;
     }

     public void delete(UUID id){
          this.userRepository.deleteById(id);
     }

     public LoginResponseDTO login(@Valid UserCredentialsDTO credentials){

          try {
               var userPassAuth = new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password());
               var auth = this.authenticationManager.authenticate(userPassAuth);
               var token = this.tokenService.generateToken((UserModel)auth.getPrincipal());
               return new LoginResponseDTO(token, this.userRepository.findByUsername(credentials.username()).getId());
          }catch (Exception exception){
               throw new ForbiddenException();
          }
     }
}
