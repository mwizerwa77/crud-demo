package com.learning.cruddemo.controllers;

import com.learning.cruddemo.exceptions.ResourceNotFoundException;
import com.learning.cruddemo.exceptions.UserLoginException;
import com.learning.cruddemo.models.Role;
import com.learning.cruddemo.models.UserAccount;
import com.learning.cruddemo.models.dtos.CreateUserAccountDTO;
import com.learning.cruddemo.repositories.IRoleRepository;
import com.learning.cruddemo.repositories.IUserAccountRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserAccountController {
    private final IUserAccountRepository userAccountRepository;
    private final IRoleRepository roleRepository;

    @GetMapping("/")
    public List<UserAccount> getAll(){
        return userAccountRepository.findAll();
    }
    @GetMapping("/{id}")
    public UserAccount getById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        return userAccountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("exceptions.notFound","UserAccount", "id", id));}
    @PostMapping
    public UserAccount getById( @Valid @RequestBody CreateUserAccountDTO dto) throws UserLoginException, ResourceNotFoundException {
        if(dto.getEmail()==null) throw  new UserLoginException("Email is mandatory");
       Role role=  roleRepository.findById(dto.getRoleId()).orElseThrow(
                () -> new ResourceNotFoundException("exceptions.notFound","Role", "id", dto.getRoleId()));
        UserAccount userAccount =  new UserAccount();
        userAccount.setEmail(dto.getEmail());
        userAccount.setPassword(dto.getPassword());
        userAccount.setFirstName(dto.getFirstName());
        userAccount.setLastName(dto.getLastName());
        userAccount.setPhoneNumber(dto.getPhoneNumber());
        userAccount.setRole(role);
        return userAccountRepository.save(userAccount);
    }

}
