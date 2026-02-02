package com.spuzakov.astro.astrobusinessservice.mapper;

import com.spuzakov.astro.astrobusinessservice.model.User;
import com.spuzakov.astro.astrobusinessservice.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {OrderMapper.class}
)
public interface UserMapper extends ObjectMapper<UserEntity, User> {

}
