package com.galaxy.catalog.codegen.types

import com.fasterxml.jackson.`annotation`.JsonProperty
import kotlin.String

public data class Image(
  @JsonProperty("imageType")
  public val imageType: String? = null,
  @JsonProperty("url")
  public val url: String? = null
) {
  public companion object
}
