package fr.vcaen.lyontour.Activity;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alertdialogpro.AlertDialogPro;
import com.android.volley.VolleyError;
import com.dd.CircularProgressButton;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import fr.vcaen.lyontour.R;
import fr.vcaen.lyontour.fragments.PointdinteretListFragment;
import fr.vcaen.lyontour.models.PointInteret;
import fr.vcaen.lyontour.models.containers.VisiteContainer;
import fr.vcaen.lyontour.network.RestHelper;

public class HomeActivity extends ActionBarActivity {

    boolean [] isSelected = {true, true, true, true, true, true};
    boolean [] isSelectedTemp = {true, true, true, true, true, true};
    final static String [] ITEMS = {"drinks","coffee","shops","arts","outdoors","sights"};
    ArrayList<String> mSelectedItems = new ArrayList<String>();
    ArrayList<String> mSelectedItemsTemp = new ArrayList<String>();
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

    private static final String TAG = HomeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        buttonValider = (CircularProgressButton) findViewById(R.id.valider);
        buttonValider.setEnabled(false);
        for (int i = 0; i<ITEMS.length; i++){
            mSelectedItems.add(ITEMS[i]);
            mSelectedItemsTemp.add(ITEMS[i]);
        }
        Log.d("HomeActivity", "liste debut : " + mSelectedItems);
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
        final TextView dateArrivee = (TextView) findViewById(R.id.date_arrivee);
        final TextView dateDepart = (TextView) findViewById(R.id.date_depart);
        final RelativeLayout cardDebutSejour =(RelativeLayout) findViewById(R.id.debutSejour);
        final RelativeLayout cardFinSejour =(RelativeLayout) findViewById(R.id.finSejour);
        final RelativeLayout cardPreference = (RelativeLayout) findViewById(R.id.preference);

        final View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final DatePickerDialog.Builder builder = new DatePickerDialog.Builder() {

                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                        String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                        //((TextView) v).setText(date);
                        if(v.getId() == R.id.finSejour) {
                            if (dateD == null) dateD = Calendar.getInstance();
                            dateD.setTimeInMillis(dialog.getDate());
                            maxDate_arrivee = dateD;
                            setDateDepartChoisie(true);
                            dateDepart.setText(date);
                        } else if(v.getId() == R.id.debutSejour) {
                            if (dateA == null) dateA = Calendar.getInstance();
                            dateA.setTimeInMillis(dialog.getDate());
                            minDate_depart = dateA;
                            setDateArriveeChoisie(true);
                            dateArrivee.setText(date);
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
                if(v.getId() == R.id.finSejour) {
                    builder.dateRange(minDate_depart.getTimeInMillis(), maxDate_depart.getTimeInMillis());
                    builder.date((dateD != null) ?
                            dateD.getTimeInMillis() :
                            (dateA != null) ? dateA.getTimeInMillis() :Calendar.getInstance().getTimeInMillis());
                } else if(v.getId() == R.id.debutSejour) {
                    builder.dateRange(minDate_arrivee.getTimeInMillis(), maxDate_arrivee.getTimeInMillis());
                    builder.date((dateA != null) ? dateA.getTimeInMillis() : Calendar.getInstance().getTimeInMillis());

                }
                builder.positiveAction("OK")
                        .negativeAction("ANNULER");
                DialogFragment fragment = DialogFragment.newInstance(builder);
                fragment.show(getSupportFragmentManager(), null);

            }};

        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_800));
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_950));
                    v.performClick();
                    return false;
                } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_950));
                    return false;
                }
                return false;
            }
        };

        cardDebutSejour.setOnTouchListener(touchListener);
        cardFinSejour.setOnTouchListener(touchListener);

        cardFinSejour.setOnClickListener(clickListener);
        cardDebutSejour.setOnClickListener(clickListener);

        cardPreference.setOnClickListener(new View.OnClickListener() {
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
                                    mSelectedItemsTemp.set(which, ITEMS[which]);
                                } else {
                                    mSelectedItemsTemp.set(which, null);
                                }
                                Log.d("HomeActivity", "liste : " + mSelectedItemsTemp);
                            }
                        });

                builder.setPositiveButton(R.string.valider, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mSelectedItems.clear();
                        for (int i = 0; i < isSelected.length; i++) {
                            if (isSelected[i])
                                mSelectedItems.add(ITEMS[i]);
                        }
                        isSelectedTemp = isSelected.clone();
                        Log.d("HomeActivity", "clik valider");
                    }
                });
                builder.setNegativeButton(R.string.annuler, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d(TAG, "g");
                        isSelected = isSelectedTemp.clone();
                    }
                });
                builder.create().show();
            }
        });

        buttonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonValider.setIndeterminateProgressMode(true);

                final ProgressBar pb = new ProgressBar(HomeActivity.this);
                pb.setIndeterminate(true);
                pb.setLayoutParams(buttonValider.getLayoutParams());
                ViewGroup parent = (ViewGroup) buttonValider.getParent();
                int i = parent.indexOfChild(buttonValider);
                parent.removeView(buttonValider);
                parent.addView(pb, i);

                final Intent listActivity = new Intent(HomeActivity.this, PointdinteretListActivity.class);
                final SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy", Locale.US);
                listActivity.putExtra(PointdinteretListFragment.ARG_DATE_DEBUT,sdf.format(dateD.getTime()));
                listActivity.putExtra(PointdinteretListFragment.ARG_DATE_FIN, sdf.format(dateA.getTime()));
                getSharedPreferences("lyon_tour", MODE_PRIVATE).edit()
                        .putString("date_debut", sdf.format(dateA.getTime()))
                        .putString("date_fin", sdf.format(dateD.getTime()))
                        .commit();



                RestHelper.getInstance(HomeActivity.this).getAttractions(
                        sdf.format(dateA.getTime()),
                        sdf.format(dateD.getTime()),
                        mSelectedItems,
                        new RestHelper.APICallBack<List<PointInteret>>() {
                            @Override
                            public void response(List<PointInteret> object) {
                                VisiteContainer.clear();
                                VisiteContainer.addAll(object);
                                ViewGroup parent = (ViewGroup) pb.getParent();
                                int i = parent.indexOfChild(pb);
                                parent.removeView(pb);
                                buttonValider.setProgress(100);
                                parent.addView(buttonValider, i);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(listActivity);
                                    }
                                }, 500);

                            }

                            @Override
                            public void error(VolleyError error) {
                                Log.d(TAG, "" + error.getMessage());
                                ViewGroup parent = (ViewGroup) pb.getParent();
                                int i = parent.indexOfChild(pb);
                                parent.removeView(pb);
                                parent.addView(buttonValider, i);
                                buttonValider.setProgress(-1);
                            }
                        });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(dateA != null && dateD != null) {
            buttonValider.setProgress(0);
        }
    }
}