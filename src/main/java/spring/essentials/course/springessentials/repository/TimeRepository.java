package spring.essentials.course.springessentials.repository;

import spring.essentials.course.springessentials.domain.Time;

import java.util.List;

public interface TimeRepository {
    List<Time> listAll();
}
