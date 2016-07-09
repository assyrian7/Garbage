package com.peerbuds.profileservice;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

public class ProfileDaoImpl implements ProfileDao{
	
	
	/*
	@Override
	public int add(Profile p) {
		// TODO Auto-generated method stub
		List<Profile> profiles = getProfiles();
		for(Profile profile: profiles){
			if(profile.getUsername().equals(p.getUsername())){
				return 0;
			}
		}
		getHibernateTemplate().save(p);
		return 1;
	}

	@Override
	public void update(Profile p) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(p);
		
	}

	@Override
	public void delete(Profile p) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(p);
		
	}

	@Override
	public List<Profile> getProfiles() {
		// TODO Auto-generated method stub
		List list = getHibernateTemplate().find("select * from Profile");
		return (List<Profile>)list;
	}

	@Override
	public Profile getProfileByUsername(String username) {
		// TODO Auto-generated method stub
		List list = getHibernateTemplate().find("from Profile where username=?", username);
		return (Profile)list.get(0);
	}

	@Override
	public String returnAllProfiles() {
		// TODO Auto-generated method stub
		ProfileList list = new ProfileList(getProfiles());
		list.setJSONObject();
		return list.toString();
	}
	*/
	
	private SessionFactory sessionFactory;
	
	
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	
	@Override
	public int add(Profile p) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		
		try{
			tx = session.beginTransaction();
			session.save(p);
			tx.commit();
		} catch(HibernateException e){
			if(tx != null){
				tx.rollback();
			}
			e.printStackTrace();
		} finally{
			session.flush();
			session.close();
		}
		return 0;
	}
	@Override
	public int signUp(Profile p){
		
		Profile usernameExists = getProfileByUsername(p.getUsername());
		Profile emailExists = getProfileByEmail(p.getEmail());
		if(usernameExists != null){
			return 1;
		}
		else if(emailExists != null){
			return 2;
		}
		
		return add(p);
		
	}
	@Override
	public int login(Profile p){
		
		Profile exists = getProfileByUsername(p.getUsername());
		if(exists == null){
			return 0;
		} else if(!exists.getPassword().equals(p.getPassword())){
			return 1;
		} else{
			return 2;
		}
		
	}
	@Override
	public int update(Profile p) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.update(p);
			tx.commit();
		} catch(HibernateException e){
			if(tx != null){
				tx.rollback();
			}
			e.printStackTrace();
		} finally{
			session.flush();
			session.close();
		}
		return 1;
	}

	@Override
	public void delete(Profile p) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.delete(p);
			tx.commit();
		} catch(HibernateException e){
			if(tx != null){
				tx.rollback();
			}
			e.printStackTrace();
		} finally{
			session.flush();
			session.close();
		}
	}

	@Override
	public Profile getProfileById(int id) {
		// TODO Auto-generated method stub
		List<Profile> profiles = getProfiles();
		Profile profile = null;
		for(Profile p: profiles){
			if(id == p.getId()){
				profile = p;
				break;
			}
		}
		return profile;
	}

	@Override
	public List<Profile> getProfiles() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		List<Profile> profiles = null;
		try{
			profiles = session.createQuery("FROM Profile").list();
		} catch(HibernateException e){
			e.printStackTrace();
		} finally{
			session.flush();
			session.close();
		}
		for(Profile p: profiles){
			p.setJSONObject();
		}
		
		return profiles;
	}
	@Override
	public Profile getProfileByUsername(String username){
		List<Profile> profiles = getProfiles();
		for(Profile profile: profiles){
			if(username.equals(profile.getUsername())){
				return profile;
			}
		}
		return null;
	}
	@Override
	public String returnAllProfiles() {
		// TODO Auto-generated method stub
		ProfileList list = new ProfileList(getProfiles());
		list.setJSONObject();
		return list.toString();
	}


	@Override
	public Profile getProfileByEmail(String email) {
		// TODO Auto-generated method stub
		List<Profile> profiles = getProfiles();
		for(Profile profile: profiles){
			if(email.equals(profile.getEmail())){
				return profile;
			}
		}
		return null;
	}


	@Override
	public Profile getProfileByUsernameOrEmail(String username, String email) {
		// TODO Auto-generated method stub
		List<Profile> profiles = getProfiles();
		for(Profile profile: profiles){
			if(username.equals(profile.getUsername()) || email.equals(profile.getEmail())){
				return profile;
			}
		}
		return null;
	}
	

}
