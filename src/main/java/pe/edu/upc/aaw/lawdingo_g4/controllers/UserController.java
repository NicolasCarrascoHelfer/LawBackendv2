package pe.edu.upc.aaw.lawdingo_g4.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import pe.edu.upc.aaw.lawdingo_g4.dtos.UserDTO;
import pe.edu.upc.aaw.lawdingo_g4.entities.Users;
import pe.edu.upc.aaw.lawdingo_g4.serviceinterfaces.IUserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private IUserService uS;
    
    @PostMapping("/save")
    public String saveUser(@Valid Users user, BindingResult result, Model model, SessionStatus status, @RequestBody UserDTO dto)
            throws Exception {
        if (result.hasErrors()) {
            return "usersecurity/user";
        } else {

            ModelMapper m = new ModelMapper();
            Users u = m.map(dto, Users.class);
            String bcryptPassword = bcrypt.encode(u.getPassword());
            u.setPassword(bcryptPassword);
            uS.insert(u);
            return "Usuario creado";
        }

    }


    @GetMapping("/startsWith/{letter}")
    public List<Users> getUsersWhoseNameStartsWith(@PathVariable String letter) {
        return uS.getUsersWhoseNameStartsWith(letter);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id){
        uS.delete(id);
    }

    @GetMapping
    public List<UserDTO> list(){
        return uS.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, UserDTO.class);
        }).collect(Collectors.toList());
    }
    @PutMapping()
    public void goUpdate(@RequestBody UserDTO dto){
        ModelMapper m=new ModelMapper();
        Users u=m.map(dto,Users.class);
        uS.insert(u);
    }
    @GetMapping("/{id}")
    public UserDTO ListId(@PathVariable("id")Integer id){
        ModelMapper m = new ModelMapper();
        UserDTO dto = m.map(uS.ListId(id), UserDTO.class);
        return dto;
    }
    @GetMapping("/username/{username}")
    public UserDTO ListUsername(@PathVariable("username")String name){
        ModelMapper m = new ModelMapper();
        UserDTO dto = m.map(uS.listUsername(name), UserDTO.class);
        return dto;
    }
}



