package com.galaxy.catalog.codegen.client

import com.netflix.graphql.dgs.client.codegen.GraphQLQuery
import kotlin.String

public data class SkusGraphQLQuery(
  public val skuid: String? = null
) : GraphQLQuery("query") {
  init {
    if(skuid != null) {
        input["skuid"] = skuid
    }
  }
  public override fun getOperationName(): String = "skus";

  public class Builder {
    public var skuid: String? = null

    public fun build(): SkusGraphQLQuery = SkusGraphQLQuery(skuid)

    public fun skuid(skuid: String?): Builder {
      this.skuid = skuid;
      return this
    }
  }
}
