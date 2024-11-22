import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";

@Injectable({
  providedIn: "root",
})
export class TicketServiceService {
  BaseURL: string;

  constructor() {
    this.BaseURL = environment.BASE_URL;
  }
}
