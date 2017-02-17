package modelo.bean;





import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import modelo.dominio.Book;
import modelo.dominio.Offer;
import modelo.dominio.Owner;
import modelo.dominio.RuralHouse;

import java.util.List;
import java.util.Set;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.hibernate.Session;

import businessLogic.FacadeImplementation;
import dataAccess.HibernateManagger;
import dataAccess.HibernateUtil;

public class QueryAvailabilityBean {
	RuralHouse casa;
	List<RuralHouse> ruralHouses;
	String firstDay;
	int nNights;
	private DataModel<Offer> ofertas;
	
	
	public int getnNights() {
		return nNights;
	}

	public void setnNights(int nNights) {
		this.nNights = nNights;
	}

	public DataModel<Offer> getOfertas(){
		return ofertas;
	}
	
	public RuralHouse getCasa() {
		return casa;
	}

	public void setCasa(RuralHouse casa) {
		this.casa = casa;
	}
	
	public QueryAvailabilityBean(){
	}
	
	 public String getFirstDay(){
	    return firstDay;
	 }
	    
	 public void setFirstDay(String f){	 
		 firstDay = f;
	 }
	 
	 public ArrayList<RuralHouse> getRuralHouses(){
		ruralHouses = new ArrayList<RuralHouse>();
		try {
		Vector<RuralHouse> rhs = HibernateManagger.getInstance().getAllRuralHouses();
		for (RuralHouse rh: rhs){			
			ruralHouses.add(rh);
		}
		} catch (Exception e) {
			System.out.println("Error QueryAvaliability "+e);
			e.printStackTrace();
		}
		return (ArrayList<RuralHouse>) ruralHouses;
	}

	public String comprobarQ(){
		try {
			long noches= (1000*60*60*24) * nNights;
			Date primero = new SimpleDateFormat("d/M/y", Locale.ENGLISH).parse(this.firstDay);
				Date fin= new Date((long)(primero.getTime()+noches));
				System.out.println("llegada "+primero+" Noches "+noches+" Partida "+fin );
		Vector<Offer> ofertas2 = new Vector<Offer>();
		ofertas2 = HibernateManagger.getInstance().getOffers(casa, primero, fin);
		ofertas = new ListDataModel<Offer>(ofertas2);
		} catch (Exception e) {
			System.out.println("Error ComprobarQ "+e);
			e.printStackTrace();	
		}
		return "aceptar";
	}
}
