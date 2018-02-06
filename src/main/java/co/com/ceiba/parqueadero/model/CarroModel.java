package co.com.ceiba.parqueadero.model;

public class CarroModel extends VehiculoModel {
	
	private static String TIPOVEHICULO="Carro"; 

	public CarroModel() {
		super();
	}

	public CarroModel(String placa, boolean parqueado) {
		super(placa, parqueado);
	}

	@Override
	public String getTipoVehiculo() {
		return TIPOVEHICULO;
	}
}