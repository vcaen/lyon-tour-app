package fr.vcaen.lyontour.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import fr.vcaen.lyontour.R;
import fr.vcaen.lyontour.fragments.CalendarDialog;
import fr.vcaen.lyontour.network.RestHelper;

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

    @Override
    protected void onStart() {
        super.onStart();
        final Date dateA = new Date();
        final Date dateD = new Date();
        final Button buttonA = (Button) findViewById(R.id.date_arrivee);
        final Button buttonD = (Button) findViewById(R.id.date_depart);

       /* buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog.Builder builder = new DatePickerDialog.Builder() {
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                        String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                        buttonA.setText(date);
                        String dA = buttonA.getText().toString();
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                            dateA.setTime(sdf.parse(dA).getTime());
                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        Toast.makeText(fragment.getDialog().getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                        super.onNegativeActionClicked(fragment);
                    }
                };

                builder.positiveAction("OK")
                        .negativeAction("ANNULER");

                DialogFragment fragment = DialogFragment.newInstance(builder);
                fragment.show(getSupportFragmentManager(), null);
            }
        });
*/

        final View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DatePickerDialog.Builder builder = new DatePickerDialog.Builder() {
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                        String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                        ((Button) v).setText(date);
                        if(v.getId() == R.id.date_depart)
                            dateD.setTime(dialog.getDate());
                        else
                            dateA.setTime(dialog.getDate());
                        if(v.getId() == R.id.date_depart &&  dateD.compareTo(dateA) == -1){
                            buttonD.setText("Fin du séjour");
                            Toast.makeText(getApplicationContext(), "Votre date de départ est antérieure à votre date d'arrivée", Toast.LENGTH_SHORT).show();
                        }
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        Toast.makeText(fragment.getDialog().getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                        super.onNegativeActionClicked(fragment);
                    }




                };
                if(v.getId() == R.id.date_depart) {
                    Calendar infiniteDate = Calendar.getInstance();
                    infiniteDate.set(3000, 12, 31);
                    builder.dateRange(dateA.getTime(), infiniteDate.getTimeInMillis());
                } else {

                    Calendar startDate = Calendar.getInstance();
                    startDate.set(1970,1,1);
                    builder.dateRange(startDate.getTimeInMillis(), dateD.getTime());
                }
                builder.positiveAction("OK")
                        .negativeAction("ANNULER");
                DialogFragment fragment = DialogFragment.newInstance(builder);
                fragment.show(getSupportFragmentManager(), null);

            }};

        buttonD.setOnClickListener(clickListener);
        buttonA.setOnClickListener(clickListener);

        final Button buttonValider = (Button) findViewById(R.id.valider);
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