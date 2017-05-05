package gbnreddy.com.mapsexample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationSource.OnLocationChangedListener {

    Double lat1 = 17.451448;
    Double lon1 = 78.379281;
    Double lat2 = 17.499050;
    Double lon2 = 78.389796;
    private GoogleMap mMap;
    private LatLng latLng;
    private Circle circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        latLng = new LatLng(17.519732, 78.384766);
        Criteria crit = new Criteria();
        crit.setAccuracy(Criteria.ACCURACY_FINE);
        crit.setPowerRequirement(Criteria.POWER_LOW);
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        String provider = manager.getBestProvider(crit, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = manager.getLastKnownLocation(provider);
        latLng=new LatLng(location.getLatitude(),location.getLongitude());
        manager.requestLocationUpdates(provider,5,5, (LocationListener) this);
        /*if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 5, (LocationListener) this);
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
        } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5, 5, (LocationListener) this);
            Location location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
        }*/
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera

        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in circle")/*.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))*/
                .anchor(0.0f, 1.0f));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        addCircle(latLng);


        MarkerOptions markerOptions1 = new MarkerOptions().position(new LatLng(lat1, lon1))/*.title("test marker").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))*/
                .anchor(0.0f, 1.0f);
        if (checkMarker(circle, new LatLng(lat1, lon1))) {
            markerOptions1.title("marker1 inside circle");
        } else {
            markerOptions1.title("marker1 outside of circle");
        }

        mMap.addMarker(markerOptions1);


        MarkerOptions markerOptions2 = new MarkerOptions().position(new LatLng(lat2, lon2))/*.title("test marker").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))*/
                .anchor(0.0f, 1.0f);
        if (checkMarker(circle, new LatLng(lat2, lon2))) {
            markerOptions2.title("marker2 inside circle");
        } else {
            markerOptions2.title("marker2 outside of circle");
        }

        mMap.addMarker(markerOptions2);
        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    @Override
    public void onLocationChanged(Location location) {
        mMap.clear();
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions1 = new MarkerOptions().position(new LatLng(lat1, lon1))/*.title("test marker").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))*/
                .anchor(0.0f, 1.0f);
        if (checkMarker(circle, new LatLng(lat1, lon1))) {
            markerOptions1.title("marker1 inside circle");
        } else {
            markerOptions1.title("marker1 outside of circle");
        }

        mMap.addMarker(markerOptions1);


        MarkerOptions markerOptions2 = new MarkerOptions().position(new LatLng(lat2, lon2))/*.title("test marker").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))*/
                .anchor(0.0f, 1.0f);
        if (checkMarker(circle, new LatLng(lat2, lon2))) {
            markerOptions2.title("marker2 inside circle");
        } else {
            markerOptions2.title("marker2 outside of circle");
        }

        mMap.addMarker(markerOptions2);

    }

    public void addCircle(LatLng laLn) {
        circle = mMap.addCircle(new CircleOptions()
                .center(laLn)
                .radius(2500)
                // Radius of the circle


                // Border color of the circle
                .strokeColor(Color.BLACK)

                // Fill color of the circle
                //.fillColor(0x30ff0000)
                .fillColor(0x150000FF)

                // Border width of the circle
                .strokeWidth(2));

    }

    public boolean checkMarker(Circle c, LatLng laln) {
        float[] distance = new float[2];
        Location.distanceBetween(laln.latitude, laln.longitude,
                circle.getCenter().latitude, circle.getCenter().longitude, distance);
        if (distance[0] > circle.getRadius()) {
            return false;
        } else {
            return true;
        }
    }

}
