package com.galaxy.catalog.codegen.client

import com.fasterxml.jackson.`annotation`.JsonProperty
import kotlin.String

public data class SkuRepresentation(
  @JsonProperty("skuid")
  public val skuid: String,
  @JsonProperty("__typename")
  public val __typename: String = "Sku"
) {
  public override fun toString(): String = linkedMapOf(
  "skuid" to ("\"" + skuid + "\""),
  "__typename" to ("\"" + __typename + "\""),
  ).map { it.key + ":" + it.value }.joinToString(",", "{", "}")

  public companion object
}
