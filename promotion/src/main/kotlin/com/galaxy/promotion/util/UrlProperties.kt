package com.galaxy.promotion.util

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding


@ConstructorBinding
@ConfigurationProperties("galaxy.url")
data class UrlProperties(var catalog: String) ;