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
    public static final int NO_RESULT_SEARCH = 0;
    public static final int NO_RESULT_MAIN = 1;
    public static final int NO_URL = 2;
    //public static final int HTTP_ERROR_300 = 300;
    public static final int HTTP_ERROR_400 = 400;
    public static final int HTTP_ERROR_429 = 429;
    public static final int HTTP_ERROR_500 = 500;
    public static final int OTHER_ERROR = 600;

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
            case NO_RESULT_SEARCH:
                configTextAlertDialog(
                        R.string.no_result_title,
                        R.string.no_result_message_search,
                        R.string.new_search,
                        R.string.home);
                break;
            case NO_RESULT_MAIN:
                configTextAlertDialog(
                        R.string.no_result_title,
                        R.string.no_result_message_main,
                        R.string.yes,
                        R.string.no);
                break;
            case NO_URL:
                configTextAlertDialog(
                        R.string.no_url_title,
                        R.string.no_url_message,
                        R.string.continu,
                        R.string.return_activity);
            case HTTP_ERROR_400:
                configTextAlertDialog(
                        R.string.error_400_title,
                        R.string.error_400_message,
                        R.string.send_report,
                        R.string.home);
                break;
            case HTTP_ERROR_429:
                configTextAlertDialog(
                        R.string.error_429_title,
                        R.string.error_429_message,
                        R.string.send_report,
                        R.string.home);
                break;
            case HTTP_ERROR_500:
                configTextAlertDialog(
                        R.string.error_500_title,
                        R.string.error_500_message,
                        R.string.send_report,
                        R.string.quit);
                break;
            case OTHER_ERROR:
                configTextAlertDialog(
                        R.string.other_error_title,
                        R.string.other_error_message,
                        R.string.send_report,
                        R.string.home);
                break;
            default:
                break;
        }
    }

    private void configTextAlertDialog(int titleId, int messageId, int positiveTagId, int negativeTagId) {
        mTitleId = titleId;
        mMessageId = messageId;
        mTagPositiveButtonId = positiveTagId;
        mTagNegativeButtonId = negativeTagId;
    }

    public interface AlertDialogClickEvent {
        void doPositiveClick();

        void doNegativeClick();
    }
}
