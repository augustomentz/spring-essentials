package spring.essentials.course.springessentials.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.essentials.course.springessentials.domain.Time;
import spring.essentials.course.springessentials.service.TimeService;
import spring.essentials.course.springessentials.util.DateUtil;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("times")
@Log4j2
@RequiredArgsConstructor
public class TimeController {
    private final DateUtil dateUtil;
    private final TimeService animeService;

    @GetMapping
    public ResponseEntity<List<Time>> list() {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));

        return ResponseEntity.ok(animeService.listAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Time> findById(@PathVariable long id) {
        return ResponseEntity.ok(animeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Time> save(@RequestBody Time time) {
        return new ResponseEntity<>(animeService.save(time), HttpStatus.CREATED);
    }
}
