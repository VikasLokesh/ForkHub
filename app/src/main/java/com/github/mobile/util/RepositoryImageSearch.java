package com.github.mobile.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;

import org.eclipse.egit.github.core.RepositoryContents;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.service.ContentsService;

import java.io.IOException;
import java.util.List;

import static android.util.Base64.DEFAULT;
import static java.lang.Integer.MAX_VALUE;
import static org.eclipse.egit.github.core.client.IGitHubConstants.HOST_DEFAULT;

/**
 * Created by root on 14-01-2017.
 */


public class RepositoryImageSearch {

    private final ContentsService service;

    public RepositoryImageSearch(ContentsService service) {
        this.service = service;
    }
    /**
     * Request an image using the contents API if the source URI is a path to a
     * file already in the repository
     *
     * @param source
     * @return
     * @throws IOException
     */
    public List<RepositoryContents> requestRepositoryImage(final String source)
            throws IOException {
        if (TextUtils.isEmpty(source))
            return null;

        Uri uri = Uri.parse(source);
        if (!HOST_DEFAULT.equals(uri.getHost()))
            return null;

        List<String> segments = uri.getPathSegments();
        if (segments.size() < 5)
            return null;

        String prefix = segments.get(2);
        // Two types of urls supported:
        // github.com/github/android/raw/master/app/res/drawable-xhdpi/app_icon.png
        // github.com/github/android/blob/master/app/res/drawable-xhdpi/app_icon.png?raw=true
        if (!("raw".equals(prefix) || ("blob".equals(prefix) && !TextUtils
                .isEmpty(uri.getQueryParameter("raw")))))
            return null;

        String owner = segments.get(0);
        if (TextUtils.isEmpty(owner))
            return null;
        String name = segments.get(1);
        if (TextUtils.isEmpty(name))
            return null;
        String branch = segments.get(3);
        if (TextUtils.isEmpty(branch))
            return null;

        StringBuilder path = new StringBuilder(segments.get(4));
        for (int i = 5; i < segments.size(); i++) {
            String segment = segments.get(i);
            if (!TextUtils.isEmpty(segment))
                path.append('/').append(segment);
        }

        if (TextUtils.isEmpty(path))
            return null;

        List<RepositoryContents> contents = service.getContents(
                RepositoryId.create(owner, name), path.toString(), branch);
        return contents;
    }

}
