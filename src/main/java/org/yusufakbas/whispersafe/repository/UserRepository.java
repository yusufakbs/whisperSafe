package org.yusufakbas.whispersafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.yusufakbas.whispersafe.model.Users;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {

    public Users findByEmail(String email);

    @Query("select u from Users u where u.fullName like %:query% or u.email like %:query%")
    public List<Users> searchUser(@Param("query") String query);

}
