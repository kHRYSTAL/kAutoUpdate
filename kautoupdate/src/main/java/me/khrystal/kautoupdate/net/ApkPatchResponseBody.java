package me.khrystal.kautoupdate.net;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/10/13
 * update time:
 * email: 723526676@qq.com
 */

public class ApkPatchResponseBody extends ResponseBody {

    private ResponseBody mResponseBody;
    private ProgressListener mProgressListener;
    private BufferedSource mBufferedSource;

    public ApkPatchResponseBody(ResponseBody responseBody, ProgressListener listener) {
        mResponseBody = responseBody;
        mProgressListener = listener;
        if (mResponseBody != null && mProgressListener!= null)
            mProgressListener.onPreExecute(contentLength());

    }


    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        if (mResponseBody != null)
            return mResponseBody.contentLength();
        return 0;
    }

    @Override
    public BufferedSource source() {
        if (mBufferedSource == null) {
            mBufferedSource = Okio.buffer(source(mResponseBody.source()));
        }
        return mBufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long currentBytes = 0L;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
//                if read != -1 means not start or not finish
                currentBytes += bytesRead != -1 ? bytesRead : 0;
                if (null != mProgressListener) {
                    mProgressListener.onUpdate(currentBytes, bytesRead == -1);
                }
                return bytesRead;
            }
        };
    }

    public interface ProgressListener {
        void onPreExecute(long contentLength);

        /**
         * this callback not work on uiThread
         * @param currentBytes
         * @param isComplete
         */
        void onUpdate(long currentBytes, boolean isComplete);

    }
}
