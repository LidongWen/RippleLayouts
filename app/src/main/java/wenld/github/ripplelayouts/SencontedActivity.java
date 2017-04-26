package wenld.github.ripplelayouts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import wenld.github.ripplelayout.RippleHelper;

/**
 * <p/>
 * Author: wenld on 2017/4/26 17:05.
 * blog: http://www.jianshu.com/u/99f514ea81b3
 * github: https://github.com/LidongWen
 */

public class SencontedActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_001;
    private Button btn_002;
    private TextView tv_001;
    private ImageView iv_001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        initView();
    }

    private void initView() {
        btn_001 = (Button) findViewById(R.id.btn_001);
        btn_002 = (Button) findViewById(R.id.btn_002);
        tv_001 = (TextView) findViewById(R.id.tv_001);
        iv_001 = (ImageView) findViewById(R.id.iv_001);

        new RippleHelper(this, btn_001);
        new RippleHelper(this, btn_002);
        new RippleHelper(this, tv_001);
        new RippleHelper(this, iv_001);
//                .setRippleBackgroundResource(R.color.half_colorAccent)
//                .setRippleColorResource(R.color.colorAccent);


        btn_001.setOnClickListener(this);
        btn_002.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_001:

                break;
            case R.id.btn_002:

                break;
        }
    }
}
