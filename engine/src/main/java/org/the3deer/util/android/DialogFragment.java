package org.the3deer.util.android;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

public class DialogFragment extends androidx.fragment.app.DialogFragment implements DialogInterface.OnClickListener {

    // params
    protected int title;
    protected String[] items;

    // variables
    protected FragmentActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = getActivity();
        this.title = getArguments().getInt("title");
        this.items = getResources().getStringArray(getArguments().getInt("items"));

        // this fragment will be displayed in a dialog
        //setShowsDialog(true);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                //.setIcon(R.drawable.ic_launcher)
                .setTitle(title)
                /*.setPositiveButton(R.string.dialog_ok,
                        (dialog, whichButton) -> onOk()
                )
                .setNegativeButton(R.string.dialog_cancel,
                        (dialog, whichButton) -> onCancel()
                )*/
                .setItems(items, this);


        return builder
                .create();
    }

    protected void onOk(){

    }

    protected void onCancel(){

    }

    @Override
    public void onClick(DialogInterface dialogI, int position) {
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (getActivity() instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) getActivity()).onDismiss(dialog);
        }
    }

}