import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { EventModule } from "src/app/module/event/event-module/event-module.module";
import { TicketServiceService } from "src/app/Service/TicketService/ticket-service.service";

@Component({
  selector: "app-event",
  templateUrl: "./event.component.html",
  styleUrls: ["./event.component.css"],
})
export class EventComponent implements OnInit {
  eventList: EventModule[];

  constructor(
    private env: TicketServiceService,
    private route: Router,
  ) {
    this.eventList = [];
  }

  ngOnInit() {}
}
