package com.example.wyf.expressdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_company)
    EditText etCompany;
    @BindView(R.id.et_menu)
    EditText etMenu;
    @BindView(R.id.ll_add_text)
    LinearLayout llAddText;
    private String key = "6a9c5c29e3b5d77c122e4163d06dacd2";
    private String express_url = "http://v.juhe.cn/exp/index";
    private final String company_url = "http://v.juhe.cn/exp/com?key=6a9c5c29e3b5d77c122e4163d06dacd2";
    //http://v.juhe.cn/exp/index?key=6a9c5c29e3b5d77c122e4163d06dacd2&com=yd&no=575677355677
    private ArrayList<ComInfo> comInfoList;
    private String mComanyNo;
    private String mCompanyName = "";
    private String mCompanyMenu = "";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_TOAST:
                    Toast.makeText(MainActivity.this, "数据初始化完毕", Toast.LENGTH_SHORT).show();
                    break;
                case LOAD_EXPRESS:
                    List<ExpressInfo.ResultBean.ListBean> list = mExpressInfo.getResult().getList();
                    llAddText.removeAllViews();
                    for (int i = 0; i < list.size(); i++) {
                        String datetime = list.get(i).getDatetime();
                        String remark = list.get(i).getRemark();
                        TextView textView = new TextView(MainActivity.this);
                        textView.setTextColor(Color.BLACK);
                        textView.setTextSize(18);
                        textView.setText(datetime + "  " + remark);
                        llAddText.addView(textView, 0);
                    }
                    break;
                case ERROR_EXPRESS:
                    llAddText.removeAllViews();
                    TextView textView = new TextView(MainActivity.this);
                    textView.setTextColor(Color.BLACK);
                    textView.setTextSize(18);
                    textView.setText((String) msg.obj);
                    llAddText.addView(textView, 0);
                    break;
            }
        }
    };
    private ArrayList<ExpressInfo> mExpressInfoList;
    private final int SHOW_TOAST = 100;
    private final int LOAD_EXPRESS = 101;
    private final int ERROR_EXPRESS = 102;
    private ExpressInfo mExpressInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getCompanyNo();
    }

    public void click(View v) {
        String company = etCompany.getText().toString().trim();
        String menu = etMenu.getText().toString().trim();
        if (!TextUtils.isEmpty(company) && !TextUtils.isEmpty(menu)) {
            checkCom(company, menu);
            mCompanyName = company;
            mCompanyMenu = menu;
        } else if (TextUtils.isEmpty(company)) {
            Toast.makeText(this, "请输入公司名称", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(menu)) {
            Toast.makeText(this, "请输入订单号", Toast.LENGTH_SHORT).show();
        }
    }

    private void getData(final String menu) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String path = express_url + "?key=" + key + "&com=" + mComanyNo + "&no=" + menu;
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    int responseCode = conn.getResponseCode();
                    if (responseCode == 200) {
                        InputStream inputStream = conn.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String line = null;
                        StringBuffer stringBuffer = new StringBuffer();
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuffer.append(line);
                        }
                        String s = stringBuffer.toString();
                        if (s.length() < 74) {
                            JSONObject object = new JSONObject(s);
                            String reason = object.getString("reason");
                            Message msg = Message.obtain();
                            msg.obj = reason;
                            msg.what = ERROR_EXPRESS;
                            mHandler.sendMessage(msg);
                        } else {
                            Gson gson = new Gson();
                            mExpressInfo = gson.fromJson(s, ExpressInfo.class);
                            mHandler.sendEmptyMessage(LOAD_EXPRESS);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void checkCom(String company, String menu) {
        mComanyNo = null;
        for (ComInfo info : comInfoList) {
            if (info.com.equals(company)) {
                mComanyNo = info.no;
            }
        }
        if (mComanyNo == null) {
            Toast.makeText(this, "快递公司输入有误", Toast.LENGTH_SHORT).show();
        } else {
            getData(menu);
        }
    }


    public List<ComInfo> getCompanyNo() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String path = company_url;
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    int responseCode = conn.getResponseCode();
                    if (responseCode == 200) {
                        InputStream inputStream = conn.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String line = null;
                        StringBuffer stringBuffer = new StringBuffer();
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuffer.append(line);
                        }
                        JSONArray jsonArray = new JSONObject(stringBuffer.toString()).getJSONArray("result");
                        comInfoList = new ArrayList<ComInfo>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = (JSONObject) jsonArray.get(i);
                            String com = object.getString("com");
                            String no = object.getString("no");
                            comInfoList.add(new ComInfo(com, no));
                        }
                        mHandler.sendEmptyMessage(SHOW_TOAST);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return null;
    }

    class ComInfo {
        public ComInfo(String com, String no) {
            this.com = com;
            this.no = no;
        }

        public String com;
        public String no;
    }
}
