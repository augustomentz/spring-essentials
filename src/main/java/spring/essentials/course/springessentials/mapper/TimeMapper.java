package spring.essentials.course.springessentials.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import spring.essentials.course.springessentials.domain.Time;
import spring.essentials.course.springessentials.requests.TimePostRequestBody;
import spring.essentials.course.springessentials.requests.TimePutRequestBody;

@Mapper(componentModel = "spring")
public abstract class TimeMapper {
    public static final TimeMapper INSTANCE = Mappers.getMapper(TimeMapper.class);

    public abstract Time toTime(TimePostRequestBody timePostRequestBody);
    public abstract Time toTime(TimePutRequestBody timePutRequestBody);
}
