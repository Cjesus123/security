package com.tutorial.controller;

import com.tutorial.dto.NewUserRequestModel;
import com.tutorial.dto.UpdateUserRequestModel;
import com.tutorial.entity.Role;
import com.tutorial.entity.User;
import com.tutorial.enums.RoleName;
import com.tutorial.service.RoleService;
import com.tutorial.service.UserService;
import com.tutorial.utiles.Mensaje;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/personas")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/lista")
    public ResponseEntity<List<User>> listUsers(){

        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Obtener todos los usuarios
        List<User> allUsers = userService.listUser();
        List<User> users = allUsers.stream()
                .filter(user -> !user.getUserName().equals(currentUsername))
                .collect(Collectors.toList());
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int id){
        if(!userService.existsById(id))
            return new ResponseEntity(new Mensaje("El usuario con id " + id + " no existe"), HttpStatus.NOT_FOUND);
        User user = userService.getUserById(id).get();
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registerUser(@Valid @RequestBody NewUserRequestModel newUserRequestModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Mensaje("Ingrese datos válidos!"), HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByUserName(newUserRequestModel.getUserName())) {
            return new ResponseEntity<>(new Mensaje("El nombre de usuario ya existe!"), HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByEmail(newUserRequestModel.getEmail())) {
            return new ResponseEntity<>(new Mensaje("El email ingresado se encuentra registrado en el sistema"), HttpStatus.BAD_REQUEST);
        }
        User user = new User(newUserRequestModel.getName(), newUserRequestModel.getLastName(),
                newUserRequestModel.getUserName(), newUserRequestModel.getEmail(),
                passwordEncoder.encode(newUserRequestModel.getPassword()));

        if (newUserRequestModel.getRole() == null || newUserRequestModel.getRole().isEmpty()) {
            return new ResponseEntity<>(new Mensaje("Seleccione un rol válido!"), HttpStatus.BAD_REQUEST);
        } else {
                switch (newUserRequestModel.getRole()) {
                    case "admin":
                        user.setRole(new Role(RoleName.ROLE_ADMIN));
                        break;
                    case "director_carrera":
                        user.setRole(new Role(RoleName.ROLE_DIRECTOR_CARRERA));
                        break;
                    case "bedelia":
                        user.setRole(new Role(RoleName.ROLE_BEDELIA));
                        break;
                    default:
                        return new ResponseEntity<>(new Mensaje("Rol seleccionado no válido!"), HttpStatus.BAD_REQUEST);
                }
            }
        userService.save(user);
        return new ResponseEntity<>("Usuario registrado correctamente", HttpStatus.OK);
    }


    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable("id") Integer id, @Valid @RequestBody UpdateUserRequestModel updateUserRequestModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Mensaje("Ingrese datos válidos!"), HttpStatus.BAD_REQUEST);
        }

        if (!userService.existsById(id)) {
            return new ResponseEntity<>(new Mensaje("El usuario no existe"), HttpStatus.NOT_FOUND);
        }

        if (userService.existsByUserName(updateUserRequestModel.getUserName()) &&
                !userService.getUserById(id).get().getUserName().equals(updateUserRequestModel.getUserName())) {
            return new ResponseEntity<>(new Mensaje("El nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
        }

        if (userService.existsByEmail(updateUserRequestModel.getEmail()) &&
                !userService.getUserById(id).get().getEmail().equals(updateUserRequestModel.getEmail())) {
            return new ResponseEntity<>(new Mensaje("El email ya se encuentra registrado en el sistema"), HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUserById(id).orElseThrow(() -> new RuntimeException("Error: Usuario no encontrado"));
        user.setName(updateUserRequestModel.getName());
        user.setLastName(updateUserRequestModel.getLastName());
        user.setUserName(updateUserRequestModel.getUserName());
        user.setEmail(updateUserRequestModel.getEmail());

        if (updateUserRequestModel.getRole() == null || updateUserRequestModel.getRole().isEmpty()) {
            return new ResponseEntity<>(new Mensaje("Seleccione un rol válido!"), HttpStatus.BAD_REQUEST);
        } else {
            switch (updateUserRequestModel.getRole()) {
                case "admin":
                    user.setRole(new Role(RoleName.ROLE_ADMIN));
                    break;
                case "director_carrera":
                    user.setRole(new Role(RoleName.ROLE_DIRECTOR_CARRERA));
                    break;
                case "bedelia":
                    user.setRole(new Role(RoleName.ROLE_BEDELIA));
                    break;
                default:
                    return new ResponseEntity<>(new Mensaje("Rol seleccionado no válido!"), HttpStatus.BAD_REQUEST);
            }
        }
        userService.save(user);
        return new ResponseEntity<>(new Mensaje("Usuario actualizado correctamente"), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if(!userService.existsById(id))
            return new ResponseEntity(new Mensaje("No existe el usuario"), HttpStatus.NOT_FOUND);
        userService.deleteUser(id);
        return new ResponseEntity(new Mensaje("Se elimino el usuario"), HttpStatus.OK);
    }
}
