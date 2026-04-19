package in.com.rays.user;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

public class UserModel {

	public void add(UserDTO dto) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();

		Session session = sf.openSession();

		Transaction tx = session.beginTransaction();

		session.save(dto);

		tx.commit();

		session.close();
	}

	public void update(UserDTO dto) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();

		Session session = sf.openSession();

		Transaction tx = session.beginTransaction();

		session.update(dto);

		tx.commit();

		session.close();
	}

	public void delete(UserDTO dto) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();

		Session session = sf.openSession();

		Transaction tx = session.beginTransaction();

		session.delete(dto);

		tx.commit();

		session.close();
	}

	public UserDTO findByPk(int pk) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();

		Session session = sf.openSession();

		UserDTO dto = (UserDTO) session.get(UserDTO.class, 1);

		session.close();

		return dto;
	}

	public UserDTO authenticate(String login, String password) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();

		Session session = sf.openSession();

		Query q = session.createQuery("from UserDTO where loginId = ? and password = ?");

		q.setString(0, login);
		q.setString(1, password);

		List list = q.list();

		UserDTO dto = null;

		if (list.size() == 1) {

			dto = (UserDTO) list.get(0);

		}
		session.close();
		return dto;
	}

	public List<UserDTO> search(UserDTO dto, int pageNo, int pageSize) {

		List<UserDTO> list = null;

		SessionFactory sf = new Configuration().configure().buildSessionFactory();

		Session session = sf.openSession();

		// select * from UserDTO where 1=1
		Criteria criteria = session.createCriteria(UserDTO.class);

		if (dto != null) {
			// and firstName like 'xyz%'
			if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
				criteria.add(Restrictions.like("firstName", dto.getFirstName() + "%"));
			}
			if (dto.getLastName() != null && dto.getLastName().length() > 0) {
				criteria.add(Restrictions.like("lastName", dto.getLastName() + "%"));
			}
			// and loginId = 'xyz'
			if (dto.getLoginId() != null && dto.getLoginId().length() > 0) {
				criteria.add(Restrictions.eq("loginId", dto.getLoginId()));
			}
			if (dto.getPassword() != null && dto.getPassword().length() > 0) {
				criteria.add(Restrictions.eq("password", dto.getPassword()));
			}
		}

		// limit pageNo, pageSize
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			criteria.setFirstResult(pageNo); // initial index
			criteria.setMaxResults(pageSize);// number of record
		}

		list = criteria.list();

		if (list.size() == 0) {
			return null;
		}

		return list;

	}
}