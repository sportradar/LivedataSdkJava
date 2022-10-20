package com.sportradar.sdk.test.system.framework.actions;

import org.hamcrest.Description;
import org.jmock.States;
import org.jmock.api.Action;
import org.jmock.api.Invocation;

public class PutStateInOkAction implements Action {

    States[] states;

    public PutStateInOkAction(States... states) {
        this.states = states;
    }

    /**
     * Performs an action in response to an invocation.
     *
     * @param invocation The invocation to perform.
     * @return The result of the invocation, if not throwing an exception.
     * Must return {@code null} if the invoked method has a void return type.
     * @throws Throwable An exception to be thrown to the caller, if not returning a value.  Any checked exception
     *                   thrown must be in the {@code throws} list of the invoked method.
     */
    @Override
    public Object invoke(Invocation invocation) throws Throwable {
        for (States state : states) {
            state.become("OK");
        }
        return null;
    }

    /**
     * Generates a description of the object.  The description may be part of a
     * a description of a larger object of which this is just a component, so it
     * should be worded appropriately.
     *
     * @param description The description to be built or appended to.
     */
    @Override
    public void describeTo(Description description) {

    }
}
