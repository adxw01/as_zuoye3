package com.example.test5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpActivity1 extends AppCompatActivity {

    TextView textView1;
    Button button1, button2, button3,button4,button5,button6;
    ImageView imageView1;

    VideoView videoView1;


    String result;

    class Mytast extends AsyncTask<String, String, String> {
        // 在后台线程中执行的任务，在这里执行网络请求，获取百度网站的内容

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("https://www.baidu.com");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream stream = connection.getInputStream();
//原生方法解析图片
/*                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                imageView1.setImageBitmap(bitmap);*/

                InputStreamReader reader = new InputStreamReader(stream);
                BufferedReader reader1 = new BufferedReader(reader);
                result = reader1.readLine();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            // 在 UI 线程中执行的任务，在这里更新 TextView 显示获取到的内容
            //s = "abc";
            textView1.setText(s);

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http1);

        videoView1 = findViewById(R.id.videoView);
        imageView1 = findViewById(R.id.http1_imageView1);
        textView1 = findViewById(R.id.http1_textView1);
        button1 = findViewById(R.id.http1_button1);
        button2 = findViewById(R.id.http1_button2);
        button3 = findViewById(R.id.http1_button3);
        button4 = findViewById(R.id.http1_button4);
        button5 = findViewById(R.id.http1_button5);
        button6 = findViewById(R.id.http1_button6);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Mytast().execute();

            }
        });

        Context context = this;
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 使用 Glide 库加载从指定 URL 加载的图片到 imageView1 中
                // Glide 是一个图片加载库，用于异步加载图片并将其设置到 ImageView 中
                Glide.with(context) // 使用当前上下文 Context
                        .load("https://www.httpbin.org/image/png") // 加载指定 URL 的图片
                        .into(imageView1); // 将加载的图片设置到 imageView1 中显示
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testget();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testpost();
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testpng();
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playOnlineVideo();
            }
        });

    }



    public void testget() {
        // 创建一个 OkHttpClient 实例
        OkHttpClient okHttpClient = new OkHttpClient();

        // 创建一个 GET 请求，并添加自定义的 header（在此例中添加了一个名为 "key" 值为 "value" 的 header）
        Request request = new Request.Builder()
                .url("https://www.httpbin.org/get?id=111") // 设置请求的 URL
                .addHeader("key", "value") // 添加自定义的 header
                .get() // 声明这是一个 GET 请求
                .build();

        // 使用 OkHttpClient 发起异步请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 在请求失败时执行的操作，例如处理连接问题或异常
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 当请求成功时，执行的操作

                // 打印响应的主体内容到日志中
                // 这里的 response.body().toString() 返回的是响应主体的字符串表示形式，可用于调试或日志记录
                Log.d("xr", response.body().toString());
            }
        });
    }

    public void testpost() {
// 创建一个 OkHttpClient 实例
        OkHttpClient okHttpClient = new OkHttpClient();

        // 准备一个字符串，并尝试将其转换为整数，但没有使用这个整数
        String str1 = "100";
        Integer.parseInt(str1);

        // 设置请求的媒体类型为 JSON，并创建一个请求体
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, "{}");

        // 创建一个 POST 请求，并将上面创建的请求体附加到请求中
        Request request = new Request.Builder()
                .url("https://www.httpbin.org/post") // 设置请求的 URL
                .post(requestBody) // 声明这是一个 POST 请求，并将请求体添加到请求中
                .build();

        // 使用 OkHttpClient 发起异步请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 在请求失败时执行的操作，例如处理连接问题或异常
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 当请求成功时，执行的操作

                // 打印响应的主体内容到日志中
                // 这里的 response.body().toString() 返回的是响应主体的字符串表示形式，可用于调试或日志记录
                Log.d("xr", response.body().toString());
            }
        });
    }

    public void testpng() {
        // 创建一个 OkHttpClient 实例
        OkHttpClient okHttpClient = new OkHttpClient();


// 创建一个 MediaType 对象，指定内容类型为 image/png，字符集为 UTF-8
        MediaType mediaType = MediaType.parse("image/png; charset=utf-8");

// 创建一个请求体 RequestBody 实例，使用指定的媒体类型并传输一个空的 JSON 对象
        RequestBody requestBody = RequestBody.create(mediaType, "{}");


        // 创建一个 GET 请求
        Request request = new Request.Builder()
                .url("https://www.httpbin.org/image/png") // 设置请求的 URL
                .get() // 声明这是一个 GET 请求
                .build();

        // 使用 OkHttpClient 发起异步请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 在请求失败时执行的操作，例如处理连接问题或异常
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 当请求成功时，执行的操作

                // 解码响应的流为 Bitmap 图像
                final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());

                // 在 UI 线程上更新 ImageView 的图像
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 设置 ImageView 的 Bitmap 为获取到的图像
                        imageView1.setImageBitmap(bitmap);
                    }
                });
            }
        });

    }

    private void playOnlineVideo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 创建 URL 对象
                    URL url = new URL("https://v-cdn.zjol.com.cn/276982.mp4");

                    // 建立与 URL 的连接并获取输入流
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();

                    // 在 UI 线程上设置视频 URI 并开始播放
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            videoView1.setVideoURI(Uri.parse("https://v-cdn.zjol.com.cn/276982.mp4"));
                            videoView1.requestFocus();
                            videoView1.start();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

