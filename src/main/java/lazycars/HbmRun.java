package lazycars;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class HbmRun {

    public static void main(String[] args) {
        List<Make> makes = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Make vw = Make.of("VW");

            Model polo = Model.of("polo", vw);
            Model jetta = Model.of("jetta", vw);
            Model tiguan = Model.of("tiguan", vw);
            Model passat = Model.of("passat", vw);
            Model touareg = Model.of("touareg", vw);


            vw.addModel(polo);
            vw.addModel(jetta);
            vw.addModel(tiguan);
            vw.addModel(passat);
            vw.addModel(touareg);

            session.persist(vw);
            session.persist(polo);
            session.persist(jetta);
            session.persist(tiguan);
            session.persist(passat);
            session.persist(touareg);

            makes = session.createQuery( "from Make").list();
            for (Make make : makes) {
                for (Model model : make.getModels()) {
                    System.out.println(model);
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
