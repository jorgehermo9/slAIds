package es.hackUDC.slAIds.model.entities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PresentationDao extends JpaRepository<Presentation, Long> {

    public List<Presentation> findByModelUserId(Long userId);
}
