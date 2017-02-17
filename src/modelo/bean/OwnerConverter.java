package modelo.bean;



import java.util.Vector;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import dataAccess.HibernateManagger;
import modelo.dominio.Owner;

public class OwnerConverter implements Converter{

	@Override
	public Owner getAsObject(FacesContext arg0, UIComponent arg1, String submittedValue)
			throws ConverterException {
		if (submittedValue == null || submittedValue.isEmpty()) {
            return null;
        }
		try {
			Owner owner = null;
			Vector<Owner> owners= HibernateManagger.getInstance().getOwners();
			for(Owner o: owners){
				if(o.getName().equals(submittedValue)){
					owner = o;
				}
			}
			return owner;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ConverterException {
		Owner owner = (Owner) arg2;
		if(owner != null){
			return owner.getName();
		}
		else return "";
	}
}
