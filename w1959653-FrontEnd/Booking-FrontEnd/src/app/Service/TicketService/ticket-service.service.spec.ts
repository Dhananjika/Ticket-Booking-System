import { TestBed, inject } from "@angular/core/testing";

import { TicketServiceService } from "../../Service/TicketService/ticket-service.service";

describe("TicketServiceService", () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TicketServiceService],
    });
  });

  it("should be created", inject(
    [TicketServiceService],
    (service: TicketServiceService) => {
      expect(service).toBeTruthy();
    },
  ));
});
