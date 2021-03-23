package spring.essentials.course.springessentials.requests;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class TimePostRequestBody {
    @NotEmpty(message = "The time name cannot be empty")
    private String name;
}
