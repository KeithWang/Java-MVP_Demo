package vicwang.demo.mvp.presenter;

import vicwang.demo.adapter.item.MainHouseListItem;
import vicwang.demo.mvp.model.ApiCallBack;
import vicwang.demo.mvp.presenter.base.BaseModel;
import vicwang.demo.mvp.presenter.base.BasePresenter;
import vicwang.demo.mvp.presenter.base.BaseView;

public interface MainBridge {
    interface View extends BaseView<Presenter> {
        void onShowLoadingView(boolean isShow);

        void onShowErrorMsg(int errorType);

        void onHouseDataLoadSuccess(String result, Object o);

        void onAnimalDataLoadSuccess(String result, Object o, MainHouseListItem item);
    }

    interface Presenter extends BasePresenter<Model> {
        void initHouseData();

        void initAnimalData(MainHouseListItem item);
    }

    interface Model extends BaseModel {
        void getHouseData(ApiCallBack callback);

        void getAnimalData(String targetArea, ApiCallBack callback);
    }

}
