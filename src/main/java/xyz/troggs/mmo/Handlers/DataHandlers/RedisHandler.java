package xyz.troggs.mmo.Handlers.DataHandlers;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import xyz.troggs.mmo.Main;

import java.net.URI;

public class RedisHandler {

    private Main main;

    public Jedis redis;

    public RedisHandler(Main main){
        this.main = main;
    }

    public void connectDatabase() throws Exception{
        redis = new Jedis(new URI("redis://default:qerl31T7alYIUXwQzShL@containers-us-west-13.railway.app:5541"));
        redis.connect();
        redis.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                //TODO do the jazz
            }
        }, "main");
    }
}
