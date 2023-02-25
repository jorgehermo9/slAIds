package es.hackUDC.slAIds.model.services.UserService;

import es.hackUDC.slAIds.model.entities.ModelUser;
import es.hackUDC.slAIds.model.exceptions.DuplicateInstanceException;
import es.hackUDC.slAIds.model.exceptions.IncorrectLoginException;
import es.hackUDC.slAIds.model.exceptions.IncorrectPasswordException;
import es.hackUDC.slAIds.model.exceptions.InstanceNotFoundException;

public interface UserService {

  void signUp(ModelUser user) throws DuplicateInstanceException;

  ModelUser login(String userName, String password) throws IncorrectLoginException;

  ModelUser loginFromId(Long id) throws InstanceNotFoundException;

  ModelUser updateProfile(Long id, String firstName, String lastName, String email) throws InstanceNotFoundException;

  void changePassword(Long id, String oldPassword, String newPassword)
      throws InstanceNotFoundException, IncorrectPasswordException;

}
