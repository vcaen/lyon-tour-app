package fr.vcaen.lyontour.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import fr.vcaen.lyontour.R;

public class HomeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
        final Calendar dateA = Calendar.getInstance();
        final Calendar dateD = Calendar.getInstance();
        dateD.set(3000, 12, 31);
        final TextView dateArrivee = (TextView) findViewById(R.id.date_arrivee);
        final TextView dateDepart = (TextView) findViewById(R.id.date_depart);
        final CircularProgressButton buttonValider = (CircularProgressButton) findViewById(R.id.valider);
        buttonValider.setEnabled(false);


        final View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DatePickerDialog.Builder builder = new DatePickerDialog.Builder() {
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                        String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                        ((TextView) v).setText(date);
                        if(v.getId() == R.id.date_depart) {
                            dateD.setTimeInMillis(dialog.getDate());
                            setDateDepartChoisie(true);
                        }else {
                            dateA.setTimeInMillis(dialog.getDate());
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
                if(v.getId() == R.id.date_depart) {
                    Calendar infiniteDate = Calendar.getInstance();
                    infiniteDate.set(3000, 12, 31);
                    builder.dateRange(dateA.getTimeInMillis(), infiniteDate.getTimeInMillis());
                } else {

                    Calendar startDate = Calendar.getInstance();
                    builder.dateRange(startDate.getTimeInMillis(), dateD.getTimeInMillis());
                }
                builder.positiveAction("OK")
                        .negativeAction("ANNULER");
                DialogFragment fragment = DialogFragment.newInstance(builder);
                fragment.show(getSupportFragmentManager(), null);

            }};

        dateDepart.setOnClickListener(clickListener);
        dateArrivee.setOnClickListener(clickListener);

        buttonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listActivity = new Intent(HomeActivity.this, PointdinteretListActivity.class);
                startActivity(listActivity);
                Log.d("HomeActivity", "bouton valide clique");
            }
        });
    }
}