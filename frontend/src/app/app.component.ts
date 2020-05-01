import { Component } from '@angular/core';
import { RequestService } from './request.service';
import { UrlResolver, NullTemplateVisitor } from '@angular/compiler';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  countryData = { Country : null, NewConfirmed : null, TotalConfirmed : null, NewDeaths : null, TotalDeaths : null, NewRecovered : null, TotalRecovered : null ,Date : null};
  countries :any;

  constructor(private httpService:RequestService) {
    this.httpService.host = prompt('Please the backend gateway url');
  }

  ngOnInit(): void {
    this.httpService.getCountries().subscribe(
      resp=>{
          this.countries = resp   
      },
      err=>{
          alert( err.message);
      })
  }

  onChange(value) {
    this.httpService.getCountryData(value).subscribe(
      resp=>{
          let result = JSON.parse(JSON.stringify(resp));
          this.countryData.Country = result.Country 
          this.countryData.NewConfirmed = result.NewConfirmed 
          this.countryData.TotalConfirmed = result.TotalConfirmed 
          this.countryData.NewDeaths = result.NewDeaths 
          this.countryData.TotalDeaths = result.TotalDeaths 
          this.countryData.NewRecovered = result.NewRecovered 
          this.countryData.TotalRecovered = result.TotalRecovered 
          this.countryData.Date = result.Date 
          console.log(this.countryData.NewConfirmed)
      },
      err=>{
          alert( err.message);
      })
    
  }
}
