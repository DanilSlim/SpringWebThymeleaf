package spittr.data;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spittr.domain.Spittle;

@Repository
@Transactional(transactionManager = "htxManager")
public class SpittleRepositoryImpl implements SpittleRepository {
	
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public SpittleRepositoryImpl(SessionFactory sessionFactory) {
		
		this.sessionFactory=sessionFactory;
	}
	
	
	private Session getCurrentSession() {
		
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public List<Spittle> findSpittles(long spitterId, int count) {
		
		@SuppressWarnings("unchecked")
		 Query<Spittle> query=getCurrentSession().createQuery(
			   "select spittle from Spittle spittle where spittle.spitter_id = :spitterId");
		
		  query.setParameter("spitterId", spitterId);
		
		//@SuppressWarnings("unchecked")
		//Query<Spittle> query=getCurrentSession().createQuery("select spittle from Spittle spittle");
		
		return query.getResultList();
	}

}
