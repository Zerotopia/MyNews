package com.example.mynews.controller.fragment;

//import androidx.app.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;

import com.example.mynews.R;

/**
 *
 */
public class AlertDialogFragment extends DialogFragment {

    private AlertDialogClickEvent mAlertDialogClickEvent;
    public static final int NO_RESULT = 0;
    // public static final int NO_URL = 1;
    // public static final int HTTP_ERROR = 2;

    private static final String USAGE = "USAE";

    private int mUsage;
    private int mTitleId;
    private int mMessageId;
    private int mTagPositiveButtonId;
    private int mTagNegativeButtonId;

    public AlertDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mAlertDialogClickEvent = (AlertDialogClickEvent) context;
    }

    /**
     *
     */
    public static AlertDialogFragment newInstance(int usage) {
        AlertDialogFragment fragment = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putInt(USAGE, usage);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null) {
            mUsage = getArguments().getInt(USAGE);
            setDialogArguments();
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder
                .setIcon(R.drawable.ic_mood_bad_black_24dp)
                .setCancelable(false)
                .setMessage(mMessageId)
                .setTitle(mTitleId)
                .setPositiveButton(mTagPositiveButtonId, (dialog, which) -> mAlertDialogClickEvent.doPositiveClick())
                .setNegativeButton(mTagNegativeButtonId, (dialog, which) -> mAlertDialogClickEvent.doNegativeClick());

        return alertDialogBuilder.create();


    }

    private void setDialogArguments() {
        switch (mUsage) {
            case 0:
                mTitleId = R.string.no_result_title;
                mMessageId = R.string.no_result_message;
                mTagPositiveButtonId = R.string.new_search;
                mTagNegativeButtonId = R.string.home;
                break;
            default:
                break;
        }
    }

    public interface AlertDialogClickEvent {
        void doPositiveClick();

        void doNegativeClick();
    }
}
