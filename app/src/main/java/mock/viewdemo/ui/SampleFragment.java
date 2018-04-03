package mock.viewdemo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mock.viewdemo.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class SampleFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private final static String MESSAGE = "message";

    public SampleFragment(){

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SampleFragment newInstance(int position) {
        SampleFragment fragment = new SampleFragment();
        Bundle args = new Bundle();
        String title = "Page-"+(position+1);
        args.putString(MESSAGE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getArguments().getString(MESSAGE));
        return rootView;
    }
}
