package es.hackUDC.slAIds.rest.dtos;

import es.hackUDC.slAIds.model.entities.ModelUser;

public class UserConversor {

  private UserConversor() {
  }

  public final static UserDto toUserDto(ModelUser user) {
    return new UserDto(user.getId(), user.getUserName(), user.getFirstName(), user.getLastName(), user.getEmail(),
        user.getRole().toString());
  }

  public final static ModelUser toUser(UserDto userDto) {

    return new ModelUser(userDto.getUserName(), userDto.getPassword(), userDto.getFirstName(), userDto.getLastName(),
        userDto.getEmail());
  }

  public final static AuthenticatedUserDto toAuthenticatedUserDto(String serviceToken, ModelUser user) {

    return new AuthenticatedUserDto(serviceToken, toUserDto(user));

  }

}
