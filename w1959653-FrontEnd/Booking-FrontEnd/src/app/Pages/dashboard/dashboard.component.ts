import { HttpParams } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Key } from "protractor";
import { EventModule } from "src/app/module/event/event-module";
import { AuthService } from "src/app/Service/AuthService/auth-service.service";
import { TicketServiceService } from "src/app/Service/TicketService/ticket-service.service";

declare var $: any;

@Component({
  selector: "app-dashboard",
  templateUrl: "./dashboard.component.html",
  styleUrls: ["./dashboard.component.css"],
})
export class DashboardComponent implements OnInit {
  eventList: EventModule[] = [];
  username: string;
  searchEventList: EventModule[] = [];
  searchTerm: string;
  passingValue: any;

  constructor(
    private env: TicketServiceService,
    private route: Router,
    private authService: AuthService,
  ) {
    this.username = null;
    this.searchTerm = null;
  }

  ngOnInit() {
    if (this.authService.getUserRole() === "Vendor") {
      const url = "event/getVendorEvents";
      this.env.username$.subscribe((username: string) => {
        this.username = username;
      });

      const params = new HttpParams().set("username", this.username);

      this.env.sendGetRequestWithParamsForArray(url, params).subscribe(
        (response) => {
          this.eventList = response;
          this.searchEventList = [...this.eventList];
        },
        (error) => {
          console.error("Error:", error);
        },
      );
    } else if (this.authService.getUserRole() === "Customer") {
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

  addTickets(eventModule: EventModule) {
    if (eventModule.configurationStatus === "I") {
      const modal = document.getElementById("configModal");
      if (modal) {
        this.passingValue = { key: eventModule.eventId };
        $(modal).modal("show");
      }
    }
  }

  submitConfiguration() {
    console.log("Configuration submitted:");
    $("#addTicketsModal").modal("hide"); // Close modal after submission
  }
}
