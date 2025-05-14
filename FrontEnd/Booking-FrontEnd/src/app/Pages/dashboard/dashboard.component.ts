import { HttpParams } from "@angular/common/http";
import { Component, OnInit, Output, EventEmitter } from "@angular/core";
import { Router } from "@angular/router";
import { interval, Observable, Subscription } from "rxjs";
import { ConfigurationModule } from "src/app/module/configuration/configuration-module";
import { EventModule } from "src/app/module/event/event-module";
import { AuthService } from "src/app/Service/AuthService/auth-service.service";
import { PollingService } from "src/app/Service/PollingService/polling.service";
import { TicketServiceService } from "src/app/Service/TicketService/ticket-service.service";
import { switchMap } from "rxjs/operators";

declare var $: any;

@Component({
  selector: "app-dashboard",
  templateUrl: "./dashboard.component.html",
  styleUrls: ["./dashboard.component.css"],
})
export class DashboardComponent implements OnInit {
  private eventList: EventModule[] = [];
  private username: string;
  private searchEventList: EventModule[] = [];
  private searchTerm: string;
  private errorMessage: string;
  private eventModule: any;
  private warnMessage: string;
  private successMessage: string;
  private confirmMessage: string;
  private infoMessage: string;
  private event: EventModule;
  private configuration: ConfigurationModule;
  private ticketCount: number = 0;
  private subscription: Subscription;
  private subscriptions: Subscription[] = [];

  constructor(
    private env: TicketServiceService,
    private route: Router,
    private authService: AuthService,
    private polling: PollingService,
  ) {
    this.username = null;
    this.searchTerm = null;
    this.errorMessage = null;
    this.warnMessage = null;
    this.successMessage = null;
    this.confirmMessage = null;
    this.infoMessage = null;
    this.event = new EventModule();
    this.eventModule = new EventModule();
    this.configuration = new ConfigurationModule();
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

          console.log("List length: " + this.eventList.length);
          if (this.eventList && this.eventList.length > 0) {
            this.eventList.forEach((value) => {
              if (value.eventId) {
                this.startPolling(value.eventId).subscribe(
                  (response: number) => {
                    value.availableTickets = response; // Update availableTickets with the response
                    console.log("Returned value: " + value.availableTickets);
                  },
                  (error) => {
                    console.error(
                      `Error polling event ${value.eventId}:`,
                      error,
                    );
                  },
                );
              } else {
                console.warn("Invalid event data:", value);
              }
            });
          }
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

          console.log("List length: " + this.eventList.length);
          if (this.eventList && this.eventList.length > 0) {
            this.eventList.forEach((value) => {
              if (value.eventId) {
                this.startPolling(value.eventId).subscribe(
                  (response: number) => {
                    value.availableTickets = response; // Update availableTickets with the response
                    console.log("Returned value: " + value.availableTickets);
                  },
                  (error) => {
                    console.error(
                      `Error polling event ${value.eventId}:`,
                      error,
                    );
                  },
                );
              } else {
                console.warn("Invalid event data:", value);
              }
            });
          }
        },
        (error) => {
          console.error("Error:", error);
        },
      );
    }
    console.log(this.eventList);
  }

  startPolling(eventID: number): Observable<number> {
    return interval(1000).pipe(
      switchMap(() => this.polling.fetchData(eventID)),
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }

  addConfig(eventModule: EventModule) {
    this.event = eventModule;
    this.eventModule = eventModule;
    const modal = document.getElementById("configModal");
    if (modal) {
      $(modal).modal("show");
    }
  }

  startEvent(eventModule: EventModule) {
    this.event = eventModule;
    const url = "systemControl/startSystem";
    const params = new HttpParams().set(
      "eventID",
      eventModule.eventId.toString(),
    );

    this.env.sendPostRequestWithParams(url, params).subscribe((response) => {
      if (response === "System Started") {
        console.log(response);
        eventModule.systemStatus = "A";
        this.successMessage = response;
        const modal = document.getElementById("successMessage");
        if (modal) {
          $(modal).modal("show");
        }
      } else {
        console.log(response);
        this.errorMessage = response;
        const modal = document.getElementById("errorMessage");
        if (modal) {
          $(modal).modal("show");
        }
      }
    });
  }

  stopEvent(eventModule: EventModule) {
    this.event = eventModule;
    const url = "systemControl/stopSystem";
    const params = new HttpParams().set(
      "eventID",
      eventModule.eventId.toString(),
    );

    this.env.sendPostRequestWithParams(url, params).subscribe((response) => {
      if (response === "System Stopped") {
        console.log(response);
        eventModule.systemStatus = "I";
        this.successMessage = response;
        const modal = document.getElementById("successMessage");
        if (modal) {
          $(modal).modal("show");
        }
      } else {
        console.log(response);
        this.errorMessage = response;
        const modal = document.getElementById("errorMessage");
        if (modal) {
          $(modal).modal("show");
        }
      }
    });
  }

  addTickets(eventModule: EventModule) {
    this.event = eventModule;
    const url = "ticketController/addTicket";
    const params = new HttpParams()
      .set("eventID", eventModule.eventId.toString())
      .set("username", this.authService.getUserRole());

    this.env
      .sendPostRequestWithParams(url, params)
      .subscribe((response: any) => {
        if (response && response.includes("Tickets Added")) {
          this.successMessage = response;
          const modal = document.getElementById("successMessage");
          if (modal) {
            $(modal).modal("show");
          }
        } else if (response && response.includes("All Tickets Are Released")) {
          eventModule.systemStatus = "R";
          this.infoMessage = response;
          const modal = document.getElementById("infoMessage");
          if (modal) {
            $(modal).modal("show");
          }
        } else if (response && response.includes("On Hold")) {
          this.infoMessage = response;
          const modal = document.getElementById("infoMessage");
          if (modal) {
            $(modal).modal("show");
          }
        } else {
          this.confirmMessage = response;
          const modal = document.getElementById("confirmMessage");
          if (modal) {
            $(modal).modal("show");
          }
        }
      });
  }

  buyTickets(eventModule: EventModule) {
    this.event = eventModule;
    const url = "configuration/getConfiguration";
    const params = new HttpParams().set(
      "eventID",
      eventModule.eventId.toString(),
    );

    this.env
      .sendGetRequestWithParamsResponseEntity<ConfigurationModule>(url, params)
      .subscribe((response) => {
        this.configuration = response;
      });

    this.event = eventModule;
    this.eventModule = eventModule;
    const modal = document.getElementById("buyTicketModal");
    if (modal) {
      $(modal).modal("show");
    }
  }

  handleBuyTicket(result: string) {
    if (result && result.includes("Tickets Purchased")) {
      this.successMessage = result;
      const modal = document.getElementById("successMessage");
      if (modal) {
        $(modal).modal("show");
      }
    } else if (result && result.includes("Sold Out")) {
      this.event.systemStatus = "S";
      this.infoMessage = result;
      const modal = document.getElementById("infoMessage");
      if (modal) {
        $(modal).modal("show");
      }
    } else if (result && result.includes("Currently Unavailable")) {
      this.infoMessage = result;
      const modal = document.getElementById("infoMessage");
      if (modal) {
        $(modal).modal("show");
      }
    } else {
      this.confirmMessage = result;
      const modal = document.getElementById("confirmMessage");
      if (modal) {
        $(modal).modal("show");
      }
    }

    $("#buyTicketModal").modal("hide");
  }

  submitConfiguration() {
    console.log("Configuration submitted:");
    $("#configModal").modal("hide"); // Close modal after submission
  }

  handleConfigurationSubmission(event: {
    eventModule: EventModule;
    response: string;
  }) {
    if (event.eventModule.configurationStatus === "A") {
      const modal = document.getElementById("successMessage");
      if (modal) {
        this.successMessage = "Configuration submitted successfully!";
        $(modal).modal("show");
      }
      this.submitConfiguration();
    } else {
      const modal = document.getElementById("warnMessage");
      if (modal) {
        this.warnMessage = event.response;
        $(modal).modal("show");
      }
    }
  }

  confirm() {
    $("#confirmMessage").modal("hide");
    if (this.authService.getUserRole() === "Vendor") {
      const url = "ticketController/confirmAdding";
      const params = new HttpParams()
        .set("eventID", this.event.eventId.toString())
        .set("username", this.authService.getUserRole());

      this.env.sendPostRequestWithParams(url, params).subscribe((response) => {
        if (response && response.includes("Tickets Added")) {
          this.successMessage = response;
          const modal = document.getElementById("successMessage");
          if (modal) {
            $(modal).modal("show");
          }
        } else if (response && response.includes("All Tickets Are Released")) {
          this.infoMessage = response;
          const modal = document.getElementById("infoMessage");
          if (modal) {
            $(modal).modal("show");
          }
        } else if (response && response.includes("On Hold")) {
          this.infoMessage = response;
          const modal = document.getElementById("infoMessage");
          if (modal) {
            $(modal).modal("show");
          }
        }
      });
    } else if (this.authService.getUserRole() === "Customer") {
      const url = "ticketController/confirmPurchase";
      const params = new HttpParams()
        .set("eventID", this.event.eventId.toString())
        .set("username", this.authService.getUserRole());

      this.env.sendPostRequestWithParams(url, params).subscribe((response) => {
        if (response && response.includes("Tickets Purchased")) {
          this.successMessage = response;
          const modal = document.getElementById("successMessage");
          if (modal) {
            $(modal).modal("show");
          }
        } else if (response && response.includes("Sold Out")) {
          this.infoMessage = response;
          const modal = document.getElementById("infoMessage");
          if (modal) {
            $(modal).modal("show");
          }
        } else if (response && response.includes("Currently Unavailable")) {
          this.infoMessage = response;
          const modal = document.getElementById("infoMessage");
          if (modal) {
            $(modal).modal("show");
          }
        }
      });
    }
  }

  close() {
    $("#errorMessage").modal("hide");
  }

  closeWarn() {
    $("#warnMessage").modal("hide");
  }

  closeSuccess() {
    $("#successMessage").modal("hide");
  }

  confirmClose() {
    $("#confirmMessage").modal("hide");
  }

  closeInfo() {
    $("#infoMessage").modal("hide");
  }
}
