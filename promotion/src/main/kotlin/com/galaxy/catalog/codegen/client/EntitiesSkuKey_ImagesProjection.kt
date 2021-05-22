package com.galaxy.catalog.codegen.client

import com.netflix.graphql.dgs.client.codegen.BaseSubProjectionNode

public class EntitiesSkuKey_ImagesProjection(
  parent: EntitiesSkuKeyProjection,
  root: EntitiesProjectionRoot
) : BaseSubProjectionNode<EntitiesSkuKeyProjection, EntitiesProjectionRoot>(parent, root) {
  public fun imageType(): EntitiesSkuKey_ImagesProjection {
    fields["imageType"] = null
    return this
  }

  public fun url(): EntitiesSkuKey_ImagesProjection {
    fields["url"] = null
    return this
  }
}
