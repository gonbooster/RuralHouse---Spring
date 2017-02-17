package modelo.bean;



import java.util.Set;
import java.util.Vector;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import dataAccess.HibernateManagger;
import modelo.dominio.RuralHouse;

public class RuralHouseConverter implements Converter{

	@Override
	public RuralHouse getAsObject(FacesContext arg0, UIComponent arg1, String submittedValue)
			throws ConverterException {
		if (submittedValue == null || submittedValue.isEmpty()) {
            return null;
        }
		try {
			RuralHouse rh = null;
			Vector<RuralHouse> rhs= HibernateManagger.getInstance().getAllRuralHouses();
			for(RuralHouse r: rhs){
				if(submittedValue.equals(r.getHouseNumber()+": "+r.getCity() )){
					rh = r;
				}
			}
			return rh;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ConverterException {
		RuralHouse rh = (RuralHouse) arg2;
		if(rh != null){
			return rh.getHouseNumber()+": "+rh.getCity();
		}
		else return "";
	}

}
