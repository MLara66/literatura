# Literatura

## 📜  Catalogo de Literatura - Segundo Desafio

Api elaborado con el objetivo de afianzar conocimientos adquiridos durante el curso de Spring Data JPA

### Prerequisitos

Antes de consumir esta Api, debes de tener instalado los siguientes pasos y programas:

- Clonar este repositorio con GitHub.
- Disponer en tu PC de tu entorno de desarrollo integrado como: IntelliJ IDEA
- Disponer de PgAdmin4 en tu PC debido a que es necesario:
  Crear la bade de datos: alura_literatura

## Instalación 🔧

- Abrir proyecto desde IntelliJ IDEA

## Ejecución 🛠️
Para ejecutar la Api desde local, puede seguir los siguientes pasos:  

``` 
Paso 1
Ir a la clase Principal + boton derecho Run "LiteraturaApplication... main()"

Paso 2
Una vez en ejecucion, tendra la oportunidad de elegir a traves de un menu
como el siguiente:

********************************************** 
 
Elija la opción a través de su número:
1 - Buscar libro por titulo
2 - Listar libros registrados
3 - Listar autores registrados
4 - Listar autores vivos en un determinado año
5 - Listar libros por idioma
0 - Salir

**********************************************
Paso 3 
Seleccione colocando en numero la opcion que desea realizar

Paso 4
Segun lo seleccionado, se le solicitara algun valor, titulo de libro, año
 o idioma

Paso 5
En unos segundos, dispondra de la informacion solicitada.

```
## Ejemplo

```
Elija la opción a través de su número:
1 - Buscar libro por titulo
2 - Listar libros registrados
3 - Listar autores registrados
4 - Listar autores vivos en un determinado año
5 - Listar libros por idioma
0 - Salir

5
Ingrese el idioma para buscar los libros:
es - Español
en - Inglés
fr - Francés
pt - Portugués
es
Hibernate: select l1_0.id,l1_0.autor_id,l1_0.idiomas,l1_0.numero_de_descargas,l1_0.titulo from libros l1_0 where l1_0.idiomas like ? escape ''
Hibernate: select a1_0.id,a1_0.fecha_de_fallecimiento,a1_0.fecha_de_nacimiento,a1_0.nombre,l1_0.autor_id,l1_0.id,l1_0.idiomas,l1_0.numero_de_descargas,l1_0.titulo from autores a1_0 left join libros l1_0 on a1_0.id=l1_0.autor_id where a1_0.id=?
Libros en español:
----------------
Titulo: Don Quijote
Autor: Cervantes Saavedra, Miguel de
Idioma: es
Numero de descargas: 14926.0
----------------
--------------------------------------
```

## Construido con 🛠️

* [IntelliJ IDEA 2023.2.1 (Community Edition)]
* [pgAdmin4 Version 7.6]

## Autor ✒️

Maria Lara

=======

