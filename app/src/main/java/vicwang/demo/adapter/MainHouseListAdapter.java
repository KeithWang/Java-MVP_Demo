package vicwang.demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import vicwang.demo.R;
import vicwang.demo.adapter.item.MainHouseListItem;
import vicwang.demo.util.ViewClick;

public class MainHouseListAdapter extends RecyclerView.Adapter<MainHouseListAdapter.ViewHolder> {


    private ArrayList<MainHouseListItem> mData;
    private LayoutInflater mInflater;
    private Context mContext;
    private ItemClickListener mClickListener;


    public MainHouseListAdapter(Context context, ArrayList<MainHouseListItem> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public MainHouseListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.main_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainHouseListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.wTxt_Title.setText(mData.get(i).getE_Name());
        viewHolder.wTxt_Content.setText(mData.get(i).getE_Info());
        viewHolder.wTxt_Sub_Content.setText(mData.get(i).getE_Memo().equals("") ? mContext.getString(R.string.main_house_no_rest_date) : mData.get(i).getE_Memo());
        viewHolder.wImg_pic.setBackground(null);

        Glide.with(mContext)
                .load(mData.get(i).getE_Pic_URL())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        viewHolder.wProcessBar.setVisibility(View.GONE);
                        viewHolder.wImg_pic.setBackground(mContext.getDrawable(android.R.drawable.ic_notification_clear_all));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        viewHolder.wProcessBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(viewHolder.wImg_pic);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView wTxt_Title;
        TextView wTxt_Content;
        TextView wTxt_Sub_Content;
        ImageView wImg_pic;
        ProgressBar wProcessBar;
        RelativeLayout wLay_Click_Area;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            wTxt_Title = itemView.findViewById(R.id.main_list_item_txt_title);
            wTxt_Content = itemView.findViewById(R.id.main_list_item_txt_content);
            wTxt_Sub_Content = itemView.findViewById(R.id.main_list_item_txt_sub_content);
            wImg_pic = itemView.findViewById(R.id.main_list_item_img_pic);
            wProcessBar = itemView.findViewById(R.id.main_list_item_loading_processbar);
            wLay_Click_Area = itemView.findViewById(R.id.main_list_item_lay_click_area);

            wLay_Click_Area.setOnClickListener(nCustomClickListener);
        }

        private ViewClick nCustomClickListener = new ViewClick() {

            @Override
            protected void CustomOnClick(View view) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(view, getAdapterPosition());
                }
            }
        };

    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
