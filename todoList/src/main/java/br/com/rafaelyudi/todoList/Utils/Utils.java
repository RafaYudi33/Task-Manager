package br.com.rafaelyudi.todoList.Utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {
    

    public static String[] getNullPropertyName(Object source) {
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
    public static void copyPartialProp(Object source, Object target){
        BeanUtils.copyProperties(source, target, getNullPropertyName(source));
    }

    // Utils.copyPartialProp(taskModel, task) é usada para atualizar os valores nulos no objeto taskModel com os valores correspondentes do objeto
    // task recuperado do banco de dados. Isso é especialmente útil quando você recebe uma requisição que contém apenas os atributos que precisam
    // ser alterados, e todos os outros atributos do objeto taskModel são nulos.
    // A função copyPartialProp identifica quais propriedades do taskModel têm valores nulos (ou seja, os atributos que não foram fornecidos na 
    //requisição) e copia os valores correspondentes do objeto task para o taskModel. Isso garante que você preserve os valores existentes em task 
    //para as propriedades que não foram fornecidas na requisição.
    // Assim, somente as propriedades fornecidas na requisição serão atualizadas, enquanto as demais permanecerão inalteradas, com os valores
    // originais obtidos do objeto task. Esse é um padrão comum em atualizações parciais para garantir que os valores não fornecidos na requisição
    // não sejam sobrescritos com valores nulos

    
}
