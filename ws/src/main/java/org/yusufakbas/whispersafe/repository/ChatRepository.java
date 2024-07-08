package org.yusufakbas.whispersafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.yusufakbas.whispersafe.model.Chat;
import org.yusufakbas.whispersafe.model.Users;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("select c from Chat c join c.users u where u.id=:userId")
    public List<Chat> findChatByUserId(@Param("userId") Long userId);

    @Query("select c from Chat c where c.isGroup = false And :user Member of c.users and :reqUser Member of c.users")
    public Chat findSingleChatByUserIds(@Param("user") Users user, @Param("reqUser") Users reqUser);

}
