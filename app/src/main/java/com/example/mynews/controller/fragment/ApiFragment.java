package com.example.mynews.controller.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.idling.CountingIdlingResource;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mynews.R;
import com.example.mynews.controller.adapteur.ArticleAdapter;
import com.example.mynews.model.Article;
import com.example.mynews.model.Results;
import com.example.mynews.network.NYService;
import com.example.mynews.network.RetrofitClient;
import com.example.mynews.view.CustomItemDecoration;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

import static com.example.mynews.controller.fragment.AlertDialogFragment.ALERT_DIALOG_TAG;
import static com.example.mynews.controller.fragment.AlertDialogFragment.HTTP_ERROR_400;
import static com.example.mynews.controller.fragment.AlertDialogFragment.HTTP_ERROR_429;
import static com.example.mynews.controller.fragment.AlertDialogFragment.HTTP_ERROR_500;
import static com.example.mynews.controller.fragment.AlertDialogFragment.NO_RESULT_MAIN;
import static com.example.mynews.controller.fragment.AlertDialogFragment.NO_RESULT_SEARCH;
import static com.example.mynews.controller.fragment.AlertDialogFragment.OTHER_ERROR;
import static com.example.mynews.network.NYService.APIKEY;
import static com.example.mynews.network.NYService.NEWS_DESK;


/**
 * ApiFragment is the fragment that handle API calls of the application.
 */
public class ApiFragment extends Fragment implements Observer<Results> {

    /**
     * Tag used by newInstance to save the parameters passed in
     * the arguments of the constructor.
     */
    private final static String POSITION = "POSITION";
    private static final String PARAMETERS = "PARAMETERS";

    /**
     * We use the CountingIdlingResource system to ensure that
     * the espresso test works fine. The principe is to increment
     * a counter before the api call and decrement the counter when
     * the api call is done. Then espresso wait that the counter
     * has a good value to continue the instrumented test.
     */
    private static final String RXPROCESS = "RXPROCESS";
    private static CountingIdlingResource mCount = new CountingIdlingResource(RXPROCESS);

    /**
     * The recycler view that will display the list of the articles that will be return
     * by the NewYork Times API.
     */
    private RecyclerView mRecyclerView;

    /**
     * Variable that will be contained the position of the selected page in the ViewPager.
     */
    private int mPosition;

    /**
     * Array of String that will contained all arguments need for the research API.
     */
    private String[] mArguments;

    /**
     * Integer that contains the number of articles of a response of an API request.
     */
    private int mNumberOfArticles;

    /**
     * An integer whose the value depends of the kind of error that will occur.
     * It will be passed in argument of the AlertDialogFragment.newInstance() method.
     */
    private int mUsage;

    /**
     * Boolean set to true in the instrumented Test.
     * It determines if we use a Mock network service for test or
     * the true network service.
     */
    private static boolean mTestMode = false;

    /**
     * true if the response of the api request should be display, false otherwise.
     * For example the response of the api request send by the notification service
     * will be not display.
     */
    private boolean mViewMode = false;

    /**
     * This two boolean are for a proper management of the AlertDialog.
     * Since the ViewPager "precompute" the neighbour page, the AlertDialog
     * not appears in the good page. And since api calls are perform in
     * another thread, these booleans help too to solve asynchronous problems.
     */
    private boolean mOnNextDone = false;
    private boolean mOnErrorDone = false;

    @NonNull
    public static ApiFragment newInstance(int position, String[] parameters) {
        ApiFragment fragment = new ApiFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        args.putStringArray(PARAMETERS, parameters);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * In onCreateView we :
     * 1- set mViewMode to true.
     * 2- config the recyclerView (the recyclerView adapter will be set when
     * we receive the response of the API call.
     * 3- perform API call.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflaterLayout = inflater.inflate(R.layout.fragment_api, container, false);
        mRecyclerView = inflaterLayout.findViewById(R.id.fragment_api_recyclerview);

        mViewMode = true;

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new CustomItemDecoration(requireContext()));

        apiCall(Schedulers.io(), AndroidSchedulers.mainThread());

        return inflaterLayout;
    }

    /**
     * Method that perform the API call.
     * The schedulers are passed in argument to allow
     * use Scheduler.trampoline() in the Notification Worker.
     */
    public void apiCall(Scheduler subscribeScheduler, Scheduler observeScheduler) {
        Observable<Results> observable;
        initArguments();

        mCount.increment();

        observable = initObservable(mPosition, mArguments);
        observable.subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler)
                .unsubscribeOn(subscribeScheduler)
                .subscribe(this);
    }

    /**
     * Determines the good request for each page of the ViewPager.
     * If the variable position not correspond to a position in the
     * ViewPager, we send the research article request.
     *
     * @param position   of the selected page in the ViewPager.
     * @param parameters for the case of research article.
     * @return an observable to observe the response of the API call. The observable
     * depend of the position in the ViewPager.
     */
    private Observable<Results> initObservable(int position, String[] parameters) {
        Observable<Results> observable;
        NYService nyService = (mTestMode) ? RetrofitClient.getMock() : RetrofitClient.getInstance();

        if (position == 0) observable = nyService.topArticle(NEWS_DESK[0], APIKEY);
        else if (position == 1) observable = nyService.popularArticle(APIKEY);
        else if (position <= NEWS_DESK.length)
            observable = nyService.topArticle(NEWS_DESK[position - 1], APIKEY);
        else observable = nyService.searchArticle(parameters[0],
                    parameters[1],
                    parameters[2],
                    parameters[3],
                    APIKEY);
        return observable;
    }

    /**
     * We Initialize the two variables mPosition and mArguments from the
     * variable passed in arguments of the constructor newInstance.
     */
    private void initArguments() {
        if (getArguments() != null) {
            mPosition = getArguments().getInt(POSITION, -1);

            String[] args = getArguments().getStringArray(PARAMETERS);
            mArguments = new String[4];
            if (args != null) {
                for (int i = 0; i < 4; i++) {
                    if (i < args.length) {
                        if ((args[i] != null) && (!args[i].isEmpty())) mArguments[i] = args[i];
                        else mArguments[i] = null;
                    } else mArguments[i] = null;
                }
            }
        } else mPosition = -1;
    }

    /**
     * onSubscribe, onNext, onError, and onComplete implements the Observer interface.
     */
    @Override
    public void onSubscribe(Disposable d) {

    }

    /**
     * When we receive the response of the API call then
     * we can set mNumberOfArticles and if the result shoul be display (ViewMode)
     * we set the adapter for the recyclerView.
     * The method noArticleFoundDialog display an AlertDialog if there is no Article
     * return by the API (i.e. if mNumberOfArticle = 0).
     *
     * @param results It is the response return by the API call.
     */
    @Override
    public void onNext(Results results) {
        ArrayList<Article> articles = results.listOfArticle();
        mNumberOfArticles = articles.size();
        if (mViewMode) {
            noArticleFoundDialog(isResumed());
            mRecyclerView.setAdapter(new ArticleAdapter(articles));
        }
    }

    /**
     * If the API return an error, we call the function errorDialog that will
     * display an AlertDialog with the appropriate message.
     */
    @Override
    public void onError(Throwable e) {
        if (mViewMode) {
            if ((e instanceof HttpException))
                errorDialog(((HttpException) e).code(), isResumed());
            else errorDialog(OTHER_ERROR, isResumed());
        }
        mCount.decrement();
    }

    @Override
    public void onComplete() {
        mCount.decrement();
    }

    /**
     * The problem is that the ViewPager "pre-compute" the next page.
     * So if the AlertDialog is generate in position i, it can be appear
     * in position i-1.
     * So the methods that display the AlertDialog are call in onResume method
     * for that the AlertDialog is displayed only when the page is resumed.
     * <p>
     * BUT we are two cases :
     * case 1 : the page has been "pre-compute"
     * when the page is display, onResume is execute and AlertDialog is display.
     * case 2 : the page hasn't been "pre-compute"
     * the page is directly display, onResume is execute before the API call is perform.
     * In this case the AlertDialog is not directly display since the boolean
     * mOnNextDone (resp. mOnErrorDone) is false.
     * In this case the AlertDialog will be display when the method onNext (resp. onError)
     * will be execute.
     */
    @Override
    public void onResume() {
        noArticleFoundDialog(mOnNextDone);
        mOnNextDone = false;
        errorDialog(mUsage, mOnErrorDone);
        mOnErrorDone = false;
        super.onResume();
    }

    /**
     * Methods that displays an AlertDialog if mNumberOfArticle is zero.
     *
     * @param resume boolean to know if the page "is Resume" or not.
     */
    private void noArticleFoundDialog(boolean resume) {
        if ((mNumberOfArticles == 0) && resume) {
            mUsage = (mPosition == getResources().getStringArray(R.array.subjects).length) ? NO_RESULT_SEARCH : NO_RESULT_MAIN;
            generateAlertDialog();
        } else mOnNextDone = true;
    }

    /**
     * Methods that displays an AlertDialog if an error occur.
     *
     * @param error  An integer to determine the error code.
     * @param resume boolean to know if the page "is Resume" or not.
     */
    private void errorDialog(int error, boolean resume) {
        if (resume) {
            if (500 <= error) {
                mUsage = HTTP_ERROR_500;
            } else if (400 <= error) {
                mUsage = (error == 429) ? HTTP_ERROR_429 : HTTP_ERROR_400;
            } else mUsage = OTHER_ERROR;
            generateAlertDialog();
        } else mOnErrorDone = true;
    }

    /**
     * Method call in noArticleFoundDialog and errorDialog to generate an AlertDialog.
     */
    private void generateAlertDialog() {
        AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(mUsage);
        alertDialogFragment.setCancelable(false);
        alertDialogFragment.show(requireActivity().getSupportFragmentManager(), ALERT_DIALOG_TAG);
    }

    public int getNumberOfArticles() {
        return mNumberOfArticles;
    }

    /**
     * Method used in instrumented Test to set mTestMode to true.
     */
    public static void setTestMode(boolean testMode) {
        mTestMode = testMode;
    }

    public static CountingIdlingResource getCount() {
        return mCount;
    }
}