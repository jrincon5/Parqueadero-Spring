package co.com.ceiba.parqueadero.model;

public class Carro extends Vehiculo {
	
	private static final String TIPOVEHICULO="Carro"; 

	public Carro(String placa) {
		super(placa);
	}
	
	public Carro() {
	}

	@Override
	public String getTipoVehiculo() {
		return TIPOVEHICULO;
	}
	
}