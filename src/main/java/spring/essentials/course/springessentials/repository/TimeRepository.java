package spring.essentials.course.springessentials.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.essentials.course.springessentials.domain.Time;

public interface TimeRepository extends JpaRepository<Time, Long> {}
