package com.colinknecht.tasktimer;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.colinknecht.tasktimer.AppProvider.CONTENT_AUTHORITY;
import static com.colinknecht.tasktimer.AppProvider.CONTENT_AUTHORITY_URI;

/**
 * Created by colinknecht on 7/26/17.
 */

public class TaskContract {
    static final String TABLE_NAME = "Tasks";

    //Tasks Fields
    public static class Columns {////////////////
        public static final String _ID = BaseColumns._ID;
        public static final String TASKS_NAME = "Name";
        public static final String TASKS_DESCRIPTION = "Description";
        public static final String TASKS_SORT_ORDER = "SortOrder";

        private Columns(){
            //private Constructor to prevent instantiation
        }
    }//////////////////////////////////////end Columns Class

    /**
     * The URI to access the Tasks table
     */
    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);
    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    static Uri buildTaskUri(long taskId) {
        return ContentUris.withAppendedId(CONTENT_URI,taskId);
    }
    static long getTaskId(Uri uri) {
        return ContentUris.parseId(uri);
    }
}
