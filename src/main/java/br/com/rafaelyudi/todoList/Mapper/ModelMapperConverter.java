package br.com.rafaelyudi.todoList.Mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import br.com.rafaelyudi.todoList.Task.TaskDTO;
import br.com.rafaelyudi.todoList.Task.TaskModel;
import br.com.rafaelyudi.todoList.User.UserDTO;
import br.com.rafaelyudi.todoList.User.UserModel;

public class ModelMapperConverter {
    
    private static ModelMapper mapper = new ModelMapper(); 

    static{
        mapper.createTypeMap(TaskModel.class, TaskDTO.class).addMapping(TaskModel::getId, TaskDTO::setKey);  
        mapper.createTypeMap(TaskDTO.class, TaskModel.class).addMapping(TaskDTO::getKey, TaskModel::setId); 
        mapper.createTypeMap(UserModel.class, UserDTO.class).addMapping(UserModel::getId, UserDTO::setKey); 
        mapper.createTypeMap(UserDTO.class, UserModel.class).addMapping(UserDTO::getKey, UserModel::setId);
    }

    public static <O,D> D parseObject(O origin, Class<D> destination){
        return mapper.map(origin, destination); 
    }

    public static <O,D> List<D> parseListObject(List<O> origin, Class<D> destination){
        
        List<D> destinationList = new ArrayList<D>(); 

        for (O o : origin) {
            destinationList.add( mapper.map(o, destination));
        }
        
        return destinationList;
    }
    

}