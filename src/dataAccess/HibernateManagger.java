package dataAccess;

import java.io.File;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.hibernate.Session;

import dataAccess.HibernateUtil;
//import dataModel.Offer;
import modelo.dominio.Book;
import modelo.dominio.Offer;
import modelo.dominio.Owner;
import modelo.dominio.RuralHouse;
import exceptions.OfferCanNotBeBooked;
public class HibernateManagger { 
	
	private static HibernateManagger hManager=new HibernateManagger();

	private HibernateManagger() {
		
			this.openDatabase();

	}
    
	
	public  void openDatabase(){

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		System.out.println("Creando propietario...");		
			 Owner gonzalo = new Owner("gonzalo","gonzalolog","passgonzalo");
			 Owner aitor = new Owner("Aitor","aitorlog","passAitor");
			 aitor.setBankAccount("12345888");
			 gonzalo.setBankAccount("12345677");
			 session.save(gonzalo);
			 session.save(aitor);
			 session.getTransaction().commit();
			 System.out.println("Creando casa rural...");
			 RuralHouse r = new RuralHouse(1, gonzalo,"Ezkioko etxea","Ezkio");
			 this.guardarRuralHouse(r.getHouseNumber(),gonzalo,r.getDescription(),r.getCity());
			System.out.println("DataBase Initialized");
		}
		

	private void guardarRuralHouse(int houseNumber, Owner owner, String description, String city){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession(); 
		session.beginTransaction(); 
		RuralHouse  rh = new RuralHouse(houseNumber, owner, description, city);
		session.save(rh);
		session.getTransaction().commit();
		}

	
	public Vector<Owner> getOwners(){
		Vector<Owner> owners = new Vector<Owner>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession(); 
		session.beginTransaction(); 
		List<Owner> result = session.createQuery("from Owner").list(); 
		session.getTransaction().commit(); 		
		
		for(Owner o : result){
			owners.add(o);
		}
		return owners;
	}
	/**
	 * Obtener todas las casas rurales
	 */
	public Vector<RuralHouse> getAllRuralHouses(){
		Vector<RuralHouse> rhs = new Vector<RuralHouse> ();
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession(); 
		session.beginTransaction(); 
		
		List<RuralHouse> result = session.createQuery("from RuralHouse").list(); 
		session.getTransaction().commit(); 
		
		for (RuralHouse r : result) {
			rhs.add(r);
		}
		return rhs;
	}
	
	/**
	 * This method creates an offer with a house number, first day, last day and price
	 * precondition: There is no overlapping offers
	 * @param House
	 *            number, start day, last day and price
	 * @return None
	 */
	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay,
			float price) throws RemoteException, Exception {
		Offer oferta = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession(); 
		session.beginTransaction(); 
		
		List<RuralHouse> result = session.createQuery("from RuralHouse where housenumber='"+
				ruralHouse.getHouseNumber()+"'").list(); 
			
		if (result.size()>0) {//Si existe esa casa rural
			RuralHouse rh = result.get(0);//Cogemos la primera casa (al ser la clave es única)
			
			//Para un control más estricto no crea la oferta si se solapa
			/*Vector<Offer> offers = rh.getOffers(firstDay, lastDay);
			if(offers.size()<=0){		
				oferta = rh.createOffer(firstDay, lastDay, price);
			}*/
			
			oferta = rh.createOffer(firstDay, lastDay, price);
			session.save(oferta);
			
		}
		session.getTransaction().commit();
		return oferta;
	}
	
	public RuralHouse getRuralHouse(RuralHouse rh){
		//?????
		
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			RuralHouse proto = new RuralHouse(rh.getHouseNumber(),null,rh.getDescription(),rh.getCity());
			List<RuralHouse> result = session.createQuery("from RuralHouse").list();
			 return  rh=(RuralHouse)result;

	}
	
	
	public Owner getOwnerByName(String name){
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			List<Owner> result = session.createQuery("from Owner WHERE Name='"+name+"'").list();
			Owner aux = result.get(0);
	        return aux;
		}catch(Exception e){
			System.out.println("Error: "+e);
			return null;
		}

	}
	

	
	/**
	 * This method creates a book with a corresponding parameters
	 * 
	 * @param First
	 *            day, last day, house number and telephone
	 * @return a book
	 */
	public Book createBook(Offer offer, String bookTelephoneNumber){
		Book reserva = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession(); 
		session.beginTransaction(); 
		
		reserva = offer.createBook(bookTelephoneNumber);
		
		//Si la reserva es null no se añade
		if(reserva != null){
			reserva.setBookNumber(offer.getOfferNumber());
			reserva.setOffer(offer);
			session.save(reserva);
		}
		
		session.getTransaction().commit();
		return reserva;
	}
	
	/**
	 * This method returns the instance of a DB4oManager class 
	 * 
	 * @return the db4o manager
	 */
	public static HibernateManagger getInstance()  {
		
		return hManager;
	}


	public Vector<Offer> getOffers(RuralHouse casa, Date firstDay, Date lastDay) {
		// TODO Auto-generated method stub
		return casa.getOffers(firstDay, lastDay);
	}


	public Book createBook(RuralHouse ruralHouse, Date firstDate, Date lastDate, String bookTelephoneNumber)
			throws OfferCanNotBeBooked {
		try {
			System.out.println("buscando oferta...");
			Offer offer = ruralHouse.findOffer(firstDate, lastDate);
			System.out.println("Mostrando numero de la oferta que coinciden con las fechas insertadas "+ offer.getOfferNumber());
			Book b = null;
			if(offer != null){
				b=createBook(offer,bookTelephoneNumber);
			}
			return b;
		} catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}
	}

	
	
}
	
