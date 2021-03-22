package spring.essentials.course.springessentials.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TimePostRequestBody {
    @NotEmpty(message = "The time name cannot be empty")
    private String name;
    @NotEmpty(message = "The stadium name cannot be empty")
    private String stadium;
}
