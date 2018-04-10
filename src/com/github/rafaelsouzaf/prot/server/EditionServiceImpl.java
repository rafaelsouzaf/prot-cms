package com.github.rafaelsouzaf.prot.server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.github.rafaelsouzaf.prot.client.view.edition.EditionModel;
import com.github.rafaelsouzaf.prot.client.view.edition.EditionService;
import com.github.rafaelsouzaf.prot.server.util.HibernateUtil;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class EditionServiceImpl extends RemoteServiceServlet implements EditionService {
	
	public List<EditionModel> getAll() {
		List<EditionModel> result = new LinkedList<EditionModel>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
		    result = session.createCriteria(EditionModel.class).list();
		    session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return result;
	}

	public PagingLoadResult<EditionModel> getAll(PagingLoadConfig config) {
		
		/**
		 * Toma numero total de datos de la DB
		 */
		int numeroTotal = getAllSize();
		
		/**
		 * Consulta
		 */
		List<EditionModel> result = new ArrayList<EditionModel>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(EditionModel.class);
			criteria.setFirstResult(config.getOffset());
			criteria.setMaxResults(config.getLimit());
			result = criteria.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		
		return new BasePagingLoadResult<EditionModel>(result, config.getOffset(), numeroTotal);
		
	}
	
	private Integer getAllSize() {
		Integer retorno = 0;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(EditionModel.class);
			criteria.setProjection(Projections.rowCount());
			retorno = ((Integer)criteria.list().get(0)).intValue();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return retorno;
	}

	public void delete(EditionModel model) {
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

	public void edit(EditionModel model) {
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

	public Integer insert(EditionModel model) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			session.save(model);
			EditionModel merge = (EditionModel) session.merge(model);
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
			Criteria criteria = session.createCriteria(EditionModel.class);
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
