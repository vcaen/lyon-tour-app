package fr.vcaen.lyontour.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alertdialogpro.AlertDialogPro;
import com.dd.CircularProgressButton;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import fr.vcaen.lyontour.R;

public class HomeActivity extends ActionBarActivity {

    final ArrayList mSelectedItems = new ArrayList();
    boolean [] isSelected = {true, true, true, true, true, true, true, true, true};
    final boolean [] isSelectedTemp = {true, true, true, true, true, true, true, true, true};
    Calendar dateA;
    Calendar dateD;
    static Calendar maxDate = Calendar.getInstance();
    static {
        maxDate.set(3000,12,31);
    }
    Calendar maxDate_depart = maxDate;
    Calendar minDate_depart = Calendar.getInstance();
    Calendar maxDate_arrivee = maxDate;
    Calendar minDate_arrivee = Calendar.getInstance();
    CircularProgressButton buttonValider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        buttonValider = (CircularProgressButton) findViewById(R.id.valider);
        buttonValider.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    boolean dateDepartChoisie = false;
    boolean dateArriveeChoisie = false;

    public void setDateDepartChoisie(boolean dateChoisie) {
        this.dateDepartChoisie = dateChoisie;
    }

    public void setDateArriveeChoisie(boolean dateChoisie) {
        this.dateArriveeChoisie = dateChoisie;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //dateD.set(3000, 12, 31);
        final TextView dateArrivee = (TextView) findViewById(R.id.date_arrivee);
        final TextView dateDepart = (TextView) findViewById(R.id.date_depart);
        final TextView vosPreferences = (TextView) findViewById(R.id.preferenceClik);
        //final RelativeLayout debutSejour =(RelativeLayout) findViewById(R.id.debutSejour);
        //final RelativeLayout finSejour =(RelativeLayout) findViewById(R.id.finSejour);

        final View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final DatePickerDialog.Builder builder = new DatePickerDialog.Builder() {

                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                        String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                        ((TextView) v).setText(date);
                        if(v.getId() == R.id.date_depart) {
                            if (dateD == null) dateD = Calendar.getInstance();
                            dateD.setTimeInMillis(dialog.getDate());
                            maxDate_arrivee = dateD;
                            setDateDepartChoisie(true);
                        }else {
                            if (dateA == null) dateA = Calendar.getInstance();
                            dateA.setTimeInMillis(dialog.getDate());
                            minDate_depart = dateA;
                            setDateArriveeChoisie(true);
                        }
                        buttonValider.setEnabled(dateArriveeChoisie && dateDepartChoisie);
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        super.onNegativeActionClicked(fragment);
                    }

                };

                // onclick
                if(v.getId() == R.id.date_depart) {
                    builder.dateRange(minDate_depart.getTimeInMillis(), maxDate_depart.getTimeInMillis());
                    builder.date((dateD != null) ?
                            dateD.getTimeInMillis() :
                            (dateA != null) ? dateA.getTimeInMillis() :Calendar.getInstance().getTimeInMillis());
                } else {
                    builder.dateRange(minDate_arrivee.getTimeInMillis(), maxDate_arrivee.getTimeInMillis());
                    builder.date((dateA != null) ? dateA.getTimeInMillis() : Calendar.getInstance().getTimeInMillis());

                }
                builder.positiveAction("OK")
                        .negativeAction("ANNULER");
                DialogFragment fragment = DialogFragment.newInstance(builder);
                fragment.show(getSupportFragmentManager(), null);

            }};

        dateDepart.setOnClickListener(clickListener);
        dateArrivee.setOnClickListener(clickListener);

        //finSejour.setOnClickListener(clickListener);
        //debutSejour.setOnClickListener(clickListener);

        vosPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialogPro.Builder builder =  new AlertDialogPro.Builder(HomeActivity.this, R.style.FilterDialog);

                builder.setTitle(R.string.vos_preferences);
                builder.setMultiChoiceItems(R.array.choixPreference, isSelected,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    mSelectedItems.add(which);
                                    isSelectedTemp[which] = true;
                                } else if (mSelectedItems.contains(which)) {
                                    mSelectedItems.remove(Integer.valueOf(which));
                                    isSelectedTemp[which] = false;
                                }
                            }
                        });

                builder.setPositiveButton(R.string.valider, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }

                });
                builder.setNegativeButton(R.string.annuler, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        for (int i = 0; i < isSelected.length ; i++) {
                            isSelected[i] = isSelectedTemp[i];
                        }
                    }
                });

                builder.create().show();
            }
        });

        buttonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listActivity = new Intent(HomeActivity.this, PointdinteretListActivity.class);
                startActivity(listActivity);
            }
        });
    }


}