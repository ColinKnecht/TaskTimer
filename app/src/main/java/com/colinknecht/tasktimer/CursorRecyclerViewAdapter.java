package com.colinknecht.tasktimer;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by lori on 8/9/2017.
 * Tim Buchalka's video 223 shows how to do this
 */

class CursorRecyclerViewAdapter extends RecyclerView.Adapter<CursorRecyclerViewAdapter.TaskViewHolder> {
    private static final String TAG = "CursorRecyclerViewAdapt";
    private Cursor mCursor;

    public CursorRecyclerViewAdapter(Cursor cursor) {
        Log.d(TAG, "CursorRecyclerViewAdapter: Constructor Called");
        mCursor = cursor;
    }
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: new View Requested");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_items, parent,false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: starts");

        if (mCursor == null || mCursor.getCount() == 0) {
            Log.d(TAG, "onBindViewHolder: providing instructions");
            holder.name.setText("Instructions");
            holder.description.setText("This is the description");
//            holder.description.setText("Use the add button (+) in the toolbar above to create new tasks" +
//            "\n Task with the lower sort order will be placed higher up in the list " +
//            " Tasks with the same sort order will be sorted alphabetically." +
//            "\n Tapping a task will start the timer for that task");
            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);
        } else {
            if (!mCursor.moveToPosition(position)) {
                throw new IllegalStateException("Couldnt move cursor to postion " + position);
            }
            holder.name.setText(mCursor.getString(mCursor.getColumnIndex(TaskContract.Columns.TASKS_NAME)));
            holder.description.setText(mCursor.getString(mCursor.getColumnIndex(TaskContract.Columns.TASKS_DESCRIPTION)));
            holder.editButton.setVisibility(View.VISIBLE); //TODO add onClick Listener
            holder.deleteButton.setVisibility(View.VISIBLE); //TODO add onClick Listener
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: starts");
        if (mCursor == null || mCursor.getCount() == 0 ){
            return 1; //fib, because we popluate a single viewholder for instructions
        } else {
            return mCursor.getCount();
        }
    }

    /**
     * Swap in a new Cursor, returning the old Cursor
     * The returned old cursor is <em>not</em> closed
     * @param newCursor the new cursor to be used
     * @return returns the previously set Cursor, or null if there wasnt one
     * If the given new cursor is the same instance as the previously set
     * Cursor, null is returned
     */
    Cursor swapCursor (Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }
        final Cursor oldCursor = mCursor;
        mCursor = newCursor;
        if (newCursor != null) {
            //notify the observers about the newe cursor
            notifyDataSetChanged();
        } else {
            //notify observers about the lack of a data set
            notifyItemRangeRemoved(0, getItemCount());
        }
        return oldCursor;
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "TaskViewHolder";
        TextView name = null;
        TextView description = null;
        ImageButton editButton = null;
        ImageButton deleteButton = null;

        public TaskViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.tli_name);
            this.description = (TextView) itemView.findViewById(R.id.tli_description);
            this.editButton = (ImageButton) itemView.findViewById(R.id.tli_edit);
            this.deleteButton = (ImageButton) itemView.findViewById(R.id.tli_delete);
        }
    }
}
