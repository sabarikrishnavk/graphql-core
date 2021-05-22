package com.galaxy.catalog.codegen.types

import com.fasterxml.jackson.`annotation`.JsonProperty
import kotlin.Boolean
import kotlin.String

public data class Attributes(
  @JsonProperty("attributeid")
  public val attributeid: String,
  @JsonProperty("name")
  public val name: String,
  @JsonProperty("value")
  public val value: String,
  @JsonProperty("searchable")
  public val searchable: Boolean? = null,
  @JsonProperty("promotionable")
  public val promotionable: Boolean? = null,
  @JsonProperty("itemdefining")
  public val itemdefining: Boolean? = null
) {
  public companion object
}
