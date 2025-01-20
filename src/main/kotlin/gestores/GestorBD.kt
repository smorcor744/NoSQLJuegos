package gestores

import dataclass.Juego
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import org.bson.Document
import java.util.Date

class GestorBD(private val coll : MongoCollection<Juego>) {

    fun findPorGenero(genero: String): List<Juego> {
        val juegos = coll.find(Filters.eq("genero", genero)).toList()
         ordenarPorTitulo(juegos).forEach { println(it)}
        return ordenarPorTitulo(juegos)
    }


    private fun ordenarPorTitulo(juegos: List<Juego>): List<Juego>{
        return juegos.sortedBy { it.titulo }
    }

    fun insertarJuegos(titulo: String, genero: String, precio: Double, fecha_lanzamiento: Date) {
        val juego = Juego(titulo, genero, precio, fecha_lanzamiento)
        if (juego.titulo.isEmpty()) {
            println("No se puede insertar un juego sin titulo")
            return
        }
        val juegoExistente = coll.find(Filters.eq("titulo", juego.titulo))
        if (juegoExistente.count() == 0) {
            coll.insertOne(juego)
            println("Juego insertado correctamente!!: \n$juego")
        }
    }

    fun findByTitulo(titulo: String): Juego? {
        return coll.find(Filters.eq("titulo", titulo)).firstOrNull()
    }

    fun actualizaTituloJuego(juegoTitulo: String, titulo: String?) {
        if (titulo.isNullOrEmpty()) {
            println("No se puede actualizar un juego para quitarle el titulo")
        }

        val actualizacion = Document("\$set",Document("titulo", titulo))
        val filtro = Filters.eq("titulo", juegoTitulo)

        coll.updateOne(filtro,actualizacion)
        println("Juego actualizado correctamente!! ${findByTitulo(juegoTitulo)}")

    }

    fun actualizaGeneroJuego(juegoTitulo: String, genero: String) {
        val actualizacion = Document("\$set",Document("genero", genero))
        val filtro = Filters.eq("titulo", juegoTitulo)

        coll.updateOne(filtro,actualizacion)
        println("Juego actualizado correctamente!! ${findByTitulo(juegoTitulo)}")

    }

    fun actualizaPrecioJuego(juegoTitulo: String, precio: String) {
        val actualizacion = Document("\$set",Document("precio", precio))
        val filtro = Filters.eq("titulo", juegoTitulo)

        coll.updateOne(filtro,actualizacion)
        println("Juego actualizado correctamente!! ${findByTitulo(juegoTitulo)}")

    }

    fun actualizaFechaJuego(juegoTitulo: String, fecha: Date) {
        val actualizacion = Document("\$set",Document("fecha_lanzamiento", fecha))
        val filtro = Filters.eq("titulo", juegoTitulo)

        coll.updateOne(filtro,actualizacion)
        println("Juego actualizado correctamente!! ${findByTitulo(juegoTitulo)}")

    }

    fun borrarJuego(juegoTitulo: String) {
        coll.deleteOne(Filters.eq("titulo", juegoTitulo))
    }

    fun borrarTodosJuegoGenero(genero: String) {
        coll.deleteMany(Filters.eq("genero", genero))
        println("Juego borrados correctamente!! Genero: $genero ${findPorGenero(genero)}")

    }

}