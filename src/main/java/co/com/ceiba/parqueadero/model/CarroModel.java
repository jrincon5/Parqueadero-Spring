package co.com.ceiba.parqueadero.model;

public class CarroModel extends VehiculoModel {
	
	private static final String TIPOVEHICULO="Carro"; 

	public CarroModel(String placa, boolean parqueado) {
		super(placa, parqueado);
	}
	
	public CarroModel() {
	}

	@Override
	public String getTipoVehiculo() {
		return TIPOVEHICULO;
	}
}