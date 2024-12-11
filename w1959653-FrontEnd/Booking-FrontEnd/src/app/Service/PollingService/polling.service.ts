import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { TicketServiceService } from "../TicketService/ticket-service.service";

@Injectable({
  providedIn: "root",
})
export class PollingService {
  private apiUrl = "ticketController/getTicketStatus";

  constructor(
    private http: HttpClient,
    private env: TicketServiceService,
  ) {}

  fetchData(eventID: number): Observable<number> {
    const params = new HttpParams().set("eventID", eventID.toString());
    return this.http.get<number>(`${this.env.BaseURL}${this.apiUrl}`, {
      params: params,
    });
  }
}
