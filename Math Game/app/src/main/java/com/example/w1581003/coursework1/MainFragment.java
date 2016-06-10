package com.example.w1581003.coursework1;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class MainFragment extends Fragment {
    private AlertDialog nDialog;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){



       View rootView = inflater.inflate(R.layout.fragment_main,container,false);

        View n_button = rootView.findViewById (R.id.new_button);
        View c_button = rootView.findViewById (R.id.continue_button);
        View a_button = rootView.findViewById (R.id.about_button);
        View e_button = rootView.findViewById (R.id.exit_button);

        e_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }

                                    });

        a_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.desc);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.gotcha, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                nDialog = builder.show();
            }
        });

        c_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                getActivity().startActivity(intent);
            }
        });

        n_button.setOnClickListener(new View.OnClickListener(){

            private String[] difficulty = {"Novice", "Easy", "Medium", "Guru"};
            @Override
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.diff).setSingleChoiceItems(difficulty, 0, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface diff_info, int choice) {
                        diff_info.dismiss();
                        newGame(choice);
                    }
                });

                nDialog = builder.show();
            }
        });

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

        if (nDialog != null)
            nDialog.dismiss();
    }
    private void newGame(int choice){

        Intent gameDiff = new Intent(getActivity(), GameActivity.class);
        gameDiff.putExtra("difficulty", choice);
        getActivity().startActivity(gameDiff);
    }
}
