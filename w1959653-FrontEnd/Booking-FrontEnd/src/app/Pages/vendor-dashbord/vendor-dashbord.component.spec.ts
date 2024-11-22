import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VendorDashbordComponent } from './vendor-dashbord.component';

describe('VendorDashbordComponent', () => {
  let component: VendorDashbordComponent;
  let fixture: ComponentFixture<VendorDashbordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VendorDashbordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VendorDashbordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
