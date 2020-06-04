package hiber.service;

import hiber.dao.UserDao;
import hiber.model.Car;
import hiber.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Service("userServiceImp")
@Service
public class UserServiceImp implements UserService {

   @Autowired
   private UserDao userDao;

/*   public UserServiceImp(UserDao userDao) {
      this.userDao = userDao;
   }*/

   @Transactional
   @Override
   public void add(User user) {
      userDao.add(user);
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> listUsers() {
      return userDao.listUsers();
   }

   @Transactional
   @Override
   public void addCar(Car car) {
      userDao.addCar(car);
   }

   @Transactional(readOnly = true)
   @Override
   public List<Car> listCars() {
      return userDao.listCars();
   }

   @Transactional
   @Override
   public void clearAll() {
      userDao.clearAll();
   }

   @Transactional
   @Override
   public User getUser(String name, int series) {
      return userDao.getUser(name, series);
   }

}
