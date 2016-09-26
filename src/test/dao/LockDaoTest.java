package dao;

import domain.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import utils.HibernateUtils;

import static org.junit.Assert.*;

/**
 * Created by lenovo on 2016/09/26.
 */
public class LockDaoTest {
    @Test
    public void save() throws Exception {
        Session session = HibernateUtils.getSession();
        Session session1 = HibernateUtils.getSession();
        Product product = session.get(Product.class, 1L);
        Product product1 = session1.get(Product.class, 1L);
        Transaction transaction = session.getTransaction();
        Transaction transaction1 = session1.getTransaction();
        try {
            transaction.begin();
            transaction1.begin();
            product.setNum(product.getNum()-8);
            product1.setNum(product1.getNum()-5);
            session.update(product);
            session1.update(product1);
            transaction.commit();
            transaction1.commit();
        } catch (Exception e) {
            System.out.println("请刷新页面");
        } finally {
            session.close();
            session1.close();
        }
    }

    @Test
    public void create() throws Exception {
        Session session = HibernateUtils.getSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Product product = new Product();
        product.setName("内裤");
        product.setNum(10);
        session.save(product);
        transaction.commit();
        session.close();
    }


}