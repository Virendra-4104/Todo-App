package com.todoapp.todoAppBackend.controller;

import com.todoapp.todoAppBackend.entity.User;
import com.todoapp.todoAppBackend.service.UserService;
import com.todoapp.todoAppBackend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserAuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

//    Create Account
    @PostMapping("/sign-up")
    public ResponseEntity<?> createAccount(@RequestBody User user){
        try {
            userService.createAccount(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error occur in server", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    login Account
    @PostMapping("/log-in")
    public ResponseEntity<?> loginAccount(@RequestBody User loginRequest){
        try{
            User user = userService.loginAccount(loginRequest);
            String token = jwtUtil.generateToken(user.getUsername());
            return new ResponseEntity<>(token,HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error occur in server",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    update Account
    @PutMapping("/{username}")
    public ResponseEntity<?> updateAccount(@PathVariable String username,@RequestBody User updateRequest){
        try{
            User user = userService.updateAccount(username, updateRequest);
            return new ResponseEntity<>(user,HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>("Error occur in server",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    delete Account
    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteAccount(@PathVariable String username){
        try{
            userService.deleteAccount(username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return  new ResponseEntity<>("Error occur in server",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
