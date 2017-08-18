package com.colinknecht.tasktimer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddEditActivityFragment extends Fragment {
    private static final String TAG = "AddEditActivityFragment";
    public enum FragmentEditMode {EDIT,ADD}
    private FragmentEditMode mMode;

    private EditText mNameTextView;
    private EditText mDescriptionTextView;
    private EditText mSortOrderTextView;
    private Button mSaveButton;
    private OnSaveClicked mSaveListener = null;

    interface OnSaveClicked {
        void onSaveClicked();
    }


    public AddEditActivityFragment() {
        Log.d(TAG, "AddEditActivityFragment: constructor called");
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach: starts");
        super.onAttach(context);

        //Activities containing this fragment must implement its callbacks
        Activity activity = getActivity();
        if (!(activity instanceof OnSaveClicked)) {
            throw new ClassCastException(activity.getClass().getSimpleName() +
                    " must implement AddEditActivityFragment.OnSaveClicked interface");
        }
        mSaveListener = (OnSaveClicked) activity;
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach: starts");
        super.onDetach();
        mSaveListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: starts");
        View view = inflater.inflate(R.layout.fragment_add_edit, container, false);
        mNameTextView = (EditText) view.findViewById(R.id.add_edit_name);
        mDescriptionTextView = (EditText) view.findViewById(R.id.add_edit_description);
        mSortOrderTextView = (EditText) view.findViewById(R.id.add_edit_sortorder);
        mSaveButton = (Button) view.findViewById(R.id.add_edit_save);

//        Bundle argument = getActivity().getIntent().getExtras(); //change later
        Bundle argument = getArguments();


        final Task task;
        if (argument != null) {
            Log.d(TAG, "onCreateView:  retrieving task details");

            task = (Task) argument.getSerializable(Task.class.getSimpleName());
            if (task != null) {
                Log.d(TAG, "onCreateView: Task Details Found, editing....");
                mNameTextView.setText(task.getName());
                mDescriptionTextView.setText(task.getDescription());
                mSortOrderTextView.setText(Integer.toString(task.getSortOrder()));
                mMode = FragmentEditMode.EDIT;
            }
            else {
                // no task, so we must be adding a new task, not editing an existing one
                mMode = FragmentEditMode.ADD;
            }
        }
        else {
            task = null;
            Log.d(TAG, "onCreateView: no arguments, adding new record");
            mMode = FragmentEditMode.ADD;
        }

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update the database if at least one field has changed
                //theres no need to hit the database unless this has happened
                int so;
                if (mSortOrderTextView.length() > 0){
                    so = Integer.parseInt(mSortOrderTextView.getText().toString());
                }else {
                    so = 0;
                }

                ContentResolver contentResolver = getActivity().getContentResolver();
                ContentValues values = new ContentValues();

                switch (mMode) {
                    case EDIT:
                        if (!mNameTextView.getText().toString().equals(task.getName())) {
                            values.put(TaskContract.Columns.TASKS_NAME, mNameTextView.getText().toString());
                        }
                        if (!mDescriptionTextView.getText().toString().equals(task.getDescription())){
                            values.put(TaskContract.Columns.TASKS_DESCRIPTION, mNameTextView.getText().toString());
                        }
                        if (so != task.getSortOrder()){
                            values.put(TaskContract.Columns.TASKS_SORT_ORDER, so);
                        }
                        if (values.size() != 0) {
                            Log.d(TAG, "onClick: updating task");
                            contentResolver.update(TaskContract.buildTaskUri(task.getM_Id()), values, null, null);
                        }
                        break;
                    case ADD:
                        if (mNameTextView.length() > 0) {
                            Log.d(TAG, "onClick: adding new task");
                            values.put(TaskContract.Columns.TASKS_NAME, mNameTextView.getText().toString());
                            values.put(TaskContract.Columns.TASKS_DESCRIPTION, mDescriptionTextView.getText().toString());
                            values.put(TaskContract.Columns.TASKS_SORT_ORDER,so);
                            contentResolver.insert(TaskContract.CONTENT_URI,values);
                        }
                        break;
                }
                Log.d(TAG, "onClick: done editing");
                if (mSaveListener != null) {
                    mSaveListener.onSaveClicked();
                }
            }
        });
        Log.d(TAG, "onCreateView: exiting");
        return view;
    }
}
