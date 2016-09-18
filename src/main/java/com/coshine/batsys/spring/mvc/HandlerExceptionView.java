package com.coshine.batsys.spring.mvc;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.util.Map;

public class HandlerExceptionView extends RuntimeException {

    private static final long serialVersionUID = 2591758054072118587L;

    private ModelAndView modelAndView;

    public HandlerExceptionView(String message, View view) {
        super(message);
        this.modelAndView = new ModelAndView(view);
    }

    public HandlerExceptionView(String message, String view) {
        super(message);
        this.modelAndView = new ModelAndView(view);
    }

    public HandlerExceptionView(String message, View view, String statusCode) {
        this(message, view);
        this.modelAndView.addObject("statusCode", statusCode);
    }

    public HandlerExceptionView(String message, View view, Map<String, ?> model) {
        super(message);
        this.modelAndView = new ModelAndView(view, model);
    }

    public HandlerExceptionView(String message, String view, Map<String, ?> model) {
        super(message);
        this.modelAndView = new ModelAndView(view, model);
    }

    public HandlerExceptionView(String message, ModelAndView modelAndView) {
        super(message);
        this.modelAndView = modelAndView;
    }

    public ModelAndView getModelAndView() {
        return modelAndView;
    }

}
