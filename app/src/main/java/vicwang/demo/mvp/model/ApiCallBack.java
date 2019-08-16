package vicwang.demo.mvp.model;

public interface ApiCallBack {
    void onSuccess(final String resultMsg);
    void onFailed(final String errorMsg, Exception e);
}
