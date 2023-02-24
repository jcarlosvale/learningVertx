package com.learning.vertex_starter.worker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerExampleFailing extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(WorkerExampleFailing.class);

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new WorkerExampleFailing());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    LOG.debug("Start: {}", getClass().getName());
    startPromise.complete();
    vertx.executeBlocking(event -> {
      LOG.debug("Executing blocking code");
      try {
        Thread.sleep(5000);
        event.fail("force fail");
      } catch (InterruptedException e) {
        LOG.error("Failed: ", e);
        event.fail(e);
      }
    }, result -> {
      if (result.succeeded())
        LOG.debug("Blocking call is done.");
      else
        LOG.debug("Blocking call failed due to:", result.cause());
    });
  }
}
