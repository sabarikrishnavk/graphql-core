const { ApolloServer } = require("apollo-server");
const { ApolloGateway } = require("@apollo/gateway");

const server = new ApolloServer({
  gateway: new ApolloGateway({
    debug: true,
    serviceList: [
      { name: "auth", url: "http://localhost:8081/graphql" },
      { name: "catalog", url: "http://localhost:8082/graphql" },
      { name: "inventory", url: "http://localhost:8083/graphql" }
    ]
  }),
  subscriptions: false
});

server.listen().then(({ url }) => {
  console.log(`🚀 Server ready at ${url}`);
});