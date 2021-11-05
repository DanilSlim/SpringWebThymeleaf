package spittr.data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import spittr.domain.Spitter;

@Repository
@Transactional(transactionManager = "htxManager")
public class SpitterRepositoryImpl implements SpitterRepository {
	
	private SessionFactory sessionFactory;
	
	public SpitterRepositoryImpl(SessionFactory sessionFactory) {
		
		this.sessionFactory=sessionFactory;
	}
	
	private Session getCurrentSession() {
		
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	@Transactional(transactionManager = "htxManager", propagation = Propagation.REQUIRED, readOnly = false)
	public Spitter save(Spitter spitter) {
		
		getCurrentSession().save(spitter);
		
		return spitter;
		
	}

	@Override
	@Transactional(transactionManager = "htxManager", propagation = Propagation.REQUIRED, readOnly = false)
	public Spitter finfByUsername(String userName) {
		
		@SuppressWarnings("unchecked")
		Query<Spitter> query=getCurrentSession().createQuery(
				"select spitter from Spitter spitter where spitter.userName = :userName");
		
		query.setParameter("userName", userName);
		
		return query.uniqueResult();
	}

}
