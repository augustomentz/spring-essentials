package spring.essentials.course.springessentials.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.essentials.course.springessentials.domain.Time;
import spring.essentials.course.springessentials.exception.BadRequestException;
import spring.essentials.course.springessentials.mapper.TimeMapper;
import spring.essentials.course.springessentials.repository.TimeRepository;
import spring.essentials.course.springessentials.requests.TimePostRequestBody;
import spring.essentials.course.springessentials.requests.TimePutRequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeService {
    private final TimeRepository timeRepository;

    public Page<Time> listAll(Pageable pageable) {
        return timeRepository.findAll(pageable);
    }

    public List<Time> listAllNonPageable() {
        return  this.timeRepository.findAll();
    }

    public List<Time> findByName(String name) {
        return timeRepository.findByName(name);
    }

    public Time findByIdOrThrowBadRequestException(long id) {
        return timeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Time not found"));
    }

    @Transactional
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
