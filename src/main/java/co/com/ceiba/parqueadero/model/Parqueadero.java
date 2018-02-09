package co.com.ceiba.parqueadero.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Parqueadero{
    private ArrayList<CeldaModel> celdasCarro = new ArrayList<>();
    private ArrayList<CeldaModel> celdasMoto = new ArrayList<>();
    public static final int HORACARRO=1000;
    public static final int LIMITECARROS=20;
    public static final int LIMITEMOTOS=10;
    public static final int HORAMOTO=500;
    public static final int DIACARRO=8000;
    public static final int DIAMOTO=4000;
    public static final int MOTOALTOCILINDRAJE=2000;
    
    public FechaModel getFechaActual() {
    	Calendar Cal = Calendar.getInstance();
    	int year=Cal.get(Calendar.YEAR);
    	int mes=Cal.get(Calendar.MONTH);
    	int diaMes=Cal.get(Calendar.DAY_OF_MONTH);
    	int horaDia=Cal.get(Calendar.HOUR_OF_DAY);
    	int minuto=Cal.get(Calendar.MINUTE);
    	int second=Cal.get(Calendar.SECOND);
    	return new FechaModel(year,mes,diaMes,horaDia,minuto,second);
    }

    public boolean ingresarCarro(String placa, boolean parqueado){
    	placa=placa.toUpperCase();
        if(celdasCarro.size()<=LIMITECARROS){
            if(picoYPlaca(placa))return false;
            FechaModel f = getFechaActual();
            CarroModel c = new CarroModel(placa,parqueado);
            CeldaModel celda = new CeldaModel(c,f);
            celdasCarro.add(celda);
            //System.out.print("Ingreso un carro:");
            return true;
        }else{
            //System.out.print("Parqueadero lleno");
        }
        return false;
    }
    
    public boolean ingresarMoto(String placa, boolean parqueado, int cilindraje){
    	placa=placa.toUpperCase();
        if(celdasMoto.size()<=LIMITEMOTOS){
            if(picoYPlaca(placa))return false;
            FechaModel f = getFechaActual();
            MotoModel c = new MotoModel(placa,parqueado,cilindraje);
            CeldaModel celda = new CeldaModel(c,f);
            celdasMoto.add(celda);
            //System.out.print("Ingreso un carro:");
            return true;
        }else{
            //System.out.print("Parqueadero lleno");
        }
        return false;
    }

  //Metodo que calcula el total a pagar de los carros
    public int generarCobroCarros(FechaModel fechaEntrada, FechaModel fechaSalida){
        int horasTotales=(int)calcularHorasTotales(fechaEntrada, fechaSalida);
        int diasAPagar = horasTotales / 24;
        int horasAPagar=0;
        if((horasTotales % 24)>=9 && (horasTotales % 24)<=23) {
        	diasAPagar++;
        }else {
        	horasAPagar = horasTotales % 24;
        }        
        int totalAPagar=(diasAPagar*DIACARRO)+(horasAPagar*HORACARRO);
        return totalAPagar;
    }
    
  //Metodo que calcula el total a pagar de las motos
    public int generarCobroMotos(FechaModel fechaEntrada, FechaModel fechaSalida){
        int horasTotales=(int)calcularHorasTotales(fechaEntrada, fechaSalida);
        int diasAPagar = horasTotales / 24;
        int horasAPagar=0;
        if((horasTotales % 24)>=9 && (horasTotales % 24)<=23) {
        	diasAPagar++;
        }else {
        	horasAPagar = horasTotales % 24;
        }        
        int totalAPagar=(diasAPagar*DIAMOTO)+(horasAPagar*HORAMOTO);
        return totalAPagar;
    }
    
    //Metodo que calcula la diferencia entre dos fechas y la devuelve en horas
    public long calcularHorasTotales(FechaModel entrada, FechaModel salida) {
    	Date d1=entrada.getTime();
    	Date d2=salida.getTime();
    	long dif=(d2.getTime()-d1.getTime()) / (1000 * 60 * 60);
    	if((d2.getTime()-d1.getTime()) % (1000 * 60 * 60)!=0) dif++;
    	//System.out.println("Horas: "+(d2.getTime()-d1.getTime()) / (1000 * 60 * 60));
    	//System.out.println("Modulo: "+(d2.getTime()-d1.getTime()) % (1000 * 60 * 60));
    	return dif;
    }
    
    public boolean picoYPlaca(String placa) {
    	if(placa.startsWith("A")) {
    		if(Calendar.DAY_OF_WEEK!=0 && Calendar.DAY_OF_WEEK!=1){
    			return true;
    		}
    	}
    	return false;
    }
}