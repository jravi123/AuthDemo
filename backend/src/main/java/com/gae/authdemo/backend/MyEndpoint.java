/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.gae.authdemo.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.users.User;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */


@Api(name = "myApi", version = "v1", scopes = {Constants.EMAIL_SCOPE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE}, namespace = @ApiNamespace(ownerDomain = "backend.authdemo.gae.com", ownerName = "backend.authdemo.gae.com", packagePath = ""))
public class MyEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String greet, User user) {
        MyBean response = new MyBean();

        String message = "Hi ";

        if (user == null) {
            message += "anonymous!";
        } else {
            message += user.getEmail();
        }

        response.setData(message);

        return response;
    }

}
