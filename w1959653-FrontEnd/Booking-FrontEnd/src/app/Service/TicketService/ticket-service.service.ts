import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { BehaviorSubject } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class TicketServiceService {
  BaseURL: string;

  private usernameSource = new BehaviorSubject<string | null>(
    sessionStorage.getItem("username"),
  );
  username$ = this.usernameSource.asObservable();

  constructor(private http: HttpClient) {
    this.BaseURL = environment.BASE_URL;
  }

  //Set API full URL
  // setApi(url: string): string {
  //   return `${this.BaseURL}${url}`;
  // }

  //Set Login username
  setUsername(username: string): void {
    this.usernameSource.next(username);
    sessionStorage.setItem("username", username);
  }

  //Clean Login username
  cleanUsername(username: string): void {
    this.usernameSource.next(null);
    sessionStorage.removeItem("username");
  }

  //Request Body - Post Request
  sendPostRequest(url: string, data: any): Observable<any> {
    const headers = { "Content-Type": "application/json" };
    return this.http.post(`${this.BaseURL}${url}`, data, { headers });
  }

  //Parameters - Post Request
  sendPostRequestWithParams(url: string, params: HttpParams): Observable<any> {
    return this.http.post(`${this.BaseURL}${url}`, null, {
      params,
      responseType: "text" as "json",
    });
  }

  //Request Body + Parameters - Post Request
  sendPostRequestWithParamsAndBody(
    url: string,
    data: any,
    params: HttpParams,
  ): Observable<any> {
    return this.http.post(`${this.BaseURL}${url}`, data, {
      params,
      responseType: "text" as "json",
    });
  }

  //Without Body Or Parameters - Get Request
  sendGetRequestWithoutBodyOrParameters(url: string): Observable<any> {
    return this.http.get(`${this.BaseURL}${url}`);
  }

  //Paramerters For Array - Get Request
  sendGetRequestWithParamsForArray(
    url: string,
    params: HttpParams,
  ): Observable<any> {
    return this.http.get(`${this.BaseURL}${url}`, {
      params: params,
    });
  }

  //Paramerters For Array - Get Request
  sendGetRequestWithParams(url: string, params: HttpParams): Observable<any> {
    return this.http.get(`${this.BaseURL}${url}`, {
      params: params,
      responseType: "text" as "json",
    });
  }
}
