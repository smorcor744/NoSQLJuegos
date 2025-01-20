import com.mongodb.client.MongoClients
import io.github.cdimascio.dotenv.dotenv

object ConexionBD{
    // 1º Realizar la conexión con MongoDB (Cluster)
    // Declaro un objeto para usar las clases de dotenv
    private val dotenv = dotenv()
    // guardo en una variable la url de conexión
    private val urlConnectionMongo: String = dotenv["URL_MONGODB"]
    // podemos realizar la conexion por el CRUD
    val cluster = MongoClients.create(urlConnectionMongo)

    // 2º Nos conectamos a las base de datos
    val bd = cluster.getDatabase("Sergio")

}
