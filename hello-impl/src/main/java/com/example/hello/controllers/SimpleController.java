package com.example.hello.controllers;

import play.*;
import play.mvc.*;

public class SimpleController extends Controller {

    public Result index() {
        return ok("It works!");
    }

}