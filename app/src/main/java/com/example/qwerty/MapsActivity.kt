package com.example.qwerty


import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.qwerty.MapsActivity.MySingleton.myVariable
import com.example.qwerty.data.Point
import com.example.qwerty.data.PointViewModel
import com.getbase.floatingactionbutton.FloatingActionButton
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*
import kotlin.math.roundToInt


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val LOCATION_PERMISSION_REQUEST = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var def_pref: SharedPreferences
    private lateinit var def_prey: SharedPreferences
    private var valuable:Int = 0
    private lateinit var mPointViewModel:PointViewModel
    private  var qwer: Double = 0.0
    private var paq:Double= 0.0
   private lateinit var mine:MarkerOptions
    var fTrans: FragmentTransaction? = null
    var chbStack: CheckBox? = null
    object MySingleton {
        var myVariable: Point? = null
    }
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builde:Notification.Builder
    private val description = "Test notification"

    private val channelid = "com.example.qwerty"
    private lateinit var  location:Location
    private var dist:Float=10000.0f
    private var vastr:Float=500.0f


    private fun getLocationAccess() {
        if ((ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) && (
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
                )){
            mMap.isMyLocationEnabled = true
        } else
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST
            )
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION),
            LOCATION_PERMISSION_REQUEST
        )
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED &&  ActivityCompat.checkSelfPermission(
                            this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                mMap.isMyLocationEnabled = true
                getLocationUpdates()
                startLocationUpdates()
            }
            else {
                Toast.makeText(this, "User has not granted location access permission", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val btnsettings : FloatingActionButton = findViewById(R.id.settings)
        btnsettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        val savedbtn : FloatingActionButton = findViewById(R.id.saved)
        savedbtn.setOnClickListener {
               val intent1 = Intent(this, SavedActivity::class.java)
           startActivity(intent1)
        }




        mPointViewModel = ViewModelProvider(this).get(PointViewModel::class.java)
        val btnadd : FloatingActionButton = findViewById(R.id.adding)
        btnadd.setOnClickListener { insetDataToDatabase() }

    }


    private fun insetDataToDatabase(){

        val lattitude:Double = paq
        val longitude:Double = qwer
        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(this, Locale.getDefault())

        addresses = geocoder.getFromLocation(lattitude, longitude, 1) // Here 1 represent max location result to returned, by documents it recommended 1 to 5


        val address: String = addresses[0].getAddressLine(0);
        Log.i("tabr",address)
        val point:Point = Point(0,longitude,lattitude,address)
        mPointViewModel.addPoint(point)


    }





    private fun getLocationUpdates() {
        def_pref= PreferenceManager.getDefaultSharedPreferences(this)

        var str = def_pref.getString("reply","30sec")

        def_prey = PreferenceManager.getDefaultSharedPreferences(this)

        var str1:String? = def_prey.getString("reply1","500")

        if (str1 != null) {
            vastr = str1.toFloat()
        }
        Log.i("nmo",vastr.toString())






        locationRequest = LocationRequest()


        when (str.toString()) {
            "30" -> {


                locationRequest.interval = 30000
                locationRequest.fastestInterval = 29000

            }
            "60" -> {


                locationRequest.interval = 60000
                locationRequest.fastestInterval = 50000
            }
            "120" -> {


                locationRequest.interval = 120000
                locationRequest.fastestInterval = 110000
            }
            "300"->{


                locationRequest.interval = 300000
                locationRequest.fastestInterval = 290000
            }
            "600"->{


                locationRequest.interval = 600000
                locationRequest.fastestInterval = 590000
            }
            "900"->{


                locationRequest.interval = 900000
                locationRequest.fastestInterval = 890000
            }
            "1200"->{


                locationRequest.interval = 1200000
                locationRequest.fastestInterval = 1100000
            }
            "1500"->{


                locationRequest.interval = 1500000
                locationRequest.fastestInterval = 1400000
            }
            "1800"->{


                locationRequest.interval = 1800000
                locationRequest.fastestInterval = 1700000
            }
        }


        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY


        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult.locations.isNotEmpty()) {
                    location = locationResult.lastLocation
                    if (location != null) {
                        val latLng = LatLng(location.latitude, location.longitude)

                        paq = location.latitude
                        qwer = location.longitude
                        val markerOptions = MarkerOptions().position(latLng)
                        if(myVariable!=null) {
                            val anotherP = LatLng(myVariable!!.latitude, myVariable!!.longitide)
                            mine = MarkerOptions().position(anotherP)
                            Log.i("qazaz","yes")
                        }



                        mMap.clear()
                        if(myVariable!=null)
                            mMap.addMarker(MarkerOptions()
                                .position(LatLng( myVariable!!.latitude, myVariable!!.longitide))
                                .title("This is my title")
                                .snippet("and snippet")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
                        mMap.addMarker(markerOptions)

                        Not()


                    }
                }
            }
        }
    }
    fun calc() {
        var earthRadius = 6371000.0 //meters
        if (myVariable != null) {
            var dLat = Math.toRadians(myVariable!!.latitude - location.latitude)
            var dLng = Math.toRadians(myVariable!!.longitide - location.longitude)
            var a =
                (Math.sin(dLat / 2) * Math.sin(dLat / 2) + (Math.cos(Math.toRadians(location.latitude)) * Math.cos(
                    Math.toRadians(
                        myVariable!!.latitude
                    )
                ) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2)))
            var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
            dist = (earthRadius * c).toFloat()
            Log.i("nmo",dist.toString())
        }
    }

    fun Not(){
        calc()
        if (dist.roundToInt()<vastr) {

            val intent = Intent(this, MapsActivity::class.java)
            val pendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            notificationChannel =
                NotificationChannel(channelid, description, NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builde =
                Notification.Builder(this, channelid).setContentTitle("MyApp").setContentText("Yo almost here")
                    .setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent)
            notificationManager.notify(1234, builde.build())
        }


    }


    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        getLocationUpdates()
        startLocationUpdates()

    }


}
