package spring.essentials.course.springessentials.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import spring.essentials.course.springessentials.domain.Time;
import spring.essentials.course.springessentials.repository.TimeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TimeService implements TimeRepository {
    static List<Time> times;

    static {
        times = new ArrayList<>(List.of(new Time(1L, "Grêmio"), new Time(2L, "Flamengo")));
    }

    public List<Time> listAll() {
        return times;
    }

    public Time findById(long id) {
        return times
                .stream()
                .filter(time -> time.getId().equals((id)))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time not found"));
    }

    public Time save(Time time) {
        time.setId(ThreadLocalRandom.current().nextLong(3, 100000));

        times.add(time);

        return time;
    }
}
