package br.com.rafaelyudi.todoList.Utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import br.com.rafaelyudi.todoList.Errors.UnauthorizedException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.rafaelyudi.todoList.Task.TaskDTO;
import br.com.rafaelyudi.todoList.Task.TaskModel;


@Service
public class Utils {
    
     public String passCript(String password) {
          var passwordCript = BCrypt.withDefaults().hashToString(12, password.toCharArray());
          return passwordCript;
     }
   
    public String[] getNullPropertyName( Object source) {
        // Cria um BeanWrapper usando o objeto de origem. Isso permite acessar suas propriedades.
        final BeanWrapper src = new BeanWrapperImpl(source);
    
        // Obtém as descrições de propriedades do objeto de origem.
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
    
        // Cria um conjunto (Set) para armazenar os nomes de propriedades com valores nulos.
        Set<String> emptyNames = new HashSet<>();
    
        // Itera pelas descrições de propriedades.
        for (PropertyDescriptor pd : pds) {
            // Obtém o valor da propriedade atual.
            Object srcValue = src.getPropertyValue(pd.getName());
    
            // Verifica se o valor da propriedade é nulo.
            if (srcValue == null) {
                // Se for nulo, adiciona o nome da propriedade ao conjunto emptyNames.
                emptyNames.add(pd.getName());
            }
        }
        
        // Converte o conjunto emptyNames em um array de strings.
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    //usa o array de prop nulas do objeto da requisição, e copias todos essas propriedades nulas, do banco pra requisição, para que o update parcial seja feito
    public TaskModel copyPartialProp(TaskDTO source,  TaskModel target){
        BeanUtils.copyProperties(source, target, getNullPropertyName(source));
        return target; 
    }


    public boolean verifyAuthorization(Object idUser) {


        if (idUser.toString().equals("Unauthorized")) {
            return false;
        }
        return true;
    }

    public boolean verifyAuthorization(Object idUser, Object idUserFromRepository) {

        if (!verifyAuthorization(idUser) || !idUser.equals(idUserFromRepository)) {
            return false;
        }
        return true;
    }


}
