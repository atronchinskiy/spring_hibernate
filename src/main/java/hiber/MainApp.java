package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userServiceImp = context.getBean("userServiceImp", UserService.class); //УКАЗЫВАЕМ ИНТЕРФЕЙС UserService.class, А НЕ ЕГО РЕАЛИЗАЦИЮ!!!!

        userServiceImp.clearAll();

        User user1 = new User("User1", "Lastname1", "user1@mail.ru");
        User user2 = new User("User2", "Lastname2", "user2@mail.ru");
        userServiceImp.add(user1);
        userServiceImp.add(user2);
//        userServiceImp.add(new User("User3", "Lastname3", "user3@mail.ru"));
//        userServiceImp.add(new User("User4", "Lastname4", "user4@mail.ru"));

        Car car1 = new Car("1", 1, user1.getId());
        Car car2 = new Car("2", 2, user2.getId());
        userServiceImp.addCar(car1);
        userServiceImp.addCar(car2);

        List<User> users = userServiceImp.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println();
        }

        List<Car> cars = userServiceImp.listCars();
        for (Car car : cars) {
            System.out.println("name = " + car.getName());
            System.out.println("series = " + car.getSeries());
            System.out.println("userID = " + car.getUserID());
            System.out.println();
        }

        int seriesCar;
        String nameCar;
        Scanner scanner = new Scanner(System.in);

        System.out.println("введите имя машины: ");
        nameCar = scanner.nextLine();
        System.out.println("введите серию машины машины: ");
        seriesCar = scanner.nextInt();
        User user = userServiceImp.getUser(nameCar, seriesCar);
        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("пользователя с такой машиной не существует");
        }

        context.close();
    }
}
