
package com.galaxy.mdm.services

import org.springframework.stereotype.Service
import com.galaxy.foundation.logger.EventLogger
import com.galaxy.mdm.codegen.types.Location
import com.galaxy.mdm.codegen.types.LocationAddress
import com.galaxy.mdm.codegen.types.OperationHours
import com.galaxy.mdm.util.MdmEvents

@Service
class MdmService (val eventLogger: EventLogger){

         var address = listOf(
             LocationAddress("Universal studio","hollywood","LA street","Los Angeles","CA","US","12345","9876543210")
          )
         var operationHrs = listOf<OperationHours>(
             OperationHours("SUN", "9:00 AM", "19:00 PM",null),
             OperationHours("MON", "9:00 AM", "19:00 PM","Close early"),
             OperationHours("TUE", "9:00 AM", "19:00 PM","Close early"),
             OperationHours("WED", "9:00 AM", "19:00 PM","Close early"),
             OperationHours("THU", "9:00 AM", "19:00 PM","Close early"),
             OperationHours("FRI", "9:00 AM", "19:00 PM","Close early"),
             OperationHours("SAT", "9:00 AM", "19:00 PM","Close early")
         )

        var locations =  mutableListOf(
            Location("STR1","Store 1","STR1", address.get(0),operationHrs)
        )

    fun locations(locationids: List<String?>): List<Location> {

        eventLogger.log(MdmEvents.LOCATION_FIND,"finding skus ",locationids);
        return locations
    }

    fun saveLocation(location: Location) {
        eventLogger.log(MdmEvents.LOCATION_SAVE,"saving skus ",location);
        locations.add(location)
    }
}