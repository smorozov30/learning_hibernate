package job;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {
    public static void main(String[] args) {
        Candidate rsl = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Candidate candidate = Candidate.of("Alex", 3, 10000);
            Base base = Base.of("Base");
            Vacancy one = Vacancy.of("Java Developer");
            Vacancy two = Vacancy.of("C++ Developer");
            base.addVacancy(one);
            base.addVacancy(two);
            session.save(base);
            candidate.setBase(session.load(Base.class, 1));
            session.save(candidate);

            rsl = session.createQuery(
                    "select distinct c from Candidate c "
                            + "join fetch c.base b "
                            + "join fetch b.vacancies v "
                            + "where c.id = :cId", Candidate.class
            ).setParameter("cId", 1).uniqueResult();

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        System.out.println(rsl);
    }
}
