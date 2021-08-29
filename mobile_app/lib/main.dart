import 'package:flutter/material.dart';
import 'package:geolocator/geolocator.dart';
import 'Dart:async';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      
      home: LocationApp(),
    );
  }
}


class LocationApp extends StatefulWidget {
  const LocationApp({ Key? key }) : super(key: key);

  @override
  _LocationAppState createState() => _LocationAppState();
}

class _LocationAppState extends State<LocationApp> {

  var locationMensseger =  "";

  void getCurrentLocation() async {
    var position = await Geolocator.getCurrentPosition(desiredAccuracy: LocationAccuracy.high);
    var lastPosition =  await Geolocator.getLastKnownPosition();
    print(lastPosition);
    setState(() {
      locationMensseger = "$position.latitude, $position.longitude";
    }); 
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Uniguia"),
      ),
      body: Column(children: [
        Icon(
          Icons.location_on,
          size: 46.0,
          color: Colors.blue,
        ),
        SizedBox(
          height: 10.0,
        ),
        Text(
          "Get user Location",
          style: TextStyle(
            fontSize: 26.0,
            fontWeight: FontWeight.bold,
          ),
        ),
        SizedBox(
          height: 20.0,
        ),
        Text(
          locationMensseger
        ),
        FlatButton(
          onPressed: (){
            const oneSec = const Duration(seconds:1);
            new Timer.periodic(oneSec, (Timer t) => getCurrentLocation());
          },
          child: Text("Get Current Location"),
          color: Colors.blue[800],
          ),
          SizedBox(
            height: 20.0,
          )
      ],),
    );
  }
}
