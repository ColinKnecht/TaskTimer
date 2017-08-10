package com.colinknecht.tasktimer;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.security.InvalidParameterException;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "MainActivityFragment";
    
    public static final int LOADER_ID = 0;
    public MainActivityFragment() {
        Log.d(TAG, "MainActivityFragment: constructor called");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: starts");
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: starts");
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader: starts with id " + id);
        String [] projection = {TaskContract.Columns._ID, TaskContract.Columns.TASKS_NAME,
                                TaskContract.Columns.TASKS_DESCRIPTION, TaskContract.Columns.TASKS_SORT_ORDER};
        String sortOrder = TaskContract.Columns.TASKS_SORT_ORDER + " , " + TaskContract.Columns.TASKS_NAME;
        switch (id) {
            case LOADER_ID:
                return new CursorLoader(getActivity(),
                        TaskContract.CONTENT_URI,
                        projection,
                        null,
                        null,
                        sortOrder);
            default:
                throw new InvalidParameterException(TAG + " .onCreateLoader called with invalid loader id " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished: starts");
        int count = -1;
        if (data != null) {
            while (data.moveToNext()) {
                for (int i = 0; i < data.getColumnCount(); i++){
                    Log.d(TAG, "onLoadFinished: " + data.getColumnName(i) + " : " + data.getString(i) );
                }
                Log.d(TAG, "onLoadFinished: ====================END RECORD========================");
            }
            count = data.getCount();
        }
        Log.d(TAG, "onLoadFinished: count is " + count);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset: starts");
    }
}
