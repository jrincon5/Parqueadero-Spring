package co.com.ceiba.parqueadero.model;

import java.util.GregorianCalendar;

@SuppressWarnings("serial")
public class FechaCalendario extends GregorianCalendar{
	
	public FechaCalendario(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second) {
		super(year,month,dayOfMonth,hourOfDay,minute,second);
	}
	
}
