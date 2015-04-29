package fr.vcaen.lyontour.fragments;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;

import java.text.SimpleDateFormat;

/**
 * Created by Mariia on 4/28/2015.
 */
public class CalendarDialog extends Fragment implements View.OnClickListener {

    public static CalendarDialog newInstance() {
        CalendarDialog fragment = new CalendarDialog();

        return fragment;
    }

    @Override
    public void onClick(View v) {
        Dialog.Builder builder = null;

        builder = new DatePickerDialog.Builder() {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                Toast.makeText(fragment.getDialog().getContext(), "Date is " + date, Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                Toast.makeText(fragment.getDialog().getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.positiveAction("OK")
                .negativeAction("TOTO");




        //DialogFragment fragment = DialogFragment.newInstance(builder);
        //fragment.show(getFragmentManager(), null);

    }
}


