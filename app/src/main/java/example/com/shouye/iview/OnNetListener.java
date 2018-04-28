package example.com.shouye.iview;

/**
 * Created by lenovo on 2018/4/27.
 */

public interface OnNetListener<T>{

    //成功回调
    void onSuccess(T t);
    //失败回调
    void onFailure(Exception e);

}
