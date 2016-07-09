package com.peerbuds.profileservice;

import java.util.List;

import javassist.bytecode.Descriptor.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public interface ProfileDao{
	public int add(Profile p);
	public int update(Profile p);
	public int signUp(Profile p);
	public int login(Profile p);
	public Profile getProfileByUsername(String username);
	public Profile getProfileByEmail(String email);
	public Profile getProfileByUsernameOrEmail(String username, String email);
	public void delete(Profile p);
	public List<Profile> getProfiles();
	public Profile getProfileById(int id);
	public String returnAllProfiles();
}
/*
public class ProfileDao {

	private SessionFactory factory;
	
	public ProfileDao(){ 
		factory = new Configuration().configure().buildSessionFactory();
	}
	
	public List<Profile> getProfiles(){
		List<Profile> allProfiles = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			List profiles = session.createQuery("SELECT * FROM Profile").list();
			profiles.parallelStream().forEach((Object o) -> {
				allProfiles.add((Profile)o);
			});
			tx.commit();
		} catch(HibernateException e){
			if(tx != null){
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return allProfiles;
	}
	
	public int addProfile(Profile profile){
		List<Profile> profiles = getProfiles();
		for(Profile profileA: profiles){
			if(profileA.getUsername() == profile.getUsername()){
				return 0;
			}
		}
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.save(profile);
			tx.commit();
		} catch(HibernateException e){
			if(tx != null){
				tx.rollback();
			}
			e.printStackTrace();
		} finally{
			session.close();
		}
		return 1;
	}
	public Profile getProfileByUsername(String username){
		List<Profile> profiles = getProfiles();
		Profile profile = null;
		for(Profile profileA: profiles){
			if(profileA.getUsername() == username){
				profile = profileA;
			}
		}
		return profile;
	}
	public String returnProfileByUsername(String username){
		Profile profile = getProfileByUsername(username);
		return profile.toString();
	}
	
}
*/
