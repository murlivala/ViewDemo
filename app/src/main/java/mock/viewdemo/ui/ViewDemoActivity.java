package mock.viewdemo.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.design.widget.TabLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import mock.viewdemo.Constants;
import mock.viewdemo.R;

public class ViewDemoActivity extends AppCompatActivity {

    private final String TAG = ViewDemoActivity.class.getSimpleName();
    @BindView(R.id.layout_bottom) LinearLayout bottomLayout;
    @BindView(R.id.btn_item1) Button btn1;
    @BindView(R.id.btn_item2) Button btn2;
    @BindView(R.id.btn_item3) Button btn3;
    @BindView(R.id.btn_item4) Button btn4;
    @BindView(R.id.btn_item5) Button btn5;
    @BindView(R.id.tv_item) TextView tvItem;
    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.pager) ViewPager mViewPager;
    @BindView(R.id.view_top) HorizontalScrollView horizontalScrollView;
    private int bColor = Color.WHITE;
    private int lastItemState = Constants.INVALID_STATE;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private boolean isRotatedOrRefreshed = false;

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    private Context mSelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view);
        mSelf = this;
        if(null != savedInstanceState){
            isRotatedOrRefreshed = true;
        }
        setupViews();
    }

    private void addListeners(){
        if(null == mViewPager || null == tabLayout){
            Log.d(TAG,"addListeners() called without initialising the views.");
            return;
        }

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(!isRotatedOrRefreshed){//check to suppress unwanted toast i.e. when it comes here without tapping or swiping
                    Toast.makeText(mSelf,""+tab.getText(),Toast.LENGTH_SHORT).show();
                }
                isRotatedOrRefreshed = false;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });
    }

    private void setupViews(){
        ButterKnife.bind(this);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), Constants.PAGE_COUNT);
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);
        for (int index = Constants.BEGIN; index < Constants.PAGE_COUNT; index++) {
            tabLayout.addTab(tabLayout.newTab());
        }
        tabLayout.setupWithViewPager(mViewPager);
        addListeners();
    }

    private void refreshViews(){
        isRotatedOrRefreshed = true;
        bColor = Color.WHITE;
        lastItemState = Constants.INVALID_STATE;
        bottomLayout.setBackgroundColor(bColor);
        tvItem.setText(R.string.tv_item);
        mViewPager.setCurrentItem(Constants.BEGIN);
        horizontalScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_LEFT);
            }
        }, 100L);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            /**
             * It will request from server
             */
            refreshViews();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onPress(View v){
        String msg = "";
        switch (v.getId()){
            case R.id.btn_item1:
                msg = btn1.getText() + " Pressed !";
                tvItem.setText(msg);
                lastItemState = 1;
                break;
            case R.id.btn_item2:
                msg = btn2.getText() + " Pressed !";
                tvItem.setText(msg);
                lastItemState = 2;
                break;
            case R.id.btn_item3:
                msg = btn3.getText() + " Pressed !";
                tvItem.setText(msg);
                lastItemState = 3;
                break;
            case R.id.btn_item4:
                msg = btn4.getText() + " Pressed !";
                tvItem.setText(msg);
                lastItemState = 4;
                break;
            case R.id.btn_item5:
                msg = btn5.getText() + " Pressed !";
                tvItem.setText(msg);
                lastItemState = 5;
                break;
            case R.id.btn_blue:
                bottomLayout.setBackgroundColor(Color.BLUE);
                bColor = Color.BLUE;
                break;
            case R.id.btn_green:
                bottomLayout.setBackgroundColor(Color.GREEN);
                bColor = Color.GREEN;
                break;
            case R.id.btn_red:
                bottomLayout.setBackgroundColor(Color.RED);
                bColor = Color.RED;
                break;
            default:
        }
    }

    /**
     *  save data/state when orientation changes
     */

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putInt(Constants.COLOR,bColor);
        savedInstanceState.putInt(Constants.ITEM_STATE,lastItemState);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     *  restore data/states after orientation gets changed
     */

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        bColor = savedInstanceState.getInt(Constants.COLOR,Constants.INVALID_STATE);
        lastItemState = savedInstanceState.getInt(Constants.ITEM_STATE,Constants.INVALID_STATE);
        if(Constants.INVALID_STATE != bColor)
            bottomLayout.setBackgroundColor(bColor);

        switch(lastItemState){
            case 1:
                tvItem.setText(btn1.getText()+" Pressed !");
                break;
            case 2:
                tvItem.setText(btn2.getText()+" Pressed !");
                break;
            case 3:
                tvItem.setText(btn3.getText()+" Pressed !");
                break;
            case 4:
                tvItem.setText(btn4.getText()+" Pressed !");
                break;
            case 5:
                tvItem.setText(btn5.getText()+" Pressed !");
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        int pageCount;
        public SectionsPagerAdapter(FragmentManager fm, int numOfPages) {
            super(fm);
            pageCount = numOfPages;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            return SampleFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show total pages.
            return pageCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Frag-"+(position+1);
        }
    }

}
