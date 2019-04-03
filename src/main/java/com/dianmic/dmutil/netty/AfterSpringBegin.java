package com.dianmic.dmutil.netty;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class AfterSpringBegin extends TimerTask implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // TODO Auto-generated method stub
        if (event.getApplicationContext().getParent() == null) {
            Timer timer = new Timer();
            timer.schedule(this, 0);
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }

}
