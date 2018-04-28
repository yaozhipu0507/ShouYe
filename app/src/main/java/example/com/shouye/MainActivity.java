package example.com.shouye;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import example.com.shouye.adapter.MiaoShaAdapter;
import example.com.shouye.adapter.MyRvMiddleAdapter;
import example.com.shouye.adapter.TuiJianAdapter;
import example.com.shouye.iview.ShouYeJieKou;
import example.com.shouye.moudle.MyRvMiddleBean;
import example.com.shouye.moudle.ShouYeBean;
import example.com.shouye.present.ShouYePresenter;
import example.com.shouye.utils.GlideImageLoader;
import example.com.shouye.utils.LooperTextView;


public class MainActivity extends AppCompatActivity implements ShouYeJieKou.iView, View.OnClickListener {

    private ShouYePresenter shouYePresenter;
    private SimpleDateFormat format;
    private Handler handler = new Handler();
    private long recLen;
    private Banner mShouyebanner;
    private RecyclerView grildrecycleview;
    private TextView mShouyeTime;
    private RecyclerView miaosharecycleview;
    private TextView mShouyetuijian;
    private RecyclerView tuijianrecycleview;
    /**
     * 搜索笔记本
     */
    private EditText mSelectShop;
    private LooperTextView mLtv;
    private ImageView mErClick;
    private ImageView mInfoShowType;
    private ImageView mSyiv;
    private LinearLayout mRlms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        shouYePresenter = new ShouYePresenter(this);
        shouYePresenter.getBannerUrl(this);

        shouYePresenter.getMiddleViewUrl(this);

        mLtv.setTipList(generateTips());

    }

    /**
     * 中间的RecyclerView
     */
    @Override
    public void middleView(List<MyRvMiddleBean.DataBean> list) {
        GridLayoutManager manager = new GridLayoutManager(this, 2, OrientationHelper.HORIZONTAL,false);
        grildrecycleview.setLayoutManager(manager);
        MyRvMiddleAdapter adapter = new MyRvMiddleAdapter(MainActivity.this, list);
        grildrecycleview.setAdapter(adapter);
    }
    @Override
    public void shouYeRV(ShouYeBean shouYeBean) {
        /**
         * 设置秒杀倒计时
         */
        ShouYeBean.MiaoshaBean miaoshaBean = shouYeBean.getMiaosha();
        recLen = miaoshaBean.getTime();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                recLen -= 1000;
                format = new SimpleDateFormat("HH:mm:ss");
                final String str = format.format(recLen);
//                tv_time.setText(str);
//                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable, 100);
        /*
         * 设置到RecycleView
         */
        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        miaosharecycleview.setLayoutManager(manager);
        MiaoShaAdapter miaoShaAdapter = new MiaoShaAdapter(this, miaoshaBean.getList());
        miaosharecycleview.setAdapter(miaoShaAdapter);
        /*
         * 设置首页推荐
         */
        ShouYeBean.TuijianBean tuijian = shouYeBean.getTuijian();
        mShouyetuijian.setText(tuijian.getName());
        GridLayoutManager manager2 = new GridLayoutManager(this, 2, OrientationHelper.VERTICAL, false);
        tuijianrecycleview.setLayoutManager(manager2);
        TuiJianAdapter tuiJianAdapter = new TuiJianAdapter(this, tuijian.getList());
        tuijianrecycleview.setAdapter(tuiJianAdapter);
    }

    /*
     * 顶部的Banner以及banner的点击事件
     * @param list
     *
     */


    @Override
    public void shouBanner(final ShouYeBean shouYeBean) {
        final List<ShouYeBean.DataBean> data = shouYeBean.getData();
        List<String> images = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            images.add(data.get(i).getIcon());
        }

        mShouyebanner.setImages(images);
        mShouyebanner.setImageLoader(new GlideImageLoader());
        mShouyebanner.setBannerAnimation(Transformer.DepthPage);
        mShouyebanner.start();
        mShouyebanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (data.get(position).getUrl().length() < 1) {
                    return;
                }
                Toast.makeText(MainActivity.this, "准备跳转到WebView!!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ShouYeWebView.class);
                intent.putExtra("WebViewUrl", data.get(position).getUrl());
                MainActivity.this.startActivity(intent);
            }
        });
    }

    private void initView() {
        mErClick = (ImageView) findViewById(R.id.ErClick);
        mSelectShop = (EditText) findViewById(R.id.selectShop);
        mSelectShop.setEnabled(false);
        mInfoShowType = (ImageView) findViewById(R.id.info_show_type);
        mShouyebanner = (Banner) findViewById(R.id.shouyebanner);
        mSyiv = (ImageView) findViewById(R.id.syiv);
        grildrecycleview = (RecyclerView) findViewById(R.id.grildrecycleview);
        mShouyeTime = (TextView) findViewById(R.id.shouye_time);
        mLtv = (LooperTextView) findViewById(R.id.ltv);
        mRlms = (LinearLayout) findViewById(R.id.rlms);
        miaosharecycleview = (RecyclerView) findViewById(R.id.zhuyerecycleview2);
        mShouyetuijian = (TextView) findViewById(R.id.shouyetuijian);
        tuijianrecycleview = (RecyclerView) findViewById(R.id.zhuyerecycleview3);
        mErClick.setOnClickListener(this);
        mSelectShop.setOnClickListener(this);
        mInfoShowType.setOnClickListener(this);
        mShouyebanner.setOnClickListener(this);
        mSyiv.setOnClickListener(this);
        grildrecycleview.setOnClickListener(this);
        mShouyeTime.setOnClickListener(this);
        mLtv.setOnClickListener(this);
        mRlms.setOnClickListener(this);
        miaosharecycleview.setOnClickListener(this);
        mShouyetuijian.setOnClickListener(this);
        tuijianrecycleview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //扫一扫
            case R.id.ErClick:
                //ErClick();
                Toast.makeText(MainActivity.this, "扫一扫正在开发中......", Toast.LENGTH_SHORT).show();
                break;
            //搜索
            case R.id.selectShop:
                /*Intent intent = new Intent(MainActivity.this, SelectShopActivity.class);
                startActivity(intent);*/
                Toast.makeText(MainActivity.this, "搜索正在开发中......", Toast.LENGTH_SHORT).show();
                break;
            //消息
            case R.id.info_show_type:
                Toast.makeText(MainActivity.this, "消息正在集成中......", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    /**
     * 二维码扫描
     */
    /*private void ErClick() {
        //假如你要用的是fragment进行界面的跳转
        IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(ShouyeFragment.this).setCaptureActivity(CustomScanActivity.class);
        intentIntegrator
                .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                .setPrompt("将二维码/条码放入框内，即可自动扫描")//写那句提示的话
                .setOrientationLocked(false)//扫描方向固定
                .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                .initiateScan(); // 初始化扫描
    }*/

    /**
     * 首页跑马灯效果
     *
     * @return
     */
    private List<String> generateTips() {
        List<String> tips = new ArrayList<>();
        tips.add("AI就要掌控世界了？绝对没你想得那么快！");
        tips.add("衣服大一号,人就瘦一圈?");
        tips.add("闪瞎:全球最贵五辆摩托车");
        tips.add("一半受访者会被类人机器人吓跑!");
        tips.add("深度学习索引速度更快、占用空间更少");
        tips.add("资源| 谷歌开源TFGAN：轻量级生成对抗网络工具库?");
        tips.add("谷歌团队越狱苹果系统");
        return tips;
    }
    //获取扫描的结果
    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
            } else {
                Intent intent  = new Intent(MainActivity.this, BossActivity.class);
                intent.putExtra("page",2);
                intent.putExtra("ScanResult",intentResult.getContents());
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shouYePresenter.Dettach();
    }

}
