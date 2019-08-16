package vicwang.demo.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import vicwang.demo.R;

public abstract class BasicActivity extends AppCompatActivity {
    private Toast mToast;

    private Dialog mDialog;

    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setActivityView();
        viewInit();
        setViewValue();
        setViewListener();
    }

    abstract void setActivityView();

    abstract void viewInit();

    abstract void setViewValue();

    abstract void setViewListener();

    /*
     * Normal Toast fun
     * */
    public void showToast(String str, boolean isLONG) {
        if (mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(mContext, str, isLONG ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        mToast.show();
    }

    /*
     * Custom error dialog
     * */
    public void openErrorDialog(String contentStr, final int errorType) {
        mDialog = new Dialog(mContext, R.style.PauseDialog);
        mDialog.setContentView(R.layout.cusomt_dailog);
        Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setCancelable(false);

        Button confirm = mDialog.findViewById(R.id.custom_dialog_btn_confirm);
        TextView txt = mDialog.findViewById(R.id.custom_dialog_txt_content);

        txt.setText(contentStr);

        View.OnClickListener permission = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.custom_dialog_btn_confirm:
                        mDialog.dismiss();
                        if (errorType == 1)
                            finish();
                        break;
                }
            }
        };
        confirm.setOnClickListener(permission);

        mDialog.show();
    }


}
