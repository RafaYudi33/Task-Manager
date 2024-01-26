package br.com.rafaelyudi.todoList.User;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.rafaelyudi.todoList.Errors.UserAlreadyExistsException;
import br.com.rafaelyudi.todoList.Mapper.ModelMapperConverter;
import br.com.rafaelyudi.todoList.Task.TaskController;
import br.com.rafaelyudi.todoList.Utils.Utils;

@Service
public class UserService {

     @Autowired
     private IUserRepository userRepository;

     @Autowired 
     private Utils utils; 
     

     public UserDTO userCreate(UserDTO data) {

          var verifyUserAlreadyExists = this.userRepository.findByUsername(data.getUsername());

          if (verifyUserAlreadyExists != null) {
               throw new UserAlreadyExistsException("Esse nome de usuário ja existe!");
          }

          var passCript = utils.passCript(data);
          var userModel = ModelMapperConverter.parseObject(data, UserModel.class);
          userModel.setPassword(passCript);
          var userPersisted = this.userRepository.save(userModel);
         

          var userDto = ModelMapperConverter.parseObject(userPersisted, UserDTO.class);
          userDto.add(linkTo(methodOn(TaskController.class).create(null, null)).withRel("Criar uma tarefa").withType("POST"));

          return userDto;
     }


}
