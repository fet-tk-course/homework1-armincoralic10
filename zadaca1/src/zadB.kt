
interface Person{
    fun getIdentity(): String;
    fun getTitle(): String;
}
abstract class Engineer(
    val Name: String,
    val LastName: String,
    var internalTitle: String,
    var experience: Int,
    var expertises: List<String>
):Person{
    init{
        require(experience > 0){
            "Godine iskustva moraju biti vece od 0!"
        }
        require(expertises.isNotEmpty()){
            "Lista ekspertiza ne smije biti prazna!"
        }
    }

    override fun getIdentity(): String{
        return "$Name:$LastName";
    }
    override fun getTitle(): String{
        return internalTitle;
    }

    abstract fun specificData(): String

    fun print(){
        val identity = getIdentity()
        val title = getTitle()
        val experience = experience
        val specificData = specificData()

        println("$identity - $title - $experience - $specificData")
        println("Ekspertiza: $expertises")
    }
}

class SoftwareEngineer(
    Name: String,
    LastName: String,
    Title: String,
    Experience: Int,
    Expertises: List<String>,
    var NumberOfProjects: Int
):Engineer(
    Name,
    LastName,
    Title,
    Experience,
    Expertises
){
    override fun specificData(): String {
        return "Broj projekata: $NumberOfProjects"
    }
}

class ElectricarEngineer(
    Name: String,
    LastName: String,
    Title: String,
    Experience: Int,
    Expertises: List<String>,
    var NumberOfCeritifcates: Int
):Engineer(
    Name,
    LastName,
    Title,
    Experience,
    Expertises
){
    override fun specificData(): String {
        return "Ceritifcates: $NumberOfCeritifcates"
    }
}


fun main(){
    val s1 = SoftwareEngineer("Adnan", "Hasic", "Softverski inženjer", 5, listOf("Kotlin", "Android", "Firebase"), 12)
    val s2 = SoftwareEngineer("Dzenan", "Cerkezovic", "Senior Backend inženjer", 8, listOf("Java", "Spring Boot", "PostgreSQL", "AWS"), 35)

    val e1 = ElectricarEngineer("Alen", "Mahmutovic", "Inženjer elektrotehnike", 10, listOf("PLC programiranje", "Automatika", "SCADA"), 7)
    val e2 = ElectricarEngineer("Ibrahim", "Selimovic", "Inženjer energetike", 3, listOf("Obnovljivi izvori", "AutoCAD"), 2)

    val Engineers = listOf(s1, s2, e1, e2)

    //FOLD #############################################################################
    var CurrentMap = mutableMapOf<String, MutableList<Engineer>>()

    val sortedExpertise = Engineers.fold(CurrentMap){CurrentMap, Engineer->
        if(Engineer.experience > 5){
            for(expertise in Engineer.expertises){

                // LOGIČKA ISPRAVKA 1: Normalizacija jezika (na mala slova)
                val normalizedExpertise = expertise.lowercase().trim()

                val ExpertiseList = CurrentMap.getOrPut(normalizedExpertise){
                    mutableListOf()
                }
                ExpertiseList.add(Engineer)
            }
        }
        CurrentMap
    }
    println("Rezultat grupisanja sa fold():")
    sortedExpertise.forEach { (Expertise, Engineers) ->
        println("\n--- Ekspertiza: $Expertise ---")

        Engineers.forEach { Engineer ->
            println("-> ${Engineer.getIdentity()} (${Engineer.experience} god.)")
        }
    }


    //REDUCE ##########################################################################
    val softwareEng: List<SoftwareEngineer> = Engineers.filterIsInstance<SoftwareEngineer>()
    val electricarEng: List<ElectricarEngineer> = Engineers.filterIsInstance<ElectricarEngineer>()

    if(softwareEng.isNotEmpty()){
        val MostExperiencedSoftwareEng = softwareEng.reduce{mostExperienced, currentEng ->
            if(currentEng.experience > mostExperienced.experience){
                currentEng
            }
            else{
                mostExperienced
            }
        }
        println("\nNajiskusniji inzenjer:")
        MostExperiencedSoftwareEng.print()
    }
    else{
        println("Nema softverskih inzenjera u listi")
    }

    if(electricarEng.isNotEmpty()){
        val MostExperiencedElectricalEng = electricarEng.reduce{ mostExperienced, currentEngineer ->
            if(currentEngineer.experience > mostExperienced.experience)
            {
                currentEngineer
            }
            else{
                mostExperienced
            }
        }

        println("\nNajiskusniji elektro inzenjer:")
        MostExperiencedElectricalEng.print()
    }
    else {
        println("Nema elektro inzenjera u listi")
    }

    // AGGREGATE #################################################################
    val groupedByType = Engineers.groupingBy { it.javaClass.simpleName }
    val statsByTypes: Map<String, Int> = groupedByType.aggregate {

            key,
            accumulator: Int?,
            element,
            first ->

        val sum = accumulator ?: 0

        // Sabiranje
        when (element) {
            is SoftwareEngineer -> sum + element.NumberOfProjects
            is ElectricarEngineer -> sum + element.NumberOfCeritifcates
            else -> sum
        }
    }
    println("Međurezultati (statistike po kategorijama):")
    statsByTypes.forEach { (type, sum) ->
        println("   -> Ukupno za $type: $sum")
    }


    // sabiranje rezultata
    val ukupanZbirSvih = statsByTypes.values.sum()

    println("\n-----------------------------------------------------")
    println("Ukupan zbir svih projekata i certifikata: $ukupanZbirSvih")
    println("-----------------------------------------------------")
}