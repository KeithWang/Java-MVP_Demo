package vicwang.demo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vicwang.demo.R;
import vicwang.demo.activity.MainView;
import vicwang.demo.adapter.AnimalListAdapter;
import vicwang.demo.adapter.item.HouseListAnimalItem;
import vicwang.demo.adapter.item.MainHouseListItem;

public class HouseInfoFragment extends Fragment {

    public final static String FRAGMENT_HOUSE_INFO_TAG = "FRAGMENT_HOUSE_INFO_TAG";

    private static final String GET_PAGE_TOP_ITEM_KEY = "GET_PAGE_TOP_ITEM_KEY";
    private static final String GET_PAGE_ANIMAL_LIST_ITEM_KEY = "GET_PAGE_ANIMAL_LIST_ITEM_KEY";

    private View mView;
    private MainView mCallback;

    private RecyclerView wRecycleView;

    /*
     * Page data
     * */
    private ArrayList<HouseListAnimalItem> mAnimalData = new ArrayList<>();

    public static HouseInfoFragment newInstance(MainHouseListItem pageItem, ArrayList<HouseListAnimalItem> animalListData) {
        HouseInfoFragment fragment = new HouseInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GET_PAGE_TOP_ITEM_KEY, pageItem);
        bundle.putSerializable(GET_PAGE_ANIMAL_LIST_ITEM_KEY, animalListData);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (MainView) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_house_info, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setViewValue();
    }

    private void initView() {
        wRecycleView = mView.findViewById(R.id.house_main_recycleview);

    }

    private void setViewValue() {
        if (getArguments() != null) {
            MainHouseListItem mTopItem = (MainHouseListItem) getArguments().getSerializable(GET_PAGE_TOP_ITEM_KEY);
            mAnimalData = (ArrayList<HouseListAnimalItem>) getArguments().getSerializable(GET_PAGE_ANIMAL_LIST_ITEM_KEY);
            loadListItem(mTopItem, mAnimalData);
        } else {
            mCallback.onCallToast("Item Error!!", true);
            mCallback.onBackFragment();
        }

    }

    /*
     * Loading List Item fun
     * */
    private void loadListItem(MainHouseListItem topData, ArrayList<HouseListAnimalItem> plantData) {
        wRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        AnimalListAdapter mAdapter = new AnimalListAdapter(getContext(), topData, plantData);
        mAdapter.setClickListener(mRecycleClick);
        wRecycleView.setAdapter(mAdapter);
        wRecycleView.setItemAnimator(new DefaultItemAnimator());
    }

    /*
     * Animal list click listener
     * */
    private AnimalListAdapter.ItemClickListener mRecycleClick = new AnimalListAdapter.ItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Log.d("Test", mAnimalData.get(position - 1).getA_Name_Ch());
            mCallback.onOpenAnimalInfoPage(mAnimalData.get(position - 1));
        }
    };

}
