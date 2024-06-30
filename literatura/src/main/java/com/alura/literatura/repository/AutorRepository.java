package com.alura.literatura.repository;

import com.alura.literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
  List<Autor> findByNombre(String nombre);

  @Query(value = "SELECT * FROM autores a WHERE " +
      "(CAST(SUBSTR(a.fecha_de_nacimiento, 1, 4) AS INTEGER) <= :anio) " +
      "AND (CAST(SUBSTR(a.fecha_de_fallecimiento, 1, 4) AS INTEGER) >= :anio)",
      nativeQuery = true)
  List<Autor> findAutoresVivosEnAnio(int anio);
}