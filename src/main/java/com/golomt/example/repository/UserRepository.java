package com.golomt.example.repository;

import com.golomt.example.entity.Role;
import com.golomt.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * User Repository @author Tushig
 */

public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findAll();

    /**
     * Check User
     * * @return boolean
     **/

    boolean existsByUsername(String username);

    /**
     * Find User by username
     * * @return user /Object/
     **/

    User findByUsername(String username);

    /**
     * Delete User by username
     **/

    @Transactional
    void deleteByUsername(String username);

    /**
     * User Search - by role, username
     * * @return pagination /List/
     **/

    Page<User> findAllByRolesInAndUsernameNotAndUsernameContains(List<Role> roles, String username, String value, Pageable pageable);

    /**
     * User Search - by Schedule
     * * @return pagination /List/
     **/

    Page<User> findAllByRolesInAndUsernameIsNotInAndUsernameContains(List<Role> roles, List<String> username, String value, Pageable pageable);

    List<User> findAllByRolesIn(List<Role> roles);

}
