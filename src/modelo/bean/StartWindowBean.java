package modelo.bean;


import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.faces.event.ComponentSystemEvent;
import javax.swing.*;

import businessLogic.FacadeImplementation;
import dataAccess.HibernateManagger;



public class StartWindowBean  {

	
	public void inicializar(ComponentSystemEvent unEvento) throws RemoteException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		new FacadeImplementation();
		
	}

	/*public static void main(String[] args) throws RemoteException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		
		
		new FacadeImplementation();
				
			
	}
*/
}