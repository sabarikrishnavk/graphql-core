package com.galaxy.catalog.codegen.client

import com.netflix.graphql.dgs.client.codegen.BaseSubProjectionNode

public class EntitiesSkuKey_AttributesProjection(
  parent: EntitiesSkuKeyProjection,
  root: EntitiesProjectionRoot
) : BaseSubProjectionNode<EntitiesSkuKeyProjection, EntitiesProjectionRoot>(parent, root) {
  public fun attributeid(): EntitiesSkuKey_AttributesProjection {
    fields["attributeid"] = null
    return this
  }

  public fun name(): EntitiesSkuKey_AttributesProjection {
    fields["name"] = null
    return this
  }

  public fun value(): EntitiesSkuKey_AttributesProjection {
    fields["value"] = null
    return this
  }

  public fun searchable(): EntitiesSkuKey_AttributesProjection {
    fields["searchable"] = null
    return this
  }

  public fun promotionable(): EntitiesSkuKey_AttributesProjection {
    fields["promotionable"] = null
    return this
  }

  public fun itemdefining(): EntitiesSkuKey_AttributesProjection {
    fields["itemdefining"] = null
    return this
  }
}
