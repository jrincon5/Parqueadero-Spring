package co.com.ceiba.parqueadero.model;

import java.util.GregorianCalendar;

public class FechaModel extends GregorianCalendar{
	private int year;
	private int month;
	private int dayOfMonth;
	private int hourOfDay;
	private int minute;
	private int second;
	
	public FechaModel(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second) {
		super(year,month,dayOfMonth,hourOfDay,minute,second);
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

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}
	
}
