package com.example.fitgenie.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.fitgenie.Home;
import com.example.fitgenie.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutPlan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutPlan extends Fragment {
    public Home home;
    WebView webView;
    FloatingActionButton assistant;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WorkoutPlan() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkoutPlan.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutPlan newInstance(String param1, String param2) {
        WorkoutPlan fragment = new WorkoutPlan();
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
        return inflater.inflate(R.layout.fragment_workout_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        home = (Home) getActivity();
        assistant = view.findViewById(R.id.assistant);
        home.toolbar.setVisibility(View.INVISIBLE);
        webView = view.findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.strongrfastr.com/workout_builder_generator");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        assistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.replacefragment(new AssistantFragment());
            }
        });
    }
}