package com.example.mynews.controller.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.mynews.R;

/**
 * Class that configure AlertDialog to inform the user of a potential problem.
 */
public class AlertDialogFragment extends DialogFragment {

    private AlertDialogClickEvent mAlertDialogClickEvent;

    /**
     * These constants represent the different types of errors
     * that will be handled. When a new instance of AlertDialogFragment
     * is created one of this constants is passed in argument.
     * That allow us to know which error has occur the alertDialog.
     */
    public static final int NO_RESULT_SEARCH = 0;
    public static final int NO_RESULT_MAIN = 1;
    public static final int NO_URL = 2;
    public static final int HTTP_ERROR_400 = 400;
    public static final int HTTP_ERROR_429 = 429;
    public static final int HTTP_ERROR_500 = 500;
    public static final int OTHER_ERROR = 600;

    /**
     * Tag to put and get the argument passed in the constructor
     */
    private static final String USAGE = "USAGE";

    /**
     * Tag passed in te second argument of the method show(fragmentManager,TAG)
     */
    public static final String ALERT_DIALOG_TAG = "ALERTTAG";

    /**
     * This variable will contained the value of the integer passed in
     * argument of the constructor newInstance. Hence we can known wath
     * kind of error we treat.
     */
    private int mUsage;

    /**
     * Variables destine to contains the id of content that will be display
     * in the AlertDialog. All id are defined in R.string resource file.
     */
    private int mTitleId;
    private int mMessageId;
    private int mTagPositiveButtonId;
    private int mTagNegativeButtonId;

    public AlertDialogFragment() {
    }

    /**
     * Constructor of the AlertDialogFragment.
     */
    public static AlertDialogFragment newInstance(int usage) {
        AlertDialogFragment fragment = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putInt(USAGE, usage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mAlertDialogClickEvent = (AlertDialogClickEvent) context;
    }

    /**
     * Creation of the AlertDialog.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null) {
            mUsage = getArguments().getInt(USAGE);
            setDialogArguments();
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireActivity());
        alertDialogBuilder
                .setIcon(R.drawable.ic_mood_bad_black_24dp)
                .setMessage(mMessageId)
                .setTitle(mTitleId)
                .setPositiveButton(mTagPositiveButtonId, (dialog, which) -> mAlertDialogClickEvent.doPositiveClick())
                .setNegativeButton(mTagNegativeButtonId, (dialog, which) -> mAlertDialogClickEvent.doNegativeClick());

        return alertDialogBuilder.create();
    }

    /**
     * This method determine the title, the message, and tags buttons of the AlertDialog
     * according to the variable mUsage.
     * mUsage contains a value that determines which kind of error we treating.
     */
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
