package wenld.github.ripplelayouts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import wenld.github.ripplelayout.RippleHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new RippleHelper(this, findViewById(R.id.btn));
    }
}
