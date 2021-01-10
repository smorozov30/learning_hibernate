package carsowners;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class HbmRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

//            Engine engine = Engine.of("2JZ");
//            Car car = Car.of("Toyota", engine);
//            Driver driver = Driver.of("Sergey");
//            driver.addCar(car);
//
//            session.save(engine);
//            session.save(car);
//            session.save(driver);

            List<Driver> drivers = session.createQuery("FROM Driver").list();
            for (Driver d : drivers) {
                System.out.println(d);
                for (Car c : d.getCars()) {
                    System.out.println(c);
                }
            }

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
