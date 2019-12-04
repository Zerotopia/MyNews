package com.example.mynews.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.test.espresso.idling.CountingIdlingResource;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mynews.R;

public class MainActivity extends AppCompatActivity {

    //private Button mButton;
    //private RecyclerView mRecyclerView;
    //private EditText mEditText;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    //public static final String
    // private static final String APIKEY = "QXGOAUP24YZUfNIg4Drn3qaYAnpuV6dh";
    //private CompositeDisposable mCompositeDisposable = new CompositeDisposable();


    @Override
    protected void onStop() {
        super.onStop();
        //  mCompositeDisposable.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setElevation(0);
//        mToolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);

        mViewPager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tablayout);

        //mCount.increment();
        mViewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //  mCount.decrement();

        //   getSupportFragmentManager().beginTransaction()
        //         .add(R.id.fragment,new RecyclerFragment())
        //       .commit();

        //  mButton = findViewById(R.id.btn);
        //   mRecyclerView = findViewById(R.id.recyclerview);
        //    mEditText = findViewById(R.id.editText_rec);

        //  mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //  mRecyclerView.addItemDecoration(new CustomItemDecoration(this));
        //Drawable d = getResources().getDrawable(R.drawable.divider)
        //,
        //      DividerItemDecoration.VERTICAL));
        //  mButton.setOnClickListener(new View.OnClickListener() {
        //  @Override
        // public void onClick(View v) {


/***************** Essai avec Disposabble ***************************/
        //mCompositeDisposable.add(
        //   observable.subscribeOn(Schedulers.io())
        // .observeOn(AndroidSchedulers.mainThread())
        // .subscribe(new Consumer<Results>() {
        //   @Override
        //   public void accept(Results results) {
        //     //  Results results = response.body();
        //      // try {
        //       Log.d("ONCLICK", "accept: entre");
        //           ArrayList<Article> art = results.getResponse().getDocs();
        //           mRecyclerView.setAdapter(new ArticlesAdapter(art));
        //       //}
        //       //catch (RuntimeException e) {
        //        //   throw new Throwable("AAAA",e);
        //       //}
        //   }
        //));
        // mCompositeDisposable.dispose();

/************ Retrofit without rxjava ******************************************/
        //nyService.racine().enqueue(new Callback<String>() {
        //nyService.searchArticle(mEditText.getText().toString(), NYService.APIKEY).enqueue(new Callback<Results>() {
        //    @Override
        //    public void onResponse(Call<Results> call, Response<Results> response) {
        //        Results results = response.body();
        //        ArrayList<Article> art = results.getResponse().getDocs();
        //        mRecyclerView.setAdapter(new ArticlesAdapter(art));
//
        //    }
//
        //    @Override
        //    public void onFailure(Call<Results> call, Throwable t) {
        //        Log.d("FAIL", "FAIL");
//
        //    }
        //});
        //  }
        //});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_item:
                Intent intent_search = new Intent(this, SearchActivity.class);
                startActivity(intent_search);
                return true;
            case R.id.notif_item:
                Intent intent_notif = new Intent(this, NotificationActivity.class);
                startActivity(intent_notif);
                return true;
            case R.id.help_item:
                Toast.makeText(this,"HELP",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.about_item:
                Toast.makeText(this,"ABOUT",Toast.LENGTH_SHORT).show();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}
