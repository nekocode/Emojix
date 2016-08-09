package cn.nekocode.emojix.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.nekocode.emojix.Emojix;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Emojix Example \uD83C\uDF1A");

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        TextView textView = (TextView) findViewById(R.id.textView);
        CustomText customText = (CustomText) findViewById(R.id.textView2);
        EditText editText = (EditText) findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.button);

        assert linearLayout != null;
        assert textView != null;
        assert customText != null;
        assert editText != null;
        assert button != null;

        textView.setText("TextView: Hello World!\uD83D\uDC7E");
        customText.setText("CustomTextView: 上吧, 正义的伙伴!\uD83C\uDF8F");
        editText.setText("EditText: \uD83D\uDE37\uD83D\uDE02\uD83D\uDE04✨\uD83D\uDCA4\uD83D\uDD25");
        button.setText("Button: \uD83D\uDE80");

        TextView textView2 = new TextView(this);
        textView2.setText("Create TextView programmatically\uD83D\uDC7B");
        linearLayout.addView(textView2);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Emojix.wrap(newBase));
    }
}
