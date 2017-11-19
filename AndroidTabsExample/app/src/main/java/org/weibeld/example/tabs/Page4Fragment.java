package org.weibeld.example.tabs;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import org.weibeld.example.R;

/* Fragment used as page 4 */
public class Page4Fragment extends Fragment {

    private Button searchBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page4, container, false);
        searchBtn = (Button) rootView.findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {

            /*Method for what happens when a button is clicked*/
            @Override
            public void onClick(View arg0) {

            Toast.makeText(getActivity().getApplicationContext(),
            "Search Button is clicked", Toast.LENGTH_LONG).show();
            }


        });
        return rootView;
    }
}
