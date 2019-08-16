package vicwang.demo.activity;

import vicwang.demo.adapter.item.HouseListAnimalItem;

public interface MainView {
    void onCallToast(String str, boolean Long);

    void onBackFragment();

    void onOpenAnimalInfoPage(HouseListAnimalItem item);
}
