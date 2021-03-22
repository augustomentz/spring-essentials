package spring.essentials.course.springessentials.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.essentials.course.springessentials.domain.Time;

import java.util.List;

public interface TimeRepository extends JpaRepository<Time, Long> {
    List<Time> findByName(String name);
}
