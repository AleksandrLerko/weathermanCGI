import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MapMarker } from '@angular/google-maps';
import { GoogleMap } from '@angular/google-maps/google-map/google-map';
import { ICurrentForecast } from "src/app/domain/ICurrentForecast";
import { IForecast } from "src/app/domain/IForecast";
import { IQuery } from 'src/app/domain/IQuery';
import { ForecastService } from 'src/app/services/forecast-service.service';
import { Loader } from "@googlemaps/js-api-loader"
// import { REACTIVE_FORM_DIRECTIVES } from '@angular/forms'

@Component({
  selector: 'app-forecast-list',
  templateUrl: './forecast-list.component.html',
  styleUrls: ['./forecast-list.component.css']
})
export class ForecastListComponent implements OnInit {
  public forecastApi1!: IForecast;
  public forecastApi2!: IForecast;


  public form!: FormGroup;
  public formSubmitted = false;

  public hideHourlyApi1Block = false;
  public hideHourlyApi2Block = false;

  public days: number = 0;
  public dayRange: number[];

  public center!: google.maps.LatLngLiteral;
  public markers = [] as any;

  constructor(private forecastService: ForecastService, private formBuilder: FormBuilder) {
    this.dayRange = new Array(1, 2, 3);
  }

  ngOnInit(): void {
    console.log("ngOnInit")
    this.buildForm();
  }

  buildForm() {
    this.form = this.formBuilder.group({
      location: [null, Validators.required],
      days: 1
    });
  }

  async getData(event: any): Promise<void> {
    console.log("getData")
    event.preventDefault();
    this.formSubmitted = true;

    if (this.form.valid) {
      let location = this.form.value.location;
      let days = this.form.value.days;
      let apiUrl1 = `http://localhost:8080/api/weatherapi/${location}/${days}`;
      let apiUrl2 = `http://localhost:8080/api/visualcrossing/${location}/${days}`;

      this.forecastService.findAll(apiUrl1).subscribe(data => {
        let forecastObject: IForecast = JSON.parse(JSON.stringify(data));
        let forecast1: IForecast = {
          location: forecastObject.location,
          currentForecast: forecastObject.currentForecast,
          dailyForecast: forecastObject.dailyForecast,
        };
        console.log(typeof forecastObject.location.latitude);
        this.center = { lat: forecastObject.location.latitude, lng: forecastObject.location.longitude };
        this.forecastApi1 = forecast1;
        this.removeMarker();
        this.addMarker();
      });

      this.forecastService.findAll(apiUrl2).subscribe(data => {
        let forecastObject: IForecast = JSON.parse(JSON.stringify(data));
        let forecast2: IForecast = {
          location: forecastObject.location,
          currentForecast: forecastObject.currentForecast,
          dailyForecast: forecastObject.dailyForecast,
        }
        forecast2.dailyForecast.pop();
        this.forecastApi2 = forecast2;
      });
      this.days = this.form.value.days;
    }
  }

  click(event: google.maps.MapMouseEvent) {
    let coords = event.latLng?.toJSON();
    this.markers = [];
    this.center = {lat: coords?.lat as number, lng: coords?.lng as number}
    this.addMarker();
    this.form.controls['location'].setValue(coords?.lat + "," + coords?.lng);
  }


  addMarker() {
    this.markers.push({
      position: {
        lat: this.center.lat,
        lng: this.center.lng,
      },
    })
  }
  removeMarker(){
    this.markers = [];
  }

}
