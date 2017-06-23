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

package io.servicecomb.config;

import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.core.io.InputStreamResource;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.resolver.Resolver;

import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Created by   on 2017/1/5.
 */
public final class YAMLUtil {
    private YAMLUtil() {
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> yaml2Properties(InputStream input) {
        return new Processor(input).process();
    }

    /**
     * ref. org.springframework.boot.env.YamlPropertySourceLoader
     * 参考spring-boot对yaml文件配置扁平化处理
     */
    private static class Processor extends YamlProcessor {

        Processor(InputStream inputStream) {
            setResources(new InputStreamResource(inputStream));
        }

        @Override
        protected Yaml createYaml() {
            return new Yaml(new StrictMapAppenderConstructor(), new Representer(), new DumperOptions(), new Resolver() {
                @Override
                public void addImplicitResolver(Tag tag, Pattern regexp, String first) {
                    if (tag == Tag.TIMESTAMP) {
                        return;
                    }
                    super.addImplicitResolver(tag, regexp, first);
                }
            });
        }

        public Map<String, Object> process() {
            final Map<String, Object> result = new LinkedHashMap<String, Object>();
            process(new MatchCallback() {
                @Override
                public void process(Properties properties, Map<String, Object> map) {
                    result.putAll(getFlattenedMap(map));
                }
            });
            return result;
        }

    }

    public static <T> T[] arrayConcat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
