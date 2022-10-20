package com.sportradar.sdk.test.system.framework.actions;

import com.sportradar.sdk.feed.livescout.entities.LiveScoutEntityBase;
import org.hamcrest.Description;
import org.jmock.States;
import org.jmock.api.Action;
import org.jmock.api.Invocation;

import java.util.ArrayList;
import java.util.List;

public class LiveScoutEntityDispatchAction implements Action {


    List<LiveScoutEntityBase> entities;
    int expectedNumberOfMessages;
    States receivedAllExpecedMessages;

    public void setExpectedNumberOfMessages(int expectedNumberOfMessages) {
        this.expectedNumberOfMessages = expectedNumberOfMessages;
    }

    private int dispatchCount = 0;

    public LiveScoutEntityDispatchAction(States recievedAllExpecedMessages) {
        this.entities = new ArrayList<>();
        this.receivedAllExpecedMessages = recievedAllExpecedMessages;
    }

    @Override
    public void describeTo(Description description) {

    }

    public List<LiveScoutEntityBase> getEntities() {
        return entities;
    }

    @Override
    public Object invoke(Invocation invocation) throws Throwable {
        LiveScoutEntityBase entity = (LiveScoutEntityBase) invocation.getParametersAsArray()[0];
        entities.add(entity);
        if (++dispatchCount == expectedNumberOfMessages) {
            receivedAllExpecedMessages.become("OK");
        }
        return null;
    }
}
