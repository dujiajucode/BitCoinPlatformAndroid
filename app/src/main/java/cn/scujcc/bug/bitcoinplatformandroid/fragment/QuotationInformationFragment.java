package cn.scujcc.bug.bitcoinplatformandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.scujcc.bug.bitcoinplatformandroid.R;

/**
 * Created by lilujia on 16/3/27.
 * <p/>
 * 行情资讯
 */
public class QuotationInformationFragment extends BaseFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hello, container, false);

        setHasOptionsMenu(true);

        getActivity().setTitle("行情信息");

        TextView tv = (TextView) view.findViewById(R.id.fragment_hello_textview);
        tv.setText("行情信息");


        return view;
    }
}
