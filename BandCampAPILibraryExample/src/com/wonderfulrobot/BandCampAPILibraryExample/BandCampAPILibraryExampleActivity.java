package com.wonderfulrobot.BandCampAPILibraryExample;

import java.util.List;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.wonderfulrobot.bandcampapi.core.BandcampException;
import com.wonderfulrobot.bandcampapi.core.BandcampService;
import com.wonderfulrobot.bandcampapi.core.BandcampServiceFactory;
import com.wonderfulrobot.bandcampapi.data.DiscographyItem;
import com.wonderfulrobot.bandcampapi.data.SearchResult;
import com.wonderfulrobot.bandcampapi.util.Util;

/**
 * 
 * Example of Communicating with bandcamp.com using the bandcamp-android-api library 
 * 
 * The Class contains an in-line Asynctask for accessing the API library on another (background) thread
 * 
 * @author mr_robot
 *
 */
public class BandCampAPILibraryExampleActivity extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
        setContentView(R.layout.ic_activity_list);
        handleIntent(getIntent());

    }
        

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_options_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()){
	        case R.id.search:
	        	onSearchRequested();
	            return true;
            case R.id.quit:
            	finish();
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }
    

    /**
     * 
     * Handle New Search for a running Activity
     * 
     */
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    
    /**
     * 
     * Handle an Intent - Invoke Search if required, or perform a default search 
     * (so we have some content to display)
     * 
     * @param intent
     */
    private void handleIntent(Intent intent) {        
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
        	String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);

        	doSearch(query);
        }
        else{
        	doSearch("Sola Rosa");
        }

    }
    
    private void showProgressBar(){
    	setProgressBarIndeterminate(true);
    	setProgressBarIndeterminateVisibility(true);
    }
    
    private void hideProgressBar(){
    	setProgressBarIndeterminate(false);
    	setProgressBarIndeterminateVisibility(false);
    }
    
    /**
     * 
     * Invokes a Search by Creating an Asynctask 
     * 
     * @param query - Name of the Artist to Search
     */
    private void doSearch(String query){
    	showProgressBar();
    	SearchBandcampTask searchTask = new SearchBandcampTask();
		searchTask.execute(new String[]{query});
    }
    
    /**
     * 
     * Binds a set of results to the List
     * 
     * Only creates the Adapter if required
     * 
     * @param result
     */
	private void setDiscoResult(List<DiscographyItem> result){
		hideProgressBar();
		if(result != null){
			if(getListAdapter() == null){
				ListAdapter mAdapter = new ArrayAdapter<DiscographyItem>(this, android.R.layout.simple_list_item_1,  result);
	
				setListAdapter(mAdapter);			
			}
			else{
				ArrayAdapter<DiscographyItem> adapter = ((ArrayAdapter<DiscographyItem>) getListAdapter());
				adapter.clear();
				for(DiscographyItem d : result){
					adapter.add(d);
				}
			}
		}
	}
	
	private void displayError(BandcampException exception) {
		Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT);
	}
    
    
	/**
	 *
	 * Simple Asynctask to invoke a search on Bandcamp.com on a background thread
	 * 
	 * Creates an instance of the {@link BandcampService} and executes a search on it
	 * 
	 * @author T804772
	 *
	 */
    private class SearchBandcampTask extends AsyncTask<String, String, List<DiscographyItem>>{
    	private BandcampException exception;
    	

    	private BandcampServiceFactory mServiceFactory;
    	private BandcampService mService;
    	
    	public SearchBandcampTask(){
    		mServiceFactory = new BandcampServiceFactory();
    	}
    	

    	public BandcampService getService(){
    		if(mService == null)
    			mService = mServiceFactory.buildService(Constants.key, Constants.secret);
    		return mService;
    	}
    	
        protected void onPostExecute(List<DiscographyItem> result) {
        	if(exception != null){
        		displayError(exception);
        	}
        	if(result != null){
        		setDiscoResult(result);

        	}
        }

        /**
         * 
         * Search Occurs in two steps:
         * 
         * 1. Searching for the Band (Artist) and returning the relevant Artist ID's
         * 2. Retrieving the Discographies for the Band (Artist) ID's
         *           
         * @param query - Band/Artist Name
         * @return
         * @throws BandcampException
         */
    	private List<DiscographyItem> search(String query) throws BandcampException{
    		SearchResult result = getService().search(query);
    		
    		long[] bandIds = Util.getBandIds(result);
    		List<DiscographyItem> disco = getService().getDiscography(bandIds);
    		return disco;
    	}
    	

		@Override
		protected List<DiscographyItem> doInBackground(String... params) {
				try {
					
					return search(params[0]);
				} catch (BandcampException e) {
					exception = e;
				}

			return null;
		}
    }
}