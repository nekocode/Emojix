/*
 * Copyright 2016 nekocode
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
package cn.nekocode.emojix;

import android.content.Context;
import android.view.LayoutInflater;

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
public class Emojix {
    private static class ContextWrapper extends android.content.ContextWrapper {
        private EmojixLayoutInflater inflater;

        public ContextWrapper(Context base) {
            super(base);
        }

        @Override
        public Object getSystemService(String name) {
            if (LAYOUT_INFLATER_SERVICE.equals(name)) {
                if (inflater == null) {
                    inflater = new EmojixLayoutInflater(LayoutInflater.from(getBaseContext()), this);
                }
                return inflater;
            }
            return super.getSystemService(name);
        }
    }

    public static android.content.ContextWrapper wrap(Context base) {
        return new ContextWrapper(base);
    }
}
