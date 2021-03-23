package spring.essentials.course.springessentials.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import spring.essentials.course.springessentials.domain.Time;
import spring.essentials.course.springessentials.util.TimeCreator;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("TimeRepository test")
class TimeRepositoryTest {
    @Autowired
    private TimeRepository timeRepository;

    @Test
    @DisplayName("Should save a new time")
    void save_PersistTime_WhenSuccessful() {
        Time timeToBeSaved = TimeCreator.createTimeToBeSaved();
        Time timeSaved = this.timeRepository.save(timeToBeSaved);

        Assertions.assertThat(timeSaved).isNotNull();
        Assertions.assertThat(timeSaved.getId()).isNotNull();
        Assertions.assertThat(timeSaved.getName()).isEqualTo(timeToBeSaved.getName());
    }

    @Test
    @DisplayName("Should update a time")
    void save_UpdateTime_WhenSuccessful() {
        Time timeToBeSaved = TimeCreator.createTimeToBeSaved();
        Time timeSaved = this.timeRepository.save(timeToBeSaved);
        timeSaved.setName("Palmeiras");

        Time timeUpdated = this.timeRepository.save(timeSaved);

        Assertions.assertThat(timeUpdated).isNotNull();
        Assertions.assertThat(timeUpdated.getId()).isNotNull();
        Assertions.assertThat(timeUpdated.getName()).isEqualTo(timeSaved.getName());
    }

    @Test
    @DisplayName("Should delete a time")
    void delete_RemoveTime_WhenSuccessful() {
        Time timeToBeSaved = TimeCreator.createTimeToBeSaved();
        Time timeSaved = this.timeRepository.save(timeToBeSaved);

        this.timeRepository.delete(timeSaved);
        Optional<Time> timeOptional = this.timeRepository.findById(timeSaved.getId());
        Assertions.assertThat(timeOptional).isEmpty();
    }

    @Test
    @DisplayName("Should find a time by name")
    void findByName_ReturnListOfTime_WhenSuccessful() {
        Time timeToBeSaved = TimeCreator.createTimeToBeSaved();
        Time timeSaved = this.timeRepository.save(timeToBeSaved);

        String name = timeSaved.getName();
        List<Time> times = this.timeRepository.findByName(name);
        Assertions.assertThat(times)
                .isNotEmpty()
                .contains(timeSaved);
    }

    @Test
    @DisplayName("Should return empty when not found a time with the name")
    void findByName_ReturnEmptyList_WhenTimeIsNotFound() {
        List<Time> times = this.timeRepository.findByName("notexist");

        Assertions.assertThat(times).isEmpty();
    }

    @Test
    @DisplayName("Should throw ConstraintViolationException when name is empty")
    void save_ThrowsConstraintViolationException_WhenNameIsEmpty() {
        Time timeToBeSaved = new Time();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.timeRepository.save(timeToBeSaved))
                .withMessageContaining("The time name cannot be empty");
    }
}