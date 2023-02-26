package com.example.lfelne;

import android.net.Uri;
import android.text.format.DateFormat;
import androidx.annotation.Nullable;

import java.util.Date;

public class ImageItem {
    private final Uri IMAGE_URI;
    private final long DATE_CREATED;

    public ImageItem(@Nullable Uri image_uri, long date_created) {
        IMAGE_URI = image_uri;
        DATE_CREATED = date_created;
    }

    public Uri getUri() {
        return IMAGE_URI;
    }

    public String getDate() {
        return (String) DateFormat.format("MM/dd/yy hh:mma", new Date(DATE_CREATED));
    }

}
