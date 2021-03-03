package message;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

public class HbmRun {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("emf");
//        UserTransaction tx = new TransactionManagerSetup().getUserTransaction();
    }
}
