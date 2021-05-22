package com.galaxy.catalog.codegen.client

import com.netflix.graphql.dgs.client.codegen.BaseSubProjectionNode

public class Skus_AttributesProjection(
  parent: SkusProjectionRoot,
  root: SkusProjectionRoot
) : BaseSubProjectionNode<SkusProjectionRoot, SkusProjectionRoot>(parent, root) {
  public fun attributeid(): Skus_AttributesProjection {
    fields["attributeid"] = null
    return this
  }

  public fun name(): Skus_AttributesProjection {
    fields["name"] = null
    return this
  }

  public fun value(): Skus_AttributesProjection {
    fields["value"] = null
    return this
  }

  public fun searchable(): Skus_AttributesProjection {
    fields["searchable"] = null
    return this
  }

  public fun promotionable(): Skus_AttributesProjection {
    fields["promotionable"] = null
    return this
  }

  public fun itemdefining(): Skus_AttributesProjection {
    fields["itemdefining"] = null
    return this
  }
}
