package com.galaxy.catalog.codegen

import kotlin.String

public object DgsConstants {
  public const val QUERY_TYPE: String = "Query"

  public object QUERY {
    public const val TYPE_NAME: String = "Query"

    public const val Skus: String = "skus"
  }

  public object SKU {
    public const val TYPE_NAME: String = "Sku"

    public const val Skuid: String = "skuid"

    public const val Name: String = "name"

    public const val Price: String = "price"

    public const val Querydate: String = "querydate"

    public const val Shortdescription: String = "shortdescription"

    public const val Longdescription: String = "longdescription"

    public const val Images: String = "images"

    public const val Attributes: String = "attributes"
  }

  public object ATTRIBUTES {
    public const val TYPE_NAME: String = "Attributes"

    public const val Attributeid: String = "attributeid"

    public const val Name: String = "name"

    public const val Value: String = "value"

    public const val Searchable: String = "searchable"

    public const val Promotionable: String = "promotionable"

    public const val Itemdefining: String = "itemdefining"
  }

  public object IMAGE {
    public const val TYPE_NAME: String = "Image"

    public const val ImageType: String = "imageType"

    public const val Url: String = "url"
  }
}
