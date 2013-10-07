package com.srs.blooddonate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;

public class RegActivity extends ActionBarActivity implements TabListener {

    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
        }

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewPager = (ViewPager) findViewById(R.id.view_reg);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++)
            getSupportActionBar().addTab(getSupportActionBar().newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.action_about:
                showAbout();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void showAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch (position) {
                case 0:
                    return new Section0_Name();
                case 1:
                    return new Section1_Contact();
                case 2:
                    return new Section2_BloodGrp();
                case 3:
                    return new Section3_Instructions();
                case 4:
                    return new Section4_Summary();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.title_section0);
                case 1:
                    return getString(R.string.title_section1);
                case 2:
                    return getString(R.string.title_section2);
                case 3:
                    return getString(R.string.title_section3);
                case 4:
                    return getString(R.string.title_section4);
            }
            return null;
        }

    }

    private static String name;
    private static String blood;
    private static String cell;
    private static String email;
    private static String address;
    private static String country;
    private static String state;
    private static String city;

    private static String remSpace(String s) {

        s = s.trim();
        String fs = " ";

        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) != ' ')
                fs += s.charAt(i);

        return capitalizeFirst(fs);

    }


    private static String remConSpace(String s) {

        s = s.trim() + " ";
        String fs = " ";

        for (int i = 0; i < s.length() - 1; i++)
            if ((s.charAt(i) != ' ' && s.charAt(i + 1) != ' ') || (s.charAt(i) == ' ' && s.charAt(i + 1) != ' ') || (s.charAt(i) != ' ' && s.charAt(i + 1) == ' '))
                fs += s.charAt(i);

        return capitalizeFirst(fs);

    }

    public static String capitalizeFirst(String s) {

        s = " " + s.toLowerCase() + "  ";

        for (int i = 0; i < s.length() - 3; i++)
            if (s.charAt(i) == ' ' && s.charAt(i + 1) != ' ')
                s = s.substring(0, i + 1) + s.substring(i + 1, i + 2).toUpperCase() + s.substring(i + 2);

        return s.trim();

    }

    public static class Section0_Name extends Fragment {

        public Section0_Name() {
            name = " ";
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.reg_name, container, false);

            if (view != null) {
                ((EditText) view.findViewById(R.id.fn)).addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                    }

                    @Override
                    public void afterTextChanged(Editable e) {
                        if (e.toString().length() > 0)
                            name = remSpace(e.toString()) + name.substring(name.lastIndexOf(" "));
                    }
                });
            }

            if (view != null) {
                ((EditText) view.findViewById(R.id.ln)).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                    }

                    @Override
                    public void afterTextChanged(Editable e) {
                        if (e.toString().length() > 0)
                            name = name.substring(0, (name.lastIndexOf(" ") + 1)) + remSpace(e.toString());
                    }
                });
            }

            return view;
        }
    }

    public static class Section1_Contact extends Fragment {

        public Section1_Contact() {
            cell = email = address = country = state = city = " ";
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.reg_contact, container, false);

            if (view != null) {
                ((EditText) view.findViewById(R.id.cell)).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                    }

                    @Override
                    public void afterTextChanged(Editable e) {
                        cell = remSpace(e.toString());
                    }

                });
            }

            if (view != null) {
                ((EditText) view.findViewById(R.id.email)).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                    }

                    @Override
                    public void afterTextChanged(Editable e) {
                        if (e.toString().length() > 0)
                            email = remSpace(e.toString());
                    }
                });
            }

            if (view != null) {
                ((EditText) view.findViewById(R.id.address)).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                    }

                    @Override
                    public void afterTextChanged(Editable e) {
                        if (e.toString().length() > 0)
                            address = remConSpace(e.toString());
                    }
                });
            }

            if (view != null) {
                ((EditText) view.findViewById(R.id.country)).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                    }

                    @Override
                    public void afterTextChanged(Editable e) {
                        if (e.toString().length() > 0)
                            country = remConSpace(e.toString());
                    }
                });
            }

            if (view != null) {
                ((EditText) view.findViewById(R.id.state)).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                    }

                    @Override
                    public void afterTextChanged(Editable e) {
                        if (e.toString().length() > 0)
                            state = remConSpace(e.toString());
                    }
                });
            }

            if (view != null) {
                ((EditText) view.findViewById(R.id.city)).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                    }

                    @Override
                    public void afterTextChanged(Editable e) {
                        if (e.toString().length() > 0)
                            city = remConSpace(e.toString());
                    }
                });
            }

            return view;
        }
    }

    public static class Section2_BloodGrp extends Fragment {

        public Section2_BloodGrp() {
            blood = " ";
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.reg_bloodgrp, container, false);

            if (view != null) {
                ((Spinner) view.findViewById(R.id.bgr)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        if ((parent.getItemAtPosition(pos)) != null)
                            blood = (parent.getItemAtPosition(pos)).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }

                });
            }

            return view;

        }
    }

    public static class Section3_Instructions extends Fragment {

        public Section3_Instructions() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            return inflater.inflate(R.layout.reg_instruc, container, false);

        }

    }

    public static class Section4_Summary extends Fragment {

        public Section4_Summary() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.reg_summary, container, false);

            if (view != null) {
                ((TextView) view.findViewById(R.id.show_name)).setText("Name - " + name);
                ((TextView) view.findViewById(R.id.show_bldGrp)).setText("Blood Group - " + blood);
                ((TextView) view.findViewById(R.id.show_cell)).setText("Mobile - " + cell);
                ((TextView) view.findViewById(R.id.show_email)).setText("E-Mail ID - " + email);
                ((TextView) view.findViewById(R.id.show_address)).setText("Address - " + address);
                ((TextView) view.findViewById(R.id.show_country)).setText("Country - " + country);
                ((TextView) view.findViewById(R.id.show_state)).setText("State - " + state);
                ((TextView) view.findViewById(R.id.show_city)).setText("City - " + city);
            }

            return view;

        }

    }

    JSONObject jsonObject = new JSONObject();
    JSONArray jsonArray = new JSONArray();

    public class sendData extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... params) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://bdp.site40.net/sendRegData.php");

            try {
                HttpEntity httpEntity = new StringEntity(jsonArray.toString());
                httpPost.setEntity(httpEntity);
                httpClient.execute(httpPost);
            } catch (IOException e) {
            }

            return null;
        }
    }

    public void onClickSubmitButton(View view) throws JSONException {

        jsonObject.put("name", name);
        jsonObject.put("blood", blood);
        jsonObject.put("cell", cell);
        jsonObject.put("email", email);
        jsonObject.put("address", address);
        jsonObject.put("country", country);
        jsonObject.put("state", state);
        jsonObject.put("city", city);

        jsonArray.put(jsonObject);

        new sendData().execute();

    }

    @Override
    protected void onPause() {
        super.onPause();
        System.gc();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindDrawables(findViewById(R.id.view_reg));
        name = blood = cell = email = address = country = state = city = null;
        System.gc();
    }

    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
}