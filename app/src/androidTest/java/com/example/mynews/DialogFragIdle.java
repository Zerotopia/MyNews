//package com.example.mynews;

import android.os.AsyncTask;
import android.support.test.espresso.IdlingResource;
import android.support.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import android.support.test.internal.util.AndroidRunnerParams;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.AndroidJUnitRunner;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.junit.runners.model.InitializationError;

import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;




//public class RxTestRunner extends AndroidJUnitRunner {
//
//    @Override
//    public void onStart() {
//        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
//            @Override
//            public Scheduler apply(Scheduler scheduler) throws Exception {
//                return Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR);
//            }
//        });
//        super.onStart();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        RxJavaPlugins.reset();
//    }
//}
//
//public class DialogFragIdle implements IdlingResource {
//
//private FragmentManager mFragmentManager;
//private String mTag;
//
//
//    @Override
//    public String getName() {
//        return "dialog fragment idling resource";
//    }
//
//    @Override
//    public boolean isIdleNow() {
//       if  (manager.findFragmentByTag(tag) == null)
//        if (idle) {
//            resourceCallback?.onTransitionToIdle()
//        }
//        return idle
//        return false;
//    }
//
//    @Override
//    public void registerIdleTransitionCallback(ResourceCallback callback) {
//
//    }
//}