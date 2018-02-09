package co.com.ceiba.parqueadero.model;

import java.util.GregorianCalendar;

public class FechaModel extends GregorianCalendar{
	
	public FechaModel(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second) {
		super(year,month,dayOfMonth,hourOfDay,minute,second);
	}
	
}
