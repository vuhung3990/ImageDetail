package com.example.tux.mylab.gallery;

import com.example.tux.mylab.gallery.data.GalleryRepository;
import com.example.tux.mylab.gallery.data.MediaFile;

import java.util.List;

/**
 * Created by dev22 on 5/16/17.
 */

public class GalleryContract {
    interface View {
        /**
         * update data when get all media files success
         *
         * @param mediaFiles data to update
         */
        void updateData(List<MediaFile> mediaFiles);
    }

    interface Presenter {
        /**
         * when user show ui
         */
        void onResume();

        /**
         * when user pause activity
         */
        void onPause();
    }

    public interface Repository {
        /**
         * get all media files in device (async)
         *
         * @param event when done
         */
        void onGetAllMediaFile(GalleryRepository.Event event);
    }
}
