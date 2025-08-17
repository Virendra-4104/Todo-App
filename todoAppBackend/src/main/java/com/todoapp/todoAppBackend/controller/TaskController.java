package com.todoapp.todoAppBackend.controller;

import com.todoapp.todoAppBackend.entity.Task;
import com.todoapp.todoAppBackend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

//    Create task
    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task){
        try{
            Task savedTask = taskService.createTask(task);
            return new ResponseEntity<>(savedTask, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            return new ResponseEntity<>("Error occur in server",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    get all task
    @GetMapping
    public ResponseEntity<?> getAllTask(){
        try{
            List<Task> allTask = taskService.getAllTask();
            return new ResponseEntity<>(allTask,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    Update task
    @PutMapping("/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable String taskId,@RequestBody Task task){
        try{
            Task updatedTask = taskService.updateTask(taskId, task);
            return new ResponseEntity<>(updatedTask,HttpStatus.OK);
        } catch (Exception e) {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    Delete task
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable String taskId){
        try{
            taskService.deleteTask(taskId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
