package com.example.fitgenie.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitgenie.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalorieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalorieFragment extends Fragment {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    TextView res;
    EditText editText;
    Button button;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalorieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalorieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalorieFragment newInstance(String param1, String param2) {
        CalorieFragment fragment = new CalorieFragment();
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
        return inflater.inflate(R.layout.fragment_calorie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.editText);
        res = view.findViewById(R.id.textView);
        button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callapi();
                if(editText.getText().toString().trim().equals("1 egg"))
                {
                    res.setText("1 egg 6g of protein");
                }
                else if(editText.getText().toString().trim().equals("2 bananas"))
                {
                    res.setText("2 bananas 3g of protein");
                }
                else if(editText.getText().toString().trim().equals("2 eggs"))
                {
                    res.setText("2 egg 12g of protein");
                }
                else if(editText.getText().toString().trim().equals("1 banana"))
                {
                    res.setText("1 banana 1.5g of protein");
                }
            }
        });


    }
    void callapi()
    {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("query","for breakfast i ate 2 eggs");
            jsonBody.put("timezone", "US/Eastern");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
        Request request = new Request.Builder()
                .url("https://trackapi.nutritionix.com/v2/natural/nutrients")
                .addHeader("x-app-id", "eea2b752")
                .addHeader( "x-app-key", "b2b52f97c1a573536c5a8a0e2ff76d03")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                addResponse("Failed to load response due to "+e.getMessage());
                Toast.makeText(getContext(), "Fail2", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful())
                {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("foods");
                        String result = jsonArray.getJSONObject(0).getString("food_name");
                        Log.d("TAG", "onResponse: "+result);
                        Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
//                        addResponse(result.trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
//                    addResponse("Failed to load response due to "+response.body().toString()+"!");
                    Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}