import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import org.bson.Document
import java.util.Date

class GestorBD {
    private val db =ConexionBD.bd
    private val coll: MongoCollection<Juego> = db.getCollection("juegos",Juego::class.java)

    fun findPorGenero(genero: String): List<Juego> {
        val juegos = coll.find(Filters.eq("juego", genero)).toList()
        return ordenarPorTitulo(juegos)
    }

    private fun ordenarPorTitulo(juegos: List<Juego>): List<Juego>{
        return juegos.sortedBy { it.titulo }
    }

    fun insertarJuegos(juego: Juego) {
        if (juego.titulo.isEmpty()) {
            println("No se puede insertar un juego sin titulo")
            return
        }
        val juegoExistente = coll.find(Filters.eq("titulo", juego.titulo))
        if (juegoExistente.count() > 0) {
            coll.insertOne(juego)
        }
    }

    fun actualizaTituloJuego(juego: Juego, titulo: String?) {
        if (titulo.isNullOrEmpty()) {
            println("No se puede actualizar un juego para quitarle el titulo")
        }

        val actualizacion = Document("\$set",Document("titulo", titulo))
        val filtro = Filters.eq("titulo", juego.titulo)

        coll.updateOne(filtro,actualizacion)
    }

    fun actualizaGeneroJuego(juego: Juego, genero: String) {
        val actualizacion = Document("\$set",Document("genero", genero))
        val filtro = Filters.eq("titulo", juego.titulo)

        coll.updateOne(filtro,actualizacion)
    }

    fun actualizaPrecioJuego(juego: Juego, precio: String) {
        val actualizacion = Document("\$set",Document("precio", precio))
        val filtro = Filters.eq("titulo", juego.titulo)

        coll.updateOne(filtro,actualizacion)
    }

    fun actualizaFechaJuego(juego: Juego, fecha: Date) {
        val actualizacion = Document("\$set",Document("fecha_lanzamiento", fecha))
        val filtro = Filters.eq("titulo", juego.titulo)

        coll.updateOne(filtro,actualizacion)
    }

    fun borrarJuego(juego: Juego) {
        coll.deleteOne(Filters.eq("titulo", juego.titulo))
    }

    fun borrarTodosJuegoGenero(genero: String) {
        findPorGenero(genero).forEach {
            coll.deleteOne(Filters.eq("titulo", it.titulo))
        }
    }
}