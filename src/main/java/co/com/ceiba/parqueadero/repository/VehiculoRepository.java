package co.com.ceiba.parqueadero.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.ceiba.parqueadero.entity.VehiculoEntity;
import co.com.ceiba.parqueadero.model.VehiculoModel;

@Repository("vehiculoRepository")
public interface VehiculoRepository extends JpaRepository<VehiculoEntity, Serializable>{
	
	@Query("SELECT COUNT (t) FROM VehiculoEntity t WHERE t.tipoVehiculo = :tipoVehiculo AND t.parqueado = :parqueado")
	public abstract int countByVehiculos(@Param("tipoVehiculo") String tipoVehiculo,
			@Param("parqueado") boolean parqueado);
	
	@Query("SELECT t.placa FROM VehiculoEntity t WHERE t.placa = :placa AND t.parqueado = :parqueado")
	public abstract boolean findActiveCarro(@Param("placa") String placa, 
			@Param("parqueado") boolean parqueado);
}
