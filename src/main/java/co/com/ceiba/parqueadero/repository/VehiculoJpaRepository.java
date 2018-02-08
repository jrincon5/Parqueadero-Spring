package co.com.ceiba.parqueadero.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.ceiba.parqueadero.entity.VehiculoEntity;

@Repository("vehiculoJpaRepository")
public interface VehiculoJpaRepository extends JpaRepository<VehiculoEntity, Serializable>{
	
	//public abstract int findByPlaca
	
	@Query("SELECT COUNT (t) FROM VehiculoEntity t WHERE t.tipoVehiculo = :tipoVehiculo AND t.parqueado = :parqueado")
	public abstract int countByCarros(@Param("tipoVehiculo") String tipoVehiculo,
			@Param("parqueado") boolean parqueado);
	
	@Query("SELECT COUNT (t) FROM VehiculoEntity t WHERE t.tipoVehiculo = :tipoVehiculo")
	public abstract int countByMotos(@Param("tipoVehiculo") String tipoVehiculo);
	
}
