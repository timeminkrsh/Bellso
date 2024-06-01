package com.taprocycle.service.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.taprocycle.service.R;
import com.taprocycle.service.test.model.EventModel;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MailViewHolder> {

    List<EventModel> eventModelList;
    private Context mContext;
    AlertDialog dialog;
    ProgressDialog progressDialog;
    public static final String api_key1 = "AIzaSyAv-";
    public static final String api_key2 = "qKeV7FFMGgk8cb5QhPA6SA7TRu7HKo";
    public static final String API_KEY = "AIzaSyB9DGUkR3dgluNICA7hi3NW-ZUWrIA8jyE";
    String videourl;

    public VideoAdapter(Context mContext, List<EventModel> eventModelList) {
        this.mContext = mContext;
        this.eventModelList = eventModelList;
    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MailViewHolder holder, final int position) {
        final EventModel model = eventModelList.get(position);
      /*  progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Updating.....");*/
        String img = eventModelList.get(position).getLink();
        holder.view.requestFocus();
        holder.view.getSettings().setLightTouchEnabled(true);
        holder.view.getSettings().setJavaScriptEnabled(true);
        holder.view.getSettings().setGeolocationEnabled(true);
        holder.view.getSettings().setMediaPlaybackRequiresUserGesture(true);
        holder.view.setSoundEffectsEnabled(false);
        holder.view.loadUrl(model.getLink());
        holder.view.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    // progressDialog.show();
                }
                if (progress == 100) {
                    //  progressDialog.dismiss();
                }
            }
        });

        holder.view.setWebViewClient(new WebViewClient());
//        holder.event_title.setText(model.getTitle());
        videourl = "https://www.youtube.com/embed/" +  eventModelList.get(position).getLink() + "?rel=0";
        String video = "<iframe width=\"100%\" height=\"100%\"" + "src=\"" + videourl + "\"" + "frameborder=\"0\" allowfullscreen></iframe>";
        holder.view.loadData(video, "text/html", "utf-8");

    }
    private void emulateClick(final WebView webview) {
        long delta = 100;
        long downTime = SystemClock.uptimeMillis();
        float x = webview.getLeft() + webview.getWidth()/2; //in the middle of the webview
        float y = webview.getTop() + webview.getHeight()/2;

        final MotionEvent downEvent = MotionEvent.obtain( downTime, downTime + delta, MotionEvent.ACTION_DOWN, x, y, 0 );
        // change the position of touch event, otherwise, it'll show the menu.
        final MotionEvent upEvent = MotionEvent.obtain( downTime, downTime+ delta, MotionEvent.ACTION_UP, x+10, y+10, 0 );

        webview.post(new Runnable() {
            @Override
            public void run() {
                if (webview != null) {
                    webview.dispatchTouchEvent(downEvent);
                    webview.dispatchTouchEvent(upEvent);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return eventModelList.size();
    }


    public class MailViewHolder extends RecyclerView.ViewHolder {

        WebView view;
        TextView event_title;

        public MailViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.webview);
            //event_title=itemView.findViewById(R.id.event_title);

        }
    }
}

