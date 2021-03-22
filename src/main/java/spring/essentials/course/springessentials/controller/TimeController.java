package spring.essentials.course.springessentials.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.essentials.course.springessentials.domain.Time;
import spring.essentials.course.springessentials.requests.TimePostRequestBody;
import spring.essentials.course.springessentials.requests.TimePutRequestBody;
import spring.essentials.course.springessentials.service.TimeService;
import spring.essentials.course.springessentials.util.DateUtil;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("times")
@Log4j2
@RequiredArgsConstructor
public class TimeController {
    private final DateUtil dateUtil;
    private final TimeService timeService;

    @GetMapping
    public ResponseEntity<Page<Time>> list(Pageable pageable) {
        return ResponseEntity.ok(timeService.listAll(pageable));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Time> findById(@PathVariable long id) {
        return ResponseEntity.ok(timeService.findByIdOrThrowBadRequestException(id));
    }

    @GetMapping(path = "/find")
    public ResponseEntity<List<Time>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(timeService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<Time> save(@RequestBody @Valid TimePostRequestBody timePostRequestBody) {
        return new ResponseEntity<>(timeService.save(timePostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        timeService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody TimePutRequestBody timePutRequestBody) {
        timeService.replace(timePutRequestBody);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
