package com.studying.starter;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetSocket;

public class VertxEcho {

  private static int numberOfConnections = 0;

  public static void main(String[] args) {
    final Vertx vertx = Vertx.vertx();

    vertx.createNetServer()
      .connectHandler(VertxEcho::handleNewClient)
      .listen(3000);

    vertx.setPeriodic(5000, id -> System.out.println(howMany()));

    vertx.createHttpServer()
      .requestHandler(request -> request.response().end(howMany()))
      .listen(8080);
  }

  private static String howMany() {
    return "Connections : " + numberOfConnections;
  }

  private static void handleNewClient(final NetSocket netSocket) {
    numberOfConnections++;
    netSocket.handler(buffer -> {
      netSocket.write(buffer);
      if (buffer.toString().endsWith("/quit\n")) {
        netSocket.close();
      }
    });
    netSocket.closeHandler(unused -> numberOfConnections--);
  }
}
