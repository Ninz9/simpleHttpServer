package com.example.onlineSchool.controller;


import com.example.onlineSchool.entity.UserEntity;
import com.example.onlineSchool.exception.UserAlreadyExistException;
import com.example.onlineSchool.exception.UserDontHaveGroups;
import com.example.onlineSchool.exception.UserNotFoundExeption;
import com.example.onlineSchool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    
    @Autowired
     private UserService userService;




    @PostMapping
    public  ResponseEntity registration(@RequestBody UserEntity user){
        try{
            userService.registration(user);
            return  ResponseEntity.ok("New USER!");
        }catch (UserAlreadyExistException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }





    @GetMapping
public ResponseEntity getOneUser(@RequestParam Long id , Long flag){
        if (flag == 1) {
            try {
                return ResponseEntity.ok(userService.getOne(id));
            } catch (UserNotFoundExeption e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Error");
            }
        } else {
            try {
                return ResponseEntity.ok(userService.getGroups(id));
            }
            catch (UserNotFoundExeption userNotFoundExeption){
                return ResponseEntity.badRequest().body(userNotFoundExeption);
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
      }
      catch (Exception e){
          return ResponseEntity.badRequest().body("Error");
      }
  }

  @PutMapping
    public ResponseEntity changeUsername(@RequestParam Long id,@RequestParam String newName){
        try {
            userService.changeUsername(id , newName);
            return ResponseEntity.ok("Username was changed");
        } catch (UserNotFoundExeption userNotFoundExeption) {
            return ResponseEntity.badRequest().body(userNotFoundExeption);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERROR");
        }
  }

}
