
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import traffic_simulation.*

class VehicleTest {

    // Tests for the different capacity Factors
    // __________________________________________________________________________________________

    @Test
    fun capacityFactor_Car_capacityFactorIsCorrect(){
        val testCar : Car = Car (5 , mutableListOf(1,2))

        val result : Int = testCar.capacityFactor
        assertEquals ( 10 , result)
    }

    @Test
    fun capacityFactor_Tram_capacityFactorIsCorrect(){
        val testTram : Tram = Tram (5 , mutableListOf(1,2))

        val result : Int = testTram.capacityFactor
        assertEquals ( 50 , result)
    }

    @Test
    fun capacityFactor_Truck_capacityFactorIsCorrect(){
        val testTruck : Truck = Truck (5 , mutableListOf(1,2))

        val result : Int = testTruck.capacityFactor
        assertEquals ( 30 , result)
    }

    @Test
    fun capacityFactor_Bike_capacityFactorIsCorrect(){
        val testBike : Bike = Bike (5 , mutableListOf(1,2))

        val result : Int = testBike.capacityFactor
        assertEquals ( 1 , result)
    }

    // Tests for the default value of delay in every sub-class of interface Vehicle
    // __________________________________________________________________________________________

    @Test
    fun delay_delayOfNewCar_delayIsZero(){
        val testCar : Car = Car (5 , mutableListOf(1,2))

        val result : Int = testCar.delay
        assertEquals ( 0 , result)
    }

    @Test
    fun delay_delayOfNewTram_delayIsZero(){
        val testTram : Tram = Tram (5 , mutableListOf(1,2))

        val result : Int = testTram.delay
        assertEquals ( 0 , result)
    }

    @Test
    fun delay_delayOfNewTruck_delayIsZero(){
        val testTruck : Truck = Truck (5 , mutableListOf(1,2))

        val result : Int = testTruck.delay
        assertEquals ( 0 , result)
    }

    @Test
    fun delay_delayOfNewBike_delayIsZero(){
        val testBike : Bike = Bike (5 , mutableListOf(1,2))

        val result : Int = testBike.delay
        assertEquals ( 0 , result)
    }

    // Tests if the lists of Vehicles sub-classes are empty at the beginning
    // __________________________________________________________________________________________

    @Test
    fun delay_listsOfNewCar_listsAreEmpty(){
        val testCar : Car = Car (5 , mutableListOf(1,2))

        val list1 : MutableList<Int> = testCar.gotNewDelayInHours
        val list2 : MutableList<Int> = testCar.droveWithoutNewDelayInHours

        val result1 : Boolean = list1.isEmpty()
        assertEquals ( true , result1)
        val result2 : Boolean = list2.isEmpty()
        assertEquals ( true , result2)
    }

    @Test
    fun delay_listsOfNewTram_listsAreEmpty(){
        val testTram : Tram = Tram (5 , mutableListOf(1,2))

        val list1 : MutableList<Int> = testTram.gotNewDelayInHours
        val list2 : MutableList<Int> = testTram.droveWithoutNewDelayInHours

        val result1 : Boolean = list1.isEmpty()
        assertEquals ( true , result1)
        val result2 : Boolean = list2.isEmpty()
        assertEquals ( true , result2)
    }

    @Test
    fun delay_listsOfNewTruck_listsAreEmpty(){
        val testTruck : Truck = Truck (5 , mutableListOf(1,2))

        val list1 : MutableList<Int> = testTruck.gotNewDelayInHours
        val list2 : MutableList<Int> = testTruck.droveWithoutNewDelayInHours

        val result1 : Boolean = list1.isEmpty()
        assertEquals ( true , result1)
        val result2 : Boolean = list2.isEmpty()
        assertEquals ( true , result2)
    }

    @Test
    fun delay_listsOfNewBike_listsAreEmpty(){
        val testBike : Bike = Bike (5 , mutableListOf(1,2))

        val list1 : MutableList<Int> = testBike.gotNewDelayInHours
        val list2 : MutableList<Int> = testBike.droveWithoutNewDelayInHours

        val result1 : Boolean = list1.isEmpty()
        assertEquals ( true , result1)
        val result2 : Boolean = list2.isEmpty()
        assertEquals ( true , result2)
    }

    @Test
    fun getDelayedAtHour_trafficJamInUnorderedSeveralHours_vehicleGetsOftenDelayedAndOutputIsInOrder() {
        val BMW: Vehicle = Vehicle(1, mutableListOf(1, 2, 3, 4, 5, 6))

        BMW.getDelayedAtHour(1)
        BMW.getDelayedAtHour(5)
        BMW.getDelayedAtHour(3)
        BMW.getDelayedAtHour(4)

        val correctList: MutableList <Int> = mutableListOf(1, 3, 4, 5)
        assertEquals(correctList, BMW.gotNewDelayInHours)
    }

    @Test
    fun defaultOfVehicleIsNotDelayed() {
        val BMW: Vehicle = Vehicle(1, mutableListOf(1, 2, 3, 4, 5, 6))

        val correctList: MutableList <Int> = mutableListOf()

        assertEquals(correctList, BMW.gotNewDelayInHours)
    }

    @Test
    fun delayAndDrive_mixedDelaysAndDriveRequirements_driveRequirementsCorrectlyReported() {
        val tesla = Vehicle(1, mutableListOf(1, 5))
        assertTrue(tesla.vehicleWantsToDriveAt(1))

        tesla.getDelayedAtHour(1)
        // couldn't drive at 1, so wants to drive at 2
        assertTrue(tesla.vehicleWantsToDriveAt(2))

        tesla.getDelayedAtHour(2)
        // again, couldn't drive,  so wants to drive at 3
        assertTrue(tesla.vehicleWantsToDriveAt(3))

        tesla.driveAtHour(3)
        // finally time to drive; this resolves delay from hour 1, so no more driving
        assertFalse(tesla.vehicleWantsToDriveAt(4))

        // wants to drive at hour 5 as per original requirements
        assertTrue(tesla.vehicleWantsToDriveAt(5))
    }

    @Test
    fun delayAndDrive_mixedDelaysAndDriveRequirements_driveRequirementsCarriedAcross() {
        val tesla = Vehicle(1, mutableListOf(1, 2, 4))
        assertTrue(tesla.vehicleWantsToDriveAt(1))

        tesla.getDelayedAtHour(1)
        // couldn't drive at 1 _and_ wanted to drive at 2 anyways
        assertTrue(tesla.vehicleWantsToDriveAt(2))

        tesla.getDelayedAtHour(2)
        // couldn't drive at 1 & 2, so definitely wants to drive at 3
        assertTrue(tesla.vehicleWantsToDriveAt(3))

        tesla.driveAtHour(3)
        // still one hour delayed _and_ wants to drive at 4 anyways
        assertTrue(tesla.vehicleWantsToDriveAt(4))

        tesla.driveAtHour(4)
        // driving at 4 accomplished but still one hour delayed, so wants to drive again
        assertTrue(tesla.vehicleWantsToDriveAt(5))

        tesla.driveAtHour(5)
        // finally all done
        assertFalse(tesla.vehicleWantsToDriveAt(6))
    }
}
