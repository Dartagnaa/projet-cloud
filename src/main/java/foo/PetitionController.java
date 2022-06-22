package foo;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.api.server.spi.auth.EspAuthenticator;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.PreparedQuery.TooManyResultsException;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;

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

public class PetitionController {

	@ApiMethod(name = "petitions", httpMethod = HttpMethod.GET)
	public List<Entity> petitions() {
		Query q = new Query("test");

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(q);
		List<Entity> result = pq.asList(FetchOptions.Builder.withLimit(100));
		return result;
	}

    @ApiMethod(name = "top", httpMethod = HttpMethod.GET)
	public List<Entity> top() {
		Query q = new Query("test").addSort("nbsignatures", SortDirection.DESCENDING);;

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(q);
		List<Entity> result = pq.asList(FetchOptions.Builder.withLimit(100));
		return result;
	}
    
    @ApiMethod(name = "mySignature", httpMethod = HttpMethod.GET)
	public List<Entity> mySignature(UserClass u) {

        String user = u.email;
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        
        Key userKey = new Entity("user", user).getKey();
        Filter keyUserFilter = new FilterPredicate("_key_", FilterOperator.EQUAL, userKey);
        Query qu = new Query("user").setFilter(keyUserFilter);

        PreparedQuery pq1 = datastore.prepare(qu);
        List<Entity> result = pq1.asList(FetchOptions.Builder.withDefaults());
        
        Entity u1 = new Entity("user");
        List<Entity> result1 = new ArrayList<Entity>();

        if(result.size() == 0) {
            try{
                u1 = datastore.get(userKey);
            }catch (EntityNotFoundException err) {
                    err.printStackTrace();
                }

            String pet = u1.getProperty("signed").toString();
            String[] list = pet.split(" ");  

            
            for (String id : list) {
                Key petitionKey = new Entity("test", id).getKey();
                Filter keyFilter = new FilterPredicate("_key_", FilterOperator.EQUAL, petitionKey);
                Query q = new Query("test").setFilter(keyFilter);
            
                PreparedQuery pq = datastore.prepare(q);
                result1.addAll(pq.asList(FetchOptions.Builder.withDefaults()));
            }
        }		
		return result1;
	}

	@ApiMethod(name = "postPetition", httpMethod = HttpMethod.POST)
	public Entity postPetition(Petition p) {
        Random r = new Random();
		int k = r.nextInt(50000);
		Entity e = new Entity("test", Long.MAX_VALUE-(new Date()).getTime()+":"+k);
		e.setProperty("categorie", p.categorie);
		e.setProperty("description", p.description);
		e.setProperty("titre", p.titre);
        e.setProperty("user", "Coco");
        e.setProperty("nbsignatures", p.nbsignatures);
		

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(e);
		return e;
	}

    @ApiMethod(name = "postUser", httpMethod = HttpMethod.POST)
	public Entity postUser(UserClass u) {

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Filter keyFilter = new FilterPredicate("email", FilterOperator.EQUAL, u.email);
        Query q = new Query("user").setFilter(keyFilter);
    
        PreparedQuery pq = datastore.prepare(q);
        List<Entity> result = pq.asList(FetchOptions.Builder.withDefaults());
        Entity e = new Entity("user",u.email);     
        
        if(result.size() == 0) {
            e.setProperty("name", u.name);
            e.setProperty("email", u.email);
            e.setProperty("signed", "");
            datastore.put(e);
        }
		return e;
		
	}

    //Signer une petition
    @ApiMethod(name = "signp", httpMethod = HttpMethod.POST)
    public Entity signp(Petition p ) throws UnauthorizedException {
        Entity response = new Entity("Response");
        if(p.user !=""){
            String user = p.user;
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            
            Key userKey = new Entity("user", user).getKey();
            Filter keyUserFilter = new FilterPredicate("_key_", FilterOperator.EQUAL, userKey);
            Query qu = new Query("user").setFilter(keyUserFilter);

            PreparedQuery pq1 = datastore.prepare(qu);
            List<Entity> result1 = pq1.asList(FetchOptions.Builder.withDefaults());

            Entity u1 = new Entity("user");

            Key petitionKey = new Entity("test", p.id).getKey();
            Filter keyFilter = new FilterPredicate("_key_", FilterOperator.EQUAL, petitionKey);
            Query q = new Query("test").setFilter(keyFilter);
        
            PreparedQuery pq = datastore.prepare(q);
            List<Entity> result = pq.asList(FetchOptions.Builder.withDefaults());
                    

            Entity e = new Entity("test");
                        
            
            if(result.size() == 0 && result1.size() == 0) {
                Transaction txn = datastore.beginTransaction();
                try{
                    e = datastore.get(petitionKey);
                    u1 = datastore.get(userKey);
                }catch (EntityNotFoundException err) {
                        err.printStackTrace();
                    }
                String s = u1.getProperty("signed").toString();
                s=s+" "+p.id;

                u1.setProperty("signed", s);
                datastore.put(u1); 


                long nbsignatures = (long) e.getProperty("nbsignatures");
                nbsignatures ++;
                e.setProperty("nbsignatures", nbsignatures);
                
                datastore.put(e);
                txn.commit();
                response.setProperty("status", "ok");
                return e;
            }else {
                response.setProperty("status", "nok");
                return response;
            }
        }else{
            response.setProperty("status", "nok");
            return response;
        }
            
            
    }
	
}
