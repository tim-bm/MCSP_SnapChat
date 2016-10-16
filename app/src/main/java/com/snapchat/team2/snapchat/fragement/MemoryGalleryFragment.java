package com.snapchat.team2.snapchat.fragement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.snapchat.team2.snapchat.R;
import com.snapchat.team2.snapchat.Tools.MemoryUploader;
import com.snapchat.team2.snapchat.customAdapter.MemoryGridviewAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by bm on 14/10/2016.
 */

public class MemoryGalleryFragment extends Fragment implements PopupMenu.OnMenuItemClickListener{
    private ViewGroup rootView;
    private GridView gridView;
    private MemoryGridviewAdapter gAdapter;
    private String memoryPhotoPath="";

    private ShareActionProvider mShareActionProvider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=(ViewGroup)inflater.inflate(R.layout.fragment_memory_gallery,container,false);


        gridView=(GridView)rootView.findViewById(R.id.gridview_memory);
        gAdapter=new MemoryGridviewAdapter(this.getActivity());
        gridView.setAdapter(gAdapter);

       gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {



               File dir = new File(Environment.getExternalStoragePublicDirectory
                       (Environment.DIRECTORY_PICTURES),"SnapChat222");


               File mediaFile = new File(dir.getPath()+File.separator+gAdapter.getMemoryImages()[position]);

               memoryPhotoPath=mediaFile.getAbsolutePath();

//               Toast.makeText(MemoryGalleryFragment.this.getActivity(), memoryPhotoPath,
//                       Toast.LENGTH_SHORT).show();


               //popup menu
               PopupMenu popup=new PopupMenu(MemoryGalleryFragment.this.getActivity(),view);
               MenuInflater inflater=popup.getMenuInflater();
               popup.setOnMenuItemClickListener(MemoryGalleryFragment.this);
               inflater.inflate(R.menu.grid_memory_popup,popup.getMenu());
               //find menu item with share provider
//               MenuItem item= popup.getMenu().findItem(R.id.menu_item_share);
//
//               mShareActionProvider = (ShareActionProvider) item.getActionProvider();
//               setShareIntent(createShareIntent());
               popup.show();


               return true;
           }

       });
        return rootView;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.popup_item_memory_share:
                MemoryUploader.uploadPhoto(memoryPhotoPath,MemoryGalleryFragment.this.getActivity(),false);
                return true;
            case R.id.popup_item_memory_delete:

                if(this.deleteMemory(memoryPhotoPath)){
                    Toast.makeText(MemoryGalleryFragment.this.getActivity(), memoryPhotoPath+"deleted successfully",
                            Toast.LENGTH_SHORT).show();
                    gAdapter.notifyDataSetChanged();
                    gridView.setAdapter(gAdapter);
                }
                return true;
            case R.id.menu_item_share:

                setShareIntent(createShareIntent());

                    return true;
            default:
                return false;
        }
    }

    private boolean deleteMemory(String path){
        File file= new File(path);
            return file.delete();
    }

    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
    private Intent createShareIntent() {
//        Intent shareIntent = new Intent(Intent.ACTION_SEND);
////        shareIntent.setType("text/plain");
//
//        shareIntent.putExtra(Intent.EXTRA_TEXT,
//                "");

        Bitmap clickPhoto= BitmapFactory.decodeFile(memoryPhotoPath);

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        clickPhoto.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/temporary_file.jpg"));
        startActivity(Intent.createChooser(share, "Social Image"));

        return share;
    }
}
