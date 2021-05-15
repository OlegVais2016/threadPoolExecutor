package com.weisser.threadPoolExecutor.controller;

import com.weisser.threadPoolExecutor.callable.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@RestController
public class WebController {

    @Autowired
    ThreadPoolTaskExecutor threadPool;

    @RequestMapping("/process")
    public String process(){

        String msg = "";
        List<Future<String>> futureList = new ArrayList<>();
        for(int threadNumber = 0; threadNumber < 5; threadNumber ++){
            Worker callableTask = new Worker(String.valueOf(threadNumber));
            Future<String> result = threadPool.submit(callableTask);
            futureList.add(result);
        }

        for(Future<String> future: futureList){
            try {
                msg += future.get() + "#####";
            } catch (Exception e){}
        }

        return msg;
    }
}
