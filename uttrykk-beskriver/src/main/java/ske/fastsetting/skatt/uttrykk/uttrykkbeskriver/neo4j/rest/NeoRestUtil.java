/*
 * Licensed to Neo Technology under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Neo Technology licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.neo4j.rest;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Stream;

import static ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.neo4j.rest.Value.string;


public class NeoRestUtil {
    private static final URI SERVER_ROOT_URI = URI.create("http://localhost:7474/db/data/");

    public static void checkDatabaseIsRunning() {
        Response response = http("GET", SERVER_ROOT_URI, null);

        if(response.getStatus()!=200) {
            throw new RuntimeException("Expected HTTP 200 from "+SERVER_ROOT_URI);
        }

        response.close();
    }

    public static URI createNode() {
        return createNode(ObjectBuilder.ny().build());
     }

    public static URI createNode(Object properties) {
        final URI nodeEntryPointUri = UriBuilder.fromUri(SERVER_ROOT_URI).path("node").build();

        Response response = http("POST", nodeEntryPointUri, properties);

        final URI location = response.getLocation();
        response.close();

        return location;
    }


    public static void addLabels(URI node, String... labels) {
        URI uri = UriBuilder.fromUri(node).path("labels").build();

        ListBuilder liste = ListBuilder.liste();
        Stream.of(labels)
                .forEach(liste::value);

        http("POST", uri, liste.build()).close();

    }


    public static void addProperty(URI nodeUri, String propertyName,
                                    String propertyValue) {
        URI propertyUri = UriBuilder.fromUri(nodeUri).path("properties/").path(propertyName).build();

        http("PUT", propertyUri, string(propertyValue));
    }


    public static URI addRelationship(URI startNode, URI endNode,
                                       String relationshipType, Object attributes)
            {
        URI fromUri = UriBuilder.fromUri(startNode).path("relationships").build();

        Object relationshipJson = ObjectBuilder.ny()
                .entry("to", endNode)
                .entry("type", relationshipType)
                .entry("data", attributes)
                .build();

        Response response = http("POST", fromUri, relationshipJson);

        final URI location = response.getLocation();

        response.close();
        return location;
    }


    public static void sendTransactionalCypherQuery(String query) {

        final URI txUri = UriBuilder.fromUri(SERVER_ROOT_URI)
                .path("transaction/commit").build();

        final ObjectBuilder builder = ObjectBuilder.ny();
        builder.array("statements")
                .object().entry("statement", query);

        http("POST", txUri, builder.build()).close();
    }

    public static void find(URI startNode, String type)
            throws URISyntaxException {

        ObjectBuilder builder = ObjectBuilder.ny();

        builder.entry("order", "depth first")
                .entry("uniqueness", "node")
                .array("releationships").object()
                .entry("type", type)
                .entry("direction", "out");

        builder.object("return filter")
                .entry("language","builtin")
                .entry("name", "all");

        builder.entry("max depth",10);

        URI traverserUri = new URI(startNode.toString() + "/traverse/node");

        http("POST", traverserUri, builder.build()).close();
    }


    private static Response http(String method, URI nodeEntryPointUri, Object payload) {
        final ClientConfig configuration = new ClientConfig();

        configuration.register(JacksonFeature.class);

        Client client = ClientBuilder.newClient(configuration);
        WebTarget webTarget = client.target(nodeEntryPointUri);

        Response response = webTarget
                .request(MediaType.APPLICATION_JSON)
                .method(method, Entity.entity(payload, MediaType.APPLICATION_JSON));

        System.out.println(String.format(
                "[%s] [%s] to [%s], status code [%d], returned data: "
                        + System.getProperty("line.separator") + "%s",
                method, payload, nodeEntryPointUri, response.getStatus(),
                response.readEntity(String.class)));

        return response;
    }

}