import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { ForecastListComponent } from './components/forecast-list/forecast-list.component';
import { HttpClientModule } from '@angular/common/http';
import { ForecastService } from './services/forecast-service.service';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { GoogleMapsModule } from '@angular/google-maps';

//registerLocaleData(localeRu, 'ru');

@NgModule({
  declarations: [
    AppComponent,
    ForecastListComponent,
    HeaderComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    GoogleMapsModule,
  ],
  providers: [ForecastService],
  bootstrap: [AppComponent]
})
export class AppModule { }
