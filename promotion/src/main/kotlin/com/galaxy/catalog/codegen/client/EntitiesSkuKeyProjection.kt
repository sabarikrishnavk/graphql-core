package com.galaxy.catalog.codegen.client

import com.netflix.graphql.dgs.client.codegen.BaseSubProjectionNode
import kotlin.String

public class EntitiesSkuKeyProjection(
  parent: EntitiesProjectionRoot,
  root: EntitiesProjectionRoot
) : BaseSubProjectionNode<EntitiesProjectionRoot, EntitiesProjectionRoot>(parent, root) {
  init {
    fields["__typename"] = null
  }

  public fun images(): EntitiesSkuKey_ImagesProjection {
    val projection = EntitiesSkuKey_ImagesProjection(this, root)    
    fields["images"] = projection
    return projection
  }

  public fun attributes(): EntitiesSkuKey_AttributesProjection {
    val projection = EntitiesSkuKey_AttributesProjection(this, root)    
    fields["attributes"] = projection
    return projection
  }

  public fun skuid(): EntitiesSkuKeyProjection {
    fields["skuid"] = null
    return this
  }

  public fun name(): EntitiesSkuKeyProjection {
    fields["name"] = null
    return this
  }

  public fun price(): EntitiesSkuKeyProjection {
    fields["price"] = null
    return this
  }

  public fun querydate(): EntitiesSkuKeyProjection {
    fields["querydate"] = null
    return this
  }

  public fun shortdescription(): EntitiesSkuKeyProjection {
    fields["shortdescription"] = null
    return this
  }

  public fun longdescription(): EntitiesSkuKeyProjection {
    fields["longdescription"] = null
    return this
  }

  public override fun toString(): String {
    val builder = StringBuilder()
    builder.append("... on Sku {")
    fields.forEach { k, v ->
        builder.append(" ").append(k)
        if(v != null) {
            builder.append(" ").append(v.toString())
        }
    }
    builder.append("}")

    return builder.toString()
  }
}
