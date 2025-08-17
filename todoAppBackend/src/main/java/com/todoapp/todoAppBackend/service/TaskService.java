package com.todoapp.todoAppBackend.service;

import com.todoapp.todoAppBackend.entity.Task;
import com.todoapp.todoAppBackend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

//    Create task
    public Task createTask(Task task){
        if (task.getTitle().trim().isEmpty()){
            throw new IllegalArgumentException("title can't be empty");
        }
        if (task.getTitle() == null){
            throw new IllegalArgumentException("title can't be null");
        }
        return taskRepository.save(task);
    }

//    get all tasks
    public List<Task> getAllTask(){
        return taskRepository.findAll();
    }

//    update task
    public Task updateTask(String id, Task updateTask){
        Task oldTask = taskRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Task not exists"));
        if (updateTask.getTitle() != null){
            oldTask.setTitle(!updateTask.getTitle().trim().isEmpty()? updateTask.getTitle():oldTask.getTitle());
        }
        if (updateTask.getCompleted() == true){
            oldTask.setCompleted(updateTask.getCompleted());
        }
        taskRepository.save(oldTask);
        return oldTask;
    }

//    Delete task
    public void deleteTask(String id){
        taskRepository.deleteById(id);
    }
}
