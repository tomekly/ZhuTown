package com.dongbang.yutian.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dongbang.yutian.R;
import com.dongbang.yutian.beans.ExpertEntity;
import com.dongbang.yutian.beans.MessageEntity;
import com.dongbang.yutian.intent.ImageCaptureManager;
import com.dongbang.yutian.intent.PhotoPickerActivity;
import com.dongbang.yutian.intent.PhotoPickerIntent;
import com.dongbang.yutian.intent.PhotoPreviewActivity;
import com.dongbang.yutian.intent.PhotoPreviewIntent;
import com.dongbang.yutian.intent.SelectModel;
import com.dongbang.yutian.retrofit.RetrofitFactory;
import com.dongbang.yutian.utils.LogUtils;
import com.dongbang.yutian.utils.ToastUtils;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 新增诊断页面
 */
public class ExpertAddQuestionActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.appbarlayout)
    AppBarLayout appbarlayout;
    @Bind(R.id.layout_coordinator)
    CoordinatorLayout layoutCoordinator;
    @Bind(R.id.titleAdd)
    TextInputEditText titleAdd;
    @Bind(R.id.expert_name)
    TextInputEditText expertName;
    @Bind(R.id.contentAdd)
    TextInputEditText contentAdd;
    @Bind(R.id.btnDiagnoseAdd)
    ImageButton btnDiagnoseAdd;
    @Bind(R.id.btnDiagnoseCancel)
    ImageButton btnDiagnoseCancel;
    @Bind(R.id.expertAddress)
    TextInputEditText expertAddress;
    @Bind(R.id.expert_type)
    TextView expertType;
    @Bind(R.id.layout_search_expert)
    LinearLayout layoutSearchExpert;
//    @Bind(R.id.item_iv1)
//    ImageView photo1;
//    @Bind(R.id.item_iv2)
//    ImageView photo2;


    private int primaryColor;
    private ExpertEntity entity;
    Call<MessageEntity> call;

    private static Bitmap bitmap;
    private TextView up_img;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_add_question);
        ButterKnife.bind(this);
        initData();
        primaryColor = getResources().getColor(R.color.colorPrimary);
        initToolbar(toolbar, "", primaryColor);
        tvTitle.setText("新增诊断");
        setStatusBar();
        //上传图片
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused);

        btnMuilt = (Button) findViewById(R.id.btnMuilt);
        btnSingle = (Button) findViewById(R.id.btnSingle);
        btnCarema = (Button) findViewById(R.id.btnCarema);
        gridView = (GridView) findViewById(R.id.gridView);
        up_img = (TextView) findViewById(R.id.up_img);
//        btn = (Button) findViewById(R.id.btn);

        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 3 ? 3 : cols;
        gridView.setNumColumns(cols);

        // Item Width
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int columnSpace = getResources().getDimensionPixelOffset(R.dimen.space_size);
        columnWidth = (screenWidth - columnSpace * (cols - 1)) / cols;

        // preview
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhotoPreviewIntent intent = new PhotoPreviewIntent(ExpertAddQuestionActivity.this);
                intent.setCurrentItem(position);
                intent.setPhotoPaths(imagePaths);
                startActivityForResult(intent, REQUEST_PREVIEW_CODE);
            }
        });

        btnSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPickerIntent intent = new PhotoPickerIntent(ExpertAddQuestionActivity.this);
                intent.setSelectModel(SelectModel.SINGLE);
                intent.setShowCarema(true);
//                intent.setImageConfig(null);
                startActivityForResult(intent, REQUEST_CAMERA_CODE);
            }
        });

        btnMuilt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPickerIntent intent = new PhotoPickerIntent(ExpertAddQuestionActivity.this);
                intent.setSelectModel(SelectModel.MULTI);
                intent.setShowCarema(true); // 是否显示拍照
                intent.setMaxTotal(9); // 最多选择照片数量，默认为9
                intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
//                ImageConfig config = new ImageConfig();
//                config.minHeight = 400;
//                config.minWidth = 400;
//                config.mimeType = new String[]{"image/jpeg", "image/png"};
//                config.minSize = 1 * 1024 * 1024; // 1Mb
//                intent.setImageConfig(config);
                startActivityForResult(intent, REQUEST_CAMERA_CODE);
            }
        });
//        上传按钮
        up_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPickerIntent intent = new PhotoPickerIntent(ExpertAddQuestionActivity.this);
                intent.setSelectModel(SelectModel.MULTI);
                intent.setShowCarema(true); // 是否显示拍照
                intent.setMaxTotal(9); // 最多选择照片数量，默认为9
                intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
//                ImageConfig config = new ImageConfig();
//                config.minHeight = 400;
//                config.minWidth = 400;
//                config.mimeType = new String[]{"image/jpeg", "image/png"};
//                config.minSize = 1 * 1024 * 1024; // 1Mb
//                intent.setImageConfig(config);
                startActivityForResult(intent, REQUEST_CAMERA_CODE);
            }
        });
        btnCarema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (captureManager == null) {
                        captureManager = new ImageCaptureManager(ExpertAddQuestionActivity.this);
                    }
                    Intent intent = captureManager.dispatchTakePictureIntent();
                    startActivityForResult(intent, ImageCaptureManager.REQUEST_TAKE_PHOTO);
                } catch (IOException e) {
//                        Toast.makeText(ExpertAddQuestionActivity.this, com.foamtrace.photopicker.R.string.msg_no_camera, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        entity = (ExpertEntity) getIntent().getExtras().getSerializable("expert");
        expertName.setText(entity.getName());
        expertType.setText(entity.getCatname());
        try {

        } catch (Exception e) {
        }
    }

    private void listener() {
//        photo1.setOnClickListener(this);
//        photo2.setOnClickListener(this);
    }

    public void initToolbar(Toolbar toolbar, CharSequence title, int bgColor) {

        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 请求   新增诊断
     */
    private void requestDiagnoseAdd() {
        String title = titleAdd.getText().toString().trim();
        final String content = contentAdd.getText().toString().trim();
        final String experttype = expertType.getText().toString().trim();
        final String expertAddr = expertAddress.getText().toString();
        if (title.length() != 0 || content.length() != 0) {
            Call<MessageEntity> call = RetrofitFactory.getRetrofitService(RetrofitFactory.BASE_URL).diagnoseAdd(entity.getId(), title, content, expertAddr, experttype);
            call.enqueue(new Callback<MessageEntity>() {
                @Override
                public void onResponse(Call<MessageEntity> call, Response<MessageEntity> response) {
                    LogUtils.d(TAG, response.body().toString());
                    MessageEntity entity = response.body();
                    if (entity.getCode() == 1) {
                        ExpertAddQuestionActivity.this.finish();
                        ToastUtils.showShort(context, "添加成功");
                    } else {
                        ToastUtils.showShort(context, "添加失败");
                    }
                }

                @Override
                public void onFailure(Call<MessageEntity> c, Throwable t) {
                    LogUtils.e(TAG, t.getMessage(), t);
                }
            });
        } else {
            ToastUtils.showShort(context, "标题或内容不能为空");
        }
    }

    @OnClick({R.id.btnDiagnoseAdd, R.id.btnDiagnoseCancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDiagnoseAdd:
                requestDiagnoseAdd();
                break;
            case R.id.btnDiagnoseCancel:
                this.finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (call != null) {
            call.cancel();
        }

    }


    private Button btnMuilt; // 多选
    private Button btnSingle; // 单选
    private Button btnCarema; // 拍照
    private Button btn;

    private static final int REQUEST_CAMERA_CODE = 11;
    private static final int REQUEST_PREVIEW_CODE = 22;
    private ArrayList<String> imagePaths = null;
    private ImageCaptureManager captureManager; // 相机拍照处理类

    private GridView gridView;
    private int columnWidth;
    private GridAdapter gridAdapter;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    loadAdpater(data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT));
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    loadAdpater(data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT));
                    break;
                // 调用相机拍照
                case ImageCaptureManager.REQUEST_TAKE_PHOTO:
                    if (captureManager.getCurrentPhotoPath() != null) {
                        captureManager.galleryAddPic();

                        ArrayList<String> paths = new ArrayList<>();
                        paths.add(captureManager.getCurrentPhotoPath());
                        loadAdpater(paths);
                    }
                    break;

            }
        }
    }

    private void loadAdpater(ArrayList<String> paths) {
        if (imagePaths == null) {
            imagePaths = new ArrayList<>();
        }
        imagePaths.clear();
        imagePaths.addAll(paths);

        try {
            JSONArray obj = new JSONArray(imagePaths);
            Log.e("--", obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (gridAdapter == null) {
            gridAdapter = new GridAdapter(imagePaths);
            gridView.setAdapter(gridAdapter);
        } else {
            gridAdapter.notifyDataSetChanged();
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ExpertAddQuestion Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class GridAdapter extends BaseAdapter {
        private ArrayList<String> listUrls;

        public GridAdapter(ArrayList<String> listUrls) {
            this.listUrls = listUrls;
        }

        @Override
        public int getCount() {
            return listUrls.size();
        }

        @Override
        public String getItem(int position) {
            return listUrls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_image, null);
                imageView = (ImageView) convertView.findViewById(R.id.imageView);
                convertView.setTag(imageView);
                // 重置ImageView宽高
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(columnWidth, columnWidth);
                imageView.setLayoutParams(params);
            } else {
                imageView = (ImageView) convertView.getTag();
            }
            Glide.with(ExpertAddQuestionActivity.this)
                    .load(new File(getItem(position)))
                    .placeholder(R.mipmap.default_error)
                    .error(R.mipmap.default_error)
                    .centerCrop()
                    .crossFade()
                    .into(imageView);
            return convertView;
        }
    }








}
