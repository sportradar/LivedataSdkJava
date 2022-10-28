/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.livedata.sdk.proto.dto;

import jakarta.xml.bind.annotation.XmlTransient;

/**
 * A base class for all entities(messages) send from the sportradar server to the SDK
 *
 * @author uros.bregar
 */
@XmlTransient
public abstract class IncomingMessage extends MessageBase {

}
