package job;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Candidate one = Candidate.of("Alex", 3, 10000);
            Candidate two = Candidate.of("Nikolay", 5, 20000);
            Candidate three = Candidate.of("Nikita", 10, 30000);

            session.save(one);
            session.save(two);
            session.save(three);

            Query query = session.createQuery("from Candidate");
            for (Object c : query.list()) {
                System.out.println(c);
            }

            query = session.createQuery("from Candidate s where s.id = 2");
            System.out.println(query.uniqueResult());

            query = session.createQuery(
                    "update Candidate s set s.experience = :newExperience, s.salary = :newSalary where s.id = :fId"
            );
            query.setParameter("newExperience", 15);
            query.setParameter("newSalary", 40000);
            query.setParameter("fId", 1);
            query.executeUpdate();

            query = session.createQuery("from Candidate s where s.id = :fid").setParameter("fid", 1);
            System.out.println(query.uniqueResult());

            session.createQuery("delete from Candidate where id = :fId")
                    .setParameter("fId", 1)
                    .executeUpdate();

            query = session.createQuery("from Candidate s where s.id = :fid").setParameter("fid", 1);;
            System.out.println(query.uniqueResult());

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
