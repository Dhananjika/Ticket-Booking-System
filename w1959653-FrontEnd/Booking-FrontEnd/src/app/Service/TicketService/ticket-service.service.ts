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

  private passwordSource = new BehaviorSubject<string | null>(
    sessionStorage.getItem("password"),
  );
  password$ = this.passwordSource.asObservable();

  constructor(private http: HttpClient) {
    this.BaseURL = environment.BASE_URL;
  }

  //Set Login username
  setUsername(username: string, password: string): void {
    this.usernameSource.next(username);
    sessionStorage.setItem("username", username);

    this.passwordSource.next(password);
    sessionStorage.setItem("password", password);
  }

  //Clean Login username
  cleanUsername(): void {
    this.usernameSource.next(null);
    sessionStorage.removeItem("username");

    this.passwordSource.next(null);
    sessionStorage.removeItem("password");
  }

  //Request Body - Post Request
  sendPostRequest(url: string, data: any): Observable<any> {
    const headers = { "Content-Type": "application/json" };
    const responseType = "text";
    return this.http.post(`${this.BaseURL}${url}`, data, {
      headers,
      responseType,
    });
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

  //Paramerters - Get Request
  sendGetRequestWithParams(url: string, params: HttpParams): Observable<any> {
    return this.http.get(`${this.BaseURL}${url}`, {
      params: params,
      responseType: "text" as "json",
    });
  }

  //Paramerters - Delete Request
  sendDeleteRequestWithParam(url: string, params: HttpParams): Observable<any> {
    return this.http.delete(`${this.BaseURL}${url}`, {
      params: params,
      responseType: "text" as "json",
    });
  }

  sendGetRequestWithParamsResponseEntity<T>(
    url: string,
    params: HttpParams,
  ): Observable<T> {
    return this.http.get<T>(`${this.BaseURL}${url}`, {
      params: params,
    });
  }
}
