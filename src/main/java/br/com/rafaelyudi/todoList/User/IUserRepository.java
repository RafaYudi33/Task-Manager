package br.com.rafaelyudi.todoList.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;



public interface IUserRepository extends JpaRepository<UserModel, UUID>{
   UserModel findByUsername(String username); 
   List<UserModel> findAll();
   @NonNull
   Optional<UserModel> findById(@NonNull UUID id);
}

