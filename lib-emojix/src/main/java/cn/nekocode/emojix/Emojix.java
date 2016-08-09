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
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    public static void wrapView(View view) {
        if (view == null) return;

        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            if (textView.getTag(R.id.tag_emojix_watcher) == null) {
                EmojixTextWatcher watcher = new EmojixTextWatcher(textView);
                textView.addTextChangedListener(watcher);

                textView.setTag(R.id.tag_emojix_watcher, watcher);
            }

        } else if (view instanceof ViewGroup) {
            if (view.getTag(R.id.tag_layout_listener) == null) {
                View.OnLayoutChangeListener listener = new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                               int oldLeft, int oldTop, int oldRight, int oldBottom) {

                        ViewGroup parentView = (ViewGroup) v;
                        int len = parentView.getChildCount();
                        for (int i = 0; i < len; i ++) {
                            wrapView(parentView.getChildAt(i));
                        }
                    }
                };
                view.addOnLayoutChangeListener(listener);

                view.setTag(R.id.tag_layout_listener, listener);
            }
        }
    }
}
