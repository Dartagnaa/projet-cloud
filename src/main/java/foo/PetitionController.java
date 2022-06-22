package foo;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
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
    
    @ApiMethod(name = "Categorie", httpMethod = HttpMethod.GET)
	public List<Entity> Categorie(Petition p) {
		Filter keyFilter = new FilterPredicate("categorie", FilterOperator.EQUAL,p.categorie);
        Query q = new Query("test").setFilter(keyFilter);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(q);
		List<Entity> result = pq.asList(FetchOptions.Builder.withLimit(100));
		return result;
	}

	@ApiMethod(name = "postPetition", httpMethod = HttpMethod.POST)
	public Entity postPetition(Petition p) {

		Entity e = new Entity("test"); // quelle est la clef ?? non specifié -> clef automatique
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

		Entity e = new Entity("user"); // quelle est la clef ?? non specifié -> clef automatique
		e.setProperty("name", u.name);
		e.setProperty("email", u.email);
        e.setProperty("signed", "");
		

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(e);
		return e;
	}

    //Signer une petition
    @ApiMethod(name = "signp", httpMethod = HttpMethod.POST)
    public Entity signp(Petition p ) throws UnauthorizedException {
        /*
        //if (user == null) {
				//throw new UnauthorizedException("Invalid credentials");
        //    }
        Entity isPetitionSign = new Entity("Petition","true");
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Key pkey = KeyFactory.createKey("test", "Océane");
        //Key ukey = KeyFactory.createKey("user", user.getEmail());
        Entity ent = new Entity("test","hello");
        //Entity util = new Entity("user");
        Transaction txn=datastore.beginTransaction();

        try {
				ent = datastore.get(pkey);
               //util = datastore.get(ukey);
				int nb =  Integer.parseInt(ent.getProperty("nbsignatures").toString());
			    //ArrayList<String> signatories = (ArrayList<String>) util.getProperty("signed"); //récupération des signature de l'utilisateur.

			    //if(!signatories.contains(ent.getProperty("id").toString())) {
			    //	signatories.add(ent.getProperty("id").toString());
				    ent.setProperty("nbsignatures", nb + 1 );
                //    util.setProperty("signed", signatories );
				//    isPetitionSign = new Entity("Petition","false");
				//   }
				datastore.put(ent);
                //datastore.put(util);
				txn.commit();
			} catch (EntityNotFoundException e) {
					e.printStackTrace();
				}
			  finally {
				if (txn.isActive()) {
				    txn.rollback();
				  }
			}
			return ent;
            */
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            
            Key petitionKey = new Entity("test", p.id).getKey();
            Filter keyFilter = new FilterPredicate("_key_", FilterOperator.EQUAL, petitionKey);
            Query q = new Query("test").setFilter(keyFilter);
    
            PreparedQuery pq = datastore.prepare(q);
            List<Entity> result = pq.asList(FetchOptions.Builder.withDefaults());
          
            Entity response = new Entity("Response");
            Entity e = new Entity("test");

            if(result.size() == 0) {
                Transaction txn = datastore.beginTransaction();
                try{
                    e = datastore.get(petitionKey);
                }catch (EntityNotFoundException err) {
                        err.printStackTrace();
                    }              
    
                long nbSignataire = (long) e.getProperty("nombreSignature");
                nbSignataire ++;
                e.setProperty("nombreSignature", nbSignataire);
                datastore.put(e); 
                txn.commit();
                response.setProperty("status", "ok");
                return e;
            }else {
                response.setProperty("status", "nok");
                return response;
            }
            
    }
	
}
