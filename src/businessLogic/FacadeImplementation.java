package businessLogic;

import dataAccess.HibernateManagger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;
import java.util.Vector;

import modelo.dominio.Book;
import modelo.dominio.Offer;
import modelo.dominio.Owner;
import modelo.dominio.RuralHouse;
//import dataAccess.DB4oManager;
import exceptions.OfferCanNotBeBooked;


public class FacadeImplementation  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HibernateManagger gh;
 

	public FacadeImplementation() throws RemoteException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		
	    gh = HibernateManagger.getInstance();
	}


	}

