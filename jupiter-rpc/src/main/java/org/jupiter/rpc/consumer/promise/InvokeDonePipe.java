/*
 * Copyright (c) 2015 The Jupiter Project
 *
 * Licensed under the Apache License, version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jupiter.rpc.consumer.promise;

import org.jupiter.common.promise.DefaultPromise;
import org.jupiter.common.promise.DonePipe;
import org.jupiter.common.promise.Promise;

/**
 * jupiter
 * org.jupiter.rpc.consumer.promise
 *
 * @author jiachun.fjc
 */
public abstract class InvokeDonePipe<D, D_OUT> implements DonePipe<D, D_OUT, Throwable> {

    @SuppressWarnings("unchecked")
    @Override
    public Promise<D_OUT, Throwable> pipeDone(D result) {
        try {
            doInPipe(result);

            return (Promise<D_OUT, Throwable>) InvokePromiseContext.currentPromise();
        } catch (Throwable t) {
            return new DefaultPromise<D_OUT, Throwable>().reject(t);
        }
    }

    public abstract void doInPipe(D result);
}
