package com.dongbang.yutian.activity;

import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.company.PlaySDK.IPlaySDK;
import com.dh.DpsdkCore.Enc_Channel_Info_Ex_t;
import com.dh.DpsdkCore.Get_RealStream_Info_t;
import com.dh.DpsdkCore.IDpsdkCore;
import com.dh.DpsdkCore.Return_Value_Info_t;
import com.dh.DpsdkCore.fMediaDataCallback;
import com.dongbang.yutian.R;
import com.dongbang.yutian.common.AppConfig;
import com.dongbang.yutian.utils.LogUtils;
import com.dongbang.yutian.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;


public class VideoPlayInfoActivity extends BaseActivity {

    @Bind(R.id.videoInfoSurfaceView)
    SurfaceView videoInfoSurfaceView;
    @Bind(R.id.btnstart)
    Button btnstart;
    @Bind(R.id.btnEnd)
    Button btnEnd;

    private byte[] m_szCameraId=null;
    private int m_pDLLHandle=0;
    private int m_nPort=0;
    private int m_nSeq=0;
    private int mTimeOut=30*1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play_info);
        ButterKnife.bind(this);
        m_pDLLHandle= AppConfig.RETURN_VALUE;
        m_szCameraId=getIntent().getStringExtra("channelId").getBytes();
        m_nPort= IPlaySDK.PLAYGetFreePort();
        SurfaceHolder holder =videoInfoSurfaceView.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                IPlaySDK.InitSurface(m_nPort,videoInfoSurfaceView);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {}
        });

    }

    final fMediaDataCallback fm=new fMediaDataCallback() {
        @Override
        public void invoke(int i, int i1, int i2, byte[] bytes, int i3, byte[] bytes1, int i4) {

            int ret =IPlaySDK.PLAYInputData(m_nPort,bytes1,i4);
            if (ret ==1){
                LogUtils.d(TAG,"playing success"+i1+"package size="+i4);
            }else {
                LogUtils.e(TAG,"palaying failed "+i1 +"package size="+i4);
            }
        }
    };


    public  boolean StartRealPlay(){
        if (videoInfoSurfaceView==null){
            return  false;
        }

        boolean bOpenRet = IPlaySDK.PLAYOpenStream(m_nPort, null, 0, 1500 * 1024) != 0;
        if (bOpenRet){
            boolean bPlayRet =IPlaySDK.PLAYPlay(m_nPort,videoInfoSurfaceView)!=0;
            if (bPlayRet){
                boolean bSuccess =IPlaySDK.PLAYPlaySoundShare(m_nPort)!=0;
                LogUtils.i(TAG,"StartRealPlay2");
                if (!bSuccess){
                    IPlaySDK.PLAYStop(m_nPort);
                    IPlaySDK.PLAYCloseStream(m_nPort);
                    LogUtils.i(TAG,"StartRealPlay3");
                    return  false;
                }
            }else {
                IPlaySDK.PLAYCloseStream(m_nPort);
                LogUtils.i(TAG,"StartRealPlay4");
                 return  false;
            }
        }else {
            LogUtils.i(TAG,"StartRealPlay5");
            return  false;
        }
        return  true;
    }



    public void onClick(View view) {
          int id=view.getId();
        if (id==R.id.btnstart){
                openVideo();
        }
        if (id ==R.id.btnEnd){

        }
    }

    /**
     * 打开视频
     */
    private void openVideo(){
        if (!StartRealPlay()){
            LogUtils.e(TAG,"StartRealPlay Failed");
        }
        try {
            Return_Value_Info_t retVal=new Return_Value_Info_t();
            Get_RealStream_Info_t getRealStreamInfo=new Get_RealStream_Info_t();
            System.arraycopy(m_szCameraId,0,getRealStreamInfo.szCameraId,0,m_szCameraId.length);
            getRealStreamInfo.nMediaType=1;
            getRealStreamInfo.nRight=0;
            getRealStreamInfo.nStreamType=1;
            getRealStreamInfo.nTransType=1;
            Enc_Channel_Info_Ex_t channel_info_ex_t =new Enc_Channel_Info_Ex_t();
            IDpsdkCore.DPSDK_GetChannelInfoById(m_pDLLHandle,m_szCameraId, channel_info_ex_t);
            int ret =IDpsdkCore.DPSDK_GetRealStream(m_pDLLHandle,retVal,getRealStreamInfo,fm,mTimeOut);
            if (ret==0){
                m_nSeq=retVal.nReturnValue;
                ToastUtils.showShort(context,"success");
            }else {
                ToastUtils.showShort(context,"failed");
            }
        }catch ( Exception e){
            LogUtils.e(TAG,"xss"+e.getMessage());
        }
    }
}
