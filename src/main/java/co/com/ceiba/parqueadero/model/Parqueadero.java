package co.com.ceiba.parqueadero.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Parqueadero{
    private ArrayList<Celda> celdasCarro = new ArrayList<>();
    private ArrayList<Celda> celdasMoto = new ArrayList<>();
    static final int HORACARRO=1000;
    static final int HORAMOTO=500;
    static final int DIACARRO=8000;
    static final int DIAMOTO=4000;
    static final int MOTOALTOCILINDRAJE=2000;
    
    public ArrayList<Celda> getCeldasCarro() {
        return celdasCarro;
    }

    public void setCeldasCarro(ArrayList<Celda> celdasCarro) {
        this.celdasCarro = celdasCarro;
    }

    public ArrayList<Celda> getCeldasMoto() {
        return celdasMoto;
    }

    public void setCeldasMoto(ArrayList<Celda> celdasMoto) {
        this.celdasMoto = celdasMoto;
    }
    
    public Fecha getFechaActual() {
    	Calendar Cal = Calendar.getInstance();
    	int year=Cal.get(Calendar.YEAR);
    	int mes=Cal.get(Calendar.MONTH);
    	int diaMes=Cal.get(Calendar.DAY_OF_MONTH);
    	int horaDia=Cal.get(Calendar.HOUR_OF_DAY);
    	int minuto=Cal.get(Calendar.MINUTE);
    	return new Fecha(year,mes,diaMes,horaDia,minuto);
    }

    public boolean ingresarCarro(String placa, int cc){
    	placa=placa.toUpperCase();
        if(celdasCarro.size()<=20){
            if(picoYPlaca(placa))return false;
            Fecha f = getFechaActual();
            Carro c = new Carro(placa,cc);
            Celda celda = new Celda(c,f);
            celdasCarro.add(celda);
            //System.out.print("Ingreso un carro:");
            return true;
        }else{
            //System.out.print("Parqueadero lleno");
        }
        return false;
    }
    
    public boolean ingresarMoto(String placa, int cc){
    	placa=placa.toUpperCase();
        if(celdasMoto.size()<=10){
            if(picoYPlaca(placa))return false;
            Fecha f = getFechaActual();
            Moto c = new Moto(placa,cc);
            Celda celda = new Celda(c,f);
            celdasMoto.add(celda);
            //System.out.print("Ingreso un carro:");
            return true;
        }else{
            //System.out.print("Parqueadero lleno");
        }
        return false;
    }

    public boolean sacarCarro(String placa){
        for(int i=0;i<celdasCarro.size();i++){
            if(celdasCarro.get(i).getVehiculo().getPlaca().equals(placa)){
                //generarCobro(celdasCarro.get(i).getFecha().getHora(),Calendar.HOUR_OF_DAY);
            	celdasCarro.remove(i);
            	return true;
            }
        }
        return false;
    }
    
    public boolean sacarMoto(String placa){
        for(int i=0;i<celdasMoto.size();i++){
            if(celdasMoto.get(i).getVehiculo().getPlaca().equals(placa)){
                //generarCobro(celdasCarro.get(i).getFecha().getHora(),Calendar.HOUR_OF_DAY);
            	celdasMoto.remove(i);
            	return true;
            }
        }
        return false;
    }

  //Metodo que calcula el total a pagar de los carros
    public int generarCobroCarros(Fecha fechaEntrada, Fecha fechaSalida){
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
    public int generarCobroMotos(Fecha fechaEntrada, Fecha fechaSalida){
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
    public long calcularHorasTotales(Fecha entrada, Fecha salida) {
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