import com.mongodb.client.MongoCollection
import dataclass.Juego
import gestores.GestorBD
import gestores.GestorBiblioteca


fun main(){
    val db = ConexionBD.bd
    val coll: MongoCollection<Juego> = db.getCollection("juegos", Juego::class.java)

    val baseDatos = GestorBD(coll)
    val menu = GestorBiblioteca(baseDatos)
    menu.menu()
}