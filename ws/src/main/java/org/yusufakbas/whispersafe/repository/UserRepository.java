package org.yusufakbas.whispersafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.yusufakbas.whispersafe.model.Users;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    public Users findByEmail(String email);

    @Query("select u from Users u where u.username like %:query% or u.email like %:query%")
    public List<Users> searchUser(@Param("query") String query);

}
