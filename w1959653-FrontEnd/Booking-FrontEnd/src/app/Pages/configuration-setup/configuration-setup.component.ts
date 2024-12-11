import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { ConfigurationModule } from "src/app/module/configuration/configuration-module";
import { EventModule } from "src/app/module/event/event-module";
import { TicketServiceService } from "src/app/Service/TicketService/ticket-service.service";
import { FormsModule } from "@angular/forms";

declare var $: any;

@Component({
  selector: "app-configuration-setup",
  templateUrl: "./configuration-setup.component.html",
  styleUrls: ["./configuration-setup.component.css"],
})
export class ConfigurationSetupComponent implements OnInit {
  @Input() eventModule: EventModule;
  @Output() configurationSubmitted = new EventEmitter<{
    eventModule: EventModule;
    response: string;
  }>();
  warnMessage: string;
  successMessage: string;
  configuration: ConfigurationModule;

  constructor(private env: TicketServiceService) {
    this.warnMessage = null;
    this.successMessage = null;
    this.configuration = new ConfigurationModule();
  }

  ngOnInit() {
    this.configuration = new ConfigurationModule();
  }

  submitConfiguration() {
    this.configuration.eventId = this.eventModule.eventId;
    this.configuration.dispaly();
    const url = "configuration/submitConfiguration";
    this.env
      .sendPostRequest(url, this.configuration)
      .subscribe((response: string) => {
        if (response === "Successful") {
          this.eventModule.configurationStatus = "A";
          this.configurationSubmitted.emit({
            eventModule: this.eventModule,
            response: response,
          });
        } else {
          this.configurationSubmitted.emit({
            eventModule: this.eventModule,
            response: response,
          });
        }
      });
    this.configuration = new ConfigurationModule();
  }
}
