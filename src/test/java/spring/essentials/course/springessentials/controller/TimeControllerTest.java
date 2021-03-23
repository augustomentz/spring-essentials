package spring.essentials.course.springessentials.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring.essentials.course.springessentials.domain.Time;
import spring.essentials.course.springessentials.requests.TimePostRequestBody;
import spring.essentials.course.springessentials.requests.TimePutRequestBody;
import spring.essentials.course.springessentials.service.TimeService;
import spring.essentials.course.springessentials.util.TimeCreator;
import spring.essentials.course.springessentials.util.TimePostRequestBodyCreator;
import spring.essentials.course.springessentials.util.TimePutRequestBodyCreator;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class TimeControllerTest {
    @InjectMocks
    private TimeController timeController;
    @Mock
    private TimeService timeServiceMock;

    @BeforeEach
    void setup() {
        PageImpl<Time> timePage = new PageImpl<>(List.of(TimeCreator.createValidTime()));
        BDDMockito.when(timeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(timePage);
        BDDMockito.when(timeServiceMock.listAllNonPageable())
                .thenReturn(List.of(TimeCreator.createValidTime()));
        BDDMockito.when(timeServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(TimeCreator.createValidTime());
        BDDMockito.when(timeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(TimeCreator.createValidTime()));
        BDDMockito.when(timeServiceMock.save(ArgumentMatchers.any(TimePostRequestBody.class)))
                .thenReturn(TimeCreator.createValidTime());
        BDDMockito.doNothing().when(timeServiceMock).replace(ArgumentMatchers.any(TimePutRequestBody.class));
        BDDMockito.doNothing().when(timeServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("Should return list of times paginated")
    void list_ReturnListOfTimeInsidePageObject_WhenSuccessful() {
        String expectedName = TimeCreator.createValidTime().getName();
        Page<Time> timePage = timeController.list(null).getBody();

        Assertions.assertThat(timePage).isNotNull();
        Assertions.assertThat(timePage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(timePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Should return list of times not paginated")
    void listAll_ReturnListOfTime_WhenSuccessful() {
        String expectedName = TimeCreator.createValidTime().getName();
        List<Time> timeList = timeController.listAll().getBody();

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
        Time time = timeController.findById(1).getBody();

        Assertions.assertThat(time).isNotNull();
        Assertions.assertThat(time.getId()).isNotNull().isEqualTo(id);
    }

    @Test
    @DisplayName("Should return list of times by name")
    void findByName_ReturnListOfTime_WhenSuccessful() {
        String expectedName = TimeCreator.createValidTime().getName();
        List<Time> timeList = timeController.findByName("name").getBody();

        Assertions.assertThat(timeList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(timeList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Should return an empty list of anime when time not found")
    void findByName_ReturnEmptyList_WhenTimeNotFound() {
        BDDMockito.when(timeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Time> timeList = timeController.findByName("name").getBody();

        Assertions.assertThat(timeList)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Should return Time when successful")
    void save_ReturnTime_WhenSuccessful() {
        Time time = timeController.save(TimePostRequestBodyCreator.createTimePostRequestBody()).getBody();

        Assertions.assertThat(time)
                .isNotNull()
                .isEqualTo(TimeCreator.createValidTime());
    }

    @Test
    @DisplayName("Should return Time updated when successful")
    void replace_UpdateTime_WhenSuccessful() {
        ResponseEntity<Void> entity = timeController.replace(TimePutRequestBodyCreator.createTimePutRequestBody());

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Should delete Time when successful")
    void delete_RemoveTime_WhenSuccessful() {
        ResponseEntity<Void> entity = timeController.delete(1);

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
