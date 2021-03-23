package spring.essentials.course.springessentials.util;

import spring.essentials.course.springessentials.requests.TimePutRequestBody;

public class TimePutRequestBodyCreator {
    public static TimePutRequestBody createTimePutRequestBody() {
        return TimePutRequestBody.builder()
                .id(TimeCreator.createValidUpdatedTime().getId())
                .name(TimeCreator.createValidUpdatedTime().getName())
                .build();
    }

}
