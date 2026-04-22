package in.com.rays.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import in.com.rays.auction.Auction;

public class TestDirtyCheck {

	public static void main(String[] args) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();

		Session session = sf.openSession();

		Transaction tx = session.beginTransaction();

		Auction item = (Auction) session.get(Auction.class, 2);

		item.setDescription("change");

		tx.commit();

		session.close();
	}
}