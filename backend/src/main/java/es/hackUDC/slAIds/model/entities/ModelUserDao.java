package es.hackUDC.slAIds.model.entities;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelUserDao extends JpaRepository<ModelUser, Long> {

  boolean existsByUserName(String userName);

  Optional<ModelUser> findByUserName(String userName);

}
