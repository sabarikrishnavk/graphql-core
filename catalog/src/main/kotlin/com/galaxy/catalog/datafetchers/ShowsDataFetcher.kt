/*
 * Copyright 2021 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.galaxy.catalog.datafetchers

import com.galaxy.catalog.services.SkuService
import com.galaxy.foundation.jwt.UserType
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize

@DgsComponent
class ShowsDataFetcher(private val showsService: SkuService) {
    /**
     * This datafetcher resolves the shows field on Query.
     * It uses an @InputArgument to get the titleFilter from the Query if one is defined.
     */
    @DgsQuery
//    @Secured("REGISTERED")
//    @PreAuthorize("hasAnyRole('ROLE_GUEST')") //Will throw exception
    @PreAuthorize("hasAnyRole('ROLE_REGISTERED')")
    fun skus(@InputArgument skuid : String?): List<com.galaxy.catalog.codegen.types.Sku> {
        return if(skuid != null) {
            showsService.skus().filter { it.name.contains(skuid) }
        } else {
            showsService.skus()
        }
    }
}