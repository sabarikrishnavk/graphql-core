package com.galaxy.catalog.codegen.client

import com.netflix.graphql.dgs.client.codegen.BaseSubProjectionNode

public class Skus_ImagesProjection(
  parent: SkusProjectionRoot,
  root: SkusProjectionRoot
) : BaseSubProjectionNode<SkusProjectionRoot, SkusProjectionRoot>(parent, root) {
  public fun imageType(): Skus_ImagesProjection {
    fields["imageType"] = null
    return this
  }

  public fun url(): Skus_ImagesProjection {
    fields["url"] = null
    return this
  }
}
