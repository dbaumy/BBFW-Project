package traffic_simulation

// Imports for parsing library
import com.univocity.parsers.common.record.Record
import com.univocity.parsers.csv.CsvParser
import com.univocity.parsers.csv.CsvParserSettings
import java.io.*

fun main(args: Array<String>) {

    // The information of vehicles and their interest to drive is given in a csv-file
    // Therefore we use a library to parse

    // Setup of the parsing like symbol of separation etc.
    // in this case mostly the default settings so just a few things have to be set
    val settings = CsvParserSettings()
    settings.format.setLineSeparator("\n")
    settings.isHeaderExtractionEnabled = true
    // this is to make the parser ignoring the first line in the csv-file

    // creating a parser with the former made settings
    val csvParser = CsvParser(settings)

    // reading of the csv-file
    val reader = FileAccess().getReader("/driveInterest.csv")

    // analyze (parse) of the readed csv
    val allRows: MutableList<Record> = csvParser.parseAllRecords(reader)

    // insert the parsed information of csv-file in usable lists
    for (record in allRows) {

        val id_String : String = record.values.get(0)
        val wannaDrive_String : String = record.values.get(1)

        val id_Int : Int = id_String.toInt()
        val wannaDrive_Boolean : Boolean = wannaDrive_String.toBoolean()

        // This function is not yet implement in the class Vehicle
        // addInputToVehicles( id = id_Int , wannaDrive = wannaDrive_Boolean )

    }


    scenario(testList, testRoad)
}

fun scenario(vehicleList: List<Vehicle>, road: RoadNetwork){
    road.calculateTraffic(vehicleList)
}


val testRoad: RoadNetwork = RoadNetwork(8.0)

// Creation of sufficient cars for local testing without using CSV input for now
val BMW     : Vehicle = Vehicle(1.0)
val AUDI    : Vehicle = Vehicle(1.0)
val VOLVO   : Vehicle = Vehicle(1.0)
val VW      : Vehicle = Vehicle(1.0)
val TESLA   : Vehicle = Vehicle(1.0)
val SUZUKI  : Vehicle = Vehicle(1.0)
val HONDA   : Vehicle = Vehicle(1.0)
val FERRARI : Vehicle = Vehicle(1.0)
val PORSCHE : Vehicle = Vehicle(1.0)
val DAIHATSU: Vehicle = Vehicle(1.0)
val FORD    : Vehicle = Vehicle(1.0)
val OPEL    : Vehicle = Vehicle(1.0)

val testList : List<Vehicle> = listOf(BMW, AUDI, VOLVO, VW, TESLA, SUZUKI, HONDA, FERRARI, PORSCHE, DAIHATSU, FORD, OPEL)


// This class is needet for parsing
class FileAccess {

        fun getReader(path: String):Reader {
                return InputStreamReader(this.javaClass.getResourceAsStream(path), "UTF-8")
            }

    /*    fun getWriter(path: String): Writer {
        val file = File(path)
        return FileWriter(file)
    }*/
    }