import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";

declare var $: any;

@Component({
  selector: "app-configuration-setup",
  templateUrl: "./configuration-setup.component.html",
  styleUrls: ["./configuration-setup.component.css"],
})
export class ConfigurationSetupComponent implements OnInit {
  @Input() eventId: number;

  constructor() {}

  ngOnInit() {}
}
