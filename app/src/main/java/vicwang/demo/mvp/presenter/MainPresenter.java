package vicwang.demo.mvp.presenter;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import vicwang.demo.adapter.item.HouseListAnimalItem;
import vicwang.demo.adapter.item.MainHouseListItem;
import vicwang.demo.mvp.model.ApiCallBack;
import vicwang.demo.mvp.model.ApiRepository;

public class MainPresenter implements MainBridge.Presenter {
    private MainBridge.View mView;
    private ApiRepository mApiRepo;

    public MainPresenter(MainBridge.View view, ApiRepository repo) {
        this.mView = view;
        this.mApiRepo = repo;
    }

    @Override
    public void initHouseData() {
        mApiRepo.getHouseData(new ApiCallBack() {
            @Override
            public void onSuccess(final String resultMsg) {
                try {
                    final JSONArray array = new JSONObject(resultMsg).getJSONObject("result").getJSONArray("results");
                    final ArrayList<MainHouseListItem> listData = new ArrayList<>();
                    Gson gson = new Gson();
                    for (int i = 0; i < array.length(); i++) {
                        MainHouseListItem data = gson.fromJson(array.get(i).toString(), MainHouseListItem.class);
                        listData.add(data);
                    }

                    mView.onHouseDataLoadSuccess(array.toString(), listData);

                } catch (Exception e) {
                    mView.onShowErrorMsg(1);
                }
            }

            @Override
            public void onFailed(String errorMsg, Exception e) {
                mView.onShowErrorMsg(1);
            }
        });
    }

    @Override
    public void initAnimalData(final MainHouseListItem item) {
        mApiRepo.getAnimalData(item.getE_Name(), new ApiCallBack() {
            @Override
            public void onSuccess(final String resultMsg) {
                try {
                    final JSONArray array = new JSONObject(resultMsg).getJSONObject("result").getJSONArray("results");
                    final ArrayList<HouseListAnimalItem> listData = new ArrayList<>();
                    Gson gson = new Gson();
                    for (int i = 0; i < array.length(); i++) {
                        HouseListAnimalItem data = gson.fromJson(array.get(i).toString(), HouseListAnimalItem.class);
                        listData.add(data);
                    }
                    mView.onAnimalDataLoadSuccess(array.toString(), listData, item);
                } catch (Exception e) {
                    mView.onShowErrorMsg(1);
                }
            }

            @Override
            public void onFailed(String errorMsg, Exception e) {
                mView.onShowErrorMsg(0);
            }
        });
    }
}
