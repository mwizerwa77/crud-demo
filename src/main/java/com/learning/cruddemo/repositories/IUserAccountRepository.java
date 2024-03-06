package com.learning.cruddemo.repositories;

import com.learning.cruddemo.models.Role;
import com.learning.cruddemo.models.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserAccountRepository extends JpaRepository<UserAccount, Long> {
}
