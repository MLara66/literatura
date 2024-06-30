package com.alura.literatura.repository;

import com.alura.literatura.model.Autor;
import com.alura.literatura.model.DatosAutor;
import com.alura.literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

  @Query("SELECT l FROM Libro l WHERE l.idiomas LIKE %:idioma%")
  List<Libro> findLibrosByIdioma(String idioma);
}
