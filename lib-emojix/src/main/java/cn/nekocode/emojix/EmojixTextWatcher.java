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

import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.DynamicDrawableSpan;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class EmojixTextWatcher implements TextWatcher {
    private TextView textView;
    private int start = -1;
    private int count = -1;

    public EmojixTextWatcher(TextView textView) {
        this.textView = textView;

        SpannableString s = new SpannableString(textView.getText());
        if (!TextUtils.isEmpty(s)) {
            float textSize = textView.getTextSize();

            EmojiconHandler.addEmojis(textView.getContext(), s, (int) textSize,
                    DynamicDrawableSpan.ALIGN_BASELINE, (int) textSize, 0, -1);

            textView.setText(s, TextView.BufferType.EDITABLE);

            if (textView instanceof EditText) {
                EditText editText = (EditText) textView;
                editText.setSelection(s.length());
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (this.start == -2) return;
        if (this.start == -1 && count > 0) {
            // When text is added
            this.start = start;
            this.count = count;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (start >= 0) {
            float textSize = textView.getTextSize();
            int pos = start;

            start = -2;
            EmojiconHandler.addEmojis(textView.getContext(), s, (int) textSize,
                    DynamicDrawableSpan.ALIGN_BASELINE, (int) textSize, pos, count);
            start = -1;
        }
    }
}
