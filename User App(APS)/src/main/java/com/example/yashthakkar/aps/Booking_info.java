package com.example.yashthakkar.aps;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Booking_info extends AppCompatActivity {

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    Spinner spinner2;
    ArrayAdapter<CharSequence> adapter1;
    Spinner spinner3;
    ArrayAdapter<CharSequence> adapter2;
    Button datebutton,outdatebutton,outtimebutton,submitbutton;
    final int DIALOG=0;
    int yearx,monthx,dayx;
    int hourx,minutex;
    int hourxo,minutexo;
    int flag=0;
    int hourbook,hourbook1;
    String timebook;
   // String location[]=new String[2];
    String city,area;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_info);
        final Calendar cal=Calendar.getInstance();
        yearx=cal.get(Calendar.YEAR);
        monthx=cal.get(Calendar.MONTH);
        dayx=cal.get(Calendar.DAY_OF_MONTH);
        //location[]=new String[2];
        hourx=cal.get(Calendar.HOUR);
        minutex=cal.get(Calendar.MINUTE);

        hourxo=cal.get(Calendar.HOUR);
        minutexo=cal.get(Calendar.MINUTE);

        Button timebutton=(Button)findViewById(R.id.timebutton);
        timebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Booking_info.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hourbook=selectedHour;
                        EditText timedisp=(EditText)findViewById(R.id.timedisp);

                        timedisp.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hourx, minutex,true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        outtimebutton=(Button)findViewById(R.id.outtimebutton);
        outtimebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Booking_info.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hourbook1=selectedHour;
                        EditText outtimedisp=(EditText)findViewById(R.id.outimedisp);

                        if(hourx>selectedHour){
                            outtimedisp.setError("please select appropiate out time");
                            outtimedisp.requestFocus();
                            return;
                        }
                        else{
                            timebook=Integer.toString(hourbook1-hourbook);
                        outtimedisp.setText( selectedHour + ":" + selectedMinute);}
                    }
                }, hourxo, minutexo,true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


       // Toast.makeText(this,"Booking hours :"+timebook,Toast.LENGTH_LONG).show();
        dialogonclick();
        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.Bookingtype, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), adapterView.getItemAtPosition(i) + "selected", Toast.LENGTH_LONG).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        adapter1 = ArrayAdapter.createFromResource(this, R.array.city, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner2.setAdapter(adapter1);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               city= adapterView.getItemAtPosition(i).toString();
                if (adapterView.getItemIdAtPosition(i) == 1) {

                    adapter2 = ArrayAdapter.createFromResource(Booking_info.this, R.array.Ahemdabad, android.R.layout.simple_spinner_item);
                    adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    spinner3.setAdapter(adapter2);
                    spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            area=adapterView.getItemAtPosition(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                } else if(adapterView.getItemIdAtPosition(i)==2) {
                    adapter2 = ArrayAdapter.createFromResource(Booking_info.this, R.array.Rajkot, android.R.layout.simple_spinner_item);
                    adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    spinner3.setAdapter(adapter2);
                    spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            area=adapterView.getItemAtPosition(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        submitbutton=(Button)findViewById(R.id.submitbutton);
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Booking_info.this,ConfirmBooking.class));

                Bundle b=new Bundle();
                b.putStringArray("location", new String[]{city, area,timebook});
                Intent i=new Intent(Booking_info.this,ConfirmBooking.class);
                //
                //Intent i=new Intent(context, Class);
                i.putExtras(b);

                //Intent i=new Intent(Booking_info.this,ConfirmBooking.class);
              //  i.putExtra("location",location[2]);
               // i.putExtras(b);
                startActivity(i);
            }
        });
    //   submitbutton.setText(timebook);
    }

        public void dialogonclick(){

            datebutton=(Button)findViewById(R.id.datebutton);
            datebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    flag=1;
                    showDialog(DIALOG);

                }
            });

            outdatebutton=(Button)findViewById(R.id.outdatebutton);
            outdatebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    flag=0;
                    showDialog(DIALOG);
                }
            });


    }
        public Dialog onCreateDialog(int id){
            if(id==DIALOG){
                return new DatePickerDialog(this,dpickerListener,yearx,monthx,dayx);
            }
            return null;
        }

        private DatePickerDialog.OnDateSetListener dpickerListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                yearx=i;
                monthx=i1+1;
                dayx=i2;

                String date=yearx+"/"+monthx+"/"+dayx;
                if(flag==1){
                EditText datedisplay=(EditText)findViewById(R.id.datedisplay);
                datedisplay.setText(date);}
                else if(flag==0){

                    EditText outdatedisp=(EditText)findViewById(R.id.outdatedisp);
                    outdatedisp.setText(date);
                }
            }
        };


}
