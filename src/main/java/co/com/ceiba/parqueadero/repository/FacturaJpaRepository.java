package co.com.ceiba.parqueadero.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.ceiba.parqueadero.entity.FacturaEnt;

@Repository("facturaJpaRepository")
public interface FacturaJpaRepository extends JpaRepository<FacturaEnt, Serializable>{

}
