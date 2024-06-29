package org.lafed.testautomationexercise.handlers;

import org.springframework.stereotype.Component;

@Component
public class TAEHandler extends AbstractHandler {

    public TAEHandler(String environment, String browserType, String downloadPath, String url, String userName, String password) {
        super(environment, browserType, downloadPath, url, userName, password);
    }
}
