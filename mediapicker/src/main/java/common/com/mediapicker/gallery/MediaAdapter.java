package common.com.mediapicker.gallery;

import android.content.Context;
import android.support.v4.util.ArraySet;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import common.com.mediapicker.R;
import common.com.mediapicker.gallery.data.BaseItemObject;
import common.com.mediapicker.gallery.data.MediaFile;

import static common.com.mediapicker.gallery.data.BaseItemObject.TYPE_HEADER;
import static common.com.mediapicker.utils.MediaSanUtils.isPhoto;

class MediaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private MyEvent myEvent;
    /**
     * true: multi choice, false: single choice
     */
    private boolean isEnableMultiChoice = false;
    private ArraySet<Integer> tickedPositions = new ArraySet<>();

    MediaAdapter(Context context) {
        this.context = context;
    }

    /**
     * origin data (update data) => generate display data
     */
    private List<MediaFile> mediaData = new ArrayList<>();
    /**
     * NOTE: do not update this list, it's generated list by {@link #mediaData}
     */
    private List<BaseItemObject> displayMediaList = new ArrayList<>();
    /**
     * display type values: {@link Gallery#SORT_BY_TIME}, {@link Gallery#SORT_BY_FOLDER}, {@link Gallery#SORT_BY_PHOTOS}, {@link Gallery#SORT_BY_VIDEOS}
     */
    private int displayType = Gallery.SORT_BY_TIME;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == TYPE_HEADER) {
            holder = new HeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_header, parent, false));
        } else {
            holder = new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false));
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int type = getItemViewType(position);
        if (type == TYPE_HEADER) {
            GalleryHeader headerData = (GalleryHeader) displayMediaList.get(position);
            HeaderHolder headerHolder = (HeaderHolder) holder;
            headerHolder.header.setText(headerData.getHeader());
        } else {
            final MediaFile mediaFile = getItem(position);
            final ItemHolder itemHolder = (ItemHolder) holder;
            Glide.with(context)
                    .load(mediaFile.getPath())
                    .centerCrop()
                    .crossFade()
                    .error(R.drawable.ic_broken_image_blue_grey_900_48dp)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            // don't show invalid image file, example: aa.jpg but it not image
                            mediaData.remove(mediaFile);
                            displayMediaList.remove(mediaFile);
                            notifyDataSetChanged();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(itemHolder.thumb);

            itemHolder.text.setText(mediaFile.getName());

            // multi choice => show tick + event
            if (isEnableMultiChoice) {
                itemHolder.tick.setVisibility(View.VISIBLE);
                itemHolder.tick.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            Log.d("aaaa", "add: " + position);
                            tickedPositions.add(position);
                            if (myEvent != null) myEvent.OnSelectedChange(tickedPositions.size());
                        } else {
                            tickedPositions.remove(position);
                            if (myEvent != null) myEvent.OnSelectedChange(tickedPositions.size());
                        }
                    }
                });
                itemHolder.tick.setChecked(tickedPositions.contains(position));
            } else {
                itemHolder.tick.setVisibility(View.GONE);
            }

            // set on item click
            itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // if multi choice => tick
                    if (isEnableMultiChoice) itemHolder.tick.toggle();
                    if (myEvent != null) myEvent.OnItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return displayMediaList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return displayMediaList.size();
    }

    /**
     * update data for display
     *
     * @param mediaFiles data update
     */
    void updateData(List<MediaFile> mediaFiles) {
        this.mediaData = mediaFiles;
        generateData(displayType);
    }

    private void generateData(int displayType) {
        switch (displayType) {
            case Gallery.SORT_BY_FOLDER:
                sortByFolder();
                break;
            case Gallery.SORT_BY_PHOTOS:
                sortByPhotos();
                break;
            case Gallery.SORT_BY_VIDEOS:
                sortByVideos();
                break;
            default:
                sortByTime();
                break;
        }
    }

    /**
     * @param position to check type
     * @return true: header type, false item type
     */
    boolean isHeader(int position) {
        return displayMediaList.size() != 0 && displayMediaList.get(position).getType() == TYPE_HEADER;
    }

    /**
     * generate list data to display sort by time
     */
    void sortByTime() {
        displayMediaList.clear();
        displayType = Gallery.SORT_BY_TIME;
        Collections.sort(mediaData, new Comparator<MediaFile>() {
            @Override
            public int compare(MediaFile o1, MediaFile o2) {
                Long time2 = o2.getTime();
                return time2.compareTo(o1.getTime());
            }
        });

        String currentDate = null;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd", Locale.US);
        for (MediaFile media : mediaData) {
            Date date = new Date(media.getTime());
            String formattedDate = fmt.format(date);

            // check if not same day with currentDate
            if (!formattedDate.equals(currentDate)) {

                currentDate = formattedDate;
                displayMediaList.add(new GalleryHeader(currentDate));
            }
            displayMediaList.add(media);
        }

        notifyDataSetChanged();
    }

    /**
     * generate list data to display sort by folder
     */
    void sortByFolder() {
        displayMediaList.clear();
        displayType = Gallery.SORT_BY_FOLDER;
        Collections.sort(mediaData, new Comparator<MediaFile>() {
            @Override
            public int compare(MediaFile o1, MediaFile o2) {
                String folder2 = o2.getFolder();
                return folder2.compareTo(o1.getFolder());
            }
        });

        String currentFolder = null;
        for (MediaFile media : mediaData) {
            // check if not same day with currentDate
            if (!media.getFolder().equals(currentFolder)) {

                currentFolder = media.getFolder();
                displayMediaList.add(new GalleryHeader(currentFolder));
            }
            displayMediaList.add(media);
        }

        notifyDataSetChanged();
    }

    /**
     * generate list data to display sort by photo
     */
    void sortByPhotos() {
        displayMediaList.clear();
        displayType = Gallery.SORT_BY_PHOTOS;
        for (MediaFile media : mediaData) {
            if (isPhoto(media.getPath())) {
                displayMediaList.add(media);
            }
        }
        notifyDataSetChanged();
    }

    /**
     * generate list data to display sort by video
     */
    void sortByVideos() {
        displayMediaList.clear();
        displayType = Gallery.SORT_BY_VIDEOS;
        for (MediaFile media : mediaData) {
            if (!isPhoto(media.getPath())) {
                displayMediaList.add(media);
            }
        }
        notifyDataSetChanged();
    }

    /**
     * register event on item click/tick
     *
     * @param myEvent interface to register callback
     */
    void setItemEvents(MyEvent myEvent) {
        this.myEvent = myEvent;
    }

    /**
     * set choice mode
     *
     * @param isMultiChoice true: multi choice, false: single choice
     */
    void setChoiceMode(boolean isMultiChoice) {
        isEnableMultiChoice = isMultiChoice;
    }

    /**
     * @return true: multi choice, false: single choice
     * @see #setChoiceMode(boolean)
     */
    boolean isEnableMultiChoice() {
        return isEnableMultiChoice;
    }

    /**
     * @param position position to get item
     */
    MediaFile getItem(int position) {
        return (MediaFile) displayMediaList.get(position);
    }

    MediaFile[] getSelectedItems() {
        MediaFile[] mediaFiles = new MediaFile[tickedPositions.size()];
        for (int i = 0; i < tickedPositions.size(); i++) {
            mediaFiles[i] = (MediaFile) displayMediaList.get(tickedPositions.valueAt(i));
        }
        return mediaFiles;
    }

    interface MyEvent {
        /**
         * event click item in recycle view
         *
         * @param position current pos
         */
        void OnItemClick(int position);

        /**
         * only work when {@link #setChoiceMode(boolean)} <- true
         */
        void OnSelectedChange(int total);
    }
}

class ItemHolder extends RecyclerView.ViewHolder {
    ImageView thumb;
    TextView text;
    AppCompatCheckBox tick;

    ItemHolder(View itemView) {
        super(itemView);
        thumb = (ImageView) itemView.findViewById(R.id.thumb);
        text = (TextView) itemView.findViewById(R.id.txt);
        tick = (AppCompatCheckBox) itemView.findViewById(R.id.tick);
    }
}

class HeaderHolder extends RecyclerView.ViewHolder {
    TextView header;

    HeaderHolder(View itemView) {
        super(itemView);
        header = (TextView) itemView.findViewById(R.id.header);
    }
}