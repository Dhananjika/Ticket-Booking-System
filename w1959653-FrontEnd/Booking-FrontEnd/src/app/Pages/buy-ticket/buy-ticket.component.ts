import { HttpParams } from "@angular/common/http";
import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { ConfigurationModule } from "src/app/module/configuration/configuration-module";
import { EventModule } from "src/app/module/event/event-module";
import { AuthService } from "src/app/Service/AuthService/auth-service.service";
import { TicketServiceService } from "src/app/Service/TicketService/ticket-service.service";

declare var $: any;
@Component({
  selector: "app-buy-ticket",
  templateUrl: "./buy-ticket.component.html",
  styleUrls: ["./buy-ticket.component.css"],
})
export class BuyTicketComponent implements OnInit {
  @Input() eventModule: EventModule;
  @Input() configuration: ConfigurationModule;
  @Output() buyTicketExecute = new EventEmitter<string>();
  totalAmount: number;
  maxTicketCount: number;
  username: string;
  ticketCount: number;

  constructor(
    private env: TicketServiceService,
    private authService: AuthService,
  ) {
    this.configuration = new ConfigurationModule();
    this.totalAmount = 0;
    this.maxTicketCount = 0;
    this.username = null;
    this.ticketCount = 0;
  }

  ngOnInit() {}

  calculateTotalAmount(ticketCount: number): void {
    this.totalAmount = this.eventModule.eventNormalTicketPrice * ticketCount;
    this.ticketCount = ticketCount;
    console.log("Total Amount: " + this.totalAmount);
  }

  buyTicket() {
    const url = "ticketController/purchaseTicket";
    const params = new HttpParams()
      .set("ticketCount", this.ticketCount.toString())
      .set("eventID", this.eventModule.eventId.toString())
      .set("username", this.authService.getUserRole());

    this.env.sendPostRequestWithParams(url, params).subscribe((response) => {
      this.buyTicketExecute.emit(response);
    });

    this.ticketCount = 0;
    this.totalAmount = 0;
  }
}
