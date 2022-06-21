package foo;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.SortDirection;

import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.Filter;


@Api(name = "myApi",
    version = "v1",
    audiences = "448601331771-9pfrlvj1pgcnr8jdsrsil9c0bjcbpq92.apps.googleusercontent.com",
    clientIds = "448601331771-9pfrlvj1pgcnr8jdsrsil9c0bjcbpq92.apps.googleusercontent.com",
    namespace =
    @ApiNamespace(
        ownerDomain = "helloworld.example.com",
        ownerName = "helloworld.example.com",
        packagePath = "")
)

public class UserEndpoint {


    @ApiMethod(name = "postUser", httpMethod = HttpMethod.POST)
	public Entity postUser(UserClass u) {

		Entity e = new Entity("user"); // quelle est la clef ?? non specifié -> clef automatique
		e.setProperty("name", u.name);
		e.setProperty("email", u.email);
        e.setProperty("signed", "");
		

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(e);
		return e;
	}

    @ApiMethod(name = "signed", httpMethod = HttpMethod.POST)
	public Entity postUser(UserClass u,Petition p) {

		Entity e = new Entity("user"); // quelle est la clef ?? non specifié -> clef automatique
		e.setProperty("name", u.name);
		e.setProperty("email", u.email);
        e.setProperty("signed", u.signed.add(p.titre));
		

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(e);
		return e;
	} 
}