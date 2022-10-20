package com.sportradar.sdk.test.system.framework.actions;

import org.hamcrest.Description;
import org.jmock.States;
import org.jmock.api.Action;
import org.jmock.api.Invocation;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

public class LoginAction implements Action {

    private States loginState;

    public LoginAction(States loginState) {
        checkNotNull(loginState);
        this.loginState = loginState;
    }

    @Override
    public void describeTo(Description description) {
    }

    @Override
    public Object invoke(Invocation invocation) throws Throwable {
        loginState.become("OK");
        return null;
    }
}
