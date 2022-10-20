/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.proto.dto;

import jakarta.xml.bind.annotation.XmlTransient;

/**
 * A base class for all entities(messages) send from the SDK to the sportradar server
 *
 * @author uros.bregar
 */
@XmlTransient
public abstract class OutgoingMessage extends MessageBase {

}
