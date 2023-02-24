package com.learning.vertex_starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.learning.vertex_starter.eventbus.RequestResponseExample.RequestVerticle.ADDRESS;

public class RequestResponseExample {

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new RequestVerticle());
    vertx.deployVerticle(new ResponseVerticle());
  }

  static class RequestVerticle extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(RequestVerticle.class);
    public static final String ADDRESS = "my.request.address";

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      var eventBus = vertx.eventBus();
      var message = "Hello World!!";
      LOG.debug("Sending: {}", message);
      eventBus.<String>request(ADDRESS, message, reply -> {
        LOG.debug("Response {}", reply.result().body());
      });
    }
  }

  static class ResponseVerticle extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(ResponseVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().<String>consumer(ADDRESS, message -> {
        LOG.debug("Received Message: {}", message.body());
        message.reply("Received your message. Thanks");
      });
    }
  }
}
