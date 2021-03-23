package spring.essentials.course.springessentials.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring.essentials.course.springessentials.domain.Time;
import spring.essentials.course.springessentials.exception.BadRequestException;
import spring.essentials.course.springessentials.repository.TimeRepository;
import spring.essentials.course.springessentials.util.TimeCreator;
import spring.essentials.course.springessentials.util.TimePostRequestBodyCreator;
import spring.essentials.course.springessentials.util.TimePutRequestBodyCreator;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class TimeServiceTest {
    @InjectMocks
    private TimeService timeService;
    @Mock
    private TimeRepository timeRepositoryMock;

    @BeforeEach
    void setup() {
        PageImpl<Time> timePage = new PageImpl<>(List.of(TimeCreator.createValidTime()));
        BDDMockito.when(timeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(timePage);
        BDDMockito.when(timeRepositoryMock.findAll())
                .thenReturn(List.of(TimeCreator.createValidTime()));
        BDDMockito.when(timeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(TimeCreator.createValidTime()));
        BDDMockito.when(timeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(TimeCreator.createValidTime()));
        BDDMockito.when(timeRepositoryMock.save(ArgumentMatchers.any(Time.class)))
                .thenReturn(TimeCreator.createValidTime());
        BDDMockito.doNothing().when(timeRepositoryMock).delete(ArgumentMatchers.any(Time.class));
    }

    @Test
    @DisplayName("Should return list of times paginated")
    void listAll_ReturnListOfTimeInsidePageObject_WhenSuccessful() {
        String expectedName = TimeCreator.createValidTime().getName();
        Page<Time> timePage = timeService.listAll(PageRequest.of(1, 1));

        Assertions.assertThat(timePage).isNotNull();
        Assertions.assertThat(timePage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(timePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Should return list of times not paginated")
    void listAllNonPageable_ReturnListOfTime_WhenSuccessful() {
        String expectedName = TimeCreator.createValidTime().getName();
        List<Time> timeList = timeService.listAllNonPageable();

        Assertions.assertThat(timeList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(timeList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Should call findById and return a Time")
    void findById_ReturnTime_WhenSuccessful() {
        Long id = TimeCreator.createValidTime().getId();
        Time time = timeService.findByIdOrThrowBadRequestException(1);

        Assertions.assertThat(time).isNotNull();
        Assertions.assertThat(time.getId()).isNotNull().isEqualTo(id);
    }

    @Test
    @DisplayName("Should throws BadRequestException when not found a Time")
    void findById_ThrowBadRequestException_WhenNotFoundTime() {
        BDDMockito.when(timeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> timeService.findByIdOrThrowBadRequestException(1));
    }

    @Test
    @DisplayName("Should return list of times by name")
    void findByName_ReturnListOfTime_WhenSuccessful() {
        String expectedName = TimeCreator.createValidTime().getName();
        List<Time> timeList = timeService.findByName("name");

        Assertions.assertThat(timeList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(timeList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Should return an empty list of anime when time not found")
    void findByName_ReturnEmptyList_WhenTimeNotFound() {
        BDDMockito.when(timeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Time> timeList = timeService.findByName("name");

        Assertions.assertThat(timeList)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Should return Time when successful")
    void save_ReturnTime_WhenSuccessful() {
        Time time = timeService.save(TimePostRequestBodyCreator.createTimePostRequestBody());

        Assertions.assertThat(time)
                .isNotNull()
                .isEqualTo(TimeCreator.createValidTime());
    }

    @Test
    @DisplayName("Should return Time updated when successful")
    void replace_UpdateTime_WhenSuccessful() {
        Assertions.assertThatCode(() -> timeService.replace(TimePutRequestBodyCreator.createTimePutRequestBody()))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Should delete Time when successful")
    void delete_RemoveTime_WhenSuccessful() {
        Assertions.assertThatCode(() -> timeService.delete(1))
                .doesNotThrowAnyException();
    }
}