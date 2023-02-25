package es.hackUDC.slAIds.model.services.UserService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.hackUDC.slAIds.model.entities.ModelUserDao;
import es.hackUDC.slAIds.model.entities.ModelUser;
import es.hackUDC.slAIds.model.exceptions.DuplicateInstanceException;
import es.hackUDC.slAIds.model.exceptions.IncorrectLoginException;
import es.hackUDC.slAIds.model.exceptions.IncorrectPasswordException;
import es.hackUDC.slAIds.model.exceptions.InstanceNotFoundException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

  @Autowired
  private PermissionChecker permissionChecker;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private ModelUserDao userDao;

  @Override
  public void signUp(ModelUser user) throws DuplicateInstanceException {

    if (userDao.existsByUserName(user.getUserName())) {
      throw new DuplicateInstanceException("project.entities.user", user.getUserName());
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole(ModelUser.RoleType.USER);

    userDao.save(user);

  }

  @Override
  @Transactional(readOnly = true)
  public ModelUser login(String userName, String password) throws IncorrectLoginException {

    Optional<ModelUser> user = userDao.findByUserName(userName);

    if (!user.isPresent()) {
      throw new IncorrectLoginException(userName, password);
    }

    if (!passwordEncoder.matches(password, user.get().getPassword())) {
      throw new IncorrectLoginException(userName, password);
    }

    return user.get();

  }

  @Override
  @Transactional(readOnly = true)
  public ModelUser loginFromId(Long id) throws InstanceNotFoundException {
    return permissionChecker.checkUser(id);
  }

}
