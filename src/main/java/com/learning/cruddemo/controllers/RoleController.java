package com.learning.cruddemo.controllers;

import com.learning.cruddemo.exceptions.ResourceNotFoundException;
import com.learning.cruddemo.exceptions.UserLoginException;
import com.learning.cruddemo.models.Role;
import com.learning.cruddemo.repositories.IRoleRepository;
import com.learning.cruddemo.utils.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private  final IRoleRepository roleRepository;


    @GetMapping
    public List<Role> getAll(){
        return roleRepository.findAll();
    }
    @GetMapping("/{id}")
    public Role getById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        return roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("exceptions.notFound","Role", "id", id));
    }
    @PostMapping
    public Role getById( @Valid @RequestBody Role dto) throws UserLoginException {
        if(dto.getName()==null) throw  new UserLoginException("Name is mandatory");
        return roleRepository.save(dto);
    }

}
