//package com.example.mynews;

import androidx.test.espresso.IdlingResource;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.internal.util.AndroidRunnerParams;
import androidx.test.runner.AndroidJUnitRunner;


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