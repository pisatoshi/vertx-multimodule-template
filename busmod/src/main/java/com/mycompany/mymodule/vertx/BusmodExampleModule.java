package com.mycompany.mymodule.vertx;

import org.vertx.java.busmods.BusModBase;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;

public class BusmodExampleModule extends BusModBase implements Handler<Message<JsonObject>> {

    @Override
    public void start() {
        super.start();
        final Logger logger = container.logger();
        eb.registerHandler("vertx.mod.testexample", this);
        logger.info("BusmodExampleModule started");
    }

    /**
     * Handle an incoming message.
     */
    @Override
    public void handle(Message<JsonObject> message) {
        message.reply("BusMod responded!");
    }
}

