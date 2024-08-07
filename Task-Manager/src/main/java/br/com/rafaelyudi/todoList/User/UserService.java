package br.com.rafaelyudi.todoList.User;


import br.com.rafaelyudi.todoList.Errors.UnauthorizedException;
import br.com.rafaelyudi.todoList.Errors.UserAlreadyExistsException;
import br.com.rafaelyudi.todoList.Mapper.ModelMapperConverter;
import br.com.rafaelyudi.todoList.Security.Role;
import br.com.rafaelyudi.todoList.Security.TokenService;
import br.com.rafaelyudi.todoList.Utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserService {

     @Value("${security.jwt.expirationInMillisecond}")
     private long expirationInMillisecond;
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

     private static final Logger logger = LoggerFactory.getLogger(UserService.class);

     public UserDTO userCreate(UserDTO data) {
          logger.info("Register a user!");
          var verifyUserAlreadyExists = this.userRepository.findByUsername(data.getUsername());

          if (verifyUserAlreadyExists != null) {
               throw new UserAlreadyExistsException("Esse nome de usuário ja existe!");
          }
          var passEncode = this.passwordEncoder.encode(data.getPassword());
          var userModel = ModelMapperConverter.parseObject(data, UserModel.class);
          userModel.setPassword(passEncode);
          userModel.setRole(Role.USER);
          var userDto = ModelMapperConverter.parseObject(this.userRepository.save(userModel), UserDTO.class);
          userDto.add(linkTo(methodOn(UserController.class).login(null)).withRel("Fazer login").withType("POST"));
          return userDto;
     }

     public void delete(HttpServletRequest request){
          logger.info("Delete a user!");
          this.userRepository.deleteById((UUID)request.getAttribute("idUser"));
     }

     public LoginResponseDTO login(UserCredentialsDTO credentials){
          logger.info("Login a user!");

          try {
               var userPassAuth = new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password());
               var auth = this.authenticationManager.authenticate(userPassAuth);
               var token = this.tokenService.generateToken((UserModel)auth.getPrincipal());
               return new LoginResponseDTO(token, LocalDateTime.now().plus(expirationInMillisecond, ChronoUnit.MILLIS));
          }catch (Exception exception){
               throw new UnauthorizedException("Usuário e/ou senha incorretos!");
          }
     }
}
