package com.example.castscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.MediaRouteActionProvider;
import android.support.v7.app.MediaRouteButton;
import android.support.v7.media.MediaControlIntent;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastMediaControlIntent;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {



    String APP_ID="F6D3E50B";
    CastDevice castDevice;


    private MediaRouter.Callback callback = new MediaRouter.Callback() {
        @Override
        public void onRouteSelected(MediaRouter router, MediaRouter.RouteInfo route) {
            super.onRouteSelected(router, route);


            castDevice=CastDevice.getFromBundle(route.getExtras());
            Toast.makeText(MainActivity.this,
                    "" +route.getConnectionState()+

                            ""+route.getDescription(), Toast.LENGTH_SHORT).show();


        }

        @Override
        public void onRouteUnselected(MediaRouter router, MediaRouter.RouteInfo route) {
            super.onRouteUnselected(router, route);
            castDevice=null;
        }

        @Override
        public void onRouteUnselected(MediaRouter router, MediaRouter.RouteInfo route, int reason) {
            super.onRouteUnselected(router, route, reason);
        }

        @Override
        public void onRouteAdded(MediaRouter router, MediaRouter.RouteInfo route) {
            super.onRouteAdded(router, route);
        }

        @Override
        public void onRouteRemoved(MediaRouter router, MediaRouter.RouteInfo route) {
            super.onRouteRemoved(router, route);
        }

        @Override
        public void onRouteChanged(MediaRouter router, MediaRouter.RouteInfo route) {
            super.onRouteChanged(router, route);
        }

        @Override
        public void onRouteVolumeChanged(MediaRouter router, MediaRouter.RouteInfo route) {
            super.onRouteVolumeChanged(router, route);
        }

        @Override
        public void onRoutePresentationDisplayChanged(MediaRouter router, MediaRouter.RouteInfo route) {
            super.onRoutePresentationDisplayChanged(router, route);
        }

        @Override
        public void onProviderAdded(MediaRouter router, MediaRouter.ProviderInfo provider) {
            super.onProviderAdded(router, provider);
        }

        @Override
        public void onProviderRemoved(MediaRouter router, MediaRouter.ProviderInfo provider) {
            super.onProviderRemoved(router, provider);
        }

        @Override
        public void onProviderChanged(MediaRouter router, MediaRouter.ProviderInfo provider) {
            super.onProviderChanged(router, provider);
        }
    };
    private android.support.v7.media.MediaRouter mediaRouter;
    private MediaRouteSelector mediaRouteSelector;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        mediaRouter = android.support.v7.media.MediaRouter.getInstance(this);

           /* mediaRouteSelector=new MediaRouteSelector.Builder().
            addControlCategory(CastMediaControlIntent.
                    categoryForCast(CastMediaControlIntent.DEFAULT_MEDIA_RECEIVER_APPLICATION_ID)).build();

*/



          mediaRouteSelector=new MediaRouteSelector.Builder().addControlCategory(MediaControlIntent.CATEGORY_REMOTE_PLAYBACK).
          addControlCategories(Collections.singleton(MediaControlIntent.CATEGORY_LIVE_AUDIO)).
          addControlCategories(Collections.singleton(MediaControlIntent.CATEGORY_LIVE_VIDEO)).build();


            try {
                Toast.makeText(this, "" + castDevice.getFriendlyName(), Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(this, ""+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
    }


    @Override
    protected void onStart() {

        mediaRouter.addCallback(mediaRouteSelector, callback, MediaRouter.CALLBACK_FLAG_PERFORM_ACTIVE_SCAN);

        super.onStart();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main, menu);


        MenuItem menuItem = menu.findItem(R.id.media_route_menu_item);



        MediaRouteButton mediaRouteButton=new MediaRouteButton(this);








/*

           MediaRouteActionProvider mediaRouteActionProvider = (MediaRouteActionProvider) MenuItemCompat.getActionProvider(menuItem);




MediaRouteActionProvider mediaRouteActionProvider=(MediaRouteActionProvider)MenuItemCompat.getActionProvider(menuItem);

*/

         mediaRouteButton.setRouteSelector(mediaRouteSelector);

        return true;


    }


    @Override
    protected void onStop() {
        mediaRouter.removeCallback(callback);

        super.onStop();



    }
}
