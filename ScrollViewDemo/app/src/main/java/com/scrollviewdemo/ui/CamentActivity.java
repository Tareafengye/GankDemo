package com.scrollviewdemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.scrollviewdemo.R;
import com.scrollviewdemo.base.BaseActivity;
import com.scrollviewdemo.view.CameraSurfaceView;
import com.scrollviewdemo.view.RectOnCamera;

public class CamentActivity extends BaseActivity implements RectOnCamera.IAutoFocus{
    private CameraSurfaceView mCameraSurfaceView;
    private RectOnCamera mRectOnCamera;
    private Button takePicBtn;
    private boolean isClicked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cament);
        mCameraSurfaceView = (CameraSurfaceView) findViewById(R.id.cameraSurfaceView);
        mRectOnCamera = (RectOnCamera) findViewById(R.id.rectOnCamera);
        takePicBtn= (Button) findViewById(R.id.takePic);
        mRectOnCamera.setIAutoFocus(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.takePic:
                mCameraSurfaceView.takePicture();
                break;
            default:
                break;
        }
    }

    @Override
    public void autoFocus() {
            mCameraSurfaceView.setAutoFocus();
    }
}
