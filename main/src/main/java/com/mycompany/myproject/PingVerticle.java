/*
 * Copyright 2013 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 *
 */
package com.mycompany.myproject;


import com.google.gson.JsonObject;
import com.jetdrone.vertx.yoke.Yoke;
import com.jetdrone.vertx.yoke.middleware.Favicon;
import com.jetdrone.vertx.yoke.middleware.Router;
import com.jetdrone.vertx.yoke.middleware.Static;
import com.jetdrone.vertx.yoke.middleware.YokeRequest;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.Verticle;
import org.vertx.java.core.*;
import org.vertx.java.platform.*;

public class PingVerticle extends Verticle {

    @Override
    public void start() {

        JsonObject config = new JsonObject();
        container.deployModule("com.mycompany~my-busmod~1.0.0-final");

//        vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
//            @Override
//            public void handle(HttpServerRequest httpServerRequest) {
//                httpServerRequest.response().end("Hello smartjava");
//            }
//        }).listen(8888);
//
//
//        container.logger().info("Webserver started, listening on port: 8888");

        Yoke yoke = new Yoke(this);
        yoke.use(new Handler<YokeRequest>() {
            @Override
            public void handle(final YokeRequest request) {
                EventBus eb = vertx.eventBus();
                eb.send("vertx.mod.testexample", "This is a message", new Handler<Message<String>>() {
                    public void handle(Message<String> message) {
                        request.response().end("I received a reply " + message.body());
                    }
                });

            }
        });
        yoke.listen(8080);
    }
}
