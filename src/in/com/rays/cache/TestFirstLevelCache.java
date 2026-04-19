package in.com.rays.cache;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import in.com.rays.user.UserDTO;

public class TestFirstLevelCache {

	public static void main(String[] args) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();

		Session session = sf.openSession();

		Transaction tx = session.beginTransaction();

		UserDTO dto1 = (UserDTO) session.get(UserDTO.class, 1);

		System.out.print(dto1.getId());
		System.out.print("\t" + dto1.getFirstName());
		System.out.print("\t" + dto1.getLastName());
		System.out.print("\t" + dto1.getLoginId());
		System.out.print("\t" + dto1.getPassword());
		System.out.print("\t" + dto1.getDob());
		System.out.println("\t" + dto1.getAddress());

		session.evict(dto1); // evict method clear object from firstLevel cache

		UserDTO dto2 = (UserDTO) session.get(UserDTO.class, 1);

		System.out.print(dto2.getId());
		System.out.print("\t" + dto2.getFirstName());
		System.out.print("\t" + dto2.getLastName());
		System.out.print("\t" + dto2.getLoginId());
		System.out.print("\t" + dto2.getPassword());
		System.out.print("\t" + dto2.getDob());
		System.out.println("\t" + dto2.getAddress());

		tx.commit();
		session.close();
	}
}