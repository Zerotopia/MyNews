package com.example.mynews.controller;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.mynews.R;

import java.util.Calendar;

public class SearchFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static final String BOOLEAN = "activity";

    private EditText mBeginDate;
    private EditText mEndDate;
    private Boolean date;
    private Boolean mactivity;
//
//    private OnFragmentInteractionListener mListener;
//
//    public SearchFragment() {
//        // Required empty public constructor
//    }

    public static SearchFragment newInstance(Boolean which_activity) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putBoolean(BOOLEAN, which_activity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mactivity = (getArguments() == null) || getArguments().getBoolean(BOOLEAN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate_search = inflater.inflate(R.layout.search_fragment, container, false);
        LinearLayout dateLayout = inflate_search.findViewById(R.id.date_search);
        if (mactivity) {
            dateLayout.setVisibility(View.GONE);
        }
        return inflate_search;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!mactivity) {
            mBeginDate = view.findViewById(R.id.start_date);
            mEndDate = view.findViewById(R.id.end_date);


            mBeginDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    date = true;
                    displayPictureDialog();
                }
            });

            mEndDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    date = false;
                    displayPictureDialog();
                }
            });
        }
    }

    private void displayPictureDialog() {

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (!mactivity) {
            if (date) mBeginDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            else mEndDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
        }
    }


//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
