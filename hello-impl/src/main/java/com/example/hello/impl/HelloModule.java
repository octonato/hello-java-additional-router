/*
 * 
 */
package com.example.hello.impl;

import com.example.hello.controllers.SimpleController;
import com.google.inject.AbstractModule;
import com.lightbend.lagom.internal.javadsl.server.*;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import com.example.hello.api.HelloService;
import play.api.http.HttpErrorHandler;
import play.routing.Router;
import router.Routes;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Collections;

/**
 * The module that binds the HelloService so that it can be served.
 */
public class HelloModule extends AbstractModule implements ServiceGuiceSupport {
  @Override
  protected void configure() {

    serverFor(HelloService.class, HelloServiceImpl.class)
            .withAdditionalRouters(PlayRoutersProvider.class)
            .bind();

  }

  static class PlayRoutersProvider implements Provider<AdditionalRouters> {
    final private HttpErrorHandler httpErrorHandler;
    final private SimpleController simpleController;
    final private AdditionalRouters additionalRouters;

    @Inject
    PlayRoutersProvider(HttpErrorHandler httpErrorHandler, SimpleController simpleController) {
      this.httpErrorHandler = httpErrorHandler;
      this.simpleController = simpleController;
      this.additionalRouters =
        new NonEmptyAdditionalRouters(Collections.singletonList(
                new Routes(httpErrorHandler, simpleController).asJava()
        ));
    }


    @Override
    public AdditionalRouters get() {
      return additionalRouters;
    }
  }
 }
