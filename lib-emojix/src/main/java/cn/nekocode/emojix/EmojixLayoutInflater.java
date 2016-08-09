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

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xmlpull.v1.XmlPullParser;

import java.lang.reflect.Field;

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class EmojixLayoutInflater extends LayoutInflater {
    private static final String[] CLASS_PREFIXES = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };

    protected EmojixLayoutInflater(LayoutInflater original, Context newContext) {
        super(original, newContext);
        setUpLayoutFactories(false);
    }

    protected EmojixLayoutInflater(LayoutInflater original, Context newContext, final boolean cloned) {
        super(original, newContext);
        setUpLayoutFactories(cloned);
    }

    private void setUpLayoutFactories(boolean cloned) {
        if (cloned) return;

        if (getFactory2() != null && !(getFactory2() instanceof WrapperFactory2)) {
            setFactory2(getFactory2());
        }

        if (getFactory() != null && !(getFactory() instanceof WrapperFactory)) {
            setFactory(getFactory());
        }
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new EmojixLayoutInflater(this, newContext, true);
    }

    @Override
    public View inflate(XmlPullParser parser, ViewGroup root, boolean attachToRoot) {
        return super.inflate(parser, root, attachToRoot);
    }

    @Override
    public void setFactory(Factory factory) {
        // Only set our factory and wrap calls to the Factory trying to be set!
        if (!(factory instanceof WrapperFactory)) {
            super.setFactory(new WrapperFactory(factory, this));
        } else {
            super.setFactory(factory);
        }
    }

    @Override
    public void setFactory2(Factory2 factory2) {
        // Only set our factory and wrap calls to the Factory2 trying to be set!
        if (!(factory2 instanceof WrapperFactory2)) {
            super.setFactory2(new WrapperFactory2(factory2, this));
        } else {
            super.setFactory2(factory2);
        }
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected View onCreateView(View parent, String name, AttributeSet attrs) throws ClassNotFoundException {
        return onViewCreated(super.onCreateView(parent, name, attrs), getContext(), attrs);
    }

    @Override
    protected View onCreateView(String name, AttributeSet attrs) throws ClassNotFoundException {
        return onViewCreated(super.onCreateView(name, attrs), getContext(), attrs);
    }

    public View onViewCreated(View view, final Context context, final AttributeSet attrs) {
        if (view == null) return null;

//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Emojix);
//        boolean useEmojix = a.getBoolean(R.styleable.Emojix_emoji, true);
//        a.recycle();

        Emojix.wrapView(view);

        return view;
    }

    private static class BaseFactory {
        static private Field constructorArgs = null;

        protected View createView(EmojixLayoutInflater inflater, String name, Context context, AttributeSet attrs) {
            View view = null;

            // Try to create view manually
            int li = name.lastIndexOf(".") + 1;
            if (li != 0) {
                String prefix = name.substring(0, li);
                String rname = name.substring(li, name.length());

                try {
                    view = inflater.createView(rname, prefix, attrs);
                } catch (Exception ignored) {
                }

                if (view == null) {
                    if (constructorArgs == null)
                        constructorArgs = ReflectionUtils.getField(LayoutInflater.class, "mConstructorArgs");

                    final Object[] mConstructorArgsArr = (Object[]) ReflectionUtils.getValue(constructorArgs, inflater);
                    final Object lastContext = mConstructorArgsArr[0];
                    // The LayoutInflater actually finds out the correct context to use. We just need to set
                    // it on the mConstructor for the internal method.
                    // Set the constructor ars up for the createView, not sure why we can't pass these in.
                    mConstructorArgsArr[0] = context;
                    ReflectionUtils.setValue(constructorArgs, inflater, mConstructorArgsArr);
                    try {
                        view = inflater.createView(name, null, attrs);
                    } catch (Exception ignored) {
                    } finally {
                        mConstructorArgsArr[0] = lastContext;
                        ReflectionUtils.setValue(constructorArgs, inflater, mConstructorArgsArr);
                    }
                }

            } else {
                for (String prefix : CLASS_PREFIXES) {
                    try {
                        view = inflater.createView(name, prefix, attrs);
                        if (view != null) break;
                    } catch (Exception ignored) {
                    }
                }
            }

            return view;
        }
    }

    private static class WrapperFactory extends BaseFactory implements Factory {
        private final Factory factory;
        private final EmojixLayoutInflater inflater;

        public WrapperFactory(Factory factory, EmojixLayoutInflater inflater) {
            this.factory = factory;
            this.inflater = inflater;
        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            View view = createView(inflater, name, context, attrs);

            if (view == null) {
                view = factory.onCreateView(name, context, attrs);
            }

            return inflater.onViewCreated(view, context, attrs);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static class WrapperFactory2 extends BaseFactory implements Factory2 {
        protected final Factory2 factory2;
        protected final EmojixLayoutInflater inflater;

        public WrapperFactory2(Factory2 factory2, EmojixLayoutInflater inflater) {
            this.factory2 = factory2;
            this.inflater = inflater;
        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            return inflater.onViewCreated(factory2.onCreateView(name, context, attrs), context, attrs);
        }

        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            View view = createView(inflater, name, context, attrs);

            if (view == null) {
                view = factory2.onCreateView(name, context, attrs);
            }

            return inflater.onViewCreated(view, context, attrs);
        }
    }
}
