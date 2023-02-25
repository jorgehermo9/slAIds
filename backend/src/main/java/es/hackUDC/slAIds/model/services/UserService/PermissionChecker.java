package es.hackUDC.slAIds.model.services.UserService;

import es.hackUDC.slAIds.model.exceptions.InstanceNotFoundException;
import es.hackUDC.slAIds.model.entities.ModelUser;

public interface PermissionChecker {

  void checkUserExists(Long userId) throws InstanceNotFoundException;

  ModelUser checkUser(Long userId) throws InstanceNotFoundException;

}
