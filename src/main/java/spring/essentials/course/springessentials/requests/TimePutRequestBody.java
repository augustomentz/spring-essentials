package spring.essentials.course.springessentials.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimePutRequestBody {
    private Long id;
    private String name;
}
