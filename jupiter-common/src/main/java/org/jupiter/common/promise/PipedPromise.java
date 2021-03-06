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

/*
 * Copyright 2013 Ray Tsang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jupiter.common.promise;

/**
 * Forked from <a href="https://github.com/jdeferred">JDeferred</a>.
 */
@SuppressWarnings("unchecked")
public class PipedPromise<D, F, D_OUT, F_OUT> extends DefaultPromise<D_OUT, F_OUT> {

    public PipedPromise(Promise<D, F> promise, final DonePipe<D, D_OUT, F_OUT> donePipe, final FailPipe<F, D_OUT, F_OUT> failPipe) {
        promise.then(
                new DoneCallback<D>() {

                    @Override
                    public void onDone(D result) {
                        if (donePipe != null) {
                            pipe(donePipe.pipeDone(result));
                        } else {
                            resolve((D_OUT) result);
                        }
                    }
                },
                new FailCallback<F>() {

                    @Override
                    public void onFail(F cause) {
                        if (failPipe != null) {
                            pipe(failPipe.pipeFail(cause));
                        } else {
                            reject((F_OUT) cause);
                        }
                    }
        });
    }

    protected Promise<D_OUT, F_OUT> pipe(Promise<D_OUT, F_OUT> promise) {
        return promise.then(
                new DoneCallback<D_OUT>() {

                    @Override
                    public void onDone(D_OUT result) {
                        resolve(result);
                    }
                },
                new FailCallback<F_OUT>() {

                    @Override
                    public void onFail(F_OUT cause) {
                        reject(cause);
                    }
                }
        );
    }
}
