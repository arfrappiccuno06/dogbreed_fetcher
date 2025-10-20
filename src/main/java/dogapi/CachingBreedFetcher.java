package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher  {

    private int callsMade = 0;
    private final BreedFetcher fetcher;
    private final Map<String, List<String>> cache = new HashMap<>();

    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.fetcher = fetcher;
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedFetcher.BreedNotFoundException {
        // step 1 : check if subreeds of that breed are already stored in the cache
        if (cache.containsKey(breed)) {
            return cache.get(breed);
        }
        // if it dont have it then get it from breedfetcher and store in cache
        callsMade++;
        List<String> subbreeds = fetcher.getSubBreeds(breed);
        cache.put(breed, subbreeds);
        return subbreeds;

    }


    public int getCallsMade() {
        return callsMade;
    }

}