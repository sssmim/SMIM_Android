package org.techtown.smim.ui.dashboard;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.techtown.smim.R;
import org.techtown.smim.database.personal;
import org.techtown.smim.ui.home.HomeFragment;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.co.prnd.YouTubePlayerView;

public class GroupExercisePlay_Fragment extends Fragment implements AutoPermissionsListener {
    CameraSurfaceView cameraView;
    Date date1;
    Date date2;
    Long mem_num;
    String url;
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
    public List<personal> list3 = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = (View)inflater.inflate(R.layout.activity_group_exercise_play, container, false);

        YouTubePlayerView youtubePV = root.findViewById(R.id.youtube_player_view);

        Bundle bundle = getArguments();
        mem_num = bundle.getLong("mem_num");
        url = bundle.getString("url");

        youtubePV.play(url,null);

        FrameLayout previewFrame = root.findViewById(R.id.previewFrame);
        cameraView = new CameraSurfaceView(getContext());
        previewFrame.addView(cameraView);

        AutoPermissions.Companion.loadAllPermissions(getActivity(), 101);

        Button gstart = root.findViewById(R.id.gstart);
        Button gend = root.findViewById(R.id.gend);
        gstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Long now = System.currentTimeMillis();
                date1 = new Date(now);

            }
        });

        gend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long now = System.currentTimeMillis();
                date2 = new Date(now);
                Long diff = date2.getTime() - date1.getTime();
                String diffTime = dateFormat.format(diff);

                Long sec = diff / 1000;
                int sec1 = sec.intValue();

                RequestQueue requestQueue;
                Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
                Network network = new BasicNetwork(new HurlStack());
                requestQueue = new RequestQueue(cache, network);
                requestQueue.start();

                String url = "http://52.78.235.23:8080/personal";

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        // 한글깨짐 해결 코드
                        String changeString = new String();
                        try {
                            changeString = new String(response.getBytes("8859_1"),"utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        Type listType = new TypeToken<ArrayList<personal>>(){}.getType();
                        list3 = gson.fromJson(changeString, listType);

                        Integer index = 0;

                        for(int i=0; i<list3.size(); i++) {
                            if(list3.get(i).mem_num == mem_num) {
                                index = i;
                            }
                        }

                        Map map = new HashMap();
                        map.put("id", list3.get(index).id);
                        map.put("pwd", list3.get(index).pwd);
                        map.put("name", list3.get(index).name);
                        map.put("interest", list3.get(index).interest);
                        map.put("group_num",  list3.get(index).group_num);
                        map.put("point",sec1);
                        JSONObject params = new JSONObject(map);

                        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, url + "/" + list3.get(index).mem_num.toString(), params,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject obj) {

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                    }
                                }) {

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=UTF-8";
                            }
                        };
                        requestQueue.add(objectRequest);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                requestQueue.add(stringRequest);


                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                HomeFragment fragment1 = new HomeFragment();
                Bundle bundles = new Bundle();
                bundles.putInt("point", sec1);
                fragment1.setArguments(bundles);
                transaction.replace(R.id.container, fragment1);
                transaction.commit();


            }});




        return root;
    }

    public void takePicture() {
        cameraView.capture(new Camera.PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    String outUriStr = MediaStore.Images.Media.insertImage(
                            getActivity().getContentResolver(),
                            bitmap,
                            "Captured Image",
                            "Captured Image using Camera.");

                    if (outUriStr == null) {
                        return;
                    } else {
                        Uri outUri = Uri.parse(outUriStr);
                        getActivity().sendBroadcast(new Intent(
                                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, outUri));
                    }

                    camera.startPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder mHolder;
        private Camera camera = null;

        public CameraSurfaceView(Context context) {
            super(context);

            mHolder = getHolder();
            mHolder.addCallback(this);
        }

        public void surfaceCreated(SurfaceHolder holder) {
            camera = Camera.open();

            setCameraOrientation();

            try {
                camera.setPreviewDisplay(mHolder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            camera.startPreview();
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }

        public boolean capture(Camera.PictureCallback handler) {
            if (camera != null) {
                camera.takePicture(null, null, handler);
                return true;
            } else {
                return false;
            }
        }

        public void setCameraOrientation() {
            if (camera == null) {
                return;
            }

            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(0, info);
            info.facing = Camera.CameraInfo.CAMERA_FACING_FRONT;
            WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
            int rotation = manager.getDefaultDisplay().getRotation();

            int degrees = 0;
            switch (rotation) {
                case Surface.ROTATION_0: degrees = 0; break;
                case Surface.ROTATION_90: degrees = 90; break;
                case Surface.ROTATION_180: degrees = 180; break;
                case Surface.ROTATION_270: degrees = 270; break;
            }

            int result;
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                result = (info.orientation + degrees) % 360;
                result = (360 - result) % 360;
            } else {
                result = (info.orientation - degrees + 360) % 360;
            }

            camera.setDisplayOrientation(result);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        AutoPermissions.Companion.parsePermissions(getActivity(), requestCode, permissions, this);
    }

    @Override
    public void onDenied(int requestCode, String[] permissions) {
        //Toast.makeText(this, "permissions denied : " + permissions.length, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGranted(int requestCode, String[] permissions) {
       // Toast.makeText(this, "permissions granted : " + permissions.length, Toast.LENGTH_LONG).show();
    }
}
