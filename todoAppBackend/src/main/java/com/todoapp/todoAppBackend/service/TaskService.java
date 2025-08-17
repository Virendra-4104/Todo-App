package com.todoapp.todoAppBackend.service;

import com.todoapp.todoAppBackend.entity.Task;
import com.todoapp.todoAppBackend.entity.User;
import com.todoapp.todoAppBackend.repository.TaskRepository;
import com.todoapp.todoAppBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

//    task create by user

    public Task createTask(String username, Task task){
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null){
            throw new IllegalArgumentException("User not found");
        }
        if (task.getTitle().trim().isEmpty()){
            throw new IllegalArgumentException("title can't be empty");
        }
        if (task.getTitle() == null){
            throw new IllegalArgumentException("title can't be null");
        }
        Task saved = taskRepository.save(task);
        user.getTasks().add(saved);
        userRepository.save(user);
        return taskRepository.save(saved);
    }

//    get all task by user
    public List<Task> getTaskByUser(String username){
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null){
            throw new IllegalArgumentException("User not found");
        }
        return user.getTasks();
    }

//    update task by user
    public Task updateTask(String username,String id, Task updateTask){
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null){
            throw new IllegalArgumentException("User not found");
        }
        Task oldTask = taskRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Task not exists"));
        if (updateTask.getTitle() != null){
            oldTask.setTitle(!updateTask.getTitle().trim().isEmpty()? updateTask.getTitle():oldTask.getTitle());
        }
        if (updateTask.getCompleted() == true){
            oldTask.setCompleted(updateTask.getCompleted());
        }
        Task saved = taskRepository.save(oldTask);
        return oldTask;
    }

//    Delete task by user
    public void deleteTask(String username,String id){
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null){
            throw new IllegalArgumentException("User not found");
        }
        boolean removed = user.getTasks().removeIf(x->x.getId().equals(id));
        if (!removed) throw new IllegalArgumentException("task not found");
        taskRepository.deleteById(id);
    }
}
