package co.com.ceiba.parqueadero.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.ceiba.parqueadero.entity.VehiculoEnt;

@Repository("parqueaderoJpaRepository")
public interface ParqueaderoJpaRepository extends JpaRepository<VehiculoEnt, Serializable>{
	
	public abstract VehiculoEnt findByPlaca(String placa);
}
