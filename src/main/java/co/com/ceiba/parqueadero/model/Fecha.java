package co.com.ceiba.parqueadero.model;

import java.util.GregorianCalendar;

public class Fecha extends GregorianCalendar{
	private int year;
	private int month;
	private int dayOfMonth;
	private int hourOfDay;
	private int minute;
	
	public Fecha(int year, int month, int dayOfMonth, int hourOfDay, int minute) {
		super(year,month,dayOfMonth,hourOfDay,minute);
	}
	
	public int getYear() {
		return year;
	}
	
	public int getMes() {
		return month;
	}
	
	public int getDia() {
		return dayOfMonth;
	}
	
	public int getHora() {
		return hourOfDay;
	}
	
	public int getMinuto() {
		return minute;
	}
}
