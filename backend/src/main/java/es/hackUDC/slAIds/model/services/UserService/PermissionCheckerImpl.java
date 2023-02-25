package es.hackUDC.slAIds.model.services.UserService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.hackUDC.slAIds.model.exceptions.InstanceNotFoundException;
import es.hackUDC.slAIds.model.entities.ModelUserDao;
import es.hackUDC.slAIds.model.entities.ModelUser;

@Service
@Transactional(readOnly = true)
public class PermissionCheckerImpl implements PermissionChecker {

  @Autowired
  private ModelUserDao userDao;

  @Override
  public void checkUserExists(Long userId) throws InstanceNotFoundException {

    if (!userDao.existsById(userId)) {
      throw new InstanceNotFoundException("project.entities.user", userId);
    }

  }

  @Override
  public ModelUser checkUser(Long userId) throws InstanceNotFoundException {

    Optional<ModelUser> user = userDao.findById(userId);

    if (!user.isPresent()) {
      throw new InstanceNotFoundException("project.entities.user", userId);
    }

    return user.get();

  }

}
