package com.example.project_text.activity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.project_text.R;
import com.example.project_text.base.BaseActivity;
import com.example.project_text.fragment.home.HomeFragment;
import com.example.project_text.fragment.tencent.TencentFragment;
import com.example.project_text.view.ButtonNavigationView;

public class MainActivity extends BaseActivity {
    private ButtonNavigationView mButtonNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragment(getSupportFragmentManager(), HomeFragment.class, R.id.main_fragment_container, null);

        mButtonNavigationView = findViewById(R.id.main_botton);
        mButtonNavigationView.setOnTabChangedListener(new ButtonNavigationView.OnTabCheckedChangedListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int id = buttonView.getId();

                switch (id) {
                    case R.id.main_button_tab_home:
                        addFragment(getSupportFragmentManager(), HomeFragment.class, R.id.main_fragment_container, null);
                        Toast.makeText(MainActivity.this, buttonView.getText().toString(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.main_button_tab_knowledge:
                        Toast.makeText(MainActivity.this, buttonView.getText().toString(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.main_button_tab_wechat:
                        addFragment(getSupportFragmentManager(), TencentFragment.class, R.id.main_fragment_container, null);
                        Toast.makeText(MainActivity.this, buttonView.getText().toString(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.main_button_tab_navigation:
                        Toast.makeText(MainActivity.this, buttonView.getText().toString(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.main_button_tab_project:
                        Toast.makeText(MainActivity.this, buttonView.getText().toString(), Toast.LENGTH_SHORT).show();
                        break;

                }

            }
        });

    }
}
