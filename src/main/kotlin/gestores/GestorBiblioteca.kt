package gestores

import ConexionBD.bd
import ConexionBD.cluster
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

class GestorBiblioteca(private val db: GestorBD) {
    fun menu(){
        var onda = true
        while (onda) {
            try {
                println("Menu de BD ${bd.name}")
                println(" 1º Obtener todos los juegos del mismo genero ordenado por el titulo. \n 2º Insertar juego.\n 3º Eliminar todos los juegos del mismo genero\n 4º Modificar los datos de un juego\n 5º Salir")
                val opcion = readln().toInt()

                when (opcion) {
                    1 -> opcion1()
                    2 -> opcion2()
                    3 -> opcion3()
                    4 -> opcion4()
                    5 -> onda = false
                }


            }catch (e:Exception){
                println(e.toString())
            }
        }
        cluster.close()
    }

    private fun opcion1(){
        println("Dame el genero: ")
        val genero = readln()
        db.findPorGenero(genero)
    }

    private fun opcion2(){
        println("Dame el titulo (obligatorio): ")
        val titulo = readln()
        println("El genero:")
        val genero = readln()
        println("El precio:")
        val precio = readln().toDoubleOrNull() ?: 0.0
        println("La fecha de lanzamiento(día, mes, año): ")
        val fecha = readln().split(",")
        val fechaFormateada = formatearFecha(fecha)

        db.insertarJuegos(titulo,genero,precio,fechaFormateada)

    }

    private fun opcion3(){
        println("Dame el genero: ")
        val genero = readln()
        db.borrarTodosJuegoGenero(genero)
    }

    private fun opcion4(){
        println("Dame el titulo del juego que quieras modificar: ")
        val titulo = readln()
        if (db.findByTitulo(titulo) == null) {
            println("Titulo no encontrado")

        }
        else {
            println("Que quieres modificar:\n 1º Titulo.\n 2º Genero.\n 3º Precio.\n 4º Fecha de lanzamiento.\n 5º Salir.")
            val opcion = readln().toInt()
            when (opcion) {
                1 -> {
                    println("Dame el titulo actualizado: ")
                    val titulo2 = readln()
                    db.actualizaTituloJuego(titulo, titulo2)
                }

                2 -> {
                    println("Dame el genero actualizado: ")
                    val genero = readln()
                    db.actualizaGeneroJuego(titulo, genero)
                }

                3 -> {
                    println("Dame el precio actualizado: ")
                    val precio = readln()
                    db.actualizaPrecioJuego(titulo, precio)
                }

                4 -> {
                    println("Dame la fecha de lanzamiento actualizado(día,mes,año): ")
                    val fecha = readln().split(",")
                    val fechaFormateada = formatearFecha(fecha)
                    db.actualizaFechaJuego(titulo, fechaFormateada)
                }

                5 -> {
                    println("Saliendo...")
                }
            }
        }
    }



    private fun formatearFecha(lista: List<String>): Date {

        if (lista.size != 3 || lista.isEmpty()) {
            println("Fecha incorrecta, debe contener exactamente 3 entidades (día, mes, año).\n Insertando fecha actual.")
            return Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
        }

        return try {
            val dia = lista[0].toInt()
            val mes = lista[1].toInt()
            val anio = lista[2].toInt()

            val formato = SimpleDateFormat("dd-MM-yyyy")
            val fechaString = "$dia-$mes-$anio"
            formato.parse(fechaString)
        } catch (e: NumberFormatException) {
            println("String detectado. **Error**\n Fecha actual insertada.")
            return Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
        }
    }

}