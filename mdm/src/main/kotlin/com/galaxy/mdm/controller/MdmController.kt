package com.galaxy.mdm.controller

import com.galaxy.foundation.context.CustomContext
import com.galaxy.mdm.codegen.types.Location
import com.galaxy.mdm.codegen.types.LocationAddress
import com.galaxy.mdm.codegen.types.SaveLocation
import com.galaxy.mdm.services.MdmService
import com.netflix.graphql.dgs.*
import com.netflix.graphql.dgs.context.DgsContext
import graphql.schema.DataFetchingEnvironment
import org.springframework.security.access.prepost.PreAuthorize


@DgsComponent
class MdmController(private val mdmService: MdmService ) {
    /**
     * This datafetcher resolves the shows field on Query.
     * It uses an @InputArgument to get the titleFilter from the Query if one is defined.
     */
    @DgsQuery
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
    fun locations(@InputArgument locationids: List<String>): List<Location> {

        return mdmService.locations(locationids)
    }

    @DgsData(parentType = "Query", field = "getStoreId")
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
    fun getStoreId(dfe: DataFetchingEnvironment?): String? {
        val customContext: CustomContext = DgsContext.getCustomContext(dfe!!)
        return customContext.location
    }

    @DgsMutation
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED','ROLE_GUEST')")
    fun savelocations(location: SaveLocation, dfe: DataFetchingEnvironment?): Location {


        val savedlocation = Location(
            location.locationid, location.name, location.locationcode,
            LocationAddress(
                location.address1,
                location.address2,
                location.address3,
                location.city,
                location.state,
                location.country,
                location.zipcode,
                location.phonenumber
            )
        )
        mdmService.saveLocation(savedlocation)
        return savedlocation
    }
}