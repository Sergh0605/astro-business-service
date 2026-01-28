package com.spuzakov.astro.astrobusinessservice.persistence.repository;

import com.spuzakov.astro.astrobusinessservice.persistence.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

  Optional<UserEntity> findByTelegramId(long telegramId);

}
