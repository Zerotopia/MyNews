package com.example.mynews.controller;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import com.example.mynews.R;

public class MainActivity extends AppCompatActivity {

    //private Button mButton;
    //private RecyclerView mRecyclerView;
    //private EditText mEditText;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
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

        mViewPager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tablayout);

        mViewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

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

}
