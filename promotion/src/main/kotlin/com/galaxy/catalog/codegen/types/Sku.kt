package com.galaxy.catalog.codegen.types

import com.fasterxml.jackson.`annotation`.JsonProperty
import java.time.OffsetDateTime
import kotlin.Int
import kotlin.String
import kotlin.collections.List

public data class Sku(
  @JsonProperty("skuid")
  public val skuid: String,
  @JsonProperty("name")
  public val name: String,
  @JsonProperty("price")
  public val price: Int? = null,
  @JsonProperty("querydate")
  public val querydate: OffsetDateTime? = null,
  @JsonProperty("shortdescription")
  public val shortdescription: String? = null,
  @JsonProperty("longdescription")
  public val longdescription: String? = null,
  @JsonProperty("images")
  public val images: List<Image?>? = null,
  @JsonProperty("attributes")
  public val attributes: List<Attributes?>? = null
) {
  public companion object
}
