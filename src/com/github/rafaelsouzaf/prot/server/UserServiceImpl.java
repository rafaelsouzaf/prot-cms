package com.github.rafaelsouzaf.prot.server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.github.rafaelsouzaf.prot.server.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.github.rafaelsouzaf.prot.client.view.user.UserModel;
import com.github.rafaelsouzaf.prot.client.view.user.UserService;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserServiceImpl extends RemoteServiceServlet implements UserService {
	
	public List<UserModel> getAll() {
		List<UserModel> result = new LinkedList<UserModel>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
		    result = session.createCriteria(UserModel.class).list();
		    session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return result;
	}

	public PagingLoadResult<UserModel> getAll(PagingLoadConfig config) {
		
		/**
		 * Toma numero total de datos de la DB
		 */
		int numeroTotal = getAllSize();
		
		/**
		 * Consulta
		 */
		List<UserModel> result = new ArrayList<UserModel>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(UserModel.class);
			criteria.setFirstResult(config.getOffset());
			criteria.setMaxResults(config.getLimit());
			result = criteria.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		
		return new BasePagingLoadResult<UserModel>(result, config.getOffset(), numeroTotal);
		
	}
	
	private Integer getAllSize() {
		Integer retorno = 0;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(UserModel.class);
			criteria.setProjection(Projections.rowCount());
			retorno = ((Integer)criteria.list().get(0)).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return retorno;
	}

	public void delete(UserModel model) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
		    session.delete(model);
		    session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	public void edit(UserModel model) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
		    session.update(model);
		    session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	public Integer insert(UserModel model) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			session.save(model);
			UserModel merge = (UserModel) session.merge(model);
			session.getTransaction().commit();
			return merge.getId();
//			HibernateUtil.getSessionFactory().close();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return 0;
	}

	public boolean isValid(String username, String password) {
		boolean retorno = false;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(UserModel.class);
			criteria.add(Restrictions.eq("username", username));
			criteria.add(Restrictions.eq("password", password));
			if (criteria.list() != null && criteria.list().size() > 0) {
				retorno = true;
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return retorno;
	}

}
