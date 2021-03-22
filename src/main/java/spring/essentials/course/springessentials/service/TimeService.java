package spring.essentials.course.springessentials.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import spring.essentials.course.springessentials.domain.Time;
import spring.essentials.course.springessentials.mapper.TimeMapper;
import spring.essentials.course.springessentials.repository.TimeRepository;
import spring.essentials.course.springessentials.requests.TimePostRequestBody;
import spring.essentials.course.springessentials.requests.TimePutRequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeService {
    private final TimeRepository timeRepository;

    public List<Time> listAll() {
        return timeRepository.findAll();
    }

    public Time findByIdOrThrowBadRequestException(long id) {
        return timeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time not found"));
    }

    public Time save(TimePostRequestBody timePostRequestBody) {
        return timeRepository.save(TimeMapper.INSTANCE.toTime(timePostRequestBody));
    }

    public void delete(long id) {
        timeRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(TimePutRequestBody timePutRequestBody) {
        Time savedTime = findByIdOrThrowBadRequestException(timePutRequestBody.getId());
        Time time = TimeMapper.INSTANCE.toTime(timePutRequestBody);
        time.setId(savedTime.getId());

        timeRepository.save(time);
    }
}
