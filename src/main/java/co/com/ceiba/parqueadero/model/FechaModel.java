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

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDayOfMonth() {
		return dayOfMonth;
	}

	public void setDayOfMonth(int dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	public int getHourOfDay() {
		return hourOfDay;
	}

	public void setHourOfDay(int hourOfDay) {
		this.hourOfDay = hourOfDay;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
}
