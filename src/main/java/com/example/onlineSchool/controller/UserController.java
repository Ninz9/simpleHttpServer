package com.example.onlineSchool.controller;


import com.example.onlineSchool.entity.UserEntity;
import com.example.onlineSchool.exception.UserAlreadyExistException;
import com.example.onlineSchool.exception.UserDontHaveGroups;
import com.example.onlineSchool.exception.UserNotFoundExeption;
import com.example.onlineSchool.model.User;
import com.example.onlineSchool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
public class UserController {

    
    @Autowired
     private UserService userService;




    @PostMapping
    public  ResponseEntity registration(@RequestBody UserEntity user){
        try{
            UserEntity tmp = userService.registration(user);
            return  ResponseEntity.ok(tmp);
        }catch (UserAlreadyExistException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }





    @GetMapping
public ResponseEntity getUser(@RequestParam Long id , Long flag){
        if (flag == 1) {
            try {
                return ResponseEntity.ok(userService.getOne(id));
            } catch (UserNotFoundExeption e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Error");
            }
        } else {
            try {
                return ResponseEntity.ok(userService.getGroups(id));
            }
            catch (UserNotFoundExeption userNotFoundExeption){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            catch (UserDontHaveGroups userDontHaveGroups){
                return ResponseEntity.badRequest().body(userDontHaveGroups);
            }
        }
}





  @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
      try{
          return ResponseEntity.ok(userService.deleteOne(id));
      } catch (UserNotFoundExeption userNotFoundExeption){
          throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
      } catch (Exception e){
          return ResponseEntity.badRequest().body("Error");
      }
  }

  @PutMapping
    public ResponseEntity changeUsername(@RequestParam Long id,@RequestParam String newName){
        try {
            userService.changeUsername(id , newName);
            return ResponseEntity.ok("Username was changed");
        } catch (UserNotFoundExeption userNotFoundExeption) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (UserAlreadyExistException userAlreadyExistException){
            throw  new ResponseStatusException(HttpStatus.CONFLICT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERROR");
        }
  }

}
