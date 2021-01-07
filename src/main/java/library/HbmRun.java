package library;

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

            Book firstBook = Book.of("first");
            Book secondBook = Book.of("second");
            Book thirdBook = Book.of("third");
            Book fourthBook = Book.of("fourth");
            Book fifthBook = Book.of("fifth");

            Author firstAuthor = Author.of("first");
            firstAuthor.addBook(firstBook);
            firstAuthor.addBook(secondBook);

            Author secondAuthor = Author.of("second");
            secondAuthor.addBook(secondBook);
            secondAuthor.addBook(thirdBook);

            Author thirdAuthor = Author.of("third");
            thirdAuthor.addBook(firstBook);
            thirdAuthor.addBook(secondBook);
            thirdAuthor.addBook(fourthBook);
            thirdAuthor.addBook(fifthBook);

            session.persist(firstAuthor);
            session.persist(secondAuthor);
            session.persist(thirdAuthor);

            Author deleted = session.get(Author.class, 2);
            session.remove(deleted);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
