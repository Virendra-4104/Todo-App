package com.todoapp.todoAppBackend.controller;

import com.todoapp.todoAppBackend.entity.Task;
import com.todoapp.todoAppBackend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

//    Create task by user
    @PostMapping("/{username}")
    public ResponseEntity<?> createTask(@PathVariable String username, @RequestBody Task task){
        try{
            Task savedTask = taskService.createTask(username,task);
            return new ResponseEntity<>(savedTask, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            return new ResponseEntity<>("Error occur in server",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    get all task of user
    @GetMapping("/{username}")
    public ResponseEntity<?> getTaskByUser(@PathVariable String username){
        try{
            List<Task> allTask = taskService.getTaskByUser(username);
            return new ResponseEntity<>(allTask,HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            return new ResponseEntity<>("Error occur in server",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    Update task of user
    @PutMapping("/{username}/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable String username,@PathVariable String taskId,@RequestBody Task task){
        try{
            Task updatedTask = taskService.updateTask(username,taskId, task);
            return new ResponseEntity<>(updatedTask,HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return  new ResponseEntity<>("Error occur in server",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    Delete task of user
    @DeleteMapping("/{username}/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable String username,@PathVariable String taskId){
        try{
            taskService.deleteTask(username,taskId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error occur in server",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
