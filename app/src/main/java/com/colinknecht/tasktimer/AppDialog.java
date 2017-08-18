package com.colinknecht.tasktimer;

import android.app.DialogFragment;
import android.os.Bundle;

/**
 * Created by colinknecht on 8/18/17.
 */

public class AppDialog extends DialogFragment {
    private static final String TAG = "AppDialog";


    /**
     * the dialogs callback interface to notify of user selected results (deletion confirmed, etc.)
     */
    interface DialogEvents {
        void onPositiveDialogResult(int dialogId, Bundle args);
        void onNegativeDialogResult (int dialogId, Bundle args);
        void onDialogCancelled(int dialogId);
    }


}
