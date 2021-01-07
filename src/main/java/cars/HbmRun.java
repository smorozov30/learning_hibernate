package cars;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Model polo = new Model("polo");
            Model jetta = new Model("jetta");
            Model tiguan = new Model("tiguan");
            Model passat = new Model("passat");
            Model touareg = new Model("touareg");
            session.save(polo);
            session.save(jetta);
            session.save(tiguan);
            session.save(passat);
            session.save(touareg);

            Make vw = new Make("VW");
            vw.addModel(session.load(Model.class, 1));
            vw.addModel(session.load(Model.class, 2));
            vw.addModel(session.load(Model.class, 3));
            vw.addModel(session.load(Model.class, 4));
            vw.addModel(session.load(Model.class, 5));

            session.save(vw);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
