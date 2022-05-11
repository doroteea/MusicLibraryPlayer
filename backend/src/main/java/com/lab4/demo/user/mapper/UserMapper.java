package com.lab4.demo.user.mapper;

import com.lab4.demo.track.model.Track;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.model.User;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "name", source = "user.username")
    })
    UserMinimalDTO userMinimalFromUser(User user);

    @Mappings({
            @Mapping(target = "name", source = "user.username"),
            @Mapping(target = "roles", ignore = true)
    })
    UserListDTO userListDtoFromUser(User user);
    @Mappings({
            @Mapping(target = "username", source = "userListDTO.name"),
            @Mapping(target = "roles", ignore = true)
    })
    User userfromUserListDto(UserListDTO userListDTO);

    @AfterMapping
    default void populateRoles(User user, @MappingTarget UserListDTO userListDTO) {
        userListDTO.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));
        //userListDTO.setRoles(user.getRoles());
    }

    @AfterMapping
    default void populatetracks(User user, @MappingTarget UserListDTO userListDTO){
        userListDTO.setRoles(user.getPurchasedTracks().stream().map(Track::getTitle).collect(Collectors.toSet()));
    }


}
