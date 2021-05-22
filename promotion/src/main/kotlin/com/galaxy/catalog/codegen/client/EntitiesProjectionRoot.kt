package com.galaxy.catalog.codegen.client

import com.netflix.graphql.dgs.client.codegen.BaseProjectionNode

public class EntitiesProjectionRoot : BaseProjectionNode() {
  public fun onSku(): EntitiesSkuKeyProjection {
    val fragment = EntitiesSkuKeyProjection(this, this)
    fragments.add(fragment)
    return fragment
  }
}
