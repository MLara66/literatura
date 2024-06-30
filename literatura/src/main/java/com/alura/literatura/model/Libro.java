package com.alura.literatura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;
  @Column(unique = true)
  private String titulo;

  @ManyToOne
  @JoinColumn(name = "autor_id")
  private Autor autor;
  private String idiomas;
  private Double numeroDeDescargas;

  public Libro(){

  }
  public Libro(String titulo, Autor autor, String idiomas, Double numeroDeDescargas) {
    this.titulo = titulo;
    this.numeroDeDescargas = numeroDeDescargas;
    this.autor = autor;
    this.idiomas = idiomas; //String.join(", ", datos.idiomas());
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public Autor getAutor() {
    return autor;
  }

  public void setAutor(Autor autor) {
    this.autor = autor;
  }

  public String getIdiomas() {
    return idiomas;
  }

  public void setIdiomas(String idiomas) {
    this.idiomas = idiomas;
  }

  public Double getNumeroDeDescargas() {
    return numeroDeDescargas;
  }

  public void setNumeroDeDescargas(Double numeroDeDescargas) {
    this.numeroDeDescargas = numeroDeDescargas;
  }

    @Override
  public String toString() {
    return "---- Libro ----\n" +
        "Titulo: " + titulo + "\n" +
        "Autor: " + (autor != null ? autor.getNombre() : "") + "\n" +
        "Idioma: " + idiomas + "\n" +
        "Numero de descargas: " + numeroDeDescargas + "\n" +
        "----------------";
  }

}
