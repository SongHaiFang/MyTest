package demo.song.com.yuekaotest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import demo.song.com.yuekaotest.fragment.OneFragment;
import demo.song.com.yuekaotest.fragment.ThreeFragment;
import demo.song.com.yuekaotest.fragment.TwoFragment;

import static demo.song.com.yuekaotest.R.id.search;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private RadioButton button1,button2,button3;
    private RadioGroup group;
    private Toolbar toolbar;
    private TextView textView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        checkNetworkState();
        ininView();
        button1.setTextSize(20);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb1:
                        button1.setTextSize(20);
                        button2.setTextSize(15);
                        button3.setTextSize(15);
                        pager.setCurrentItem(0);
                        textView.setText("圈子");
                        break;
                    case R.id.rb2:
                        button1.setTextSize(15);
                        button2.setTextSize(20);
                        button3.setTextSize(15);
                        pager.setCurrentItem(1);
                        textView.setText("朋友");
                        break;
                    case R.id.rb3:
                        button1.setTextSize(15);
                        button2.setTextSize(15);
                        button3.setTextSize(20);
                        pager.setCurrentItem(2);
                        textView.setText("我的");
                        break;
                }
            }
        });

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                group.check(group.getChildAt(position).getId());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                switch (position){
                    case 0:
                        fragment = new OneFragment();
                        break;
                    case 1:
                        fragment = new TwoFragment();
                        break;
                    case 2:
                        fragment = new ThreeFragment();
                        break;
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
//        pager.setCurrentItem(0);


    }

    private boolean checkNetworkState() {
        boolean net = false;
        //得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            net= manager.getActiveNetworkInfo().isAvailable();
        }

        if (!net) {
            setNetWork();//网络没连接调用的方法
        } else {
            netWorktrue();//网络连接调用的方法
        }

        return net;
    }

    private void netWorktrue() {
        Toast.makeText(this,"网络连接成功",Toast.LENGTH_SHORT).show();
    }

    private void setNetWork() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("网络提示信息");
        builder.setMessage("网络不可用，如果继续，请先设置网络！");
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                Intent intent = null;
                //Android系统的网络设置
                intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create();//创建并且展示，很重要，不要忘记填了
        builder.show();
    }


    private void ininView() {
        pager = (ViewPager) findViewById(R.id.viewpager);
        group = (RadioGroup) findViewById(R.id.rg);
        button1 = (RadioButton) findViewById(R.id.rb1);
        button2 = (RadioButton) findViewById(R.id.rb2);
        button3 = (RadioButton) findViewById(R.id.rb3);
        toolbar = (Toolbar) findViewById(R.id.tool);
        textView = (TextView) findViewById(R.id.t);
//        searchView = (SearchView) findViewById(search);
//        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
//        EditText textView1 = (EditText ) searchView.findViewById(id);
//        textView1.setTextSize(14);
//        textView1.setHint("按姓名和标题搜索");
    }
}
