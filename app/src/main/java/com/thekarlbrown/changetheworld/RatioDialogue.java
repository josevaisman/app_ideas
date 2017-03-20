package com.thekarlbrown.changetheworld;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * RatioDialogue Class that engages the User to set specific filters for the Bar Filter
 * Has a listener for the MainActivity to indicate current preferences
 * By Karl Brown ( thekarlbrown ) 2nd June 2015
 */

public class RatioDialogue extends DialogFragment {
    String tag;

    public static RatioDialogue newInstance() {
        RatioDialogue fragment = new RatioDialogue();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tag=getArguments().getString("tag");
        }
    }

    NoticeRatioDialogListener mListener;

    public interface NoticeRatioDialogListener {  void onRatioDialogPositiveClick(double i, String tag);    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try {
            mListener = (NoticeRatioDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
        }
    }

    /**
     * Set a Custom AlertDialog specific to Ratio Dialogue's that takes valid data into account
     * @param savedInstanceState Holds the tags of the Fragment
     * @return A created Dialogue from the builder
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = getActivity().getLayoutInflater().inflate(R.layout.minimum_ratio_dialogue, null);
        builder.setView(view);
        builder.setPositiveButton(R.string.ratio_dialogue_change, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            int i=Integer.parseInt(((EditText) view.findViewById(R.id.ratio_dialogue_entry)).getText().toString());
                            if ((i<100)&&(i>0)) {
                                mListener.onRatioDialogPositiveClick(((double)i/100.0),tag);
                            }
                        } catch (Exception e) {  }
                    }
                }
        );
        builder.setNegativeButton(R.string.ratio_dialogue_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //No action, just a custom title
            }
        });
        return builder.create();
    }
}
