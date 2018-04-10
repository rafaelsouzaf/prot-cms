package com.github.rafaelsouzaf.prot.server;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.classic.Session;

import com.github.rafaelsouzaf.prot.client.util.FileModel;
import com.github.rafaelsouzaf.prot.client.view.product.ProductModel;
import com.github.rafaelsouzaf.prot.client.view.product.ProductService;
import com.github.rafaelsouzaf.prot.server.util.HibernateUtil;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ProductServiceImpl extends RemoteServiceServlet implements ProductService {

	public List<ProductModel> getAll() {
		try {
			List<ProductModel> result = new LinkedList<ProductModel>();
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			try {
				result = session.createCriteria(ProductModel.class).list();
				session.getTransaction().commit();
			} catch (Exception e) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new LinkedList<ProductModel>();
	}

	public void delete(ProductModel model) {
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

	public void edit(ProductModel model) {
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

	public Integer insert(ProductModel model) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			session.save(model);
			ProductModel merge = (ProductModel) session.merge(model);
			session.getTransaction().commit();
			return merge.getProductId();
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

}
