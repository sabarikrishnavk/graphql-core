//package com.galaxy.catalog.config
//
//import com.netflix.graphql.dgs.DgsComponent
//import com.netflix.graphql.dgs.DgsTypeDefinitionRegistry
//import graphql.schema.idl.SchemaGenerator
//import graphql.schema.idl.SchemaParser
//import graphql.schema.idl.TypeDefinitionRegistry
//import java.io.File
//import java.util.*
//
//
//@DgsComponent
//class SchemaRegistry {
////    @DgsCodeRegistry
////    fun registry(
////        codeRegistryBuilder: GraphQLCodeRegistry.Builder,
////        registry: TypeDefinitionRegistry?
////    ): GraphQLCodeRegistry.Builder {
////        val df = DataFetcher<Int> { dfe: DataFetchingEnvironment? -> Random().nextInt() }
////        val coordinates = FieldCoordinates.coordinates("Query", "skus")
////        return codeRegistryBuilder.dataFetcher(coordinates, df)
////    }
//
//    @DgsTypeDefinitionRegistry
//    fun registry(): TypeDefinitionRegistry? {
//
//        val schemaParser = SchemaParser()
//        val schemaGenerator = SchemaGenerator()
//
//        val schemaFile: File = loadSchema("starWarsSchema.graphqls")
//
//        val typeRegistry: TypeDefinitionRegistry = schemaParser.parse(schemaFile)
////        val typeDefinitionRegistry = TypeDefinitionRegistry()
////        val skusGraphQLQuery=  com.galaxy.catalog.codegen.client.SkusGraphQLQuery.Builder().build();
////        val query = ObjectTypeExtensionDefinition.newObjectTypeExtensionDefinition().name("Query")
////            .fieldDefinition(
////
////                FieldDefinition.newFieldDefinition().name("randomNumber").type(TypeName("Int")).build()
////        ).build()
////
////
////        typeDefinitionRegistry.add(query)
//        return typeRegistry
//    }
//
//    private fun loadSchema(s: String): File {
//        val file = File("")
//        return file
//    }
//}