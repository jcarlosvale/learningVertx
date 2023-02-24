package com.learning.vertex_starter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleN extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.out.println("Start " + getClass().getName() + " on Thread " + Thread.currentThread().getName());
    System.out.println("Config of " + getClass().getName() + " : " + config().toString());
    startPromise.complete();
  }

}
