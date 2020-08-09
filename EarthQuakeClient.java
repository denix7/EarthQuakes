import java.util.*;
import edu.duke.*;

public class EarthQuakeClient {
    public EarthQuakeClient() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData,
    double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        
        for(QuakeEntry current : quakeData)
        {
            if(current.getMagnitude() > magMin)
                answer.add(current);
        }

        return answer;
    }

    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData,
    double distMax,
    Location from) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        
        for(QuakeEntry current : quakeData)
        {
            double dist = current.getLocation().distanceTo(from);
            if(dist < distMax)
            {
                answer.add(current);
            }
        }
   
        return answer;
    }
    
    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData, double minDepth, double maxDepth){
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        
        for(QuakeEntry current : quakeData)
        {
            if(current.getDepth() > minDepth && current.getDepth() < maxDepth)
                answer.add(current);
        }
        
        return answer;
    }

    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }

    }

    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        ArrayList<QuakeEntry> filters  = filterByMagnitude(list, 5);
        System.out.println("read data for "+ list.size() +" quakes");
        
        for(QuakeEntry current : filters)
        {
            System.out.println(current);
        }
        
        System.out.println("Found " + filters.size() + " quakes that match that criteria");
    }

    public void closeToMe(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for " + list.size() + " quakes");
        
        // This location is Durham, NC
        //Location city = new Location(35.988, -78.907);

        // This location is Bridgeport, CA
         Location city =  new Location(38.17, -118.82);

        // TODO
        int distMax = 1000000; //in metters
        ArrayList <QuakeEntry> filters = filterByDistanceFrom(list, distMax, city);
        
        for(QuakeEntry current : filters)
        {
            //System.out.println(current);
            double dist = current.getLocation().distanceTo(city);
            System.out.println(dist + " " + current.getInfo());
        }
        
        System.out.println("Found " + filters.size() + " quakes that match that criteria");
    }
    
    public void quakesOfDepth(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        
        System.out.println("read data for " + list.size() +" quakes");
        
        double min = -10000;
        double max = -5000;
        ArrayList<QuakeEntry> filters = filterByDepth(list, min, max);
        
        for(QuakeEntry qe : filters)
        {
            System.out.println(qe);
        }
        
        System.out.println("Found " + filters.size() + " quakes that match that criteria");
    }

    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }
    }
    
}
