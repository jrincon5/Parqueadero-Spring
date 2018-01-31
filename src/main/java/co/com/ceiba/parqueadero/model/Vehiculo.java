package co.com.ceiba.parqueadero.model;

public class Vehiculo {
	
	private String placa;
    private int cc;
    
    public Vehiculo(String placa, int cc) {
        this.placa = placa;
        this.cc = cc;
    }
    
    public Vehiculo() {}

	public String getPlaca() {
	    return placa;
	}
	
	public void setPlaca(String placa) {
	    this.placa = placa;
	}
	
	public int getCc() {
	    return cc;
	}
	
	public void setCc(int cc) {
	    this.cc = cc;
	}

}
