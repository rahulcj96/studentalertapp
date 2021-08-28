package com.studentalert.cses6;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;


public class subjectCardAdapter extends RecyclerView.Adapter<subjectCardAdapter.ViewHolder> {

    private final Context context;
    private final List<subjectContent> subjectData;
    public subjectCardAdapter(List<subjectContent> subjectData, Context context){
        super();
        //Getting all the notification
        this.subjectData = subjectData;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_subject, parent, false);
            return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final subjectContent subjectContent =  subjectData.get(position);
        holder.Name.setText(subjectContent.getName());
        holder.Description.setText(subjectContent.getDescription());
        holder.Type.setText(subjectContent.getType());
        holder.Uploader.setText(subjectContent.getAuthor());
        holder.Date.setText(subjectContent.getDate());
        holder.Description.setMaxLines(5);
        if(Global.Orientation.equals("Landscape")) {
            holder.Name.setTextSize(20);
            holder.Description.setTextSize(13);
        }
        final String DOWNLOAD_MESSAGE = "Download  " + subjectContent.getName() +" of Subject " + Global.subject + " Notes from Here :  " + holder.downloadFileurl + "\n And to Download this App : \n\n " + Global.AppShare;
        holder.downloadFileurl = subjectContent.getDownload_link();
        holder.downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        holder.type = holder.downloadFileurl.substring(holder.downloadFileurl.indexOf("Downloads/") + 10, holder.downloadFileurl.length()).replace("%20","");
        holder.path=context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/"+holder.type;
        File applicationFile = new File (holder.path);
        if(applicationFile.exists()) {
            holder.download.setText(" Open ");
        }
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add Content To download here...!!!
                if (holder.download.getText().equals("Download")) {
                    Toast.makeText(context," Downloading!",Toast.LENGTH_SHORT).show();
                    Uri uri = Uri.parse(holder.downloadFileurl);
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setTitle(subjectContent.getName());
                    request.setDescription(subjectContent.getDescription());
                    request.setDestinationInExternalFilesDir(context.getApplicationContext(),Environment.DIRECTORY_DOWNLOADS,holder.type);
                    DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                    manager.enqueue(request);
                    BroadcastReceiver onComplete = new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            Toast.makeText(context," Downloaded ",Toast.LENGTH_SHORT).show();
                            holder.download.setText(" Open ");
                        }
                    };
                    context.getApplicationContext().registerReceiver(onComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                } else if(holder.download.getText().equals(" Open ")) {
                    File file = new File (holder.path);

                    Log.i("LOGS : ",holder.path);
                    Intent target = new Intent(Intent.ACTION_VIEW);
                    String s = null;
                    try{
                        s= getMine(file.toURL().toString());
                    }catch(MalformedURLException e){
                        e.printStackTrace();
                    }
                    target.setDataAndType(Uri.fromFile(file),s);
                    target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    target.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    Intent intent = Intent.createChooser(target, "Open File");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add Content To download here...!!!
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, DOWNLOAD_MESSAGE);
                sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent sharevia = new Intent(Intent.createChooser(sharingIntent, "Share via"));
                sharevia.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(sharevia);
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO implement onClick for the card here..!
                holder.Description.setMaxLines(100);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectData.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView Name;
        public final TextView Description;
        public final TextView Type;
        public final TextView Uploader;
        public final TextView Date;
        public final Button download;
        public final ImageButton share;
        public  String path=null;
        public  String type=null;
        public  String downloadFileurl=null;
        public DownloadManager downloadManager;
        public final CardView cardView= (CardView) itemView.findViewById(R.id.card_view_subject);
        //TODO cardview is taken to use it for onClick Events
        public ViewHolder(View itemView) {
            super(itemView);

            Name = (TextView) itemView.findViewById(R.id.text_name);
            Date = (TextView) itemView.findViewById(R.id.upload_date_blank);
            Uploader = (TextView) itemView.findViewById(R.id.uploader_blank);
            Description = (TextView) itemView.findViewById(R.id.text_description);
            Type = (TextView) itemView.findViewById(R.id.textview_type);
            download = (Button) itemView.findViewById(R.id.button);
            share = (ImageButton) itemView.findViewById(R.id.sharebutton);


        }

        @Override
        public void onClick(View view) {

        }
    }
    private static String getMine(String url)
    {
        String type = null;
        String ex = MimeTypeMap.getFileExtensionFromUrl(url);
        if(ex!=null)
        {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ex);
        }
        return type;
    }
}