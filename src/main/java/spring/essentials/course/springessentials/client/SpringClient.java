package spring.essentials.course.springessentials.client;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import spring.essentials.course.springessentials.domain.Time;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Time> entity = new RestTemplate().getForEntity("http://localhost:8080/times/{id}", Time.class,1);
        log.info(entity);

        ResponseEntity<List<Time>> exchange = new RestTemplate().exchange("http://localhost:8080/times/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<Time>>() {
        });
        log.info(exchange);

        Time timePost = Time.builder().name("Cruzeiro").build();
        ResponseEntity<Time> timeSaved = new RestTemplate().exchange(
                "http://localhost:8080/times",
                HttpMethod.POST,
                new HttpEntity<>(timePost, createJsonHeader()),
                Time.class
        );
        log.info("Saved anime: {}", timeSaved);

        Time timeToUpdate = timeSaved.getBody();

        assert timeToUpdate != null;
        timeToUpdate.setName("Atlético GO");

        ResponseEntity<Void> timeUpdated = new RestTemplate().exchange(
                "http://localhost:8080/times",
                HttpMethod.PUT,
                new HttpEntity<>(timeToUpdate, createJsonHeader()),
                Void.class
        );
        log.info("Updated time: {}", timeUpdated);

        ResponseEntity<Void> timeDelete = new RestTemplate().exchange(
                "http://localhost:8080/times/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                timeToUpdate.getId()
        );
        log.info("Updated time: {}", timeUpdated);
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return httpHeaders;
    }
}
