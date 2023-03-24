package com.example.fitgenie.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fitgenie.CheckForm;
import com.example.fitgenie.Database.User;
import com.example.fitgenie.Profile;
import com.example.fitgenie.Home;
import com.example.fitgenie.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutFragment extends Fragment {
    FloatingActionButton assistant;
    public Home home;
    CardView workout_plan, check_form;
    static List<User> userList;
    ArrayList<Object> key = new ArrayList<Object>();
    long value;
    String url = "http://192.168.198:5000/predict";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WorkoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutFragment newInstance(String param1, String param2) {
        WorkoutFragment fragment = new WorkoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        home = (Home) getActivity();
        home.toolbar.setVisibility(View.VISIBLE);

        assistant = view.findViewById(R.id.assistant);
        workout_plan = view.findViewById(R.id.workout_plan);
        check_form = view.findViewById(R.id.check_form);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                value = (int) snapshot.getChildrenCount();
                userList = new ArrayList<User>();
                for (DataSnapshot child: snapshot.getChildren()) {
                    userList.add(child.getValue(User.class));
                    key.add(child.getKey());
                }

                Log.d("value", "onViewCreated: "+ userList.get(0).getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Fail!", Toast.LENGTH_SHORT).show();
            }
        });

        workout_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                try {
//                                    JSONObject jsonObject = new JSONObject(response);
////                                    String res = jsonObject.getString("placement");
////                                    if(res.equals("1"))
////                                    {
////                                        result.setText("Placement is done!");
////                                    }
////                                    else
////                                    {
////                                        result.setText("Placement is ot done!");
////                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }){
//                    //params to be passed (gpa, id, profile_score)
//                    @Override
//                    protected Map<String, String> getParams()
//                    {
//                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("gender", home.gender);
//                        params.put("goal", home.goal);
//                        params.put("type", home.wtype);
//                        params.put("level", home.level);
//
//                        return params;
//                    }
//                };
//                RequestQueue queue = Volley.newRequestQueue(getContext());
//                queue.add(stringRequest);
//                Intent intent = new Intent(getContext(), WorkoutPlan.class);
//                startActivity(intent);
                home.replacefragment(new WorkoutPlan());

            }
        });

        check_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CheckForm.class);
                startActivity(intent);
            }
        });

        assistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.replacefragment(new AssistantFragment());
            }
        });
    }
}