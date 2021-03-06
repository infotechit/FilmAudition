package com.actingregistration.daoImpl;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.actingregistration.dao.ActorDao;
import com.actingregistration.entities.Actor;
import com.actingregistration.utils.EntityFactory;

public class ActorDaoImpl implements ActorDao{
	
	private EntityManagerFactory entityManagerFactory=null;
	
	public ActorDaoImpl(){
		entityManagerFactory=EntityFactory.getEntityManagerFactory();
	}
	
	@Override
	public boolean saveActor(Actor actor) {
		EntityManager entityManager=entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction =null;
		try{
			entityTransaction=entityManager.getTransaction();
			entityTransaction.begin();
			entityManager.persist(actor);
			entityTransaction.commit();
			entityManager.close();
		}catch(Exception e){
			System.out.println("Error in persisting Actor");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean isActorValid(String emailId) throws ClassNotFoundException, SQLException {
		Entity entityAnno=Actor.class.getAnnotation(Entity.class);
		System.out.println(entityAnno.name());
		EntityManager entitymanager=entityManagerFactory.createEntityManager();
		Query query=entitymanager.createQuery("Select u.emailId from Actor u where u.emailId=:emailId");
		query.setParameter("emailId", emailId);
		List<String>list= query.getResultList(); 
		if(list.isEmpty())
			return false;
		return true;
	}

	@Override
	public boolean forLoginCheck(String emailId, String password) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Entity entityAnno=Actor.class.getAnnotation(Entity.class);
		System.out.println(entityAnno.name());
		EntityManager entitymanager=entityManagerFactory.createEntityManager();
		Query query=entitymanager.createQuery("Select u.loginStatus from Actor u where u.password=:password and u.emailId=:emailId");
		query.setParameter("password", password);
		query.setParameter("emailId", emailId);
		return (boolean) query.getSingleResult();
	}
	
	@Override
	public Actor getActor(String emailId, String password) throws ClassNotFoundException, SQLException {
		EntityManager entitymanager=entityManagerFactory.createEntityManager();
		Query query=entitymanager.createQuery("Select u from Actor u where u.emailId=:emailId");
		query.setParameter("emailId", emailId);
		Actor actor;
		try{
			actor= (Actor) query.getSingleResult(); 
		}catch(NoResultException nre){
			nre.printStackTrace();
			return null;
		}
		return actor;
	}

	@Override
	public boolean updateActor(Actor actor) {
		EntityManager entityManager=entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction =null;
		try{
			entityTransaction=entityManager.getTransaction();
			entityTransaction.begin();
			entityManager.merge(actor);
			entityTransaction.commit();
			entityManager.close();
		}catch(Exception e){
			System.out.println("Error in updating the actor");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Actor getActorByemailId(String emailId) throws ClassNotFoundException, SQLException {
		EntityManager entitymanager=entityManagerFactory.createEntityManager();
		Query query=entitymanager.createQuery("Select u from Actor u where u.emailId=:emailId");
		query.setParameter("emailId", emailId);
		Actor actor;
		try{
			actor= (Actor) query.getSingleResult(); 
			System.out.println(actor);
		}catch(NoResultException nre){
			nre.printStackTrace();
			return null;
		}
		return actor;
	}

	@Override
	public boolean forgotPassword(String emailId, Long contactNo) {
		Entity entityAnno=Actor.class.getAnnotation(Entity.class);
		System.out.println(entityAnno.name());
		EntityManager entitymanager=entityManagerFactory.createEntityManager();
		Query query=entitymanager.createQuery("Select u.loginStatus from Actor u where u.contactNo=:contactNo and u.emailId=:emailId");
		query.setParameter("contactNo", contactNo);
		query.setParameter("emailId", emailId);
		return (boolean) query.getSingleResult();
	}

	@Override
	public List<Actor> getAllRegisteredUsers() {
		Entity entityAnno=Actor.class.getAnnotation(Entity.class);
		System.out.println(entityAnno.name());
		EntityManager entitymanager=entityManagerFactory.createEntityManager();
		Query query=entitymanager.createQuery("From Actor");
		List<Actor> actorlist= query.getResultList(); 
		if(actorlist.isEmpty())
			return null;
		return actorlist;
	}
}
