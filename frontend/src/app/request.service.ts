import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RequestService {
  host:string=""
  constructor(private http:HttpClient) {
  }

  getCountries(){
    return this.http.get(this.host+"countries/");
  }

  getCountryData(country){
    return this.http.get(this.host+"data/"+country);
  }
}

