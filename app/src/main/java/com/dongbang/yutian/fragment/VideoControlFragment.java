package com.dongbang.yutian.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.company.PlaySDK.IPlaySDK;
import com.dh.DpsdkCore.Enc_Channel_Info_Ex_t;
import com.dh.DpsdkCore.Get_RealStream_Info_t;
import com.dh.DpsdkCore.IDpsdkCore;
import com.dh.DpsdkCore.Login_Info_t;
import com.dh.DpsdkCore.Return_Value_Info_t;
import com.dh.DpsdkCore.fDPSDKStatusCallback;
import com.dh.DpsdkCore.fMediaDataCallback;
import com.dongbang.yutian.R;
import com.dongbang.yutian.activity.MainActivity;
import com.dongbang.yutian.activity.VideoAccountLoginActivity;
import com.dongbang.yutian.adapter.BaseAddrAdapter;
import com.dongbang.yutian.adapter.VideoListAdapter;
import com.dongbang.yutian.common.AppConfig;
import com.dongbang.yutian.common.AppContext;
import com.dongbang.yutian.dhvideo.GroupListGetTask;
import com.dongbang.yutian.dhvideo.GroupListManager;
import com.dongbang.yutian.dhvideo.beans.TreeNode;
import com.dongbang.yutian.utils.LogUtils;
import com.dongbang.yutian.utils.SharedPreferencesUtil;
import com.dongbang.yutian.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 视频监控页面
 * fxhhq@outlook.com
 * blog www.hayquan.cn
 */
public class VideoControlFragment extends BaseFragment implements View.OnClickListener {

    TextView tvVideoErrorMsg;
    Button btnVideoSignIn;
    RelativeLayout layoutVideoError;
    LinearLayout layoutVideo;
    ImageView imgVideoStart;
    AppCompatSpinner spinner;
    SurfaceView surfaceView;
    private ContentLoadingProgressBar progressBar;
    private BaseAddrAdapter addrAdapter;
    private TreeNode treeNode;
    private View rootView;
    private static String _ip;
    private static String username;
    private static String password;
    private static long m_loginHandle;

    private int m_nSeq = 0;
    private int mPDLLHandle = 0;
    private int port = 0;
    private int mTimeOut = 30 * 1000;
    private boolean isStopPlay = false;

    static Return_Value_Info_t m_ReValue = new Return_Value_Info_t();
    static int m_nLastError = 0;
    Login_Info_t loginInfo = new Login_Info_t();


    @Override
    protected void lazyload() {

    }

    public VideoControlFragment() {
    }

    public static VideoControlFragment newInstance(String param1, String param2) {
        VideoControlFragment fragment = new VideoControlFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_video_control, container, false);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        boolean isExist = SharedPreferencesUtil.getBoolean(getActivity(), "isExist", false);
        if (isExist) {
            _ip = SharedPreferencesUtil.getString(getActivity(), "_IP", "");
            port = SharedPreferencesUtil.getInt(getActivity(), "port", 0);
            username = SharedPreferencesUtil.getString(getActivity(), "username", "");
            password = SharedPreferencesUtil.getString(getActivity(), "password", "");
            VideoLogin();
        } else {
            hideTipsLayout(1);
        }
    }

    private void initView() {
        tvVideoErrorMsg = (TextView) rootView.findViewById(R.id.tv_video_error_msg);
        btnVideoSignIn = (Button) rootView.findViewById(R.id.btn_video_sign_in);
        layoutVideo = (LinearLayout) rootView.findViewById(R.id.layout_video);
        spinner = (AppCompatSpinner) rootView.findViewById(R.id.videoSpinner);
        imgVideoStart = (ImageView) rootView.findViewById(R.id.imgVideoStart);
        surfaceView = (SurfaceView) rootView.findViewById(R.id.videoSurfaceView);
        progressBar = (ContentLoadingProgressBar) rootView.findViewById(R.id.progressBar);
        imgVideoStart.setOnClickListener(this);
        tvVideoErrorMsg.setOnClickListener(this);
        btnVideoSignIn.setOnClickListener(this);
        surfaceView.setOnClickListener(this);
        layoutVideoError = (RelativeLayout) rootView.findViewById(R.id.layout_video_error);
        mGroupListManager = GroupListManager.getInstance();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                treeNode = (TreeNode) addrAdapter.getItem(position);
                if (isStopPlay) {
                    videoStop();
                    videoPlay();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_video_error_msg:
                break;
            case R.id.btn_video_sign_in:
                ToastUtils.UIHelper.jumpActivityBackResult(getActivity(), VideoAccountLoginActivity.class, AppConfig.REQUEST_CODE_SIGN_IN);
                break;
            case R.id.imgVideoStart:
                videoPlay();
                break;
            case R.id.videoSurfaceView:
                videoStop();
                break;
        }
    }

    /**
     * 监控停止播放
     */
    public void videoStop() {
        progressBar.setVisibility(View.VISIBLE);
        int ret = IDpsdkCore.DPSDK_CloseRealStreamBySeq(mPDLLHandle, m_nSeq, mTimeOut);
        if (ret == 0) {
            imgVideoStart.setVisibility(View.VISIBLE);
            Log.e(TAG, "DPSDK_CloseRealStreamByCameraId success!");
        } else {
            Log.e(TAG, "DPSDK_CloseRealStreamByCameraId failed! ret = " + ret);
        }
        stopRealPlay();
        LogUtils.d(TAG, "videoStop");
    }

    public void stopRealPlay() {
        try {
            IPlaySDK.PLAYStopSoundShare(port);
            IPlaySDK.PLAYStop(port);
            IPlaySDK.PLAYCloseStream(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        progressBar.setVisibility(View.GONE);
        LogUtils.d(TAG, "stopRealPlay");
    }


    /**
     * 监控播放
     */
    private void videoPlay() {
        progressBar.setVisibility(View.VISIBLE);
        imgVideoStart.setVisibility(View.GONE);
        if (treeNode == null) {
            LogUtils.e(TAG, "没有视频服务");
            return;
        }
        try {
            byte[] m_szCameraId = treeNode.getChannelInfo().getSzId().getBytes();
            mPDLLHandle = AppConfig.RETURN_VALUE;
            port = IPlaySDK.PLAYGetFreePort();

            SurfaceHolder holder = surfaceView.getHolder();
            holder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    IPlaySDK.InitSurface(port, surfaceView);
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    holder.removeCallback(this);
                }
            });
            if (!StartRealPlay(surfaceView)) {
                LogUtils.e(TAG, "StartRealPlay Failed");
            }
            Return_Value_Info_t retVal = new Return_Value_Info_t();
            Get_RealStream_Info_t getRealStreamInfoT = new Get_RealStream_Info_t();
            System.arraycopy(m_szCameraId, 0, getRealStreamInfoT.szCameraId, 0, m_szCameraId.length);

            getRealStreamInfoT.nMediaType = 1;
            getRealStreamInfoT.nRight = 0;
            getRealStreamInfoT.nStreamType = 1;
            getRealStreamInfoT.nTransType = 1;
            Enc_Channel_Info_Ex_t channel_info_ex_t = new Enc_Channel_Info_Ex_t();
            IDpsdkCore.DPSDK_GetChannelInfoById(mPDLLHandle, m_szCameraId, channel_info_ex_t);
            int ret = IDpsdkCore.DPSDK_GetRealStream(mPDLLHandle, retVal, getRealStreamInfoT, fm, mTimeOut);

            if (ret == 0) {
                m_nSeq = retVal.nReturnValue;
                ToastUtils.showShort(getActivity(), "success");
                progressBar.setVisibility(View.GONE);
            } else {
                ToastUtils.showShort(getActivity(), "failed");
                progressBar.setVisibility(View.GONE);
                imgVideoStart.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
        }
        isStopPlay = true;

    }

    final fMediaDataCallback fm = new fMediaDataCallback() {
        @Override
        public void invoke(int i, int i1, int i2, byte[] bytes, int i3, byte[] bytes1, int i4) {
            int ret = IPlaySDK.PLAYInputData(port, bytes1, i4);
            if (ret == 1) {
                LogUtils.d(TAG, "playing success");
            } else {
                LogUtils.d(TAG, "playing failed");
            }
        }
    };


    public boolean StartRealPlay(SurfaceView su) {
        if (su == null) {
            return false;
        }

        boolean bOpenRet = IPlaySDK.PLAYOpenStream(port, null, 0, 1500 * 1024) != 0;
        if (bOpenRet) {
            boolean bPlayRet = IPlaySDK.PLAYPlay(port, su) != 0;
            if (bPlayRet) {
                boolean bSuccess = IPlaySDK.PLAYPlaySoundShare(port) != 0;
                LogUtils.i(TAG, "StartRealPlay2");
                if (!bSuccess) {
                    IPlaySDK.PLAYStop(port);
                    IPlaySDK.PLAYCloseStream(port);
                    LogUtils.i(TAG, "StartRealPlay3");
                    return false;
                }
            } else {
                IPlaySDK.PLAYCloseStream(port);
                LogUtils.i(TAG, "StartRealPlay4");
                return false;
            }
        } else {
            LogUtils.i(TAG, "StartRealPlay5");
            return false;
        }
        return true;
    }

    private void VideoLogin() {
        int nType = 1;
        m_nLastError = IDpsdkCore.DPSDK_Create(nType, m_ReValue);
        IDpsdkCore.DPSDK_SetDPSDKStatusCallback(m_ReValue.nReturnValue, new fDPSDKStatusCallback() {
            @Override
            public void invoke(int nPDLLHandle, int nStatus) {
                Log.v("fDPSDKStatusCallback", "nStatus = " + nStatus);
            }
        });

        loginInfo.szIp = _ip.getBytes();
        loginInfo.nPort = port;
        loginInfo.szUsername = username.getBytes();
        loginInfo.szPassword = password.getBytes();
        loginInfo.nProtocol = 2;
        VideoAccountSignIn signIn = new VideoAccountSignIn();
        signIn.execute();
    }

    public class VideoAccountSignIn extends AsyncTask<Void, Integer, Integer> {
        @Override
        protected Integer doInBackground(Void... params) {
            if (m_loginHandle != 0) {
                IDpsdkCore.DPSDK_Logout(m_ReValue.nReturnValue, 30000);
                m_loginHandle = 0;
            }

            return IDpsdkCore.DPSDK_Login(m_ReValue.nReturnValue, loginInfo, 30000);
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == 0) {
                Log.d("DpsdkLogin success:", result + "");
                IDpsdkCore.DPSDK_SetCompressType(m_ReValue.nReturnValue, 0);
                m_loginHandle = 1;
                AppConfig.RETURN_VALUE = m_ReValue.nReturnValue;
                hideTipsLayout(result);
                getNodesData();
            } else {

                Toast.makeText(getActivity(), "login failed" + result, Toast.LENGTH_SHORT).show();
                m_loginHandle = 0;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            int result = data.getExtras().getInt("result");
            hideTipsLayout(result);
            getNodesData();
        }
    }

    TreeNode root;
    private GroupListManager mGroupListManager = null;

    /**
     * 获取节点数据
     */
    private void getNodesData() {
        root = mGroupListManager.getRootNode();
        if (root == null) {
            if (mGroupListManager.getTask() == null) {
                mGroupListManager.startGroupListGetTask();
                mGroupListManager.setGroupListGetListener(iOnSuccessListener);
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int obj = (int) msg.obj;
            switch (obj) {
                case 1:
                    addrAdapter = new BaseAddrAdapter(AppContext.getInstance().getContext(), countVideoNumber(root));
                    LogUtils.e(TAG, "spinner   " + spinner);
                    spinner.setAdapter(addrAdapter);
                    break;
            }
        }
    };


    GroupListGetTask.IOnSuccessListener iOnSuccessListener = new GroupListGetTask.IOnSuccessListener() {
        @Override
        public void onSuccess(boolean success, int errCode) {
            root = mGroupListManager.getRootNode();

            Message msg = new Message();
            msg.obj = 1;
            handler.sendMessage(msg);

        }
    };

    /**
     * 隐藏提示布局
     *
     * @param status
     */
    private void hideTipsLayout(int status) {
        if (layoutVideoError != null) {
            if (status == 0) {

                layoutVideoError.setVisibility(View.GONE);
                layoutVideo.setVisibility(View.VISIBLE);

            } else {
                layoutVideoError.setVisibility(View.VISIBLE);
                layoutVideo.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 统计视频个数
     */
    private List<TreeNode> countVideoNumber(TreeNode treeNode) {
        List<TreeNode> datas = new ArrayList<>();
        for (int i = 0; i < treeNode.getChildren().size(); i++) {
            List<TreeNode> subChildren = treeNode.getChildren().get(i).getChildren();
            for (int j = 0; j < subChildren.size(); j++) {
                List<TreeNode> subSubChildren = subChildren.get(j).getChildren();
                for (int k = 0; k < subSubChildren.size(); k++) {
                    TreeNode treeNode1 = subSubChildren.get(k);
                    if (treeNode1.getChannelInfo().getState() == 1) {
                        datas.add(treeNode1);
                    }
                }
            }
        }
        return datas;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
