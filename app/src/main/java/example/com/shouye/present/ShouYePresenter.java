package example.com.shouye.present;

import android.content.Context;
import android.widget.Toast;

import example.com.shouye.iview.OnNetListener;
import example.com.shouye.moudle.MyRvMiddleBean;
import example.com.shouye.moudle.ShouYeBean;
import example.com.shouye.iview.ShouYeJieKou;
import example.com.shouye.moudle.ShouYeModel;

/**
 * Created by lenovo on 2018/4/27.
 */

public class ShouYePresenter {
    private ShouYeJieKou.IShouYeModel iShouYeModel;
    private ShouYeJieKou.iView iView;

    public ShouYePresenter(ShouYeJieKou.iView iView) {
        this.iShouYeModel = new ShouYeModel();
        this.iView = iView;
    }
    public void getBannerUrl(final Context context){
        iShouYeModel.getBannerUrl(context,new OnNetListener<ShouYeBean>() {
            @Override
            public void onSuccess(ShouYeBean shouYeBean) {
                if (iView != null) {
                    iView.shouBanner(shouYeBean);
                    iView.shouYeRV(shouYeBean);
                }
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(context, "对于请求失败这事,就不劳揭穿了!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getMiddleViewUrl(final Context context){
        iShouYeModel.getMiddleViewUrl(context,new OnNetListener<MyRvMiddleBean>() {
            @Override
            public void onSuccess(MyRvMiddleBean myRvMiddleBean) {
                iView.middleView(myRvMiddleBean.getData());
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(context, "对于请求失败这事,就不劳揭穿了!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 销毁
     */
    public void Dettach() {
        iView = null;
    }
}
