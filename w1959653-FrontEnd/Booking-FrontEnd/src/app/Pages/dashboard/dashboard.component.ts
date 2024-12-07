import { HttpParams } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { EventModule } from "src/app/module/event/event-module";
import { AuthService } from "src/app/Service/AuthService/auth-service.service";
import { TicketServiceService } from "src/app/Service/TicketService/ticket-service.service";

@Component({
  selector: "app-dashboard",
  templateUrl: "./dashboard.component.html",
  styleUrls: ["./dashboard.component.css"],
})
export class DashboardComponent implements OnInit {
  eventList: EventModule[] = [];
  buttonName: string;
  username: string;
  searchEventList: EventModule[] = [];
  searchTerm: string;
  isConfigurationSet: string;

  constructor(
    private env: TicketServiceService,
    private route: Router,
    private authService: AuthService,
  ) {
    this.buttonName = null;
    this.username = null;
    this.searchTerm = null;
    this.isConfigurationSet = "N";
  }

  ngOnInit() {
    if (this.authService.getUserRole() === "Vendor") {
      this.buttonName = "Add Tickets";
      const url = "event/getVendorEvents";
      this.env.username$.subscribe((username: string) => {
        this.username = username;
      });

      const params = new HttpParams().set("username", this.username);

      this.env.sendGetRequestWithParams(url, params).subscribe(
        (response) => {
          this.eventList = response;
          this.searchEventList = [...this.eventList];
        },
        (error) => {
          console.error("Error:", error);
        },
      );
    } else if (this.authService.getUserRole() === "Customer") {
      this.buttonName = "Buy Tickets";
      const url = "event/getEvents";
      this.env.sendGetRequestWithoutBodyOrParameters(url).subscribe(
        (response) => {
          this.eventList = response;
          this.searchEventList = [...this.eventList];
        },
        (error) => {
          console.error("Error:", error);
        },
      );
    }

    console.log(this.eventList);
  }

  onSearchTermChange() {
    this.searchEventList = this.eventList.filter(
      (event) =>
        event.eventName.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        event.eventLocation
          .toLowerCase()
          .includes(this.searchTerm.toLowerCase()) ||
        event.eventType.toLowerCase().includes(this.searchTerm.toLowerCase()),
    );
  }
}
