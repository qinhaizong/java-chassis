/*
 * Copyright 2017 Huawei Technologies Co., Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huawei.paas.cse.transport.rest.client;

import com.netflix.config.DynamicIntProperty;
import com.netflix.config.DynamicPropertyFactory;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * @author   
 * @version  [版本号, 2017年1月2日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public final class TransportClientConfig {
    private TransportClientConfig() {
    }

    public static int getThreadCount() {
        DynamicIntProperty address =
            DynamicPropertyFactory.getInstance().getIntProperty("cse.rest.client.thread-count", 1);
        return address.get();
    }

    public static int getConnectionPoolPerThread() {
        DynamicIntProperty address =
            DynamicPropertyFactory.getInstance().getIntProperty("cse.rest.client.connection-pool-per-thread", 1);
        return address.get();
    }
}