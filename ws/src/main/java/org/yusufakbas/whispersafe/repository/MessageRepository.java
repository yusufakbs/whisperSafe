package org.yusufakbas.whispersafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.yusufakbas.whispersafe.model.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("Select m From Message m join m.chat c where c.id=:chatId")
    public List<Message> findByChatId(@Param("chatId") Long chatId);

}
