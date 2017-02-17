package modelo.bean;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;

import modelo.dominio.Book;
import modelo.dominio.Offer;
import modelo.dominio.Owner;
import modelo.dominio.RuralHouse;

import org.hibernate.Session;

import businessLogic.FacadeImplementation;
import dataAccess.HibernateManagger;
import dataAccess.HibernateUtil;

public class BookRuralHouseBean {
	RuralHouse casa;
	List<RuralHouse> ruralHouses;
	String arrivalDay;
	int nNights;
	String telephone;
	

	public RuralHouse getCasa() {
		return casa;
	}
	
	public void setCasa(RuralHouse casa) {
		this.casa = casa;
	}



	public void setRuralHouses(List<RuralHouse> ruralHouses) {
		this.ruralHouses = ruralHouses;
	}



	public String getArrivalDay() {
		return arrivalDay;
	}

	public int getnNights() {
		return nNights;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setArrivalDay(String arrivalDay) {
		this.arrivalDay = arrivalDay;
	}



	public void setnNights(int nNights) {
		this.nNights = nNights;
	}



	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	public ArrayList<RuralHouse>  getRuralHouses() throws RemoteException, Exception {
		ruralHouses = new ArrayList<RuralHouse>();
		try {
		Vector<RuralHouse> rhs = HibernateManagger.getInstance().getAllRuralHouses();
		for (RuralHouse rh: rhs){				
			ruralHouses.add(rh);
		}
		} catch (Exception e) {
			System.out.println("Error QueryAvaliability"+ e);
			e.printStackTrace();
		}
		return (ArrayList<RuralHouse>) ruralHouses;
	}

//Si la fecha introducida es menor k la fecha actual + las noches deseadas se mira si hay ofertas
	//si las hay,  crea un libro  de contacto
public String comprobarB(){
	try{

	long noches= (1000*60*60*24) * nNights;
	Date llegada = new SimpleDateFormat("d/M/y", Locale.ENGLISH).parse(this.arrivalDay);
	if(llegada != null){
		Date fin= new Date((long)(llegada.getTime()+noches));
		System.out.println("llegada "+llegada+" Noches "+noches+" Partida "+fin );
		
		if (fin.compareTo(llegada)>0){
			Vector<Offer> ofertas = new Vector<Offer>();
			
			if(casa != null){
				ofertas = HibernateManagger.getInstance().getOffers(casa, llegada, fin);
				System.out.println("Mostrando numero de las ofertas para la casa seleccionada "+ ofertas.get(0).getOfferNumber());
				
				if(ofertas.size()>0)
					System.out.println("creando libro...");
					Book b= HibernateManagger.getInstance().createBook(casa, llegada, fin, telephone);
						if(b != null) {		
							
						System.out.println("Libro creado "+b);
						return "aceptar";
						
						}else
							System.out.println("Libro nulo");					
				}
				else			
					System.out.println("No hay ofertas disponibles para esa casa");
				
			}
			else
				System.out.println("Error, no se ha seleccionado una casa");				
		}
		else 
			System.out.println("Error, no se ha seleccionado una fecha");
	
	} catch (Exception e) {
		System.out.println("Error "+e);
		return "error";	
	}
	return "error";
}

}