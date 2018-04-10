package com.github.rafaelsouzaf.prot.server;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.github.rafaelsouzaf.prot.client.view.product.category.CategoryModel;
import com.github.rafaelsouzaf.prot.client.view.product.category.CategoryService;
import org.hibernate.classic.Session;

import com.github.rafaelsouzaf.prot.client.util.FileModel;
import com.github.rafaelsouzaf.prot.server.util.HibernateUtil;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class CategoryServiceImpl extends RemoteServiceServlet implements CategoryService {

	public List<CategoryModel> getAll() {
		List<CategoryModel> result = new LinkedList<CategoryModel>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
		    result = session.createCriteria(CategoryModel.class).list();
		    session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return result;
	}

	public void delete(CategoryModel model) {
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

	public void edit(CategoryModel model) {
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

	public Integer insert(CategoryModel model) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			session.save(model);
			CategoryModel merge = (CategoryModel) session.merge(model);
			session.getTransaction().commit();
			return merge.getCategoryId();
//			HibernateUtil.getSessionFactory().close();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return 88;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<FileModel> getPhotos() {

		List<FileModel> photos = new LinkedList<FileModel>();

		URL url = null;
		File folder;
		try {
			url = getThreadLocalRequest().getSession().getServletContext().getResource("/images/photos");
			System.out.println("URL: " + url);
			// %20 will be converted to a space
			folder = new File(url.toURI());
		} catch (Exception e) {
			e.printStackTrace();
			folder = new File(url.getFile());
		}
		
		File[] pics = folder.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return !name.startsWith(".");
			}
		});
		Arrays.sort(pics, new Comparator<File>() {
			public int compare(File o1, File o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		for (File pic: pics) {
			FileModel photo = new FileModel();
			photo.setName(pic.getName());
			photo.setDate(new Date(pic.lastModified()));
			photo.setSize(pic.length());
			photo.setPath("/images/photos/" + pic.getName());
			photos.add(photo);
			System.out.println("Ingresada foto: " + pic.getName());
		}
		
		return photos;
		
	}

	public PagingLoadResult<CategoryModel> getAll(PagingLoadConfig config) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isValid(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

}
