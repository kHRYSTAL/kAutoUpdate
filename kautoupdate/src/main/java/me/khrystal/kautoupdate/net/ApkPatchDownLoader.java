package me.khrystal.kautoupdate.net;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import me.khrystal.kautoupdate.BuildConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/10/13
 * update time:
 * email: 723526676@qq.com
 */

/**
 * Apk and Patch downloader
 * support break-points
 */
public class ApkPatchDownLoader {

    private String mUrl;
    private OkHttpClient mOkHttpClient;
    private File mDestFile;
    private Call mCall;
    private ApkPatchResponseBody.ProgressListener mProgressListener;


    public ApkPatchDownLoader(String url, File destFile,
                              ApkPatchResponseBody.ProgressListener progressListener) {
        mUrl = url;
        mDestFile = destFile;
        mProgressListener = progressListener;

//      middleware
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder()
                        .body(new ApkPatchResponseBody(originalResponse.body(), mProgressListener))
                        .build();
            }
        };

        mOkHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .build();
    }

    /**
     * every times download need create new Call
     * @param startPoints
     * @return
     */
    private Call newCallInstance(long startPoints) {
        Request request = new Request.Builder()
                .url(mUrl)
//                break-points use param
                .header("RANGE", "bytes=" + startPoints + "-")
                .build();
        return mOkHttpClient.newCall(request);
    }

    public void execute(final long startsPoint) {
        mCall = newCallInstance(startsPoint);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (BuildConfig.DEBUG)
                    Log.d("kAutoUpdate","Call enqueue error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                saveCurrentFile(response, startsPoint);
            }
        });
    }

    public void pause() {
        if(mCall != null){
            mCall.cancel();
            mCall = null;
        }
    }

    public void saveCurrentFile(Response response, long startsPoint) {
        ResponseBody body = response.body();
        InputStream in = body.byteStream();
//        use nio prevent blockage
        FileChannel channelOut = null;
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(mDestFile, "rwd");
            channelOut = randomAccessFile.getChannel();
            MappedByteBuffer mappedByteBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE
                    , startsPoint, body.contentLength());
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                mappedByteBuffer.put(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                if (channelOut != null)
                    channelOut.close();
                if (randomAccessFile != null)
                    randomAccessFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
