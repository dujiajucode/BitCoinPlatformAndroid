
package cn.scujcc.bug.bitcoinplatformandroid;

import android.os.Bundle;
import android.support.v13.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import cn.scujcc.bug.bitcoinplatformandroid.fragment.ActualTransactionFragment;
import cn.scujcc.bug.bitcoinplatformandroid.fragment.PersonalCenterFragment;
import cn.scujcc.bug.bitcoinplatformandroid.fragment.ProfessionalTransactionFragment;
import cn.scujcc.bug.bitcoinplatformandroid.fragment.QuotationInformationFragment;

public class MainActivity extends AppCompatActivity {


    //定义一个布局
    private LayoutInflater layoutInflater;

    //定义数组来存放Fragment界面
    private Class fragmentArray[] = {ActualTransactionFragment.class, ProfessionalTransactionFragment
            .class, QuotationInformationFragment.class, PersonalCenterFragment.class};

//    //定义数组来存放按钮图片
//    private int mImageViewArray[] = {R.drawable.tab_home_btn, R.drawable.tab_message_btn, R.drawable.tab_selfinfo_btn,
//            R.drawable.tab_square_btn, R.drawable.tab_more_btn};

    //Tab选项卡的文字
    private String mTextviewArray[] = {"现货交易", "专业交易", "行情资讯", "个人中心"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTabHost mTabHost;
        layoutInflater = LayoutInflater.from(this);

        //实例化TabHost对象，得到TabHost
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        if (mTabHost == null) return;
        mTabHost.setup(this, getFragmentManager(), R.id.realtabcontent);

        //得到fragment的个数
        int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            //设置Tab按钮的背景
            // mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
        }


    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tabbar_item, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        // imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextviewArray[index]);

        return view;
    }
}
