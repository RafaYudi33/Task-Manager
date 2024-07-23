package br.com.rafaelyudi.todoList.Utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.rafaelyudi.todoList.Task.TaskDTO;
import br.com.rafaelyudi.todoList.Task.TaskModel;


@Service
public class Utils {
    public String[] getNullPropertyName( Object source) {

        final BeanWrapper src = new BeanWrapperImpl(source);

        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
    

        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public TaskModel copyPartialProp(TaskDTO source,  TaskModel target){
        BeanUtils.copyProperties(source, target, getNullPropertyName(source));
        return target;
    }

}
