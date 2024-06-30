package com.alura.literatura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

@Entity
@Table(name = "autores")
public class Autor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre;

  private String fechaDeNacimiento;

  private String fechaDeFallecimiento;

  @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
  private List<Libro> libros = new ArrayList<>();
  public Autor() {
  }
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getFechaDeNacimiento() {
    return fechaDeNacimiento;
  }

  public void setFechaDeNacimiento(String fechaDeNacimiento) {
    this.fechaDeNacimiento = fechaDeNacimiento;
  }

  public String getFechaDeFallecimiento() {
    return fechaDeFallecimiento;
  }

  public void setFechaDeFallecimiento(String fechaDeFallecimiento) {
    this.fechaDeFallecimiento = fechaDeFallecimiento;
  }

  public List<Libro> getLibros() {
    return libros;
  }

  public void setLibros(List<Libro> libros) {
    this.libros = libros;
  }

  public void addLibro(Libro libro) {
    libros.add(libro);
    libro.setAutor(this);
  }

  @Override
  public String toString() {
    return "Autor{" +
        "id=" + id +
        ", nombre='" + nombre + '\'' +
        ", fechaDeNacimiento='" + fechaDeNacimiento + '\'' +
        ", fechaDeFallecimiento=" + fechaDeFallecimiento +
        '}';
  }

}
