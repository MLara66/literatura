package com.alura.literatura.principal;

import com.alura.literatura.model.*;
import com.alura.literatura.repository.AutorRepository;
import com.alura.literatura.repository.LibroRepository;
import com.alura.literatura.service.ConsumoAPI;
import com.alura.literatura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
  private static final String URL_BASE =  "https://gutendex.com/books/";
  private final ConsumoAPI consumoAPI = new ConsumoAPI();
  private ConvierteDatos conversor = new ConvierteDatos();
  private Scanner teclado = new Scanner(System.in);
  @Autowired
  private LibroRepository libroRepositorio;
  @Autowired
  private AutorRepository autorRepositorio;

  private List<Libro> libros;
  public Principal(LibroRepository repository,  AutorRepository autorRepositorio) {
    this.libroRepositorio = repository;
    this.autorRepositorio = autorRepositorio;
  }

  public void muestraElMenu() {
    var opcion = -1;
    while (opcion != 0) {
      System.out.println("--------------------------------------\n");
      System.out.println("Elija la opción a través de su número:");
      var menu = """
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    """;
      System.out.println(menu);
      opcion = teclado.nextInt();
      teclado.nextLine();

      switch (opcion) {
        case 1:
          buscarLibroPorTitulo();
          break;
        case 2:
          mostrarLibrosBuscados();
          break;
        case 3:
          listarAutoresRegistrados();
          break;
        case 4:
          listarAutoresVivosEnAnio();
          break;
        case 5:
          buscarLibrosPorIdioma();
          break;
        case 0:
          System.out.println("Cerrando la aplicación...");
          break;
        default:
          System.out.println("Opción inválida");
      }
    }


  }


  @Transactional
  private void buscarLibroPorTitulo() {
    List<DatosLibros> libros = getDatosLibro();
    libros.forEach(datos -> {

      List<DatosAutor> datosAutores = datos.autor();
      DatosAutor datosAutor = datosAutores.isEmpty() ? null : datosAutores.get(0);

      Autor autor = null;
      if (datosAutor != null) {

        List<Autor> autoresExistentes = autorRepositorio.findByNombre(datosAutor.nombre());
        if (autoresExistentes.isEmpty()) {
          autor = new Autor();
          autor.setNombre(datosAutor.nombre());
          autor.setFechaDeNacimiento(datosAutor.fechaDeNacimiento());
          autor.setFechaDeFallecimiento(datosAutor.fechaDeFallecimiento());
          autorRepositorio.save(autor);
        } else {
          autor = autoresExistentes.get(0);
        }
      }


      Libro libro = new Libro(datos.titulo(), autor, String.join(", ", datos.idiomas()), datos.numeroDeDescargas());
      if (autor != null) {
        autor.addLibro(libro);
      }
      try {
        libroRepositorio.save(libro);
      System.out.println("Libro: " + libro.toString());
      } catch (DataIntegrityViolationException e) {
          System.out.println("El libro ya existe: " + libro.getTitulo());
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }
  private List<DatosLibros> getDatosLibro() {
    System.out.println("Escribe el nombre del libro que deseas buscar");
    var nombreLibro = teclado.nextLine();
    var json = consumoAPI.obtenerDatos(URL_BASE+"?search=" + nombreLibro.replace(" ", "+"));
    var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
    List<DatosLibros> librosBuscados = datosBusqueda.resultados().stream()
        .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
        .collect(Collectors.toList());
    if(!librosBuscados.isEmpty()){
      System.out.println("Libro Encontrado ");
      librosBuscados.forEach(libro -> System.out.println(libro));
    }else {
      System.out.println("Libro no encontrado");
    }
    return librosBuscados;
  }

  private void mostrarLibrosBuscados() {
    libros = libroRepositorio.findAll();
    libros.forEach(System.out::println);

  }

  public void listarAutoresRegistrados() {
    List<Autor> autores = autorRepositorio.findAll();
    if (autores.isEmpty()) {
      System.out.println("No se encontraron autores registrados.");
    } else {
      System.out.println("Autores registrados:");
      autores.forEach(autor -> {
        System.out.println("Nombre: " + autor.getNombre());
        System.out.println("Fecha de nacimiento: " + autor.getFechaDeNacimiento());
        System.out.println("Libros: ");
        autor.getLibros().forEach(libro -> {
          System.out.println("\tTitulo: " + libro.getTitulo());
        });
        System.out.println("----------------");
      });
    }
  }

  public void buscarLibrosPorIdioma(){
    System.out.println("Ingrese el idioma para buscar los libros:");
    System.out.println("es - Español");
    System.out.println("en - Inglés");
    System.out.println("fr - Francés");
    System.out.println("pt - Portugués");
    var idioma = teclado.nextLine();

    listarLibrosPorIdioma(idioma);
  }

  private void listarAutoresVivosEnAnio() {
    System.out.println("Ingrese el año vivo de autor(es) que desea buscar:");
    var anioStr = teclado.nextLine();
    try {
      int anio = Integer.parseInt(anioStr);
        List<Autor> autores = autorRepositorio.findAutoresVivosEnAnio(anio);
        if (autores.isEmpty()) {
          System.out.println("No se encontraron autores vivos en el año " + anio);
        } else {
          System.out.println("--------------------------");
          System.out.println("Autores vivos en el año " + anio + ":");
          autores.forEach(autor -> {
            System.out.println("Nombre: " + autor.getNombre());
            System.out.println("Fecha de Nacimiento: " + autor.getFechaDeNacimiento());
            System.out.println("Fecha de Fallecimiento: " + autor.getFechaDeFallecimiento());
            System.out.println("--------------------------");
          });
        }
    } catch (NumberFormatException e) {
      System.out.println("Error: Ingrese un año válido en formato numérico.");
    }
  }
  @Transactional(readOnly = true)
  public void listarLibrosPorIdioma(String idioma) {
    List<Libro> libros = libroRepositorio.findLibrosByIdioma(idioma);
    if (libros.isEmpty()) {
      System.out.println("No se encontraron libros para el idioma: " + idioma);
    } else {
      if ("es".equalsIgnoreCase(idioma)) {
        System.out.println("Libros en español:");
      } else if ("en".equalsIgnoreCase(idioma)) {
        System.out.println("Libros en inglés:");
      } else if ("fr".equalsIgnoreCase(idioma)) {
        System.out.println("Libros en francés:");
      } else if ("pt".equalsIgnoreCase(idioma)) {
        System.out.println("Libros en portugués:");
      } else {
        System.out.println("Idioma no soportado.");
        return;
      }

      System.out.println("----------------");
      libros.forEach(libro -> {
        System.out.println("Titulo: " + libro.getTitulo());
        System.out.println("Autor: " + libro.getAutor().getNombre());
        System.out.println("Idioma: " + libro.getIdiomas());
        System.out.println("Numero de descargas: " + libro.getNumeroDeDescargas());
        System.out.println("----------------");
      });
    }

  }



}
