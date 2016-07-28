package cn.nekocode.emojix.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.nekocode.emojix.Emojix;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Emojix Example \uD83C\uDF1A");

        TextView textView = (TextView) findViewById(R.id.textView);
        CustomText customText = (CustomText) findViewById(R.id.textView2);
        EditText editText = (EditText) findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.button);

        assert textView != null;
        assert customText != null;
        assert editText != null;
        assert button != null;

        textView.setText("TextView: Hello World!\uD83D\uDC7E");
        customText.setText("CustomTextView: 白洞, 白色的明天在等着我们!\uD83C\uDF8F");
        editText.setText("EditText: \uD83D\uDC8F\uD83D\uDC49\uD83D\uDC47\uD83D\uDC4A\uD83D\uDE20\uD83D\uDCA9");
        button.setText("Button: \uD83D\uDE80");
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Emojix.wrap(newBase));
    }
}
