/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.jboss.netty.channel;

/**
 * The {@link CompleteChannelFuture} which is failed already.  It is
 * recommended to use {@link Channels#failedFuture(Channel, Throwable)}
 * instead of calling the constructor of this future.
 */
public class FailedChannelFuture extends CompleteChannelFuture {

    private final Throwable cause;

    /**
     * Creates a new instance.
     *
     * @param channel the {@link Channel} associated with this future
     * @param cause   the cause of failure
     */
    public FailedChannelFuture(Channel channel, Throwable cause) {
        super(channel);
        if (cause == null) {
            throw new NullPointerException("cause");
        }
        this.cause = cause;
    }

    public Throwable getCause() {
        return cause;
    }

    public boolean isSuccess() {
        return false;
    }

    public ChannelFuture sync() throws InterruptedException {
        rethrow();
        return this;
    }

    public ChannelFuture syncUninterruptibly() {
        rethrow();
        return this;
    }

    private void rethrow() {
        if (cause instanceof RuntimeException) {
            throw (RuntimeException) cause;
        }

        if (cause instanceof Error) {
            throw (Error) cause;
        }

        throw new ChannelException(cause);
    }
}
