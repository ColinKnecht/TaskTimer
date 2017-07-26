package com.colinknecht.tasktimer;

import android.provider.BaseColumns;

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
}
