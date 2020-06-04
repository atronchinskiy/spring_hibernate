package hiber.service;

import hiber.model.Car;
import hiber.model.User;

import java.util.List;

public interface UserService {
    void add(User user);

    void addCar(Car car);

    List<User> listUsers();

    List<Car> listCars();

    void clearAll();

    User getUser(String name, int series);
}

