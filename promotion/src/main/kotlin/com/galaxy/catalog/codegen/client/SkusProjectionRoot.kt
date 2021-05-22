package com.galaxy.catalog.codegen.client

import com.netflix.graphql.dgs.client.codegen.BaseProjectionNode

public class SkusProjectionRoot : BaseProjectionNode() {
  public fun images(): Skus_ImagesProjection {
    val projection = Skus_ImagesProjection(this, this)    
    fields["images"] = projection
    return projection
  }

  public fun attributes(): Skus_AttributesProjection {
    val projection = Skus_AttributesProjection(this, this)    
    fields["attributes"] = projection
    return projection
  }

  public fun skuid(): SkusProjectionRoot {
    fields["skuid"] = null
    return this
  }

  public fun name(): SkusProjectionRoot {
    fields["name"] = null
    return this
  }

  public fun price(): SkusProjectionRoot {
    fields["price"] = null
    return this
  }

  public fun querydate(): SkusProjectionRoot {
    fields["querydate"] = null
    return this
  }

  public fun shortdescription(): SkusProjectionRoot {
    fields["shortdescription"] = null
    return this
  }

  public fun longdescription(): SkusProjectionRoot {
    fields["longdescription"] = null
    return this
  }
}
