package spring.essentials.course.springessentials.util;

import spring.essentials.course.springessentials.domain.Time;
import spring.essentials.course.springessentials.requests.TimePostRequestBody;

public class TimePostRequestBodyCreator {
    public static TimePostRequestBody createTimePostRequestBody() {
        return TimePostRequestBody.builder()
                .name(TimeCreator.createTimeToBeSaved().getName())
                .build();
    }
}
