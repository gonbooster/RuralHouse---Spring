package modelo.bean;


import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;

import javax.faces.context.FacesContext;










import org.hibernate.Session;

import dataAccess.HibernateManagger;
import dataAccess.HibernateUtil;
import modelo.dominio.Offer;
import modelo.dominio.Owner;
import modelo.dominio.RuralHouse;


public class SetAvailabilityBean {
Owner owner;
List<Owner> owners;
RuralHouse casa;
List<RuralHouse> ruralHouses;
String firstDay;
String lastDay;
float price;


public Owner getOwner(){
	return owner;
}

public void setOwner(Owner o){
	owner = o;
}

public float getPrice() {
	return price;
}

public void setPrice(float price) {
	this.price = price;
}

public String getFirstDay() {
	return firstDay;
}
public void setFirstDay(String firstDay) {
	this.firstDay = firstDay;
}
public String getLastDay() {
	return lastDay;
}
public void setLastDay(String lastDay) {
	this.lastDay = lastDay;
}

public RuralHouse getCasa() {
	return casa;
}
public void setCasa(RuralHouse casa) {
	this.casa = casa;
}

public ArrayList<RuralHouse>  getRuralHouses() throws RemoteException {
	try{
	ruralHouses = new ArrayList<RuralHouse>();
	Set<RuralHouse> rh = owner.getRuralHouses();
	for (RuralHouse r: rh){
		ruralHouses.add(r);
	}
	if(ruralHouses.size()<=0)
		System.out.println("Error :: No hay ruralHouses");

return (ArrayList<RuralHouse>) ruralHouses;

	}catch(Exception e){
		System.out.println("Error "+e);
	}
	return null;
	}

public void setRuralHouses(List<RuralHouse> ruralHouses) {
	this.ruralHouses = ruralHouses;
}


public ArrayList<Owner> getOwners(){
	owners = new ArrayList<Owner>();
	try {
	Vector<Owner> ow = HibernateManagger.getInstance().getOwners();
	for (Owner o: ow){
		owners.add(o);
	}
	} catch (Exception e) {
		System.out.println("Error SetAvaliability "+ e);
		e.printStackTrace();
	}
return (ArrayList<Owner>) owners;
}

public String comprobarS1(){
	return "aceptar";
}



public String comprobarS2() throws RemoteException, Exception{//falta por implementar la casa rural	
	System.out.println("Selected item " + casa);
	try {
		
		Date entrada = new SimpleDateFormat("d/M/y", Locale.ENGLISH).parse(this.firstDay);
		System.out.println("Fecha entrada "+entrada);
		Date salida = new SimpleDateFormat("d/M/y", Locale.ENGLISH).parse(this.lastDay);
		System.out.println("Fecha salida "+ salida);
		if(casa != null){
			
			if(entrada != null && salida !=null){
				if(salida.compareTo(entrada)>0){ //compara las fechas  SI salida > entrada == 1
					System.out.println("Creando oferta...");	
					HibernateManagger.getInstance().createOffer(casa, entrada, salida, price);
					return "aceptar";
				}
				else
					System.out.println("Error la fecha de salida debe de ser posterior a la de entrada");
				
			}
			else 
				System.out.println("Erro alguna de las fechas es null");

		}
		else
			System.out.println("Error la casa rural es null");

	} catch (Exception e) {
		System.out.println("Error "+ e);
		return "error";
	}
	return "error";
}
}