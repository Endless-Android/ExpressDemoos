package com.example.administrator.expressdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.expressdemo.R.id.conn;
import static com.example.administrator.expressdemo.R.id.num;

public class MainActivity extends AppCompatActivity {
    private Button mButton;
    private EditText mNum;
    private EditText mConn;
    private Gson mGson = new Gson();
    private ExpressDate mExpressDate;
    private LinearLayout ll;
    private String mCompanyMenu = null;
    private String mCompanyNo = "";
    private ArrayList<ExpressCompanyDate> companylist = new ArrayList<ExpressCompanyDate>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getCompany();
    }

    public void click(View v) {
        ll.removeAllViews();
        String num = mNum.getText().toString().trim();
        String conn = mConn.getText().toString().trim();
        checkCompany(num, conn);
    }

    private void checkCompany(String num, String conn) {
        for (ExpressCompanyDate compan : companylist) {
            if (compan.getCom().equals(conn)) {
                mCompanyMenu = compan.getNo();
            }
        }

        if (mCompanyMenu == null) {
            Toast.makeText(MainActivity.this, "快递单号或快递公司错误", Toast.LENGTH_LONG).show();

        } else {
            mCompanyNo = num;
            getDate();
        }
    }

    private void init() {
        mConn = (EditText) findViewById(conn);
        mNum = (EditText) findViewById(num);
        ll = (LinearLayout) findViewById(R.id.ll);
        mButton = (Button) findViewById(R.id.btn);

    }


    public void getCompany() {
        final String companyurl = "http://v.juhe.cn/exp/com?key=25c8e8e1649fe42843cd923512a96205";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(companyurl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    InputStream is = connection.getInputStream();
                    String s = StringUtils.InputStreamToString(is);
                    JSONArray array = new JSONObject(s).getJSONArray("result");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jj = (JSONObject) array.get(i);
                        String name = jj.getString("com");
                        String no = jj.getString("no");
                        ExpressCompanyDate date = new ExpressCompanyDate();
                        date.setCom(name);
                        date.setNo(no);
                        companylist.add(date);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getDate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String expressurl = "http://v.juhe.cn/exp/index?key=25c8e8e1649fe42843cd923512a96205&com=" + mCompanyMenu + "&no=" + mCompanyNo;
                    URL url = new URL(expressurl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    InputStream is = connection.getInputStream();
                    String result = StringUtils.InputStreamToString(is);
                    mExpressDate = mGson.fromJson(result, ExpressDate.class);
                    final List<ExpressDate.ResultBean.ListBean> list = mExpressDate.getResult().getList();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String time = "";
                            String remark = "";
                            for (int i = 0; i < list.size(); i++) {
                                String datetime = list.get(i).getDatetime();
                                String remark1 = list.get(i).getRemark();
                                TextView tx = new TextView(MainActivity.this);
                                tx.setText(datetime + "---" + remark1);
                                ll.addView(tx);
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

}
