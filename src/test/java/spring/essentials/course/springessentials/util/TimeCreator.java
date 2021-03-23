package spring.essentials.course.springessentials.util;

import spring.essentials.course.springessentials.domain.Time;

public class TimeCreator {
    public static Time createTimeToBeSaved() {
        return Time.builder()
                .name("Grêmio")
                .build();
    }

    public static Time createValidTime() {
        return Time.builder()
                .id(1L)
                .name("Grêmio")
                .build();
    }

    public static Time createValidUpdatedTime() {
        return Time.builder()
                .id(1L)
                .name("Palmeiras")
                .build();
    }
}
