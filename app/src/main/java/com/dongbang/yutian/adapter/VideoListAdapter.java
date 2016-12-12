package com.dongbang.yutian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.company.PlaySDK.IPlaySDK;
import com.dh.DpsdkCore.Enc_Channel_Info_Ex_t;
import com.dh.DpsdkCore.Get_RealStream_Info_t;
import com.dh.DpsdkCore.IDpsdkCore;
import com.dh.DpsdkCore.Return_Value_Info_t;
import com.dh.DpsdkCore.fMediaDataCallback;

import com.dongbang.yutian.R;
import com.dongbang.yutian.activity.VideoAccountLoginActivity;
import com.dongbang.yutian.common.AppConfig;
import com.dongbang.yutian.dhvideo.beans.TreeNode;
import com.dongbang.yutian.utils.LogUtils;
import com.dongbang.yutian.utils.ToastUtils;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by DongBang on 2016/9/28.
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoViewHolder> {
    private Context context;
    private List<TreeNode> datas;

    public VideoListAdapter(Context context, List<TreeNode> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public void onViewRecycled(VideoViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video_list_view,parent,false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        holder.tvTitle.setText(datas.get(position).getChannelInfo().getSzName());
        holder.itemView.setTag(datas.get(position));
        holder.imgStartOrEnd.setOnClickListener(new ItemChildListener(holder.surfaceView,position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public  class  ItemChildListener implements View.OnClickListener{
        private int position;
        private  SurfaceView surfaceView;
        public ItemChildListener(SurfaceView surfaceView,int position){
            this.position=position;
            this.surfaceView=surfaceView;
        }
        @Override
        public void onClick(View v) {
            onClickItemChildListener.onItemClickChild(v,surfaceView,position);
        }
    }


    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private SurfaceView surfaceView;
        private ImageView imgStartOrEnd;
        private TextView tvTitle;
        public VideoViewHolder(View itemView) {
            super(itemView);
            surfaceView= (SurfaceView) itemView.findViewById(R.id.item_video_list_surfaceview);
            imgStartOrEnd= (ImageView) itemView.findViewById(R.id.item_video_list_startOrEnd);
            tvTitle= (TextView) itemView.findViewById(R.id.item_video_list_title);
        }
    }

    private OnClickItemChildListener onClickItemChildListener;

    public  void setOnClickItemChildListener(OnClickItemChildListener onClickItemChildListener){
        this.onClickItemChildListener =onClickItemChildListener;
    }


    public interface  OnClickItemChildListener{
        void onItemClickChild(View view ,SurfaceView surfaceView,int position);
    }


    public void addAll(List<TreeNode> treeNodes){
        datas.addAll(treeNodes);
        notifyDataSetChanged();
    }
    private int m_nSeq = 0;
    private int mPDLLHandle = 0;
    private int port = 0;
    private int mTimeOut = 30 * 1000;

    public void playVideoByPosition(int position,final SurfaceView surfaceView){

        try {
            TreeNode tn = getItemByPosition(position);
            byte[] m_szCameraId = tn.getChannelInfo().getSzId().getBytes();
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
                }
            });
            if (!StartRealPlay(surfaceView)){
                LogUtils.e(TAG,"StartRealPlay Failed");
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
                ToastUtils.showShort(context, "success");
            } else {
                ToastUtils.showShort(context, "failed");
            }
        }catch (Exception e){
            LogUtils.e(TAG,e.getMessage());
        }
        notifyDataSetChanged();
    }

    public  boolean StartRealPlay(SurfaceView su){
        if (su==null){
            return  false;
        }

        boolean bOpenRet = IPlaySDK.PLAYOpenStream(port, null, 0, 1500 * 1024) != 0;
        if (bOpenRet){
            boolean bPlayRet =IPlaySDK.PLAYPlay(port,su)!=0;
            if (bPlayRet){
                boolean bSuccess =IPlaySDK.PLAYPlaySoundShare(port)!=0;
                LogUtils.i(TAG,"StartRealPlay2");
                if (!bSuccess){
                    IPlaySDK.PLAYStop(port);
                    IPlaySDK.PLAYCloseStream(port);
                    LogUtils.i(TAG,"StartRealPlay3");
                    return  false;
                }
            }else {
                IPlaySDK.PLAYCloseStream(port);
                LogUtils.i(TAG,"StartRealPlay4");
                return  false;
            }
        }else {
            LogUtils.i(TAG,"StartRealPlay5");
            return  false;
        }
        return  true;
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



    public TreeNode getItemByPosition(int postion){
        return datas.get(postion);
    }

}
