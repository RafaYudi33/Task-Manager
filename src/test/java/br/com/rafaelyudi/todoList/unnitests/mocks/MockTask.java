package br.com.rafaelyudi.todoList.unnitests.mocks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.rafaelyudi.todoList.Task.TaskDTO;
import br.com.rafaelyudi.todoList.Task.TaskModel;

public class MockTask {
    
    public TaskModel mockTaskModel(int number){
        TaskModel task = new TaskModel();
        task.setId(UUID.fromString("d8321483-b592-49ac-ba3b-46f32bea96ea"));
        task.setTitle("TitleTest" + number);
        task.setDescription("DescriptionTest" + number);
        task.setPriority("PriorityTest" + number);
        task.setStartAt(LocalDateTime.of(2090, 1, 1, 1, 0, 0));
        task.setEndAt(LocalDateTime.of(2090, 2, 2, 2, 0, 0));
        task.setCreatedAt(LocalDateTime.of(2080, 1, 1, 1, 0, 0));
        task.setIdUser(UUID.fromString("e11cdccd-2087-469a-8521-34d6e67576c7"));
        return task;
    }

    public TaskDTO mockTaskDto(int number){
        TaskDTO task = new TaskDTO();
        task.setKey(UUID.fromString("d8321483-b592-49ac-ba3b-46f32bea96ea"));
        task.setTitle("TitleTest" + number);
        task.setDescription("DescriptionTest" + number);
        task.setPriority("PriorityTest" + number);
        task.setStartAt(LocalDateTime.of(2090, 1, 1, 1, 0, 0));
        task.setEndAt(LocalDateTime.of(2090, 2, 2, 2, 0, 0));
        task.setCreatedAt(LocalDateTime.of(2080, 1, 1, 1, 0, 0));
        task.setIdUser(UUID.fromString("e11cdccd-2087-469a-8521-34d6e67576c7"));
        return task;
    }

    public List<TaskModel> mockListTaskModel(){
        List<TaskModel> tasks = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            tasks.add(mockTaskModel(i));
        }

        tasks.get(1).setId(UUID.fromString("3bd45d29-53c5-4a53-93e9-f9a844ee8a26"));
        tasks.get(1).setIdUser(UUID.fromString("c43b9d5b-ee57-49ed-913f-6a15c451b31d"));

        return tasks;

    }

    public List<TaskDTO> mockListTaskDto(){
        List<TaskDTO> tasks = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            tasks.add(mockTaskDto(i));
        }

        tasks.get(1).setKey(UUID.fromString("3bd45d29-53c5-4a53-93e9-f9a844ee8a26"));
        tasks.get(1).setIdUser(UUID.fromString("c43b9d5b-ee57-49ed-913f-6a15c451b31d"));

        return tasks;

    }


}
