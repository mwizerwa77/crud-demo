package com.learning.cruddemo.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learning.cruddemo.models.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserAccountDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private long roleId;

    private String password;
}
