package co.com.ceiba.parqueadero.model;

import java.util.Calendar;

public class Parqueadero{
	public static final int CILINDRAJEREGLAMOTO = 500;
	public static final int AUMENTOCILINDRAJE = 2000;
	public static final int HORASMINIMASDELDIA = 9;
	public static final int HORASMAXIMASDELDIA = 24;
	public static final int VALORHORACARRO=1000;
    public static final int LIMITECARROS=20;
    public static final int LIMITEMOTOS=10;
    public static final int VALORHORAMOTO=500;
    public static final int VALORDIACARRO=8000;
    public static final int VALORDIAMOTO=4000;
    public static final int MILISEGUNDOS=1000;
    public static final int SEGUNDOS=60;
    public static final int MINUTOS=60;
    
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