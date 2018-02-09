package co.com.ceiba.parqueadero.model;

import java.util.Calendar;

public class Parqueadero{
    public static final int HORACARRO=1000;
    public static final int LIMITECARROS=20;
    public static final int LIMITEMOTOS=10;
    public static final int HORAMOTO=500;
    public static final int DIACARRO=8000;
    public static final int DIAMOTO=4000;
    public static final int MOTOALTOCILINDRAJE=2000;
    
    public FechaModel getFechaActual() {
    	Calendar calendar = Calendar.getInstance();
    	int year=calendar.get(Calendar.YEAR);
    	int mes=calendar.get(Calendar.MONTH);
    	int diaMes=calendar.get(Calendar.DAY_OF_MONTH);
    	int horaDia=calendar.get(Calendar.HOUR_OF_DAY);
    	int minuto=calendar.get(Calendar.MINUTE);
    	int second=calendar.get(Calendar.SECOND);
    	return new FechaModel(year,mes,diaMes,horaDia,minuto,second);
    }
}